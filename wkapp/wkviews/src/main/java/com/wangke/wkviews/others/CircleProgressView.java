package com.wangke.wkviews.others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.wangke.wkviews.R;


/**
 * Created by zhaopy on 2017/2/23.
 */

public class CircleProgressView extends View {

    private String title = "规范评价";//显示的文字
    private float progress = 0; //显示的进度
    private int mLayoutSize = 100;//整个控件的尺寸（方形）
    public int mColor;//主要颜色
    public int mColorBackground;
    public int mColorShadow;

    private float now = 0; //当前的进度
    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mColor = context.getResources().getColor(R.color.colorPrimary);
        mColorBackground = context.getResources().getColor(R.color.white);
        mColorShadow = context.getResources().getColor(R.color.shadow);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        mLayoutSize = Math.min(widthSpecSize,heightSpecSize);
        setMeasuredDimension(mLayoutSize, mLayoutSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Shader mShader = new RadialGradient(mLayoutSize/2 , mLayoutSize/2, mLayoutSize/2,new int[]{Color.BLACK ,Color.DKGRAY , Color.GRAY ,Color.WHITE }
                ,null,
                Shader.TileMode.REPEAT);


        //画阴影
        Paint paint3 = new Paint();
        paint3.setShader(mShader);
        canvas.drawCircle(mLayoutSize/2, mLayoutSize/2, mLayoutSize/2 * 0.99f, paint3);

        int centre = getWidth()/2; //获取圆心的x坐标
        float radius = mLayoutSize/2 * 0.95f; //圆环的半径


        //画白圆
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL); //设置空心
        radius = mLayoutSize/2 * 0.925f; //圆环的半径
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环


        //画第一个扇形
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL); //

        float boder = mLayoutSize/2 - radius;
        RectF rectF = new RectF(boder, boder ,
                mLayoutSize - boder,  mLayoutSize - boder);
        canvas.drawArc(rectF,-90,360 * (now / 100),true,paint);
        //画第二个内心圆，如果想让线粗一点，把0.98f改小一点
        boder = boder * 1.2f;
        rectF = new RectF(boder, boder ,
                mLayoutSize - boder,  mLayoutSize - boder);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(centre, centre, radius * 0.98f, paint); //画出圆

        //画顶头的小球，0.05f是小球的半径
        paint.setColor(mColor);

        float r = radius * 0.98f;
        canvas.drawCircle(r + (float)(r * Math.sin(Math.toRadians(360 * (now / 100)))) + boder,
                r + boder - (float)(r  * Math.cos(Math.toRadians(360 * (now / 100)))) ,
                radius * 0.05f , paint);

        String per = now + "%";

        //写文字，1.1f控制百分比字的Y轴位置

        //写百分比
        paint.measureText(per);
        paint.setColor(mColor);
        paint.setTextSize(mLayoutSize/5);//控制文字大小
        canvas.drawText(per,centre - paint.measureText(per)/2,centre - (paint.ascent()+ paint.descent()) * 1.1f ,paint);

        //写标题
        paint.setColor(Color.GRAY);
        paint.setTextSize(mLayoutSize/10);//控制文字大小
        canvas.drawText(title,centre - paint.measureText(title)/2,centre + (paint.ascent()+ paint.descent()) ,paint);

        if(now < progress - 1){
            now = now + 1 ;
            postInvalidate();
        }else if(now < progress){
            now = progress;
            postInvalidate();
        }
    }

    //外部回掉
    public void setProgress(float progress,boolean isAnim) {
        this.progress = progress;
        if(!isAnim){
            now = progress;
        }
        postInvalidate();
    }
}
