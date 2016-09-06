package sitv.s003.dianbo.com.appmarkettwo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseActivity;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceURL;

/**
 * Created by wx on 2016/1/15.
 */
public class ImageLoaderUtils implements Handler.Callback {

    /**
     * 显示图片 - 游戏 应用
     * 60 * 60
     */
    public static void showPictureWithApplication(Context mContext, String uri, ImageView iv) {
        if (mContext instanceof BaseActivity) {
            if (((BaseActivity) mContext).isFinishing())
                return;
        }
        Glide.with(mContext)
                .load(InterfaceURL.IMAGE_URL_ROOT + uri)
                        //.centerCrop()
                .placeholder(R.drawable.other_image)
                .error(R.drawable.other_image)
                .crossFade()
                .into(iv);
    }

    /**
     * 显示AppINfoDetail应用截图 倒影
     * 60 * 60
     */
    public static void showDimsW80H80(Context mContext, String uri, final ImageView iv) {

        if (mContext instanceof BaseActivity) {
            if (((BaseActivity) mContext).isFinishing())
                return;
        }
        Glide.with(mContext).load(InterfaceURL.DETAILS_SCREETSHOT_ROOT_URL + uri)
                .asBitmap()
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        iv.setImageBitmap(createReflectedImage(resource));
                    }
                });

    }

    /**
     * 显示AppItem应用应用图标 倒影
     * 60 * 60
     */
    public static void showApplicationIcon(Context mContext, AppInfo appinfo, final ImageView iv) {

        if (mContext instanceof BaseActivity) {
            if (((BaseActivity) mContext).isFinishing())
                return;
        }
        Glide.with(mContext).load(InterfaceURL.APPLICATIONICON_ROOT_URL + appinfo.getId()+"/"+appinfo.getIcon())
                .asBitmap()
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        iv.setImageBitmap(createReflectedImage(resource));
                    }
                });

    }

    /**
     * 显示图片 - 好莱坞首页横图 293x135
     * 60 * 60
     */
public static void IconPicture(Context mContext, AppInfo appinfo, final ImageView iv) {

        if (mContext instanceof BaseActivity) {
            if (((BaseActivity) mContext).isFinishing())
                return;
        }
            Glide.with(mContext)
                .load(InterfaceURL.APPLICATIONICON_ROOT_URL + appinfo.getId()+"/"+appinfo.getIcon())
                .placeholder(R.drawable.other_image)
                .error(R.drawable.other_image)
                .crossFade()
                .into(iv);

    }
//
//    /**
//     * search
//     * 显示图片 - 小
//     * 60 * 60
//     */
//    public static void showPictureWithSearch(Context mContext, String uri, ImageView iv) {
//        //if (DBG) Log.v(TAG, "showPicture() uri : " + uri);
//        if (mContext instanceof BaseActivity) {
//            if (((BaseActivity) mContext).isFinishing())
//                return;
//        }
//        Glide.with(mContext)
//                .load(InterfaceURL.PORT_SEARCH + uri)
//                        // .load(InterfaceURL.IP + uri)
//                        //.centerCrop()
//                .placeholder(R.drawable.other_image)
//                .error(R.drawable.other_image)
//                .crossFade()
//                .into(iv);
//    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    //没有textView的图片的背景框 动画
    public static void setAnimation(final Context mContext, View view) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    view.bringToFront();
                    view.startAnimation(AnimationUtils.loadAnimation(mContext,
                            R.anim.scale_out));
                }
                if (!hasFocus) {
                    view.startAnimation(AnimationUtils.loadAnimation(mContext,
                            R.anim.scale_in));
                }
            }
        });
    }

    //添加电影详情界面图片倒影效果
    public static Bitmap createReflectedImage(Bitmap originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        // 实现图片翻转90度
        matrix.preScale(1, -1);
        // 创建倒影图片（是原始图片的一半大小）
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
        // 创建总图片（原图片 + 倒影图片）
        Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);
        // 创建画布
        Canvas canvas = new Canvas(finalReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        //把倒影图片画到画布上
        canvas.drawBitmap(reflectionImage, 0, height + 1, null);
        Paint shaderPaint = new Paint();
        //创建线性渐变LinearGradient对象
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, finalReflection.getHeight() + 1, 0x70ffffff,
                0x00ffffff, Shader.TileMode.MIRROR);
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
        canvas.drawRect(0, height + 1, width, finalReflection.getHeight(), shaderPaint);
        return finalReflection;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp(像素)
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int getScreenWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        px2dip(context,width);
        return   px2dip(context,width);
    }
}

