package sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;

/**
 * Created by wx on 8/10 0010.
 */
public interface MvpAppView extends BaseMvpView{

    //RecyclerView初始化
    //  void setRecyclerItem(List<AppInfo> mDatas);

     void setRecyclerCategoryItem(List<CategoryInfo> mDatas);
}
