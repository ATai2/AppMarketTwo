package sitv.s003.dianbo.com.appmarkettwo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "eat.db";
	private static final int DB_VERSION = 2;
	// private static Context mContext;
	private static DatabaseHelper sInstance;

	public DatabaseHelper(Context paramContext) {
		super(paramContext, DB_NAME, null, DB_VERSION);
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, null, 4);
	}

	public synchronized static DatabaseHelper getInstance(Context context) {
		try {
			if (sInstance == null) {
				sInstance = new DatabaseHelper(context);
				// mContext = context;
			}
			DatabaseHelper localDatabaseHelper = sInstance;
			return localDatabaseHelper;

		} catch (Exception e) {
			return null;
		} finally {

		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table users(uid text primary key,name text null,email text null,phone text null,token text null,login_time datetime);");
		db.execSQL("create table cache(key text primary key,content text not null,etag text not null,account_id text);");
		db.execSQL("create table download(id integer primary key autoincrement,pid integer unique,packagename text unique,url text , downloadState text,filepath text, filename text, title text, thumbnail text, finishedSize integer, totalSize integer, icon text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	public static abstract interface Tables {
		public static final String CACHE = "cache";
		public static final String CATEGORY = "category";
		public static final String DATASTATS = "data_stats";
		public static final String REGION = "region";
	}

}
