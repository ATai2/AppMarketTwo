package sitv.s003.dianbo.com.appmarkettwo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.RecomAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpRecommPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpRecomView;
import sitv.s003.dianbo.com.appmarkettwo.util.ActivityUtils;

/**
 * Created by wx on 8/10 0010.
 * 推荐
 */
public class RecomFrament extends BaseFragment<MvpRecomView, MvpRecommPresenter> implements MvpRecomView {

    public static final String TAG = "RecomFrament";
    public RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private  RecomAdapter mRecomAdapter;

    private boolean isHasLoadData = false;
    public boolean isleftSidegetFocus;

    @Override
    public MvpRecommPresenter initPresenter() {
        return new MvpRecommPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_recommended;
    }

    @Override
    protected void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new StaggeredGridLayoutManager (2,
                StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.stopScroll();
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
//
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHasLoadData) {
            presenter.onResume();
            isHasLoadData = true;
        }
    }

    @Override
    public void setRecyclerItem(List<AppInfo> mAppInfoList) {

        mRecomAdapter = new RecomAdapter(mActivity, mAppInfoList,RecomFrament.this);
        mRecyclerView.setAdapter(mRecomAdapter);

        mRecomAdapter.setOnItemClickLitener(new RecomAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, AppInfo data) {
                int mid = data.getId();
                ActivityUtils.goAppActivity(mActivity, mid);
            }
        });

    }


}
