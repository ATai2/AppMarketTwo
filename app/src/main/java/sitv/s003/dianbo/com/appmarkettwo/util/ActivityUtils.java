package sitv.s003.dianbo.com.appmarkettwo.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import sitv.s003.dianbo.com.appmarkettwo.activity.AppActivity;
import sitv.s003.dianbo.com.appmarkettwo.activity.AppManagerActivity;
import sitv.s003.dianbo.com.appmarkettwo.activity.CategoryListActivity;
import sitv.s003.dianbo.com.appmarkettwo.activity.MainActivity;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;


/**
 * Created by wx on 2016/3/8.
 */
public class ActivityUtils implements ConstantUtil {
    //当获取值时从引导页进入主界面
    public static void goMainActivity(Context mActivity, String str_id, String str_packageName, String str_image_url) {
        Intent intent = new Intent(mActivity, MainActivity.class);
        if ((str_id != null && !str_id.equals("")) && (str_packageName != null
                && !str_packageName.equals("")) && (str_image_url != null &&
                !str_image_url.equals(""))) {
            intent.putExtra(PARAM_KEY_APPID, str_id);
            intent.putExtra(PARAM_KEY_NAME, str_packageName);
            intent.putExtra(PARAM_KEY_IMAGEURL, str_image_url);
        }
        mActivity.startActivity(intent);
    }
    //当没获取值时从引导页进入主界面
    public static void goMainActivityTwo(Context mActivity) {
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
    }
    //进入APPActivity
    public  static void goAppActivity(Context mActivity,int mid){
        Intent intent = new Intent(mActivity, AppActivity.class);
        intent.putExtra("extra_app_id",mid);
        mActivity.startActivity(intent);
    }
    public  static void goToZFBActivity(Context mActivity){
       // Intent intent = new Intent(mActivity, ExternalPartner.class);
       // mActivity.startActivity(intent);
    }
     public static void StartApplication(Context mActivity, AppInfo info){
         PackageManager pm = mActivity.getPackageManager();
         Intent intent = pm.getLaunchIntentForPackage(info.getPackage_name());
         mActivity.startActivity(intent);
     }
    public  static void gotoCategoryListActivity(Context mActivity,String category_id){
        Intent intent = new Intent(mActivity, CategoryListActivity.class);
        intent.putExtra("category_id", category_id);
        mActivity.startActivity(intent);
    }

    /**
     * 进入应用管理界面
     * @param mActivity
     */
    public static void gotoAppManagerActivity(Context mActivity) {
      Intent intent = new Intent(mActivity, AppManagerActivity.class);
        mActivity.startActivity(intent);
    }
}
