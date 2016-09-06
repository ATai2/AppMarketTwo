package sitv.s003.dianbo.com.appmarkettwo.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {
	public static final String AUTHORITY = "com.keep.chwl.eat";
	public static final Uri AUTHORITY_URI = Uri
			.parse("content://sitv.s003.dianbo.com.appmarkettwo");
	//content://com.keep.chwl.eat

	public static final class Cache implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(
				DBContract.AUTHORITY_URI, "cache");

		public static final String ACCOUNT_ID = "account_id";
		public static final String CONTENT = "content";
		public static final String DIRECTORY = "cache";
		public static final String ETAG = "etag";
		public static final String KEY = "key";
	}
	
	public static final class Download implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(
				DBContract.AUTHORITY_URI, "download");

		public static final String ID = "id";
		public static final String URL = "url";
		public static final String DOWNLOADSTATE = "downloadState";
		public static final String PACKAGENAME = "packagename";
		public static final String PID = "pid";
		public static final String FILEPATH = "filepath";
		public static final String FILENAME = "filename";
		public static final String TITLE= "title";
		public static final String THUMBNAIL= "thumbnail";
		public static final String FINISHEDSIZE= "finishedSize";
		public static final String TOTALSIZE= "totalSize";
		public static final String ICON= "icon";
	}
	

	// 账号表
	public static final class Users implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.withAppendedPath(
				DBContract.AUTHORITY_URI, "users");

		public static final String UID = "uid";
		public static final String NAME = "name";
		public static final String EMAIL = "email";
		public static final String PHONE = "phone";
		public static final String TOKEN = "token";
		public static final String LOGIN_TIME = "login_time";
	}

}
