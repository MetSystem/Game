package edu.cgxy.customview;

/**
 * Created by zhou on 2015/5/10.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    public float currentX = 40;
    public float currentY = 50;

    /**
     * @param context
     */
    public DrawView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建画笔
        Paint p = new Paint();
        //设置画笔的颜色
        p.setColor(Color.RED);
        //绘制一个小圆（作为小球）
        canvas.drawCircle(currentX, currentY, 15, p);
    }
}
