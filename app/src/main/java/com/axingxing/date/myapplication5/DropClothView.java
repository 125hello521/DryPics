package com.axingxing.date.myapplication5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DropClothView extends View {
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBeforeBitmap, mBackBitmap;
    private Canvas mCanvas;
    private int mLastX, mLastY;
    private int mScreenW, mScreenH;
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);


    public DropClothView(Context context) {
        super(context);
    }

    public DropClothView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPath = new Path();
        mScreenW = ScreenUtil.getScreenW(context);
        mScreenH = ScreenUtil.getScreenH(context);

        init();
    }

    public DropClothView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(80);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        //背后图片
        mBackBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.after6);
        mBackBitmap = Bitmap.createScaledBitmap(mBackBitmap, mScreenW, mScreenH, false);
        //前面图片
        mBeforeBitmap = Bitmap.createBitmap(mScreenW, mScreenH, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBeforeBitmap);
        mCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pre6),null,
                new Rect(0, 0, mScreenW,mScreenH), null);

    }

    private void drawPath(){
        mPaint.setXfermode(xfermode);
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBackBitmap, 0, 0,null);
        drawPath();
        canvas.drawBitmap(mBeforeBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if(dx > 3 || dy > 3){
                    mPath.lineTo(x,y);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}

