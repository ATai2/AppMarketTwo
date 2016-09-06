package sitv.s003.dianbo.com.appmarkettwo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * @des:关联应用
 * Created by ly on 2016/8/18.
 */
public class RelatedAppListAdapter extends BaseAdapter {
    public Context mContex;
    List<AppInfo> mdate ;
    public RelatedAppListAdapter(Context mContex,List<AppInfo> mDateSourse) {
        mdate = mDateSourse;
        this.mContex = mContex;
    }
    @Override
    public int getCount() {
        if(mdate.size()>4) {
            return 4;
        }
        return mdate.size();
    }


    @Override
    public Object getItem(int position) {
            return mdate.get(position);
    }
    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler holder=null;
        if(convertView==null){
            convertView = View.inflate(mContex, R.layout.related_application,null);
            holder = new ViewHodler();
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.icon);
            holder.tv_appname = (TextView) convertView
                    .findViewById(R.id.tv_appname);
            holder.tv_company = (TextView) convertView
                    .findViewById(R.id.tv_company);
            holder.mid = (TextView) convertView
                    .findViewById(R.id.mid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
        }

       ImageLoaderUtils.showApplicationIcon(mContex, mdate.get(position), holder.icon);
        holder.tv_appname.setText(mdate.get(position).getName());
        holder.tv_company.setText(mdate.get(position).getCompany());
        return convertView;
    }
    public  class ViewHodler{
        ImageView icon;
        TextView tv_appname;
        TextView tv_company;
        TextView mid;
    }
}
