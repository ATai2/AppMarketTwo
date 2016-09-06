package sitv.s003.dianbo.com.appmarkettwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.FragmentAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseActivity;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.customview.CustomViewPager;
import sitv.s003.dianbo.com.appmarkettwo.fragment.CategoryListFragment;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpRecommPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpRecomView;

/**
 * Created by ly on 2016/8/19.
 * 分类
 */
public class CategoryListActivity extends BaseActivity<MvpRecomView,MvpRecommPresenter> {
    public static String TAG  = "CategoryListFragment";
    private Bundle mBundle;
    private CategoryListFragment categoryListFragment;
    private CustomViewPager mViewPager;
    private FragmentAdapter mFragmentAdapter;
    private List<BaseFragment> fragments = new ArrayList<>();
    private int type;
    @Override
    public MvpRecommPresenter initPresenter() {
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appcategorylist_activity);
        doIntent();
        initView();
        init();
    }


    private void initView() {
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setCurrentItem(ONE);
        fragments.add(getCategoryFragment());
    }

    private BaseFragment getCategoryFragment() {
        if (this.categoryListFragment == null) {
            this.categoryListFragment = new CategoryListFragment();
            if (this.mBundle != null) {
                this.categoryListFragment.setArguments(this.mBundle);
            }
        }
        return categoryListFragment;
    }

    private void init() {
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
    }

    private void doIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            this.mBundle = intent.getExtras();
        }
    }

    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {
        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            type = position;
            mViewPager.setCurrentItem(position);
        }
    }
}
