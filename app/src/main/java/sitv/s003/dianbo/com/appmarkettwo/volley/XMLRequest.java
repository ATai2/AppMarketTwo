package sitv.s003.dianbo.com.appmarkettwo.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import sitv.s003.dianbo.com.appmarkettwo.util.ConstantUtil;


/**
 * Created by wx on 4/15 0015.
 */
public class XMLRequest extends Request<XmlPullParser> {

    private Map<String,String> mMap;
    private  Response.Listener<XmlPullParser> mListener;
    private String params;

    public XMLRequest(String xml, String url, Response.Listener<XmlPullParser> listener,
                      Response.ErrorListener errorListener) {
        super(Request.Method.POST,url, errorListener);
        params = xml;
        mListener = listener;
        init();
    }

    public XMLRequest(String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, errorListener);
        init();
    }
    private void init() {
        setTimeout(ConstantUtil.PARAM_MILLTIME);
    }

    @Override
    public String getBodyContentType() {
        return params;
    }
    @Override
    public byte[] getBody() throws AuthFailureError {
        return params == null ? super.getBody() : params.getBytes();
    }


    public void setTimeout(int millTime){
        setRetryPolicy(new DefaultRetryPolicy(
                millTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
        try {
            String xmlString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));
            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser response) {
        mListener.onResponse(response);
    }

}