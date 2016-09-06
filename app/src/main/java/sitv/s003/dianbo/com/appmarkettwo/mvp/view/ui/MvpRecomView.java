package sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui;



import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;

import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;

/**
 * Created by wx on 6/6 0006.
 * 动作/科幻等界面的 view
 */
public interface MvpRecomView extends BaseMvpView {

    //RecyclerView初始化
   void setRecyclerItem(List<AppInfo> mCategoryList);
  //  void setRecyclerItem(List<CategoryItem> mIndexList);

}
