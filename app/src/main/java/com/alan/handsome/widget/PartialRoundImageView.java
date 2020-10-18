package com.alan.handsome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;

import com.alan.handsome.utils.DensityUtil;


public class PartialRoundImageView extends AppCompatImageView { //圆角弧度
    private int radius = DensityUtil.dp2px(8);
    private float[] rids = {radius,radius,0f,0f,0.0f,0.0f,radius,radius};

    public PartialRoundImageView(Context context) {
        super(context);
    }

    public PartialRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PartialRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        //绘制圆角imageview
        path.addRoundRect(new RectF(0,0,w,h),rids,Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
