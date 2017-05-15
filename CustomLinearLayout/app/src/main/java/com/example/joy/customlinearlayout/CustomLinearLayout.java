package com.example.joy.customlinearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.joy.customlinearlayout.model.DeleteModel;
import com.example.joy.customlinearlayout.model.NormalModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by joy on 2017/5/8.
 */

public class CustomLinearLayout extends ViewGroup {

    //状态
    private int mState;

    //单击标识位
    public static final int STATE_PRESS_CLICK = 0X001;

    //长按标识位
    public static final int STATE_PRESS_LONG_CLICK = 0x002;

    //删除模式
    private boolean mIsDeleteMode = false;

    //长按
    private LongPressRunnable mLongPressRunnable = new LongPressRunnable();

    //View的个数
    private int mCount = 0;

    //是否已经Layout
    private boolean mHasLayout = false;


    private CustomLinearLayout mCustomLinearLayout;

    private int mDownX, mDownY;


    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCustomLinearLayout = this;
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;

        //记录每一行的宽度，width不断取最大宽度
        int lineWidth = 0;

        //每一行的高度，累加至height
        int lineHeight = 0;

        int cCount = getChildCount();

        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {
            CustomButton child = (CustomButton) getChildAt(i);

            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);// 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec
                .EXACTLY) ? sizeHeight : height);

    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<CustomButton>> mAllViews = new ArrayList<>();

    /**
     * 记录所有的View
     */
    private List<CustomButton> mViews = new ArrayList<>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        // 存储每一行所有的childView
        List<CustomButton> lineViews = new ArrayList<>();
        int cCount = getChildCount();
        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++) {
            CustomButton child = (CustomButton) getChildAt(i);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<>();
            }

            //如果不需要换行，则累加

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);


            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                CustomButton child = lineViews.get(j);


                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();


                child.layout(lc, tc, rc, bc);


                // 计算view的数量,记录每一个控件的位置,由于onLayout会调用两次，所以使用标识符time只添加一次View
                if (!mHasLayout) {
                    //将每一个绘制图标的坐标，margin值保存
                    child.saveCoordinates(lc, tc, rc, bc, mCount, lp);
                    mViews.add(child);
                    Log.e("yz", "count==" + mCount);
                    mCount++;

                }
                left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }

            left = 0;
            top += lineHeight;
        }
        mCount = 0;
        mHasLayout = true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                //记录手指按下的坐标
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();

                if (mIsDeleteMode) {
                    //删除模式
                    DeleteModel.deleteModel(mDownX, mDownY, getChildCount(), mViews, mCustomLinearLayout);
                } else {
                    //普通模式
                    NormalModel.actionDown(mDownX, mDownY, mViews, mCustomLinearLayout);
                }

                Log.e("yz", "GP_ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:

                Log.e("yz", "GP_ACTION_MOVE");

                break;

            case MotionEvent.ACTION_UP:

                //记录手指抬起的坐标
                int upX = (int) event.getX();
                int upY = (int) event.getY();

                //手指抬起放弃长按计时
                removeCallbacks(mLongPressRunnable);

                if (mState == STATE_PRESS_CLICK) {

                    NormalModel.actionUp(mDownX, mDownY, upX, upY, getChildCount(), mViews, mCustomLinearLayout);
                }
                Log.e("yz", "GP_ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 长按触发，1秒进入该方法，循环View通知CustomButton进入删除模式
     */
    private class LongPressRunnable implements Runnable {
        @Override
        public void run() {

            //进入删除模式
            enterDeleteModel();
        }
    }


    /**
     * 拦截点击事件，让点击事件在ViewGroup中处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    //取消删除模式
    public void cancelDeleteModel() {

        mIsDeleteMode = false;
        invalidate();
        listener.onCustomCancelDeleteModel();

    }

    //进入删除模式
    public void enterDeleteModel() {

        mState = STATE_PRESS_LONG_CLICK;

        for (CustomButton customButton : mViews) {
            customButton.longPress();
        }

        mIsDeleteMode = true;
        invalidate();
        listener.onCustomEnterDeleteModel();
    }

    //范围内点击
    public void isClick() {
        mState = STATE_PRESS_CLICK;
        postDelayed(mLongPressRunnable, 1000);
    }

    public void deleteSucceed(int i) {
        listener.onCustomDeleteSucceed(i);

    }

    //范围内单击
    public void isSingleClick(int i) {
        listener.onCustomSingleClick(i);

    }

    //定义一个接口对象Listener
    private OnCustomClickListener listener;


    //获得接口对象的方法。
    public void setOnCustomListener(OnCustomClickListener listener) {
        this.listener = listener;


    }

    //定义一个接口
    public interface OnCustomClickListener {

        void onCustomSingleClick(int i);

        void onCustomEnterDeleteModel();

        void onCustomCancelDeleteModel();

        void onCustomDeleteSucceed(int i);
    }


}
