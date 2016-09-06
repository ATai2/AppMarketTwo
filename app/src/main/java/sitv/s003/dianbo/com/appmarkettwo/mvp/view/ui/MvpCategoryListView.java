package sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface MvpCategoryListView extends BaseMvpView {
    //RecyclerView初始化
    void setRecyclerItem(List<AppInfo> mDatas);
}
