package sitv.s003.dianbo.com.appmarkettwo.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by wx on 8/10 0010.
 */
public class CustomViewPager extends ViewPager {
    public static boolean isEnd = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (isDispatch && event.getKeyCode() != KeyEvent.KEYCODE_DPAD_CENTER) {
//            Log.v("request_wx", "nocenter");
//            isDispatch = true;
//            return super.dispatchKeyEvent(event);
//        } else {
//            Log.v("request_wx", "center");
//            isDispatch = true;
//            return false;
//        }
  //  }

}
