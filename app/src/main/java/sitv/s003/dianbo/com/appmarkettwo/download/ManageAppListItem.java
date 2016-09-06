package sitv.s003.dianbo.com.appmarkettwo.download;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sitv.s003.dianbo.com.appmarkettwo.util.DownloadDBHelper;

/**
 * Created by Administrator on 2016/9/2.
 */
public class ManageAppListItem extends RelativeLayout implements View.OnClickListener {
    private DownloadTask mDownloadTask;
    private DownloadDBHelper mDownloadDBHelper;
    private Context mContext;
    private TextView txtAppName;
    private String mPid;
    private ImageView mAppIcon;
    public ManageAppListItem(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
        mDownloadDBHelper = new DownloadDBHelper(context);
        mContext = context;
    }


    public void bind(DownloadTask app) {
        mDownloadTask = app;
        this.mPid = app.getPid() + "";
        this.txtAppName.setText(app.getTitle());
        boolean fag = mDownloadDBHelper.getItemState(mDownloadTask.getPackageName());
        if(fag == false){
            this.setVisibility(View.GONE);
        }
      //  this.mAppIcon.setImageDrawable(app.getIcon());
    }
    @Override
    public void onClick(View view) {


    }
}
