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
    private double pi =Math.PI;
    private Resources resources;
    private Bitmap bitmap;
    private Paint paint;
    private boolean isinit;
    private LinkedList<Ball> balls = new LinkedList<>();
    private Timer timer;
    private int viewW,viewH,ballW,ballH,targetX,targetY;
    private Ball b1;
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
        b1 =new Ball();
        targetX=b1.BallX;
        targetY=b1.BallY;
        timer.schedule(new BallTask(),0,100);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isinit){
            init();
        }
//        for(Ball b : balls) {
            canvas.drawBitmap(bitmap, b1.BallX, b1.BallY, paint);
//        }


    }
    private class Ball{
        int BallX,BallY,BallSpeed;
        double BallAngel=0.75*pi;
        double vx,vy;
        Ball(){
            this(0,0);
        }
        Ball(int BallX,int BallY){
            this.BallY=BallY;
            this.BallX=BallX;
            BallSpeed=50;
            vx=BallSpeed*Math.cos(BallAngel);
            vy=BallSpeed*Math.sin(BallAngel);
            Log.v("chad","vx: "+vx +" vy: "+vy);
//             =Math.toRadians(Balltan)*pi;

        }
    }

    public void addBall(int X,int Y) {
        balls.add(new Ball(X,Y));
    }
    public  void moveBall(int X,int Y){
        targetX=X;
        targetY=Y;
        double distanceX =X-b1.BallX;
        double distanceY =Y-b1.BallY;
        double temp =Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));
        double foo =distanceX/temp;
        double bar = distanceY/temp;
        b1.vx=b1.BallSpeed*foo;
        b1.vy=b1.BallSpeed*bar;

    }
    private class BallTask extends TimerTask{
        @Override
        public void run() {
            if(targetX>=b1.BallX&&targetX<=b1.BallX+ballW&&targetY>=b1.BallY&&targetY<=b1.BallY+ballH) {
                b1.vx=b1.vy=0;
            }
                if (b1.BallX < 0 || b1.BallX + ballW > viewW) {
                    b1.vx *= -1;
                }
                if (b1.BallY < 0 || b1.BallY + ballH > viewH) {
                    b1.vy *= -1;
                }
                b1.BallX += b1.vx;
                b1.BallY += b1.vy;
                postInvalidate();

        }
    }
}
