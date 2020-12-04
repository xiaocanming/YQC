package com.example.yqc.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.yqc.R;

public class MyBreatheView extends View implements ValueAnimator.AnimatorUpdateListener {
    private int mDiffusionColor;
    private int mCoreColor;
    private float mCoreRadius;
    private float mMaxWidth;
    private int color;
    private boolean mIsDiffuse;
    private Paint mPaint;
    private float mFraction;
    private ValueAnimator mAnimator;
    private long HEART_BEAT_RATE;
    private Handler mHandler;
    private float circleX;
    private float circleY;
    private int durrarion;
    private Runnable heartBeatRunnable;
    private boolean polling;

    public MyBreatheView(Context context) {
        this(context, (AttributeSet)null);
    }

    public MyBreatheView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBreatheView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDiffusionColor = Color.parseColor("#303F9F");
        this.mCoreColor = Color.parseColor("#FF4081");
        this.mCoreRadius = 30.0F;
        this.mMaxWidth = 40.0F;
        this.color = 255;
        this.mIsDiffuse = false;
        this.HEART_BEAT_RATE = 1000L;
        this.durrarion = 2000;
        this.heartBeatRunnable = new Runnable() {
            public void run() {
                MyBreatheView.this.connect();
                polling=!polling;
                MyBreatheView.this.mHandler.postDelayed(this, MyBreatheView.this.HEART_BEAT_RATE);
            }
        };
        this.init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyBreatheView);
        if (null != array) {
            this.durrarion = array.getInt(R.styleable.MyBreatheView_duration, 2000);
            array.recycle();
        }

        this.mPaint = new Paint(1);
        this.mPaint.setAntiAlias(true);
        this.mAnimator = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F}).setDuration((long)this.durrarion);
        this.mAnimator.addUpdateListener(this);
        if (null == this.mHandler) {
            this.mHandler = new Handler();
        }

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.circleX = (float)(w / 2);
        this.circleY = (float)(h / 2);
    }

    public MyBreatheView setCoreRadius(float radius) {
        this.mCoreRadius = radius;
        return this;
    }

    public MyBreatheView setDiffusMaxWidth(float width) {
        this.mMaxWidth = width;
        return this;
    }

    public MyBreatheView setCoordinate(float x, float y) {
        this.circleX = x;
        this.circleY = y;
        return this;
    }

    public MyBreatheView setDiffusColor(int color) {
        this.mDiffusionColor = color;
        return this;
    }

    public MyBreatheView setCoreColor(int coreColor) {
        this.mCoreColor = coreColor;
        return this;
    }

    public MyBreatheView setInterval(long durataion) {
        this.HEART_BEAT_RATE = durataion;
        return this;
    }

    public void invalidate() {
        if (this.hasWindowFocus()) {
            super.invalidate();
        }

    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            this.invalidate();
        }

    }

    public MyBreatheView onStart() {
        this.mHandler.removeCallbacks(this.heartBeatRunnable);
        this.mHandler.post(this.heartBeatRunnable);
        return this;
    }

    public void onStop() {
        this.mIsDiffuse = false;
        this.mHandler.removeCallbacks(this.heartBeatRunnable);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mIsDiffuse) {
            //设置闪烁圆颜色
            if(polling){
                this.mPaint.setColor(Color.parseColor("#FDFDFD"));
            }else {
                this.mPaint.setColor(Color.parseColor("#1295D9"));
            }
            this.mPaint.setAlpha((int)((float)this.color - (float)this.color * this.mFraction));
            canvas.drawCircle(this.circleX, this.circleY, this.mCoreRadius + this.mMaxWidth * this.mFraction, this.mPaint);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setAlpha(255);
            //设置中心圆颜色
            if(polling){
                this.mPaint.setColor(Color.parseColor("#FDFDFD"));
            }else {
                this.mPaint.setColor(Color.parseColor("#1295D9"));
            }
            canvas.drawCircle(this.circleX, this.circleY, this.mCoreRadius, this.mPaint);
        }

        this.invalidate();
    }

    public void connect() {
        this.mIsDiffuse = true;
        this.mAnimator.start();
        this.invalidate();
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.mFraction = (Float)valueAnimator.getAnimatedValue();
    }
}
