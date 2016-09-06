package sitv.s003.dianbo.com.appmarkettwo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.adapter.FragmentAdapter;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseActivity;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseFragment;
import sitv.s003.dianbo.com.appmarkettwo.customview.CustomViewPager;
import sitv.s003.dianbo.com.appmarkettwo.fragment.ApplicationFrament;
import sitv.s003.dianbo.com.appmarkettwo.fragment.GameFrament;
import sitv.s003.dianbo.com.appmarkettwo.fragment.RecomFrament;
import sitv.s003.dianbo.com.appmarkettwo.fragment.SearchFrament;
import sitv.s003.dianbo.com.appmarkettwo.mvp.presenter.ui.MvpRecommPresenter;
import sitv.s003.dianbo.com.appmarkettwo.mvp.view.ui.MvpRecomView;
import sitv.s003.dianbo.com.appmarkettwo.util.ToastUtil;

public class MainActivity extends BaseActivity<MvpRecomView, MvpRecommPresenter> implements View.OnFocusChangeListener {
    private static final String TAG = "MainActivity";
    public static final String FRAGMENT_TAG_A = "ft_tag_a";
    public static final String FRAGMENT_TAG_B = "ft_tag_b";
    public static final String FRAGMENT_TAG_C = "ft_tag_c";
    public static final String FRAGMENT_TAG_D = "ft_tag_d";
    private CustomViewPager mViewPager;
    private RadioGroup group;
    private RadioButton rabRecommended, rabApplication, rabGame, rabSearch;
    private BaseFragment mAppFragment, mGameFragment, mRecommendFragment;
    private SearchFrament mSearchFragment;
    private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
    private String str_id;
    private String str_packageName;
    private String str_image_url;
    private int type;
    private FragmentAdapter mFragmentAdapter;
    private int mPressedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 请求全屏广告
//		mManager = new AdManager(this, "http://180.168.93.163/api.php",
//				"ea251a5ae032c1703789648e81d160ec", true);
//		mManager.setListener(this);
//		mManager.requestAd();
//		Intent intent = getIntent();
//		if (intent != null) {
//			String callbackParams=intent.getStringExtra("callBackParams");
//			if(callbackParams!=null&&!callbackParams.equals("")){
//				System.out.println("callbackParams:"+callbackParams);
//				//String[] str = callbackParams.split("|");
//				StringTokenizer str = new StringTokenizer(callbackParams,"|");
//				str_id = str.nextToken();
//				System.out.println("str_id"+str_id);
//				str_packageName = str.nextToken();
//				System.out.println("str_packageName"+str_packageName);
//				str_image_url = str.nextToken();
//				System.out.println("str_image_url"+str_image_url);
//				if((str_id!=null&&!str_id.equals(""))&&(str_packageName!=null&&!str_packageName.equals(""))&&(str_image_url!=null&&!str_image_url.equals(""))){
//					Intent i = new Intent(this,AppActivity.class);
//					i.putExtra("extra_app_id", str_id);
//					i.putExtra("extra_app_packageName",str_packageName);
//					byte[] bys=Utils.getImage(str_image_url);
//					Bitmap bitmap = null;
//					try{
//						bitmap =BitmapFactory.decodeByteArray(bys, 0, bys.length) ;
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//					i.putExtra("extra_app_image",bitmap);
////					intent.putExtra("extra_app_image_url",str_image_url);
//					startActivity(i);
//				}
//				finish();
//			}else{
//				Toast.makeText(this,"没有数据，程序自动退出",Toast.LENGTH_SHORT).show();
//				finish();
//			}
//		}


//        Intent it = getIntent();
//        if (it != null) {
//            str_id = it.getStringExtra("extra_app_id");
//            str_packageName = it.getStringExtra("extra_app_packageName");
//            str_image_url = it.getStringExtra("extra_app_image_url");
//            if((str_id!=null&&!str_id.equals(""))&&(str_packageName!=null&&!str_packageName.equals(""))&&(str_image_url!=null&&!str_image_url.equals(""))){
//                Intent intent = new Intent(this,AppActivity.class);
//                intent.putExtra("extra_app_id", str_id);
//                intent.putExtra("extra_app_packageName",str_packageName);
//                byte[] bys=Utils.getImage(str_image_url);
//                Bitmap bitmap = null;
//                try{
//                    bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.length) ;
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                intent.putExtra("extra_app_image",bitmap);
//                //intent.putExtra("extra_app_image_url",str_image_url);
//                startActivity(intent);
//                finish();
//            }
//        }
        setContentView(R.layout.activity_main);
        initViews();
        init();
    }

    @Override
    public MvpRecommPresenter initPresenter() {
        return null;
    }

    private void initViews() {
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        group = (RadioGroup) findViewById(R.id.group);
        rabRecommended = (RadioButton) findViewById(R.id.rab_recommended);
        rabApplication = (RadioButton) findViewById(R.id.rab_application);
        rabGame = (RadioButton) findViewById(R.id.rab_game);
        rabSearch = (RadioButton) findViewById(R.id.rab_search);
        rabRecommended.requestFocus();
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setCurrentItem(ONE);
        if (mSearchFragment == null) {
            mSearchFragment = new SearchFrament();
        }
        fragments.add(new RecomFrament());
        fragments.add(new ApplicationFrament());
        fragments.add(new GameFrament());
        fragments.add(mSearchFragment);
    }

    private void init() {

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
        rabRecommended.setOnFocusChangeListener(this);
        rabApplication.setOnFocusChangeListener(this);
        rabGame.setOnFocusChangeListener(this);
        rabSearch.setOnFocusChangeListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mViewPager.hasFocus() && keyCode == event.KEYCODE_DPAD_UP) {
            switch (type) {
                case ONE:
                    mViewPager.findFocus().setNextFocusUpId(R.id.rab_recommended);
                    break;
                case TWO:
                    mViewPager.findFocus().setNextFocusUpId(R.id.rab_application);
                    break;
                case THREE:
                    mViewPager.findFocus().setNextFocusUpId(R.id.rab_game);
                    break;
                case FOUR:
                    if (mSearchFragment.mEnter.hasFocus()) {
                        mSearchFragment.mEnter.findFocus().setNextFocusUpId(R.id.rab_search);
                    }
                    break;
            }
        }

        return super.onKeyDown(keyCode, event);

    }


    /**
     * 功能：Fragment页面改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {
        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            type = position;
            switch (position) {
                case ONE:
                    setBackground(rabRecommended, rabApplication, rabGame, rabSearch);
                    break;
                case TWO:
                    setBackground(rabApplication, rabRecommended, rabGame, rabSearch);
                    break;
                case THREE:
                    setBackground(rabGame, rabRecommended, rabApplication, rabSearch);
                    break;
                case FOUR:
                    setBackground(rabSearch, rabRecommended, rabApplication, rabGame);
                    break;
            }
            mViewPager.setCurrentItem(position);
        }
    }

    /**
     * 功能：主页TAB获取焦点事件
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.rab_recommended:
                setBackground(rabRecommended, rabApplication, rabGame, rabSearch);
                mViewPager.setCurrentItem(0);//选择某一页
                break;
            case R.id.rab_application:
                mViewPager.setCurrentItem(1);//选择某一页
                break;
            case R.id.rab_game:
                mViewPager.setCurrentItem(2);//选择某一页
                break;
            case R.id.rab_search:
                mViewPager.setCurrentItem(3);//选择某一页
                break;
        }
    }

    // 返回键按下
    @Override
    public void onBackPressed() {
        this.mPressedCount = (1 + mPressedCount);
        if (mPressedCount == 2) {
            super.onBackPressed();
            return;
        }
        // 提示再按一次退出
        ToastUtil.show(mActivity, R.string.back_key_pressed);
        // 复原
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mPressedCount = 0;
            }
        }, 2000L);
    }
}

