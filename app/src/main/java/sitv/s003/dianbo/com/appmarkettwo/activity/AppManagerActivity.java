package sitv.s003.dianbo.com.appmarkettwo.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseActivity;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTask;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTaskManager;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.activity.MvpAppManagerPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.activity.MvpAppManagerView;
import sitv.s003.dianbo.com.appmarkettwo.util.Devices;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by Administrator on 2016/9/2.
 */
public class AppManagerActivity extends BaseActivity<MvpAppManagerView,MvpAppManagerPresenter> implements MvpAppManagerView{
    private ProgressBar mFreeSpaceProgressBar;
    private LinearLayout mLinearLayout;
    public static List<DownloadTask> mDownloadingList;//我的下载管理
    public List<AppInfo> DownloadResult = new ArrayList<>();
    @Override
    public MvpAppManagerPresenter initPresenter() {
        return new MvpAppManagerPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appmanage_activity);
        initView();
        initData();
    }
    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int display_width = (int)(ImageLoaderUtils.px2dip(mActivity, dm.widthPixels)*0.6f);
        int display_heihgt = (int)(ImageLoaderUtils.px2dip(mActivity, dm.heightPixels)*0.9f);
        getWindow().setLayout(display_width, display_heihgt);
        mFreeSpaceProgressBar = (ProgressBar) findViewById(R.id.app_space_percent);
        mLinearLayout = (LinearLayout) findViewById(R.id.app_manage_sort_list);

    }
    private void initData() {
        mFreeSpaceProgressBar.setProgress(100 - Devices.freeSpacePer());
       initHistoryDownLoaded();
   }

    private void initHistoryDownLoaded() {
        mDownloadingList = DownloadTaskManager.getInstance(this).getDownloadingTask();
        Iterator<DownloadTask> iterator = mDownloadingList.iterator();
        List<AppInfo> infos = new ArrayList<>();
        int index = 0;
        while (iterator.hasNext()) {
            DownloadTask downloadTask = (DownloadTask) iterator.next();
                AppInfo info = new AppInfo();
                info.setId(downloadTask.getPid());
                infos.add(info);
                info.getId();
                index++;
            Log.i("AppManagerActivity", String.valueOf(info.getId()));
        }
        for(int i=0;i<mDownloadingList.size();i++){
            presenter.onResume(infos.get(i).getId());
        }

    }

    @Override
    public void setRecyclerItem(AppInfo mDatas) {
            DownloadResult.add(mDatas);
    }

}
