package sitv.s003.dianbo.com.appmarkettwo.download;

import sitv.s003.dianbo.com.appmarkettwo.fragment.AppDetailFragment;
import sitv.s003.dianbo.com.appmarkettwo.util.PackageUtils;

import static sitv.s003.dianbo.com.appmarkettwo.fragment.AppDetailFragment.mBtnDownload;
import static sitv.s003.dianbo.com.appmarkettwo.fragment.AppDetailFragment.mDownloadTask;

/**
 * 下载详情监听器
 * Created by Administrator on 2016/8/15.
 */

public class DetailDownloadListener implements DownloadListener {

    AppDetailFragment mdetaiFragment;
    public DetailDownloadListener(AppDetailFragment mdetaiFragment) {
     this.mdetaiFragment = mdetaiFragment;
    }
    @Override
    public void onDownloadFail() {
        mdetaiFragment.mState=DownloadState.INITIALIZE;
        DownloadTaskManager.mTaskList.remove(mDownloadTask.getPackageName());
        mDownloadTask=null;
        if (mdetaiFragment.getActivity()!=null) {
            mdetaiFragment.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mBtnDownload.setText("下载失败");
                }
            });
        }
    }
    @Override
    public void onDownloadFinish(String filepath) {
  //      Log.i("下载完成1", filepath);
        if (mdetaiFragment.getActivity()!=null){
            mdetaiFragment.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mBtnDownload.setText("下载完成");
                }
            });
        }
        boolean installed = PackageUtils.checkInstalled(mdetaiFragment.getActivity(),mDownloadTask.getPackageName());
        if (installed) {
            mdetaiFragment.mState=DownloadState.INSTALLED;
            if (mdetaiFragment.getActivity()!=null){
            mdetaiFragment.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mBtnDownload.setText("启动应用");
                }
            });
            }
        }
        else{
            final String fp=mDownloadTask.getFilePath()+mDownloadTask.getFileName();
            if (mdetaiFragment.getActivity()!=null) {
                mdetaiFragment.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (PackageUtils.INSTALL_SUCCEEDED == PackageUtils.install(mdetaiFragment.getActivity(), fp)) {
                            mdetaiFragment.mState = DownloadState.INSTALLED;
                            mBtnDownload.setText("启动应用");
                        } else {
                            mBtnDownload.setText("安装应用");
                        }
                    }
                });
            }

            if(DownloadTaskManager.mTaskList.containsKey(mDownloadTask.getPackageName())){
                DownloadTaskManager.mTaskList.remove(mDownloadTask.getPackageName());
            }
        }
        mDownloadTask.setDownloadState(mdetaiFragment.mState);
    }

    @Override
    public void onDownloadPause() {
  //      Log.i("pause", "onDownloadPause");
    }

    @Override
    public void onDownloadProgress(int finishedSize, int totalSize,
                                   int speed) {
        final int sd=(int)((finishedSize*1.0f)/(totalSize/100));//为什么要这样改，不然会出现130%的下载量
        if (mdetaiFragment.getActivity()!=null){
            mdetaiFragment.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mdetaiFragment.mdownPB.setProgress(sd);

                    mBtnDownload.setText(sd+"%");
                }
            });
        }

    }

    @Override
    public void onDownloadStart() {
    }

    @Override
    public void onDownloadStop() {
    }
}
