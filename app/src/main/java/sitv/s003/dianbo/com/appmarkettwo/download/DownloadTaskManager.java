package sitv.s003.dianbo.com.appmarkettwo.download;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import sitv.s003.dianbo.com.appmarkettwo.util.DownloadDBHelper;

public class DownloadTaskManager {

    public static final String TAG = "DownloadTaskManager";

    private static DownloadTaskManager mDownloadTaskManager;
    private DownloadOperator downloadOperator;

    private static final String mFilePath = Environment
            .getExternalStorageDirectory().getPath() + "/market/";

    public DownloadDBHelper getmDownloadDBHelper() {
        return mDownloadDBHelper;
    }

    private DownloadDBHelper mDownloadDBHelper;
    // e
    public static Map<String, DownloadOperator> mTaskList = new HashMap<String, DownloadOperator>();
    // f
    private HashMap<String, CopyOnWriteArraySet<DownloadListener>> mCurrentList = new HashMap<String, CopyOnWriteArraySet<DownloadListener>>();

    public DownloadTaskManager(Context context) {
        mDownloadDBHelper = new DownloadDBHelper(context);
    }

    // this.d.b(paramDownloadTask);
    public void updateDownloadTask(DownloadTask downloadTask) {
        this.mDownloadDBHelper.updateItem(downloadTask);
    }

    public DownloadTask getDownloadTask(String pid) {
        return this.mDownloadDBHelper.getItem(pid);
    }

    // this.d.a(paramDownloadTask);
    public void saveDownloadTask(DownloadTask downloadTask) {
        this.mDownloadDBHelper.saveItem(downloadTask);
    }

    public List<DownloadTask> getDownloadingTask() {
        return this.mDownloadDBHelper.getDownloading();
    }

    public synchronized static DownloadTaskManager getInstance(Context context) {
        try {
            if (mDownloadTaskManager == null) {
                mDownloadTaskManager = new DownloadTaskManager(context);
            }
        } catch (Exception ex) {
            Log.w(TAG, ex);
        }
        return mDownloadTaskManager;

    }

    public void startDownload(DownloadTask downloadTask) {

        if ((downloadTask.getFilePath() == null)
                || (downloadTask.getFilePath().trim().length() == 0)) {
            downloadTask.setFilePath(mFilePath);
        }
        if ((downloadTask.getFileName() == null)
                || (downloadTask.getFileName().trim().length() == 0)) {
            throw new IllegalArgumentException("file name is invalid");
        }
        if ((downloadTask.getUrl() == null)
                || (!URLUtil.isHttpUrl(downloadTask.getUrl()))) {
            throw new IllegalArgumentException("invalid http url");
        }

        if (this.mCurrentList.get(downloadTask.toKey()) == null) {
            CopyOnWriteArraySet<DownloadListener> set = new CopyOnWriteArraySet<DownloadListener>();
            this.mCurrentList.put(downloadTask.toKey(), set);
        }

        downloadTask.setDownloadState(DownloadState.INITIALIZE);
        if (!downloadTask.equals(getDownloadTask(downloadTask.getPid() + ""))) {
            saveDownloadTask(downloadTask);
        }
        if (mDownloadTaskManager.mTaskList.containsKey(downloadTask.getPackageName())) {
            mDownloadTaskManager.mTaskList.get(downloadTask.getPackageName()).resume();
        } else {
            downloadOperator = new DownloadOperator(this, downloadTask);
            downloadOperator.startDownload();
            mDownloadTaskManager.mTaskList.put(downloadTask.getPackageName(), downloadOperator);
        }
    }

    public void pauseDownload(DownloadTask downloadTask) {
        if (mDownloadTaskManager.mTaskList.containsKey(downloadTask.getPackageName())) {
            mDownloadTaskManager.mTaskList.get(downloadTask.getPackageName()).pause();
            //mDownloadTaskManager.mTaskList.remove(del);
            this.mCurrentList.remove(downloadTask.toKey());
        }
    }

    public void registerListener(DownloadTask downloadTask, DownloadListener downloadListener) {

        if (this.mCurrentList.get(downloadTask.toKey()) != null) {

            ((CopyOnWriteArraySet<DownloadListener>) this.mCurrentList
                    .get(downloadTask.toKey())).add(downloadListener);
        } else {
            CopyOnWriteArraySet<DownloadListener> copyOnWriteArraySet = new CopyOnWriteArraySet<DownloadListener>();
            this.mCurrentList.put(downloadTask.toKey(), copyOnWriteArraySet);
            ((CopyOnWriteArraySet<DownloadListener>) this.mCurrentList
                    .get(downloadTask.toKey())).add(downloadListener);
        }
    }

    public CopyOnWriteArraySet<DownloadListener> getCurrentTaskListItem(
            DownloadTask downloadTask) {
        CopyOnWriteArraySet<DownloadListener> copyOnWriteArraySet = new CopyOnWriteArraySet<DownloadListener>();
        if (this.mCurrentList.get(downloadTask.toKey()) != null) {
            copyOnWriteArraySet = (CopyOnWriteArraySet<DownloadListener>) this.mCurrentList
                    .get(downloadTask.toKey());
        }
        return copyOnWriteArraySet;
    }

    public void removeListener(DownloadTask downloadTask,
                               DownloadListener downloadListener) {
        if (downloadListener == null) {
            this.mCurrentList.remove(downloadTask.toKey());
        } else {
            if (this.mCurrentList.get(downloadTask.toKey()) != null && this.mCurrentList.get(downloadTask.toKey()).contains(
                    downloadListener)) {
                this.mCurrentList.get(downloadTask.toKey()).remove(
                        downloadListener);
            }
        }
    }

    public boolean isUrlDownloaded(String paramString) {
        boolean bool = false;
        return bool;
    }

    public boolean isfileExsit(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }
}
