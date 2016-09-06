package sitv.s003.dianbo.com.appmarkettwo.net;

/**
 * Created by wx on 2016/8/10.
 */

public class InterfaceURL {


    //IP地址
    public static final String IP = "http://192.168.1.247:8081";
    public static final String IP_MARKET = IP + "/MarketApi";
    //详情界面图片地址
    public static final String DETAILS_SCREETSHOT_ROOT_URL = "http://192.168.1.247:8081/Market/";
    //游戏和应用图片根地址
    public static final String IMAGE_URL_ROOT = "http://192.168.1.247:8081//Market/views/data/category/";
    //应用图标
    public  static final String APPLICATIONICON_ROOT_URL = "http://192.168.1.247:8081//Market/views/data/image/";
    // 账号相关
    public static final String URL_ACCOUNT_ROOT = "";

    public static final String URL_APK_ROOT = "http://192.168.1.247:8081//Market/views/data/apk";

    public static String getAccountAuth() {
        return URL_ACCOUNT_ROOT + "account/auth";
    }

    //	public static final String IMAGE_URL_ROOT ="http://192.168.1.18:8088/Market/views/data/image";
    //	public static final String CATEGORY_IMAGE_URL_ROOT ="http://192.168.1.18:8088/Market/views/data/category";

    ////	public static String getRecommend() {
    ////		return URL_EAT_ROOT + "store/recommend";
    ////	}
    //
    //	// 业务相关
    //	public static final String URL_EAT_ROOT = "http://192.168.1.18:8088/MarketApi/";

    //	public static final String IMAGE_URL_ROOT ="http://192.168.1.128:8080/Market/views/data/image";
    //	public static final String CATEGORY_IMAGE_URL_ROOT ="http://192.168.1.128:8088/Market/views/data/category";
    //
    //	public static final String URL_APK_ROOT = "http://192.168.1.128:8080/Market/views/data/apk";
    //
    //	public static String getRecommend() {
    //		return URL_EAT_ROOT + "store/recommend";
    //	}
    //
    //	// 业务相关
    //	public static final String URL_EAT_ROOT = "http://192.168.1.128:8080/MarketApi/";


    // 业务相关
    //  public static final String URL_EAT_ROOT = "http://192.168.1.128:8080//MarketApi/";
    //测试
    public static final String URL_EAT_ROOT = "http://192.168.1.247:8081/MarketApi/";
    public static final String URL_EAT_ROOT_IMAGE = "http://192.168.1.247:8081//Market/";

    // 版本验证
    public static String getChkVersion() {
        return URL_EAT_ROOT + "api/getCheck";
    }

    // 获取推荐列表
    public static String getRecommend() {
        return URL_EAT_ROOT + "api/getApks";
    }

    public static String getCategorys() {
        return URL_EAT_ROOT + "api/getCategorys";
    }


    public static String getApplist() {
        return URL_EAT_ROOT + "api/getApks";
    }


    public static String getAppDetail() {
        return URL_EAT_ROOT + "api/getApkDetail";
    }


    public static String getSearchTags() {
        return URL_EAT_ROOT + "api/getwdbook";
    }

    public static String getSearch() {
        return URL_EAT_ROOT + "api/getApks";
    }


    public static String getGame() {
        return URL_EAT_ROOT + "store/game";
    }

    public static String getHome() {
        return URL_EAT_ROOT + "store/home";
    }

    public static String getStoreList() {
        return URL_EAT_ROOT + "store/list";
    }


    public static String getUpdateDownloadLinks() {
        return URL_EAT_ROOT + "api/getUpdateDownloadLinks";
    }

    public static String getImageUrl() {
        return "views/data/image/";
    }

    /**
     * 应用接口
     *
     * @return
     */
    public static String getAppLicationURL() {
        return IP_MARKET + "/api/getCategorys";
    }

    /**
     * 游戏接口
     *
     * @return
     */
    public static String getGameURL() {
        return IP_MARKET + "/api/getCategorys";
    }
}
