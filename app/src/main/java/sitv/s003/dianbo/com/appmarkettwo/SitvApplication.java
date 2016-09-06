package sitv.s003.dianbo.com.appmarkettwo;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import sitv.s003.dianbo.com.appmarkettwo.base.BaseActivity;
import sitv.s003.dianbo.com.appmarkettwo.util.ConstantUtil;


public class SitvApplication extends Application implements ConstantUtil {

	public static final String TAG="SitvApplication";

	private RequestQueue mRequestQueue;

	private static SitvApplication mSitvApp;

	private static Context sContext;

	public static Context getContext() {
		return sContext;
	}

	public String volleyTag = STRING_DEFAULT_EMPTY;
	public Map<String,Boolean> mDownloadMap = new HashMap<>(); // 监听是否在下载


	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
		mSitvApp=this;
	}

	public static synchronized SitvApplication  getInstance(){
		return mSitvApp;
	}

	public RequestQueue getRequestQueue(){
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return mRequestQueue;
	}
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public void removeActivity(BaseActivity mActivity) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(mActivity);
		}
	}
}
