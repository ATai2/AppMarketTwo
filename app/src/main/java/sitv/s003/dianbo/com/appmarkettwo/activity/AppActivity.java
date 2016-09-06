package sitv.s003.dianbo.com.appmarkettwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseActivity;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.fragment.AppDetailFragment;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpRecommPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpRecomView;

/**
 * Created by ly on 2016/8/12.
 */
public class AppActivity extends BaseActivity<MvpRecomView,MvpRecommPresenter> {
    private Bundle mBundle;
    public static final String TAG_APPDETAIL_FRAGMENT = "appdetail_fragment";
    private BaseFragment mAppDetailFragment;
    @Override
    public MvpRecommPresenter initPresenter() {
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appdetail_activity);
        doIntent();
        showmAppDetailFragment();
    }
    private void doIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            this.mBundle = intent.getExtras();
        }
    }
    /**
     * 显示AppDetailFragment
     */
    private void showmAppDetailFragment() {
        // 管理类
        FragmentManager ftManager = getSupportFragmentManager();
        FragmentTransaction ftTransaction = ftManager.beginTransaction();
        this.mAppDetailFragment = ((BaseFragment) ftManager
                .findFragmentByTag(TAG_APPDETAIL_FRAGMENT));
        if (this.mAppDetailFragment == null) {
            this.mAppDetailFragment = new AppDetailFragment();
            if (this.mBundle != null) {
                this.mAppDetailFragment.setArguments(this.mBundle);
            }
            ftTransaction.add(R.id.appDetailFragment, this.mAppDetailFragment,
                    TAG_APPDETAIL_FRAGMENT);
        }
        ftTransaction.commit();
    }
}
