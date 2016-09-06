package sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

import sitv.s003.dianbo.com.appmarkettwo.SitvApplication;
import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.fragment.GameFrament;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BasePresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpGameView;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceParams;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceURL;
import sitv.s003.dianbo.com.appmarkettwo.util.ConstantUtil;
import sitv.s003.dianbo.com.appmarkettwo.volley.GsonRequest;

/**
 * Created by ly on 2016/8/11.
 */

public class MvpGamePresenter extends BasePresenter<MvpGameView> {

    public void onResume() {
        GsonRequest jsonObjectRequest = new GsonRequest(InterfaceURL.getGameURL(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result =  response.getJSONObject("response").getJSONObject("body").getJSONArray("dataList").toString();
                    ArrayList<CategoryInfo> mDatas = new Gson().fromJson(result,
                            new TypeToken<ArrayList<CategoryInfo>>() {
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
        }, InterfaceParams.getGameParams(ConstantUtil.GAME_URL_COUNT,ConstantUtil.GAME_URL_PARENTID,ConstantUtil.APP_URL_VALUE_USERCODE));
        SitvApplication.getInstance().addToRequestQueue(jsonObjectRequest, GameFrament.TAG);
    }
}