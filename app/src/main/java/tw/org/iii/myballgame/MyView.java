package tw.org.iii.myballgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 2018/3/13.
 */

public class MyView extends View{
    Resources resources;
    private Bitmap bitmap;
    private Paint paint;
    private boolean isinit;
    private LinkedList<Ball> balls = new LinkedList<>();
    private Timer timer;
    private int viewW,viewH,ballW,ballH;
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.monkey);
        resources = getContext().getResources();
        timer = new Timer();
    }
    private void init(){
        isinit =true;
        bitmap = BitmapFactory.decodeResource(resources,R.drawable.ball);
        paint = new Paint();
        Matrix matrix = new Matrix();
        matrix.reset();
        int ow =bitmap.getWidth();
        int oh= bitmap.getHeight();
        viewW = getWidth();
        viewH = getHeight();
         ballW = viewW / 12;  ballH = ballW;
        Log.v("chad",""+ballH+":"+ballW);
        Log.v("chad",""+ow+":"+oh);
        matrix.postScale((float)ballW/ow,(float)ballH/oh);
        bitmap = Bitmap.createBitmap(bitmap,0,0,
                ow,oh,matrix,false);
        Ball b1 =new Ball();
        balls.add(b1);
        timer.schedule(new BallTask(),0,100);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isinit){
            init();
        }
        for(Ball b : balls) {
            canvas.drawBitmap(bitmap, b.BallX, b.BallY, paint);
        }


    }
    private class Ball{
        int BallX,BallY,dx,dy;
        Ball(){
            this(0,0);
        }
        Ball(int BallX,int BallY){
            this.BallY=BallY;
            this.BallX=BallX;
            dx=dy=20;
        }
    }

    public void addBall(int X,int Y) {
        balls.add(new Ball(X,Y));
    }
    private class BallTask extends TimerTask{
        @Override
        public void run() {
            for(Ball b: balls){
                if (b.BallX < 0 || b.BallX + ballW > viewW){
                    b.dx *= -1;
                }
                if (b.BallY <0 || b.BallY + ballH > viewH){
                    b.dy *= -1;
                }
                b.BallX+=b.dx;
                b.BallY+=b.dy;
                postInvalidate();
            }
        }
    }
}
