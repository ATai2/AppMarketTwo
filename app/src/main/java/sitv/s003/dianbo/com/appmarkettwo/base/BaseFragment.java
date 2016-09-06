package sitv.s003.dianbo.com.appmarkettwo.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import sitv.s003.dianbo.com.appmarkettwo.customview.CustomProgressDialog;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BaseMvpView;
import sitv.s003.dianbo.com.appmarkettwo.mvp.base.BasePresenter;
import sitv.s003.dianbo.com.appmarkettwo.util.ConstantUtil;
import sitv.s003.dianbo.com.appmarkettwo.volley.VolleyErrorHelper;


public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements ConstantUtil, BaseMvpView {

    private static final String TAG = "BaseFragment";
    public View mView;     // 控件句柄
    protected BaseActivity mActivity;        // 对应的Activity

    public CustomProgressDialog progressDialog;
    protected boolean bInitView = false;    // 是否初始化了控件

    public String smartCard, cookies, epg;

    public T presenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        SharedPreferences ferences = mActivity.getSharedPreferences("sitv", 0);
        if (ferences != null) {
            cookies = ferences.getString("cookies", "");
            epg = ferences.getString("epg", "");
            // smartCard = ferences.getString("smartCard", "");
            smartCard = SMARTCARD;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bInitView = true;
        mView = inflater.inflate(getLayoutResID(), container, false);
        ButterKnife.bind(this, mView);
        initViews();
        presenter = initPresenter();
        return mView;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (presenter != null) {
            presenter.attach((V) this);
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (presenter != null) {
            presenter.dettach();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 设置布局文件
     */
    protected int getLayoutResID() {
        return 0;
    }

    /**
     * 初始化布局控件
     */
    protected void initViews() {

    }

    /**
     * 查找控件
     */
    protected View findViewById(int id) {
        return mView.findViewById(id);
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

}
