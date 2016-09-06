package sitv.s003.dianbo.com.appmarkettwo.util;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.db.DBContract;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadState;
import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTask;


public class DownloadDBHelper {

    private Context mContext;
    public SharedPreferences mItemState;

    public DownloadDBHelper(Context context) {
        this.mContext = context;
        mItemState = mContext.getSharedPreferences("ItemState", 0);
    }

    private ContentValues makeContentValue(DownloadTask downloadTask) {
        ContentValues v = new ContentValues();
        v.put("url", downloadTask.getUrl());
        v.put("pid", downloadTask.getPid());
        v.put("packagename", downloadTask.getPackageName());
        v.put("downloadState", downloadTask.getDownloadState().toString());
        v.put("filepath", downloadTask.getFilePath());
        v.put("filename", downloadTask.getFileName());
        v.put("title", downloadTask.getTitle());
        v.put("thumbnail", downloadTask.getThumbnail());
        v.put("finishedSize", Integer.valueOf(downloadTask.getFinishedSize()));
        v.put("totalSize", Integer.valueOf(downloadTask.getTotalSize()));
        v.put("icon", downloadTask.getIconUrl());
        return v;
    }

    public void updateItem(DownloadTask downloadTask) {

        ArrayList<ContentProviderOperation> localArrayList = new ArrayList<>();
        ContentValues v = makeContentValue(downloadTask);

        ContentProviderOperation.Builder localBuilder = ContentProviderOperation
                .newUpdate(DBContract.Download.CONTENT_URI);
        localBuilder.withValues(v).withSelection("pid=?",
                new String[]{v.get("pid").toString()});
        localArrayList.add(localBuilder.build());
        try {
            this.mContext.getContentResolver().applyBatch("sitv.s003.dianbo.com.appmarkettwo",
                    localArrayList);
            return;
        } catch (RemoteException localRemoteException) {
            localRemoteException.printStackTrace();
            return;
        } catch (OperationApplicationException ex) {
            ex.printStackTrace();
        }
    }

    public void saveItem(DownloadTask downloadTask) {
        saveItem(makeContentValue(downloadTask));
    }

    public void deleteItem(String key) {
        ArrayList<ContentProviderOperation> localArrayList = new ArrayList<ContentProviderOperation>();
        localArrayList.add(ContentProviderOperation
                .newDelete(DBContract.Download.CONTENT_URI)
                .withSelection("pid=?",
                        new String[]{key}).build());
        try {
            this.mContext.getContentResolver().applyBatch("sitv.s003.dianbo.com.appmarkettwo",
                    localArrayList);
            return;
        } catch (RemoteException localRemoteException) {
            localRemoteException.printStackTrace();
            return;
        } catch (OperationApplicationException ex) {
            ex.printStackTrace();
        }
    }

    public void saveItem(ContentValues v) {
        ArrayList<ContentProviderOperation> localArrayList = new ArrayList<ContentProviderOperation>();
        localArrayList.add(ContentProviderOperation
                .newDelete(DBContract.Download.CONTENT_URI)
                .withSelection("pid=?",
                        new String[]{v.get("pid").toString()}).build());

        ContentProviderOperation.Builder localBuilder = ContentProviderOperation
                .newInsert(DBContract.Download.CONTENT_URI);
        localBuilder.withValues(v);
        localArrayList.add(localBuilder.build());
        try {
            this.mContext.getContentResolver().applyBatch("sitv.s003.dianbo.com.appmarkettwo",
                    localArrayList);
            return;
        } catch (RemoteException localRemoteException) {
            localRemoteException.printStackTrace();
            return;
        } catch (OperationApplicationException ex) {
            ex.printStackTrace();
        }
    }

    public List<DownloadTask> getDownloading() {

        DownloadTask item = null;
        List<DownloadTask> result = new ArrayList<DownloadTask>();
        Cursor cursor = this.mContext.getContentResolver().query(
                DBContract.Download.CONTENT_URI,
                new String[]{"url", "downloadState", "pid", "packagename",
                        "filepath", "filename", "title", "thumbnail",
                        "finishedSize", "totalSize", "icon"}, "",
                null, null);

        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        item = new DownloadTask();
                        item.setUrl(cursor.getString(0));
                        item.setDownloadState(DownloadState.valueOf(cursor.getString(1)));
                        item.setPid(cursor.getInt(2));
                        item.setPackageName(cursor.getString(3));
                        item.setFilePath(cursor.getString(4));
                        item.setFileName(cursor.getString(5));
                        item.setTitle(cursor.getString(6));
                        item.setThumbnail(cursor.getString(7));
                        item.setFinishedSize(cursor.getInt(8));
                        item.setTotalSize(cursor.getInt(9));
                        item.setIconUrl(cursor.getString(10));
                        result.add(item);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return result;
    }

    public boolean getItemState(String packageName) {
        boolean fag = mItemState.getBoolean(packageName, true);
        return fag;
    }

    public void setItemState(String packageName, boolean fag) {
        Editor ed = mItemState.edit();
        ed.putBoolean(packageName, fag);
        ed.commit();

    }

    public DownloadTask getItem(String pid) {
        DownloadTask item = null;

        Cursor cursor = this.mContext.getContentResolver().query(
                DBContract.Download.CONTENT_URI,
                new String[]{"url", "downloadState", "pid", "packagename",
                        "filepath", "filename", "title", "thumbnail",
                        "finishedSize", "totalSize"}, "pid = ?",
                new String[]{pid}, null);
        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    item = new DownloadTask();
                    if (cursor.moveToNext()) {
                        item = new DownloadTask();
                        item.setUrl(cursor.getString(0));
                        item.setDownloadState(DownloadState.valueOf(cursor
                                .getString(1)));
                        item.setPid(cursor.getInt(2));
                        item.setPackageName(cursor.getString(3));
                        item.setFilePath(cursor.getString(4));
                        item.setFileName(cursor.getString(5));
                        item.setTitle(cursor.getString(6));
                        item.setThumbnail(cursor.getString(7));
                        item.setFinishedSize(cursor.getInt(8));
                        item.setTotalSize(cursor.getInt(9));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return item;
    }

}
