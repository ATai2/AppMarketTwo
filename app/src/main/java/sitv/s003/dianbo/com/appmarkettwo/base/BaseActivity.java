package sitv.s003.dianbo.com.appmarkettwo.base;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.SitvApplication;
import sitv.s003.dianbo.com.appmarkettwo.customview.CustomProgressDialog;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BasePresenter;
import sitv.s003.dianbo.com.appmarkettwo.util.ConstantUtil;
import sitv.s003.dianbo.com.appmarkettwo.volley.VolleyErrorHelper;

/**
 * Created by wx on 3/4 0004.
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends FragmentActivity implements ConstantUtil, BaseMvpView {

    private static final String TAG = "BaseActivity";
    public CustomProgressDialog progressDialog;
    protected BaseActivity mActivity;
    public Dialog dialog;
    private RelativeLayout layout;
    private LayoutInflater mInflater;

    public T presenter;

    public String cookies;
    public String epg;
    public String serviceGroupId;
    public String smartCard;
    public String stb_id;


    private  boolean isImplementInterface; //判断当前的V是否继承MvpPlayView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mInflater = LayoutInflater.from(mActivity);
        SharedPreferences ferences = mActivity.getSharedPreferences("sitv", 0);
        if (ferences != null) {
            cookies = ferences.getString("cookies", "");
            epg = ferences.getString("epg", "");
            serviceGroupId = ferences.getString("serviceGroupId", "");
           // smartCard = ferences.getString("smartCard", "");
             smartCard = SMARTCARD;
            stb_id = ferences.getString("stb_id", "");
        }
        presenter = initPresenter();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (presenter != null) {
            presenter.attach((V) mActivity);
        }
    }

    public void setBackground(RadioButton radOne,RadioButton radTwo,RadioButton radThree,RadioButton radFour){
        radOne.setBackgroundResource(R.drawable.title_bg);
        radTwo.setBackground(null);
        radThree.setBackground(null);
        radFour.setBackground(null);
        radOne.setSelected(true);
        radTwo.setSelected(false);
        radThree.setSelected(false);
        radFour.setSelected(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dettach();
        }
        SitvApplication.getInstance().removeActivity(mActivity);
    }

    //实例化 Presenter
    public abstract T initPresenter();

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void hideLoading() {
        stopProgressDialog();
    }

    @Override
    public void showMessage(Message msg) {
        Toast.makeText(mActivity,
                VolleyErrorHelper.getMessage(msg.obj, mActivity),
                Toast.LENGTH_LONG).show();
        stopProgressDialog();
    }


    public void hideKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(mActivity);
            progressDialog.setMessage("");
        }
        progressDialog.show();
    }

    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
