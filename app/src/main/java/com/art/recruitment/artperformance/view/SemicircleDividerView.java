package com.art.recruitment.artperformance.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.art.recruitment.artperformance.R;


public class SemicircleDividerView extends View {
    private Context mContext;
    /**
     * 圆直径
     */
    private int mCircleDiameter;
    private int circlePaddingTop;
    //填充宽度
    private int mStrokeWidth;

    public SemicircleDividerView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public SemicircleDividerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public SemicircleDividerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void init(AttributeSet attrs) {
        int defaultDiameter = mContext.getResources().getDimensionPixelOffset(R.dimen.demin_48);
        int defaultStrokeWidth = mContext.getResources().getDimensionPixelOffset(R.dimen.demin_1);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.SemicircleDividerView);
        circlePaddingTop = typedArray.getDimensionPixelSize(R.styleable.SemicircleDividerView_circlePaddingTop, 0);
        mCircleDiameter = typedArray.getDimensionPixelSize(R.styleable.SemicircleDividerView_circleDiameter, defaultDiameter);
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.SemicircleDividerView_strokeWidth, defaultStrokeWidth);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        int strokeWidth = mStrokeWidth;

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#f4f4f4"));
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        int halfOfWidth = getMeasuredWidth() / 2;
        int halfOfCircleWidth = mCircleDiameter / 2;
        int y = halfOfCircleWidth;
        Path path = new Path();
        path.moveTo(0, y);
        path.lineTo(halfOfWidth - halfOfCircleWidth, y);
        path.moveTo(halfOfWidth + halfOfCircleWidth, y);
        path.lineTo(getMeasuredWidth(), y);
        canvas.drawPath(path, paint);

        //在指定区域内绘制一个160度的圆弧
        RectF rectF = new RectF(halfOfWidth - halfOfCircleWidth
                , circlePaddingTop + strokeWidth / 2
                , halfOfWidth + halfOfCircleWidth
                , circlePaddingTop + mCircleDiameter + strokeWidth / 2);
        path.addArc(rectF, -12, -158);
        canvas.drawPath(path, paint);
    }
}
