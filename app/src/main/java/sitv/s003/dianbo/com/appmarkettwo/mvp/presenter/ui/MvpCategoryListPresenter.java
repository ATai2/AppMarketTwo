package sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.SitvApplication;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.fragment.SearchFrament;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BasePresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpCategoryListView;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceURL;
import sitv.s003.dianbo.com.appmarkettwo.volley.GsonRequest;

/**
 * Created by Administrator on 2016/8/19.
 */
public class MvpCategoryListPresenter extends BasePresenter<MvpCategoryListView> {
    public void onResume(String id) {

        GsonRequest jsonObjectRequest = new GsonRequest(InterfaceURL.getApplist() + "?type=0" + "&category=" + id + "&usercode=1", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("wuxiao","2");
                try {
                    String result =  response.getJSONObject("response").getJSONObject("body").getJSONArray("dataList").toString();

                    List<AppInfo> mDatas  = new Gson().fromJson(result,
                            new TypeToken<ArrayList<AppInfo>>() {
                            }.getType());
                    mView.setRecyclerItem(mDatas);
                    //    mView.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("wuxiao","3");
                mView.showMessage(errorMessage(error));
             //   mView.dellItemTAgfailure();
            }
        });
        SitvApplication.getInstance().addToRequestQueue(jsonObjectRequest, SearchFrament.TAG);

    }
}
