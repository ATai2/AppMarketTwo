package sitv.s003.dianbo.com.appmarkettwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.util.List;
import java.util.StringTokenizer;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseActivity;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTask;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTaskManager;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpRecommPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpRecomView;
import sitv.s003.dianbo.com.appmarkettwo.util.ActivityUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.CommonUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.DownloadDBHelper;
import sitv.s003.dianbo.com.appmarkettwo.util.FileUtils;


public class LauncherActivity extends BaseActivity<MvpRecomView, MvpRecommPresenter> {

    private static final String TAG = "LauncherActivity";

    private Thread splasThread = null;
    private String str_id;
    private String str_packageName;
    private String str_image_url;
    public List<DownloadTask> mDownloadingList;//我的下载管理
    private DownloadDBHelper mDownloadDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 检查和报告开发者开发应用中存在的问题
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mDownloadDBHelper = new DownloadDBHelper(mActivity);
        Intent intent = getIntent();
        if (intent != null) {
            String callbackParams = intent.getStringExtra("callBackParams");
            if (callbackParams != null && !callbackParams.equals("")) {
                System.out.println("callbackParams:" + callbackParams);
                //String[] str = callbackParams.split("|");
                StringTokenizer str = new StringTokenizer(callbackParams, "|");
                str_id = str.nextToken();
                System.out.println("str_id" + str_id);
                str_packageName = str.nextToken();
                System.out.println("str_packageName" + str_packageName);
                str_image_url = str.nextToken();
                System.out.println("str_image_url" + str_image_url);
                ActivityUtils.goMainActivity(mActivity, str_id, str_packageName, str_image_url);
                finish();
                return;
            }
        }
        deleteDownloadAPk();
        try {
            welComeThread();
        } catch (Exception e) {
            Log.e(TAG, "Exception : ", e);
            finish();
        }
    }
    /**
     * 重新启动应用后，判断是否已安装等。删除
     */
    private void deleteDownloadAPk() {
        mDownloadingList = DownloadTaskManager.getInstance(this).getDownloadingTask();
        for (int i = 0; i < mDownloadingList.size(); i++) {
            DownloadTask task = mDownloadingList.get(i);
            if( CommonUtils.isInstalled(mActivity,task.getPackageName())){
                FileUtils.deletFile(task);
            }else{
                if(FileUtils.isfileExsit(task.getFilePath()+task.getPackageName())){
                    FileUtils.deletFile(task);
                   mDownloadDBHelper.deleteItem(String.valueOf(task.getId()));
                }
            }
        }
    }
    private void welComeThread() {
        splasThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2000);
                        ActivityUtils.goMainActivityTwo(mActivity);
                        finish();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ERROR : ", e);
                }
            }
        };
        splasThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public MvpRecommPresenter initPresenter() {
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
