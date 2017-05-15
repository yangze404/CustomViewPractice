package com.example.joy.customlinearlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import com.example.joy.customlinearlayout.data.Coordinates;
import com.example.joy.customlinearlayout.model.DrawableToBitmap;


/**
 * Created by joy on 2017/4/25.
 */

public class CustomButton extends View {

    //图标名称
    private String mTextName ;

    //图标名称尺寸
    private  int  mTextSize ;

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
    private boolean mIsVisible = false;

    private Rect mSrcRect, mDestRect, mDeleteSrcRect, mDeleteDestRect, mBounds;

    //文字的宽度
    private int mTextWidth;

    //图标绘制的坐标
    private Coordinates mCoordinates=new Coordinates() ;

    //删除图标绘制的坐标
    private Coordinates mDeleteCoordinates=new Coordinates() ;


    //整个自定义的宽高
    private  int mWidth,mHeight;

    private Resources mResources=getResources();

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
//        Log.e("yang","CustomButton");



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("yang","onMeasure");
        mDeleteHeight = mDeleteBitmap.getHeight();
        mDeleteWidth = mDeleteBitmap.getWidth();

        mBmpWidth = mBitmap.getWidth();
        mBmpHeight = mBitmap.getHeight();

        // 获取文字的宽和高
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mTextName, 0, mTextName.length(), mBounds);

        mTextWidth = mBounds.width();


         mWidth = MeasureSpec.getSize(widthMeasureSpec);
         mHeight= MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(mWidth, mHeight+mDeleteHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("yang","onLayout");
        mSrcRect.set(0, 0, mBmpWidth, mBmpHeight); //代表要绘制的bitmap 区域
        mDestRect.set(mWidth/2-mBmpWidth/2, mDeleteHeight, mWidth/2+mBmpWidth/2, mBmpHeight + mDeleteHeight); //代表的是要将bitmap 绘制在屏幕的什么地方

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

        Log.e("yang","onDraw");
        //绘制图标
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);

        //根据是否删除模式选择动态显示删除图标
        if (mIsVisible) {

            canvas.drawBitmap(mDeleteBitmap, mDeleteSrcRect, mDeleteDestRect, mPaint);
        }

        //绘制图标下方的文字
//        canvas.drawText(mTextName, mBmpWidth / 2 - mTextWidth / 2, mBmpHeight + mBounds.height() + mDeleteHeight, mPaint);

        canvas.drawText(mTextName,  mWidth / 2-mTextWidth/2,mBmpHeight + mDeleteHeight+mBounds.height(), mPaint);
        Log.e("yz","mTextName.length=="+mTextName.length());
    }


    /**
     * 初始化图片
     */
    private void initView(Context context,AttributeSet attrs) {

        //获取自定义属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomButton);

        //自定义属性获取名称
        mTextName=typedArray.getString(R.styleable.CustomButton_customText);

        //自定义属性获取名称大小
        mTextSize=typedArray.getDimensionPixelSize(R.styleable.CustomButton_customTextSize,5);

        //自定义属性获取图片
        Drawable mDrawable=typedArray.getDrawable(R.styleable.CustomButton_customIcon);

        typedArray.recycle();


        // 初始化画笔、Rect
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBounds = new Rect();

        mSrcRect = new Rect();

        mDestRect = new Rect();

        mDeleteDestRect = new Rect();

        mDeleteSrcRect = new Rect();

        //实现通过XML设置图片
        mBitmap= DrawableToBitmap.drawableToBitmap(mDrawable);

        mDeleteBitmap = BitmapFactory.decodeResource(mResources, R.drawable.custom_ic_delete);

    }



    public void longPress() {
        mIsVisible = true;
        invalidate();
    }

    public void shortPress() {
        mIsVisible = false;
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

    //实现在代码中设置图标
    public void  setIcon( int res_id){

        mBitmap = BitmapFactory.decodeResource(mResources, res_id);
        invalidate();
    }

    //实现在代码中设置图标
    public void  setText( String textName){

        mTextName=textName;
        requestLayout();
        invalidate();
    }

    //实现在代码中设置文字大小
    public void  setTextSize( int size){

        mTextSize=size;
        requestLayout();
        invalidate();
    }
}
