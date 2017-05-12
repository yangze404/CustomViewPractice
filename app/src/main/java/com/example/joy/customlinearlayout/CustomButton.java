package com.example.joy.customlinearlayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import com.example.joy.customlinearlayout.data.Coordinates;


/**
 * Created by joy on 2017/4/25.
 */

public class CustomButton extends View {

    private String TEXT_NAME = "支付宝";

    // 定义画笔
    private Paint mPaint;

    //图标
    private Bitmap mBitmap;

    //删除图标
    private Bitmap mDeleteBitmap;

    //删除图标宽高
    private int mDeleteWidth = 0;
    private int mDeleteHeight = 0;

    //图标宽高
    private int mBmpWidth = 0;
    private int mBmpHeight = 0;

    //删除图标是否可见
    private boolean isVisible = false;

    private Rect mSrcRect, mDestRect, mDeleteSrcRect, mDeleteDestRect, mBounds;

    public int textWidth, mLeft, mTop;

    //图标绘制的坐标
    private Coordinates mCoordinates=new Coordinates() ;

    //删除图标绘制的坐标
    private Coordinates mDeleteCoordinates=new Coordinates() ;


    public CustomButton(Context context) {
        super(context);
        initView();

    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mDeleteHeight = mDeleteBitmap.getHeight();
        mDeleteWidth = mDeleteBitmap.getWidth();

        mBmpWidth = mBitmap.getWidth();
        mBmpHeight = mBitmap.getHeight();

        // 获取文字的宽和高
        mPaint.setTextSize(20);
        mPaint.getTextBounds(TEXT_NAME, 0, TEXT_NAME.length(), mBounds);

        textWidth = mBounds.width();

        // 计算左边位置
        mLeft = (getWidth() - mBmpWidth) / 2;

        // 计算上边位置
        mTop = (getHeight() - mBmpHeight) / 2;

        //  MeasureSpec.EXACTLY：父视图希望子视图的大小应该是specSize中指定的。
        //  MeasureSpec.AT_MOST：子视图的大小最多是specSize中指定的值，也就是说不建议子视图的大小超过specSize中给定的值。
        //  MeasureSpec.UNSPECIFIED：我们可以随意指定视图的大小。

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mSrcRect.set(0, 0, mBmpWidth, mBmpHeight); //代表要绘制的bitmap 区域
        mDestRect.set(0, mDeleteHeight, mBmpWidth, mBmpHeight + mDeleteHeight); //代表的是要将bitmap 绘制在屏幕的什么地方

        mDeleteSrcRect.set(0, 0, mDeleteWidth, mDeleteHeight); //代表要绘制的bitmap 区域
        mDeleteDestRect.set(0, 0, mDeleteWidth, mDeleteHeight); //代表的是要将bitmap 绘制在屏幕的什么地方


        // 把删除图标的坐标记录下来

        mDeleteCoordinates.left=0;
        mDeleteCoordinates.top=0;
        mDeleteCoordinates.right=mDeleteWidth ;
        mDeleteCoordinates.bottom=mDeleteHeight;




    }

    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);


        //绘制图标
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);

        //根据是否删除模式选择动态显示删除图标
        if (isVisible) {

            canvas.drawBitmap(mDeleteBitmap, mDeleteSrcRect, mDeleteDestRect, mPaint); //delete
        }

        //绘制图标下方的文字
        canvas.drawText(TEXT_NAME, mBmpWidth / 2 - textWidth / 2, mBmpHeight + mBounds.height() + mDeleteHeight,
                mPaint);

    }


    /**
     * 初始化
     */
    private void initView() {
        // 初始化画笔、Rect
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBounds = new Rect();

        mSrcRect = new Rect();

        mDestRect = new Rect();

        mDeleteDestRect = new Rect();

        mDeleteSrcRect = new Rect();


        Resources res = getResources();

        mBitmap = BitmapFactory.decodeResource(res, R.drawable.custom_ic_alipay_50px);

        mDeleteBitmap = BitmapFactory.decodeResource(res, R.drawable.custom_ic_delete);
    }



    public void longPress() {
        isVisible = true;
        invalidate();
    }

    public void shortPress() {
        isVisible = false;
        invalidate();
    }

    public void saveCoordinates(int left, int top, int right, int bottom,int count,MarginLayoutParams params) {
        mCoordinates.left = left;
        mCoordinates.top = top;
        mCoordinates.right = right;
        mCoordinates.bottom = bottom;
        mCoordinates.count=count;
        mCoordinates.mParams=params;
    }

    public  Coordinates getDeleteCoordinates (){
        return mDeleteCoordinates ;
    }

    public Coordinates getCoordinates() {
       return  mCoordinates  ;
    }

}
