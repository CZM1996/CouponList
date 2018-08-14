package com.cj.couponlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 自定义边缘凹凸的优惠券效果view
 */
public class CouponDisplayView extends LinearLayout {

    private Paint mPaint;
    /**
     * 半径
     */
    private float radius = 12;
    /**
     * 圆间距
     */
    private float gap = 12;
    /**
     * 圆数量
     */
    private int circleNum;
    private float remain;

    public CouponDisplayView(Context context) {
        this(context, null);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CouponDisplayView, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CouponDisplayView_radius_size:
                    radius = a.getDimensionPixelSize(R.styleable.CouponDisplayView_radius_size, 12);
                    break;
                case R.styleable.CouponDisplayView_gap:
                    gap = a.getDimensionPixelSize(R.styleable.CouponDisplayView_gap, 12);
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(ContextCompat.getColor(context, R.color.common_background));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain == 0) {
            //计算不整除的剩余部分
            remain = (int) (h - gap) % (2 * radius + gap);
        }
        circleNum = (int) ((h - gap) / (2 * radius + gap));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            float y = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            canvas.drawCircle(-4, y, radius, mPaint);
            canvas.drawCircle(getWidth() + 4, y, radius, mPaint);
        }
    }

    public void setPaintColor(String color) {
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    // 获取优惠券齿轮背景
    public static LayerDrawable getBacgroudDrawable(Context context, String color1, String color2, int offset) {

        // prepare
        int roundRadius = 15; // px
        float[] outerR = new float[]{roundRadius, roundRadius, roundRadius, roundRadius, roundRadius, roundRadius, roundRadius, roundRadius};
        int fillColor1 = Color.parseColor(color1);
        int fillColor2 = Color.parseColor(color2);

        // layer1
        RoundRectShape rrs1 = new RoundRectShape(outerR, null, null);
        ShapeDrawable item1 = new ShapeDrawable(rrs1);
        item1.getPaint().setColor(fillColor1);

        // layer2
        RoundRectShape rrs2 = new RoundRectShape(outerR, null, null);
        ShapeDrawable item2 = new ShapeDrawable(rrs2);
        item2.getPaint().setColor(fillColor2);

        // layers
        Drawable[] layers = new Drawable[2];
        layers[0] = item1;
        layers[1] = item2;
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        // 第二层设置offset
        int top = dip2px(context, offset);
        layerDrawable.setLayerInset(1, 0, top, 0, 0);
        return layerDrawable;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
