package sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.SitvApplication;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.bean.SearchTag;
import sitv.s003.dianbo.com.appmarkettwo.fragment.SearchFrament;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BasePresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpSearchView;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceURL;
import sitv.s003.dianbo.com.appmarkettwo.volley.GsonRequest;


/**
 * Created by wx on 6/6 0006.
 * 大家都在收索内容Presenter
 */
public class MvpSearchPresenter extends BasePresenter<MvpSearchView> {
public String paramsKey;//大家都在收索的关键字

    public void onResume() {

        GsonRequest jsonObjectRequest = new GsonRequest(InterfaceURL.getSearchTags()+"?count="+"6"+"&usercode="+"1", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                   String result =  response.getJSONObject("response").getJSONObject("body").getJSONArray("dataList").toString();

                     List<SearchTag> mDatas  = new Gson().fromJson(result,
                    new TypeToken<ArrayList<SearchTag>>() {
                            }.getType());

                    mView.setRecyclerItemTag(mDatas);
              //    mView.hideLoading();
                } catch (Exception e) {
                    mView.dellItemTAgfailure();
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.showMessage(errorMessage(error));
                mView.dellItemTAgfailure();
            }
        });
        SitvApplication.getInstance().addToRequestQueue(jsonObjectRequest, SearchFrament.TAG);

    }
    public  void LoadAppInfos(CharSequence Key){
        try{
           paramsKey = URLEncoder.encode(Key.toString(), "UTF-8");
            GsonRequest jsonObjectRequest = new GsonRequest(InterfaceURL.getSearch()+"?type="+"5"+"&keyword="+paramsKey+"&usercode="+"1", new Response.Listener<JSONObject>() {

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
                        mView.dellItemTAgLoadfailure();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.showMessage(errorMessage(error));
                    mView.dellItemTAgLoadfailure();
                }
            });
            SitvApplication.getInstance().addToRequestQueue(jsonObjectRequest, SearchFrament.APPINFO_TAG);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
