package sitv.s003.dianbo.com.appmarkettwo.mvp.base;

import android.os.Message;

import com.android.volley.VolleyError;

/**
 * Created by wx on 6/6 0006.
 */
public abstract class BasePresenter<T> {
    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }


    public Message errorMessage(VolleyError error) {
        Message msg = new Message();
        msg.obj = error;
        return msg;
    }
}
