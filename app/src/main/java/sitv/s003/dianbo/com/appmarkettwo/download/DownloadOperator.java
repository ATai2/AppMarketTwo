package sitv.s003.dianbo.com.appmarkettwo.download;

import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class DownloadOperator extends AsyncTask<String, Integer[], String> {

    private DownloadTask mDownloadTask;
    private DownloadTaskManager mDownloadTaskManager;
    private volatile boolean mPause = false;
    private volatile boolean mStop = false;
//    写的网络连接，可换用框架
    private HttpURLConnection connection = null;
    private InputStream inputStream = null;
//    不同于stream类，已经被JDK 1.4的nio的"内存映射文件(memory-mapped files)"给取代了，
//   该考虑一下是不是用"内存映射文件"来代替RandomAccessFile了。
    private RandomAccessFile outputStream = null;

    public DownloadOperator(DownloadTaskManager TaskManager,
                            DownloadTask DownloadTask) {
        this.mDownloadTask = DownloadTask;
        this.mDownloadTaskManager = TaskManager;
    }

    /**
     * 预处理方法，将下载状态设置为DOWNLOADING，
     */
    protected void onPreExecute() {

        boolean result = false;
        try {
            this.mDownloadTask.setDownloadState(DownloadState.DOWNLOADING);
            this.mDownloadTaskManager.updateDownloadTask(this.mDownloadTask);
            Iterator<DownloadListener> iterator = this.mDownloadTaskManager
                    .getCurrentTaskListItem(this.mDownloadTask).iterator();
            while (iterator.hasNext()) {
                ((DownloadListener) iterator.next()).onDownloadStart();
            }
//            获取文件头的信息，使用head方法
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    this.mDownloadTask.getUrl()).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Accept-Encoding", "musixmatch");
            connection.setRequestMethod("HEAD");
            int i = connection.getContentLength();
            this.mDownloadTask.setTotalSize(i);
            connection.disconnect();
            File path = new File(this.mDownloadTask.getFilePath());
            if (!path.exists())
                path.mkdirs();

            File file = new File(this.mDownloadTask.getFilePath()
                    + this.mDownloadTask.getFileName());

            if (!file.exists()) {
                file.createNewFile();
                this.mDownloadTask.setFinishedSize(0);
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,
                    "rwd");
            if (i > 0)
                randomAccessFile.setLength(i);
            randomAccessFile.close();
            result = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!result) {
            Iterator<DownloadListener> iterator = this.mDownloadTaskManager
                    .getCurrentTaskListItem(this.mDownloadTask).iterator();
            while (iterator.hasNext()) {

                ((DownloadListener) iterator.next()).onDownloadFail();

                this.mDownloadTask.setDownloadState(DownloadState.FAILED);
                this.mDownloadTaskManager
                        .updateDownloadTask(this.mDownloadTask);
            }
        }

    }
//    异步。网络连接，文件读取写入
    @Override
    protected String doInBackground(String... params) {

//		HttpURLConnection connection = null;
//		InputStream inputStream = null;
//		RandomAccessFile outputStream = null;
        String filePath = "";

        try {
            connection = (HttpURLConnection) new URL(
                    this.mDownloadTask.getUrl()).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("User-Agent", "NetFox");
            int startPosition = this.mDownloadTask.getFinishedSize();
            connection.setRequestProperty("Range", "bytes=" + startPosition
                    + "-");
            inputStream = connection.getInputStream();

            filePath = this.mDownloadTask.getFilePath()
                    + this.mDownloadTask.getFileName();

            File outFile = new File(filePath);
            if (!outFile.exists())
                outFile.createNewFile();
            outputStream = new RandomAccessFile(outFile, "rw");
            outputStream.seek(startPosition);
            byte[] buf = new byte[1024 * 1000];
            int read = 0;
            while (false == this.mStop) {
                if (this.mPause) {
                    this.mDownloadTask.setDownloadState(DownloadState.PAUSE);
                    this.mDownloadTaskManager
                            .updateDownloadTask(this.mDownloadTask);
                }

                while (true == this.mPause) {
                    Thread.sleep(500);
                }
                read = inputStream.read(buf);
                if (read == -1) {
                    break;
                }
                outputStream.write(buf, 0, read);
                this.mDownloadTask.setFinishedSize(mDownloadTask
                        .getFinishedSize() + read);
                int speed = (int) (this.mDownloadTask.getFinishedSize() * 100.0f / this.mDownloadTask
                        .getTotalSize());
                publishProgress(new Integer[]{
                        this.mDownloadTask.getFinishedSize(),
                        this.mDownloadTask.getTotalSize(), speed});

                if (this.mDownloadTask.getFinishedSize() == this.mDownloadTask
                        .getTotalSize()) {
                    this.mDownloadTask.setDownloadState(DownloadState.FINISHED);
                    break;
                }
                Thread.sleep(500);

            }
            if (false == this.mStop) {
                this.mStop = true;
            }

            if (this.mDownloadTask.getFinishedSize() != this.mDownloadTask
                    .getTotalSize()) {
                this.mDownloadTask.setDownloadState(DownloadState.FAILED);
            }
            this.mDownloadTaskManager.updateDownloadTask(this.mDownloadTask);

            inputStream.close();
            outputStream.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    @Override
    protected void onProgressUpdate(Integer[]... values) {
        super.onProgressUpdate(values);
        int finishedSize = values[0][0].intValue();
        int totalSize = values[0][1].intValue();
        int speed = values[0][2].intValue();
        Iterator<DownloadListener> iterator = this.mDownloadTaskManager
                .getCurrentTaskListItem(this.mDownloadTask).iterator();
        while (iterator.hasNext()) {
            ((DownloadListener) iterator.next()).onDownloadProgress(
                    finishedSize, totalSize, speed);
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Iterator<DownloadListener> iteratorEnd = this.mDownloadTaskManager
                .getCurrentTaskListItem(this.mDownloadTask).iterator();
        while (iteratorEnd.hasNext()) {
            if (this.mDownloadTask.getDownloadState() == DownloadState.FINISHED) {
                ((DownloadListener) iteratorEnd.next())
                        .onDownloadFinish(result);
            } else {
                ((DownloadListener) iteratorEnd.next()).onDownloadFail();
            }
        }

        this.mDownloadTaskManager.removeListener(this.mDownloadTask, null);
    }

    public void startDownload() {
        this.mPause = false;
        this.mStop = false;
        execute();
    }

    /**
     * @return 暂停下载
     */
    public void pause() {

        this.mPause = true;
        this.mStop = false;
    }

    /**
     * @return 恢复下载
     */
    public void resume() {
        //DownloadTaskManager.mTaskList.remove(mDownloadTask.getPackageName());
        //mDownloadTask = null;
        this.mPause = false;
        this.mStop = false;
    }

    /**
     * @return 获取已下载进度
     */
    public int getDownLoadedPercent() {
        try {
            return (int) (this.mDownloadTask.getFinishedSize() * 100.00 / this.mDownloadTask.getTotalSize());
        } catch (Exception e) {
            return 0;
        }
    }
}
