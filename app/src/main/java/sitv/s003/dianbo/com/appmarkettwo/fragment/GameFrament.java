package sitv.s003.dianbo.com.appmarkettwo.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.GameAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.baseadapter.OnItemClickListener;
import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpGamePresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpGameView;
import sitv.s003.dianbo.com.appmarkettwo.util.ActivityUtils;

/**
 * Created by wx on 8/10 0010.
 * 推荐
 */
public class GameFrament extends BaseFragment<MvpGameView,MvpGamePresenter> implements MvpGameView{
    public static final String TAG = "GameFrament";
    public static final String CAtGORY_ID = "GameFrament";
    private  GameAdapter mGameAdapter;
    public RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private boolean isLoadedDate = false;
    @Override
    public MvpGamePresenter initPresenter() {
        return new MvpGamePresenter();
    }
    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_game;
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
    protected void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    @Override
    public void setRecyclerItem(final List<CategoryInfo> mCategoryList) {
        mGameAdapter = new GameAdapter(mActivity,mCategoryList);
        mRecyclerView.setAdapter(mGameAdapter);
        mGameAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryInfo mCategoryInfo) {
                ActivityUtils.gotoCategoryListActivity(mActivity, mCategoryInfo.getCategory_id());
            }
        });
    }
}
