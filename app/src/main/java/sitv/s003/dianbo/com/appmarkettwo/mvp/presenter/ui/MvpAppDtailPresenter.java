package sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import sitv.s003.dianbo.com.appmarkettwo.SitvApplication;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.fragment.AppDetailFragment;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BasePresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpAppDetaliView;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceURL;
import sitv.s003.dianbo.com.appmarkettwo.volley.GsonRequest;

/**
 * Created by Administrator on 2016/8/15.
 */

public class MvpAppDtailPresenter extends BasePresenter<MvpAppDetaliView> {
    public void onResume(int mAppId) {
        GsonRequest jsonObjectRequest = new GsonRequest(InterfaceURL.getAppDetail()+"?id="+mAppId+"&usercode="+"1", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result =  response.getJSONObject("response").getJSONObject("body").getJSONArray("dataList").get(0).toString();
                    Log.i("MvpAppDtailPresenter", result);
                    AppInfo mDatas  = new Gson().fromJson(result,
                            new TypeToken<AppInfo>() {
                            }.getType());
                    mView.setRecyclerItem(mDatas);
                    mView.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.showMessage(errorMessage(error));

            }
        });
        SitvApplication.getInstance().addToRequestQueue(jsonObjectRequest, AppDetailFragment.TAG);

    }
}
