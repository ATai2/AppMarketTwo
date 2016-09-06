package sitv.s003.dianbo.com.appmarkettwo.mvp.base;

import android.os.Message;

/**
 * Created by wx on 6/7 0007.
 */
public interface BaseMvpView {
    //显示loading progress
    void showLoading();

    //隐藏loading progress
    void hideLoading();

    //Toast 消息
    void showMessage(Message msg);
}
