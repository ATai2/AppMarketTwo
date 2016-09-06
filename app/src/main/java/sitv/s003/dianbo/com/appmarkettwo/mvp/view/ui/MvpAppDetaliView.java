package sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui;

import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;

/**
 * Created by Administrator on 2016/8/15.
 */

public interface MvpAppDetaliView extends BaseMvpView {
    //RecyclerView初始化
    void setRecyclerItem(AppInfo mDatas);
}
