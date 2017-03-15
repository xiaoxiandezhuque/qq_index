package xuhong.zidingyikongjian.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

import xuhong.zidingyikongjian.R;

/**
 * Created by BHKJ on 2016/6/8.
 */
public class BlankActivity extends AppCompatActivity {

    TextView textView;
    private VelocityTracker vTracker = null;

    //  只为展示  没卵用
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        textView = (TextView) findViewById(R.id.textview);
    }



//            case MotionEvent.ACTION_UP:
//            case MotionEvent.AC  int   downY;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                downY = (int) event.getY();
//                if (vTracker == null) {
//                    //初始化velocityTracker的对象 vt 用来监测motionevent的动作
//                    vTracker = VelocityTracker.obtain();
//                } else {
//                    vTracker.clear();
//                }
//                vTracker.addMovement(event);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                vTracker.addMovement(event);
//                //代表的是监测每1000毫秒手指移动的距离（像素）即m/px，这是用来控制vt的单位，若括号内为1，则代表1毫秒手指移动过的像素数即ms/px
//                vTracker.computeCurrentVelocity(1000);
//                //这里x为正则代表手指向右滑动，为负则代表手指向左滑动，y的比较特殊，为正则代表手指向下滑动，为负则代表手指向上滑动
//
//                int  move = Math.abs((int) (event.getY())-downY);
//               float i = (int) vTracker.getYVelocity();
////                i = i*move;
//                Log.e("the y velocity is",i+"");
//                Log.e("move",move+"");
//                break;TION_CANCEL:
//                vTracker.recycle();
//                break;
//        }
//        return true;
//    }

}
