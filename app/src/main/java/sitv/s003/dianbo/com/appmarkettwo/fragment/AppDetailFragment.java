package sitv.s003.dianbo.com.appmarkettwo.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.ImagesAppListAdapter;
import sitv.s003.dianbo.com.appmarkettwo.adapter.RelatedAppListAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.download.DetailDownloadListener;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadState;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTask;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTaskManager;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpAppDtailPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpAppDetaliView;
import sitv.s003.dianbo.com.appmarkettwo.util.ActivityUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.CommonUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.DownloadDBHelper;
import sitv.s003.dianbo.com.appmarkettwo.util.FileUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.PackageUtils;


/**
 * Created by ly on 2016/8/15.
 */
public class AppDetailFragment extends BaseFragment<MvpAppDetaliView, MvpAppDtailPresenter> implements MvpAppDetaliView, View.OnClickListener {
    public static final String TAG = "AppDetailFragment";
    private ImageView mainImage;
    private Button bt_downLoad_manager;
    public static TextView mTxtAppName;
    private TextView mTxtCompany;
    private RatingBar mRatingbar;
    private TextView mAppType;
    private TextView mAppDetails;
    public static Button mBtnDownload;
    public ImageButton iv_back;
    private GridView mRelatedListView = null;
    private RelatedAppListAdapter mRelatedPageAdapter;
    private ImagesAppListAdapter mImagesAppListAdapter;
    private GridLayoutManager mLayoutManager;
    private RecyclerView mImagesListView = null;
    private ImageButton mZFB;//支付宝按钮
    private DownloadDBHelper mDownloadDBHelper;
    private DetailDownloadListener mDetailDownloadListener = null;
    public static DownloadTask mDownloadTask = null;
    private android.view.ViewGroup.LayoutParams lps;
    public static ProgressBar mdownPB;
    public boolean isLoadedData = false;
    private AppInfo mAppInfo;
    public static volatile DownloadState mState;
    private String installPath = "";
    private String filePath = Environment
            .getExternalStorageDirectory().getPath() + "/market/";

    @Override
    public MvpAppDtailPresenter initPresenter() {
        return new MvpAppDtailPresenter();
    }
    @Override
    protected int getLayoutResID() {
        return R.layout.appdetail_fragment;
    }
    @Override
    protected void initViews() {
        mView.findViewById(R.id.loading);
        bt_downLoad_manager = (Button) findViewById(R.id.bt_downLoad_manager);
        mainImage = (ImageView) findViewById(R.id.mainImage);
        mainImage.setBackgroundResource(0);
        iv_back = (ImageButton) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        mTxtAppName = (TextView) findViewById(R.id.appName);
        mTxtCompany = (TextView) findViewById(R.id.company);
        mRatingbar = (RatingBar) findViewById(R.id.ratingbarDefault);
        mRatingbar.setBackgroundResource(0);
        mAppType = (TextView) findViewById(R.id.appType);
        mAppDetails = (TextView) findViewById(R.id.appDetails);
        mBtnDownload = (Button) findViewById(R.id.btn_download);
        mRelatedListView = ((GridView) findViewById(R.id.relatedList));
        mImagesListView = ((RecyclerView) findViewById(R.id.imagesList));
        mZFB = (ImageButton) findViewById(R.id.btn_zfb);
        mdownPB = (ProgressBar) findViewById(R.id.downpb);
        mDownloadDBHelper = new DownloadDBHelper(getActivity());
        mainImage.setImageBitmap(ImageLoaderUtils.createReflectedImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
        mZFB.setOnClickListener(this);
        bt_downLoad_manager.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
        mRelatedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int mid = mAppInfo.getListSoft().get(position).getId();
                presenter.onResume(mid);
            }
        });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化参数设置参数设置
        initParamSet();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!isLoadedData) {
            presenter.onResume(mAppInfo.getId());
            isLoadedData = true;
        }
    }
    private void initParamSet() {
        mAppInfo = new AppInfo();
        Bundle bundle = getArguments();// 获取参数
        if (bundle != null) {
            mAppInfo.setId(bundle.getInt("extra_app_id"));
        }
    }
    private void initAppState(String packageName) {
        // 检查是否已经安装
        boolean installed =  CommonUtils.isInstalled(mActivity, packageName);
        if (installed) {
            DealInstalledState();  //处理成功Installedstate
        } else {
            DealUnInstalledState();//处理成功UnInstalledstate
        }
    }
    private void DealBtnDownLoadEvent() {
       if(mState == DownloadState.INSTALLED) {
           dealStatedInstalled();
       }else if(mState == DownloadState.FINISHED) {
           dealStateFinished();
       } else if (mState == DownloadState.PAUSE) {
           dealStatePause();
       } else if (mState == DownloadState.DOWNLOADING) {
            StopDownLoading();//暂停下载
        } else {
            firstDownLoading();//第一次下载
        }
    }
    //初始化界面
    @Override
    public void setRecyclerItem(AppInfo mDatas) {
        initAppState(mDatas.getPackage_name());
        if (mDatas != null) {
            mAppInfo = mDatas;
            //应用名称
            this.mTxtAppName.setText(mAppInfo.getName());
            //设置应用大小和所属公司
            SetSizeAndCompany();
            //设置应用RatingBar
            SetApplicationRatingBar();
            //应用名称
            this.mAppType.setText(mAppInfo.getCname());
            //应用描述
            this.mAppDetails.setText(mAppInfo.getExplains());
            //应用图标
            ImageLoaderUtils.showApplicationIcon(mActivity, mAppInfo, mainImage);

            //设置关联应用
            SetRelatedApplication();
            //设置应用截图
            SetApplicationScreenshot();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mActivity.finish();
                break;
            case R.id.bt_downLoad_manager:
                ActivityUtils.gotoAppManagerActivity(mActivity);
                break;
            case R.id.btn_zfb:
                ActivityUtils.goToZFBActivity(mActivity);
            case R.id.btn_download:
                DealBtnDownLoadEvent();

                break;
        }
    }
    private void DealInstalledState() {
        mState = DownloadState.INSTALLED;
        mBtnDownload.setText("启动应用");
        mdownPB.setVisibility(View.GONE);
    }
    private void DealUnInstalledState() {
        mDownloadTask = mDownloadDBHelper.getItem(String.valueOf(this.mAppInfo.getId()));
        if (mDownloadTask == null) {
            //处理第一次下载时的状态
            DealFirstDownloadState();
        } else {
            mdownPB.setVisibility(View.VISIBLE);
            //判断是否是下载完成
            if (mDownloadTask.getDownloadState() != DownloadState.FINISHED) {
                //设置没有下载完成时的一个状态
                SetDownloadUnFinishedState();
            } else {
                SetDownloadFinishedState();
            }
        }
    }
    /**
     * 进入详情页第一次下载之前下载状态初始化
     */
    private void DealFirstDownloadState() {
        mState = DownloadState.INITIALIZE;
        mBtnDownload.setText("下载应用");
        //获取已下载进度使用ASyktask线程池下载
        try {
            mdownPB.setProgress(DownloadTaskManager.getInstance(getActivity()).mTaskList.get(mAppInfo.getPackage_name()).getDownLoadedPercent());
        } catch (Exception e) {

        }
    }
    /**
     * 初始化下载状态处理下载没有完成时状态处理
     */
    private void SetDownloadUnFinishedState() {
        mState = mDownloadTask.getDownloadState();
        if (mState == DownloadState.INITIALIZE || mState == DownloadState.DOWNLOADING) {
            mBtnDownload.setText("下载中...");
            // 注册监听
            mDetailDownloadListener = new DetailDownloadListener(AppDetailFragment.this);
            DownloadTaskManager.getInstance(getActivity()).registerListener(mDownloadTask, mDetailDownloadListener);
        } else if (mState == DownloadState.PAUSE) {
            mBtnDownload.setText("暂停中...");
        } else {
            mBtnDownload.setText("下载失败");
        }
    }
    /**
     * 初始化下载状态设置下载完成时状态显示
     */
    private void SetDownloadFinishedState() {
        mState = DownloadState.FINISHED;
        mBtnDownload.setText("安装应用");
        mdownPB.setVisibility(View.GONE);
        installPath = mDownloadTask.getFilePath() + mDownloadTask.getFileName();
    }
    /**
     * 设置应用的大小和公司名称
     */
    private void SetSizeAndCompany() {
        StringBuilder bf = new StringBuilder();
        float f2 = Integer.valueOf(mAppInfo.getSize()) / 1024.0F / 1024.0F;

        if (f2 >= 1.0F) {
            bf.append(String.format("%1$.2f M ", Float.valueOf(f2)));
        } else {
            bf.append(String.format("%1$.2f K ", Float.valueOf(Integer.valueOf(mAppInfo.getSize()))));
        }
        this.mTxtCompany.setText(bf.toString() + " | " + mAppInfo.getCompany());
    }
    /**
     * 设置详情界面的RatingBar
     */
    private void SetApplicationRatingBar() {
        float score = Float.parseFloat(String.valueOf(mAppInfo.getScore())) / 20;
        if (score > 0.5) {
            this.mRatingbar.setRating(score);
        } else {
            this.mRatingbar.setRating((float) 0.5);
        }
    }
    /**
     * 设置关联应用显示
     */
    private void SetRelatedApplication() {
        if (mAppInfo.getListSoft() != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 4);
            layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            mRelatedPageAdapter = new RelatedAppListAdapter(mActivity, mAppInfo.getListSoft());
            this.mRelatedListView.setAdapter(this.mRelatedPageAdapter);
            this.mRelatedPageAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 设置应用截图
     */
    private void SetApplicationScreenshot() {
        if (mAppInfo.getImages() != null) {
            mImagesAppListAdapter = new ImagesAppListAdapter(mActivity, mAppInfo);
            mLayoutManager = new GridLayoutManager(mActivity, 3);
            mLayoutManager.setOrientation(OrientationHelper.VERTICAL);

            mImagesListView.setHasFixedSize(true);
            mImagesListView.setLayoutManager(mLayoutManager);
            this.mImagesListView.setAdapter(mImagesAppListAdapter);
            mImagesAppListAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 恢复下载
     */
    private void ResumeDownload() {
        // 恢复下载
        mBtnDownload.setText("下载中...");
        mState = DownloadState.DOWNLOADING;
        mDownloadTask.setDownloadState(mState);
        mDetailDownloadListener = new DetailDownloadListener(AppDetailFragment.this);
        DownloadTaskManager.getInstance(getActivity())
                .registerListener(mDownloadTask,
                        mDetailDownloadListener);
        DownloadTaskManager.getInstance(getActivity()).startDownload(mDownloadTask);
    }
    /**
     * 暂停下载
     */
    private void StopDownLoading() {
        // 下载中
        mBtnDownload.setText("暂停...");
        mState = DownloadState.PAUSE;
        mDownloadTask.setDownloadState(mState);
        DownloadTaskManager.getInstance(getActivity()).pauseDownload(mDownloadTask);
    }
    /**
     * 执行第一次下载
     */
    private void firstDownLoading() {
        String url = "http://192.168.1.247:8081/Market/views/data/apk/" + mAppInfo.getId() + "/" + mAppInfo.getUrl();

        String thumbnail = "";//mAppInfo.getFileName()占时用mAppInfo.getPackage_name()191行
        mDownloadTask = new DownloadTask(mAppInfo
                .getId(), mAppInfo.getPackage_name(), url, mAppInfo.getPackage_name(), mAppInfo
                .getName(), thumbnail, "", mAppInfo.getIcon());
        ResumeDownload();
    }
    private void dealStatePause() {
        if(FileUtils.isfileExsit(filePath + mAppInfo.getPackage_name())){
            ResumeDownload();//恢复下载
        }else{
            File destDir = new File(filePath+mAppInfo.getPackage_name());
            try{
                destDir.createNewFile();
            }catch (Exception e){
            }
            mDownloadDBHelper.deleteItem(String.valueOf(this.mAppInfo.getId()));
            firstDownLoading();//第一次下载
        }
    }
    private void dealStateFinished() {
        if (PackageUtils.INSTALL_SUCCEEDED == PackageUtils.install(getActivity(), mDownloadTask.getFilePath() + mDownloadTask.getFileName())) {
            mState = DownloadState.INSTALLED;
            mBtnDownload.setText("启动应用");
        }else{
            firstDownLoading();
        }
    }
    private void dealStatedInstalled() {
        if (CommonUtils.isInstalled(mActivity, mAppInfo.getPackage_name())) {
            CommonUtils.openApp(mActivity, mAppInfo.getPackage_name());
        } else {
            if (FileUtils.isfileExsit(filePath + mAppInfo.getPackage_name())) {
                PackageUtils.install(getActivity(), mDownloadTask.getFilePath() + mDownloadTask.getFileName());
            } else {
                firstDownLoading();
            }
        }
    }
}
