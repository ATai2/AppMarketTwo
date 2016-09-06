package sitv.s003.dianbo.com.appmarkettwo.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ShopProvider extends ContentProvider {
	private static final UriMatcher mMatcher = new UriMatcher(-1);
	private DatabaseHelper mHelper;

	static {
		mMatcher.addURI("sitv.s003.dianbo.com.appmarkettwo", "category", 1);
		mMatcher.addURI("sitv.s003.dianbo.com.appmarkettwo", "category/#", 2);
		mMatcher.addURI("sitv.s003.dianbo.com.appmarkettwo", "cache", 3);
		mMatcher.addURI("sitv.s003.dianbo.com.appmarkettwo", "cache/#", 4);
		mMatcher.addURI("sitv.s003.dianbo.com.appmarkettwo", "download", 5);
		mMatcher.addURI("sitv.s003.dianbo.com.appmarkettwo", "download/#", 6);
	}

	@Override
	public int delete(Uri url, String arg1, String[] arg2) {
		SQLiteDatabase db = this.mHelper.getWritableDatabase();
		int i = 0;
		switch (mMatcher.match(url)) {
		case 1:
		case 2:
		case 3:
			i = db.delete("cache", arg1, arg2);
			break;
		case 5:
		case 6:
			i = db.delete("download", arg1, arg2);
			break;
		default:
			break;
		}

		return i;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri url, ContentValues values) {
		SQLiteDatabase db = this.mHelper.getWritableDatabase();

		long l = 0;
		switch (mMatcher.match(url)) {
		case 1:
		case 2:
		case 3:
			l = db.insert("cache", null, values);
			break;
		case 5:
		case 6:
			l = db.insert("download", null, values);
			break;
		default:
			break;
		}

		getContext().getContentResolver().notifyChange(
				ContentUris.withAppendedId(url, l), null);
		return ContentUris.withAppendedId(url, l);
	}

	@Override
	public boolean onCreate() {
		this.mHelper = DatabaseHelper.getInstance(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri url, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		SQLiteDatabase db = this.mHelper.getReadableDatabase();
		Cursor cursor = null;
		switch (mMatcher.match(url)) {
		case 1:
		case 2:
		case 3:
			cursor = db.query("cache", arg1, arg2, arg3, null, null, arg4);
			break;
		case 5:
		case 6:
			cursor = db.query("download", arg1, arg2, arg3, null, null, arg4);
			break;
		default:
			break;
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = this.mHelper.getWritableDatabase();
		int i = 0;
		switch (mMatcher.match(uri)) {
		case 1:
		case 2:
		case 3:
			i = db.update("cache", values, selection, selectionArgs);
			break;
		case 5:
		case 6:
			i = db.update("download", values, selection, selectionArgs);
			break;
		default:
			break;
		}

		return i;
	}

}
