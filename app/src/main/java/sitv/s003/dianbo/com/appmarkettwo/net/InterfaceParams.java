package sitv.s003.dianbo.com.appmarkettwo.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wx on 2016/8/22.
 */
public class InterfaceParams {

    /**
     * 应用数据参数
     * @param
     * @return
     */
    public static Map<String, String> getAppLicationParams(String type,String category,String usercode) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("category", category);
        params.put("usercode", usercode);
        return params;
    }
    /**
     * 游戏数据参数
     * @param
     * @return
     */
    public static Map<String, String> getGameParams(String count,String parentid,String usercode) {
        Map<String, String> params = new HashMap<>();
        params.put("count", count);
        params.put("parentid", parentid);
        params.put("usercode", usercode);
        return params;
    }
    /**
     * 应用数据参数
     * @param
     * @return
     */
    public static Map<String, String> getApplicationParams(String count,String parentid,String usercode) {
        Map<String, String> params = new HashMap<>();
        params.put("count", count);
        params.put("parentid", parentid);
        params.put("usercode", usercode);
        return params;
    }



}
