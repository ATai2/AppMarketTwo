package sitv.s003.dianbo.com.appmarkettwo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by Administrator on 2016/9/2.
 */
public class DownLoadListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<AppInfo> dataSourse;
    public DownLoadListAdapter(Context context,List<AppInfo> dataSourse) {
    this.mcontext = context;
    this.dataSourse = dataSourse;
    }

    @Override
    public int getCount() {
        if(dataSourse.size()!=0){
            return dataSourse.size();
        }
        return 0;
    }

    @Override
    public AppInfo getItem(int position) {
        return dataSourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHodler holder=null;
        if(convertView==null){
            convertView = View.inflate(mcontext, R.layout.download_item,null);
            holder = new ViewHodler();
            holder.icon = (ImageView) convertView
                   .findViewById(R.id.icon);
            holder.tv_appname = (TextView) convertView
                   .findViewById(R.id.tv_appname);
            holder.startApplication = (Button) convertView.findViewById(R.id.startApplication);
            holder.delete = (Button) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else{
            holder = (ViewHodler) convertView.getTag();
        }
        ImageLoaderUtils.IconPicture(mcontext, dataSourse.get(position), holder.icon);
        holder.tv_appname.setText(dataSourse.get(position).getName());
        return convertView;
    }
    public  class ViewHodler{
        ImageView icon;
        TextView tv_appname;
        Button startApplication;
        Button delete;
    }
}
