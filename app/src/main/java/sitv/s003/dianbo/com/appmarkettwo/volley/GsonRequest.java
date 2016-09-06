package sitv.s003.dianbo.com.appmarkettwo.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import sitv.s003.dianbo.com.appmarkettwo.util.ConstantUtil;


/**
 * Created by wx on 3/3 0003.
 */
public class GsonRequest extends Request<JSONObject> {

    private Map<String,String> mMap;
    private Response.Listener<JSONObject> mListener;
    private Map<String, String> mHeaders=new HashMap<String, String>(1);

    public GsonRequest(String url,Response.Listener<JSONObject> listener, Response.ErrorListener errorListener,Map map) {
        super(Request.Method.POST, url, errorListener);
        mListener=listener;
        mMap=map;
        init();
    }
    public GsonRequest(String url,Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
        init();
    }

    private void init() {
        setTimeout(ConstantUtil.PARAM_MILLTIME);
    }

    public void setTimeout(int millTime){
        setRetryPolicy(new DefaultRetryPolicy(
                millTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.v("mMap",mMap.toString());
        return mMap;
    }

    public void setCookie(String cookie){
        mHeaders.put("Cookie", cookie);
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}
