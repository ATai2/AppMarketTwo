package sitv.s003.dianbo.com.appmarkettwo.mvp.view.activity;

import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;

/**
 * Created by Administrator on 2016/9/2.
 */
public interface MvpAppManagerView extends BaseMvpView {

    //RecyclerView初始化
    void setRecyclerItem(AppInfo mDatas);
}
