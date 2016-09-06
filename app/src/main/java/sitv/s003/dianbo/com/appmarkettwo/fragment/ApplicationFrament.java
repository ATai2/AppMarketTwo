package sitv.s003.dianbo.com.appmarkettwo.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.AppAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpAppPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpAppView;
import sitv.s003.dianbo.com.appmarkettwo.util.ActivityUtils;

/**
 * Created by wx on 8/10 0010.
 * 应用
 */
public class ApplicationFrament extends BaseFragment<MvpAppView, MvpAppPresenter> implements MvpAppView {
    public   static final String   TAG = "ApplicationFrament";
    public static String APPLICATION_APP="application_app";
    private StaggeredGridLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private  AppAdapter mAppAdapter;
    private boolean isLoadedDate = false;

    @Override
    public MvpAppPresenter initPresenter() {
        return new MvpAppPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_application;
    }

    @Override
    protected void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isLoadedDate){
            presenter.onResume();
            isLoadedDate = true;
        }
    }

    @Override
    public void setRecyclerCategoryItem(final List<CategoryInfo> mDatas) {
        mAppAdapter = new AppAdapter(mActivity, mDatas);
        mRecyclerView.setAdapter(mAppAdapter);
        mAppAdapter.setOnItemClickListener(new AppAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryInfo mCategoryInfo) {
                ActivityUtils.gotoCategoryListActivity(mActivity, mCategoryInfo.getCategory_id());
            }
        });
    }


}
