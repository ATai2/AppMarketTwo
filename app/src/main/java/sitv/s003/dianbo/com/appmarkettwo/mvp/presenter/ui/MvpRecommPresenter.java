package sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

import sitv.s003.dianbo.com.appmarkettwo.SitvApplication;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.fragment.RecomFrament;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BasePresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpRecomView;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceURL;
import sitv.s003.dianbo.com.appmarkettwo.volley.GsonRequest;


/**
 * Created by wx on 6/6 0006.
 * 动作/科幻等界面的 Presenter
 */
public class MvpRecommPresenter extends BasePresenter<MvpRecomView> {
    public void onResume() {

        GsonRequest jsonObjectRequest = new GsonRequest(InterfaceURL.getRecommend()+"?type=3"+"&count=8"+"&usercode=1", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result =  response.getJSONObject("response").getJSONObject("body").getJSONArray("dataList").toString();
                     ArrayList<AppInfo> mDatas  = new Gson().fromJson(result,
                    new TypeToken<ArrayList<AppInfo>>() {
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
        SitvApplication.getInstance().addToRequestQueue(jsonObjectRequest, RecomFrament.TAG);

    }
}
