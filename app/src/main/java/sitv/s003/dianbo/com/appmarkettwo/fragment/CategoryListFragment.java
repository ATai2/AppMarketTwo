package sitv.s003.dianbo.com.appmarkettwo.fragment;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.CategoryListAdapter;
import sitv.s003.dianbo.com.appmarkettwo.adapter.MyViewPagerAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.customview.CustomViewPager;
import sitv.s003.dianbo.com.appmarkettwo.customview.FocusGridLayoutManager;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpCategoryListPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpCategoryListView;
import sitv.s003.dianbo.com.appmarkettwo.util.ActivityUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by ly on 2016/8/19.
 */
public class CategoryListFragment extends BaseFragment<MvpCategoryListView,MvpCategoryListPresenter> implements MvpCategoryListView{

    public  static  String TAG_RESUM = "CategoryListFragment";
    private RecyclerView mRecyclerView;
    private ImageButton iv_back;
    private boolean isLoadedData = false;
    private CategoryInfo mCategoryInfo;
    private CategoryListAdapter mCategoryAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private ViewPager mViewPager;
    private static final int PAGE_SIZE = 8;

    //翻页进度条
    private MyViewPagerAdapter adapter;
    private static int sTotalPages = 1;
    private List<RecyclerView> mLists;
    private ImageView cursor;// 动画图片
    private float offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private float bmpW;// 动画图片宽度
    float one;
    float two;

    @Override
    public MvpCategoryListPresenter initPresenter() {
        return new MvpCategoryListPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.categorylist_fragment;
    }

    @Override
    protected void initViews() {
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        cursor = (ImageView) findViewById(R.id.cursor);
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mActivity.finish();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCategoryInfo = new CategoryInfo();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategoryInfo.setCategory_id(bundle.getString("category_id"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isLoadedData){
           presenter.onResume(mCategoryInfo.getCategory_id());

            isLoadedData = true;
        }

    }

    /**
     * ViewPager页面选项卡进行切换时候的监听器处理
     *
     * @author jiangqingqing
     */
    class MyOnPageChanger implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            currIndex = 0;//当前页设置为1
            Animation animation = null;
            float from = (offset * 2 + bmpW) * currIndex;
            float to = (offset * 2 + bmpW) * mViewPager.getCurrentItem();
            animation = new TranslateAnimation(from, to, 0, 0);
            currIndex = mViewPager.getCurrentItem();
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

    }

    @Override
    public void setRecyclerItem(final List<AppInfo> mDatas) {
        mLists = new ArrayList<>();
        final int PageCount = (int) Math.ceil(mDatas.size() / 8.0f);
        for (int i = 0; i < PageCount; i++) {
            mRecyclerView = new RecyclerView(mActivity);
            mLayoutManager = new FocusGridLayoutManager(4, OrientationHelper.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mCategoryAdapter=new CategoryListAdapter(mActivity, mDatas, i);
            mRecyclerView.setAdapter(mCategoryAdapter);
            mRecyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            mLists.add(mRecyclerView);
            mCategoryAdapter.setOnItemClickLitener(new CategoryListAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, AppInfo data) {
                    int mid =  data.getId();
                    ActivityUtils.goAppActivity(mActivity, mid);
                }
            });
        }
        mViewPager.setOnPageChangeListener(new MyOnPageChanger());
        adapter = new MyViewPagerAdapter(mActivity, mLists);
        mViewPager.setAdapter(adapter);

// 计算总页数
        if (mDatas.size() % PAGE_SIZE == 0) {
            sTotalPages = mDatas.size() / PAGE_SIZE;
        } else {
            sTotalPages = mDatas.size() / PAGE_SIZE + 1;
        }
        InitImage(sTotalPages);
        //    currIndex = 0;
    }
    public void InitImage(int size) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float screenW = dm.widthPixels;// 获取分辨率宽度
        screenW -= ImageLoaderUtils.dip2px(getActivity(), 210);
        bmpW = (screenW / size) * (7.0f / 8.0f);
        offset = (screenW / size - bmpW) / 2.0f;// 计算偏移量
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams((int) bmpW, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins((int) offset, 0, 0, 0);
        this.cursor.setLayoutParams(layout);
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        this.cursor.setImageMatrix(matrix);// 设置动画初始位置
        one = offset * 2.0f + bmpW;// 页卡1 -> 页卡2 偏移量
        two = one;// 页卡1 -> 页卡2 偏移量
    }
}
