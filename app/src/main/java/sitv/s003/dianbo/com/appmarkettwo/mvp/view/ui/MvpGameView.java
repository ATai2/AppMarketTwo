package sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;

/**
 * Created by Administrator on 2016/8/11.
 */

public interface MvpGameView extends BaseMvpView {
    //RecyclerView初始化
    void setRecyclerItem(List<CategoryInfo> mCategoryList);
}
