package tw.org.iii.myballgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyView myView;
    private int screenX,screenY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView=findViewById(R.id.drawArea);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                screenY=(int)motionEvent.getY();
                screenX=(int)motionEvent.getX();
                return false;
            }
        });
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("chad","x:"+screenX+"y:"+screenY);
                myView.addBall(screenX,screenY);
            }
        });
    }
}
