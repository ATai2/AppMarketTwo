package sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui;


import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.bean.SearchTag;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;

/**
 * Created by wx on 6/6 0006.
 * 动作/科幻等界面的 view
 */
public interface MvpSearchView extends BaseMvpView {
    //RecyclerViewTag初始化
    void setRecyclerItem(List<AppInfo> mCategoryList);
     //RecyclerViewTag初始化
      void setRecyclerItemTag(List<SearchTag> mCategoryList);
     //  void setRecyclerItem(List<CategoryItem> mIndexList);
     //数据加载tag失败后处理
      void dellItemTAgfailure();
     //数据加载tag被点击后失败后处理
      void dellItemTAgLoadfailure();
}
