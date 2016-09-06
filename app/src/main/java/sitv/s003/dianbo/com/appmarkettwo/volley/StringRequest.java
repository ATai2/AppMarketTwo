package sitv.s003.dianbo.com.appmarkettwo.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import sitv.s003.dianbo.com.appmarkettwo.util.ConstantUtil;


/**
 * Created by wx on 6/30 0030.
 */
public class StringRequest extends Request<String> {
    private Response.Listener<String> mListener;
    private Map<String, String> mHeaders = new HashMap<String, String>(1);

    public StringRequest(String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, errorListener);
        mListener=listener;
        init();
    }

    private void init() {
        setTimeout(ConstantUtil.PARAM_MILLTIME);
    }

    public void setTimeout(int millTime) {
        setRetryPolicy(new DefaultRetryPolicy(
                millTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    public void setCookie(String cookie) {
        mHeaders.put("Cookie", cookie);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
