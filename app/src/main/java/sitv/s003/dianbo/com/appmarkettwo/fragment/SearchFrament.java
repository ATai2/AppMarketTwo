package sitv.s003.dianbo.com.appmarkettwo.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.SearchAppListAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.bean.SearchTag;
import sitv.s003.dianbo.com.appmarkettwo.customview.FixGridLayout;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpSearchPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpSearchView;
import sitv.s003.dianbo.com.appmarkettwo.util.ActivityUtils;
import sitv.s003.dianbo.com.appmarkettwo.util.QRCodeEncoderTest;

/**
 * Created by wx on 8/10 0010.
 * 搜索
 */
public class SearchFrament extends BaseFragment<MvpSearchView,MvpSearchPresenter>implements MvpSearchView,View.OnClickListener{
    public static final String TAG = "SearchFrament";
    public static final String APPINFO_TAG = "appInfo_Tag";
    public EditText mEnter; // 文本框显示的内容
    private ListView mListView;
    private RelativeLayout mRelativePrompt;
    private LinearLayout mlinearNoResult;
    LinearLayout lineardynamic;
    private ImageView mCode;
    FixGridLayout fixGridLayout;
    private String mKey = "";
    private boolean isHasDateTag = false;
    @Override
    public MvpSearchPresenter initPresenter() {
        return new MvpSearchPresenter();
    }
    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViews() {
        mEnter = (EditText) findViewById(R.id.et_text);
        mRelativePrompt = (RelativeLayout) findViewById(R.id.rl_prompt);
        mlinearNoResult = (LinearLayout) findViewById(R.id.linear_noresult);
        lineardynamic = (LinearLayout) findViewById(R.id.lineardynamic);
        mListView = (ListView) findViewById(R.id.lv_info);
        mCode = (ImageView) findViewById(R.id.iv_code);
        mListView.setVisibility(View.GONE);
        //收索edittext文字监听
        mEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEnterOnTextChanged(s,start,before,count);
            }
        });
        //二维码生成
        try {
            Bitmap bitmap =  QRCodeEncoderTest.Create2DCode("http://www.baidu.com");
            mCode.setImageBitmap(bitmap);
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(!isHasDateTag){
            presenter.onResume();
            isHasDateTag = true;
        }
    }
    @Override
    public void setRecyclerItemTag(List<SearchTag> mCategoryList) {
        fixGridLayout = (FixGridLayout) findViewById(R.id.ll_wrap);
        // 为fixGridLayout控件设置标题
        final TextView title = new TextView(mActivity);
        title.setTextColor(getResources().getColor(R.color.blue));
        title.setTextSize(20);
        title.setText(getResources().getString(R.string.search_found));
        title.setPadding(10, 10, 5, 2);
        fixGridLayout.addView(title);
        showComonSearch(mCategoryList);
    }
    //网络加载失败后真正处理逻辑
    @Override
    public void dellItemTAgfailure() {
        mListView.setVisibility(View.GONE);
        mlinearNoResult.setVisibility(View.VISIBLE);
    }
    @Override
    public void dellItemTAgLoadfailure() {
        mRelativePrompt.setVisibility(View.GONE);
        mlinearNoResult.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }
    /**
     * 展示大家都在收索
     * @param searchTagInfos
     */
     private void showComonSearch(List<SearchTag> searchTagInfos) {

        for (int i=0;i<searchTagInfos.size();i++) {
            LinearLayout layout = new LinearLayout(getActivity());
            layout.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            setLoyoutParams(layout);
            final Button btn = new Button(getActivity());
            setBtnParams(searchTagInfos, i, btn);
            final TextView tv = new TextView(mActivity);
            setTitleParamsw(searchTagInfos, i, layout, btn, tv);
            fixGridLayout.addView(layout);
            layout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        btn.setTextColor(getResources().getColor(R.color.white));
                        btn.setNextFocusUpId(R.id.et_text);
                    }
                    else{
                        btn.setTextColor(getResources().getColor(R.color.main_bar_text_color));
                    }
                }
            });
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealLayOutOnclicked(tv);
                }
            });
            layout.setNextFocusUpId(R.id.et_text);
       }
    }
    /**
     * @des  设置title参数
     * @param searchTagInfos
     * @param i
     * @param layout
     * @param btn
     * @param tv
     */
    private void setTitleParamsw(List<SearchTag> searchTagInfos, int i, LinearLayout layout, Button btn, TextView tv) {
        tv.setVisibility(View.GONE);
        tv.setTextColor(Color.WHITE);
        tv.setText(searchTagInfos.get(i).getName());
        layout.addView(btn);
        layout.addView(tv);
        layout.setPadding(5, 2, 5, 2);
    }
    /**
     * @des 设置btn参数
     * @param searchTagInfos
     * @param i
     * @param btn
     */
    private void setBtnParams(List<SearchTag> searchTagInfos, int i, Button btn) {
        btn.setBackgroundColor(0x000);
        btn.setTextColor(getResources().getColor(R.color.main_bar_text_color));
        btn.setPadding(5, 2, 5, 2);
        btn.setTextSize(20);
        btn.setFocusable(true);
        btn.setText(searchTagInfos.get(i).getName());
    }
    /**
     * @des 设置layout参数
     * @param layout
     */
    private void setLoyoutParams(LinearLayout layout) {
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 50));
        layout.setBackgroundResource(R.drawable.border_search_selector);
        layout.setFocusable(true);
        layout.setGravity(Gravity.CENTER);
    }
    /**
     * @des   当文本输入框中文字监听改变时调用此方法
     * @param mstring 关键字
     * @param start
     * @param before
     * @param count
     */
    private void mEnterOnTextChanged(CharSequence mstring, int start, int before, int count) {
        mKey = mstring.toString();
        if (!"".equals(mKey)) {
            mRelativePrompt.setVisibility(View.GONE);
            mlinearNoResult.setVisibility(View.GONE);
            presenter.LoadAppInfos(mKey);
        }else{
            mRelativePrompt.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            mlinearNoResult.setVisibility(View.GONE);
        }
    }
    /**
     * @des   收索完成后刷新界面
     * @param mCategoryList
     */
    @Override
    public void setRecyclerItem(List<AppInfo> mCategoryList) {
        Log.i("SearchFrament", mCategoryList.toString());
        mListView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);   //加载成功后显示界面
        mlinearNoResult.setVisibility(View.GONE);//不加上把网线插拔会有收索为空bug
        SearchAppListAdapter adapter = new SearchAppListAdapter(this.mActivity,mCategoryList);
        mListView.setAdapter(adapter);
        final List<AppInfo> mCategoryListTemp = mCategoryList;
        SecondJudgment();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int mid = mCategoryListTemp.get(position).getId();
                ActivityUtils.goAppActivity(mActivity, mid);
            }
        });
    }
    /**
     * @des   tag被点击后执行网络加载
     * @param tv
     */
    private void dealLayOutOnclicked(TextView tv) {
        mKey = tv.getText().toString();
        if (!"".equals(mKey)) {
            mlinearNoResult.setVisibility(View.GONE);
            mRelativePrompt.setVisibility(View.GONE);
            presenter.LoadAppInfos(mKey);
        }
    }
    /**
     * 实现点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lv_info:
                break;
        }
    }
    /**
     * 双重判断修复收索功能切换bug
     */
    private void SecondJudgment() {
        if(mEnter.getText().toString().equals("")){
            mlinearNoResult.setVisibility(View.GONE);
            mRelativePrompt.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
    }
}
