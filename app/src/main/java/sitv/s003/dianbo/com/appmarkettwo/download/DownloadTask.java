package sitv.s003.dianbo.com.appmarkettwo.download;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.webkit.URLUtil;


import sitv.s003.dianbo.com.appmarkettwo.util.Coder;

public class DownloadTask {

	private int id;
	private int pid;
	private String packageName;
	private String url;
	private String fileName;
	private String title;
	private String thumbnail;
	private String filePath;
	private int finishedSize;
	private int totalSize;
	private int percent;
	private int speed;
	private Drawable icon; // 应用程序图像
	private String iconUrl; // 应用程序图像Url

	private volatile DownloadState downloadState;//下载过程中有6钟状态

	public String toKey() {
		return Coder.encodeSHA(this.packageName);
	}

	public DownloadTask() {
	}

	public DownloadTask(int pid, String packageName, String url,
			String fileName, String title, String thumbnail, String filePath, String iconUrl) {

		if (!URLUtil.isHttpUrl(url))
			throw new IllegalArgumentException(
					"invalid url,nust start with http://");
		if (TextUtils.isEmpty(title))
			throw new IllegalArgumentException("invalid fileName");
		if (TextUtils.isEmpty(packageName))
			throw new IllegalArgumentException("invalid packageName");
		if (pid <= 0)
			throw new IllegalArgumentException("invalid pid");

		this.pid = pid;
		this.packageName = packageName;
		this.url = url;
		this.fileName = fileName;
		this.title = title;
		this.thumbnail = thumbnail;
		this.filePath = filePath;
		this.iconUrl=iconUrl;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFinishedSize() {
		return finishedSize;
	}

	public void setFinishedSize(int finishedSize) {
		this.finishedSize = finishedSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public DownloadState getDownloadState() {
		return downloadState;
	}

	public void setDownloadState(DownloadState downloadState) {
		this.downloadState = downloadState;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean equals(Object obj) {
		boolean bool = true;
		if (this == obj)
			return bool;

		if (obj == null) {
			bool = false;
		} else if (getClass() != obj.getClass()) {
			bool = false;
		} else {
			DownloadTask task = (DownloadTask) obj;
			if (this.getFileName() == null) {
				if (task.getFileName() != null)
					bool = false;
			} else if (!this.getFileName().equals(task.getFileName()))
				bool = false;
			else if (this.getFilePath() == null) {
				if (task.getFilePath() != null)
					bool = false;
			} else if (!this.getFilePath().equals(task.getFilePath()))
				bool = false;
			else if (this.getUrl() == null) {
				if (task.getUrl() != null)
					bool = false;
			} else if (!this.getUrl().equals(task.getUrl()))
				bool = false;
		}

		return bool;
	}

}
