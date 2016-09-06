package sitv.s003.dianbo.com.appmarkettwo.download;

public abstract interface DownloadListener {

	public abstract void onDownloadFail();

	public abstract void onDownloadFinish(String filepath);

	public abstract void onDownloadPause();

	public abstract void onDownloadProgress(int finishedSize, int totalSize,
											int speed);

	public abstract void onDownloadStart();

	public abstract void onDownloadStop();
}
