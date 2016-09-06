package sitv.s003.dianbo.com.appmarkettwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.net.InterfaceURL;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by Administrator on 2016/8/18.
 */
public class ImagesAppListAdapter extends RecyclerView.Adapter<ImagesAppListAdapter.ItemImagerViewHodler> {

    private AppInfo appInfo;
    private Context mContext;
    public ImagesAppListAdapter(Context mContext, AppInfo appInfo) {
        this.mContext = mContext;
        this.appInfo=appInfo;
    }

    @Override
    public ItemImagerViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = null;
        final ItemImagerViewHodler viewHolder;
        mView = LayoutInflater.from(mContext).inflate(R.layout.app_images_item, parent, false);
        viewHolder=new ItemImagerViewHodler(mView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ItemImagerViewHodler holder, int position) {
        ImageLoaderUtils.showDimsW80H80(mContext, InterfaceURL.getImageUrl()+appInfo.getId()+"/"+appInfo.getImages().get(position).getImage(), holder.picture);
        ImageLoaderUtils.setAnimation(mContext, holder.itemView);

        }

    @Override
    public int getItemCount() {
        if(appInfo.getImages().size()>3){
            return 3;
        }else{
            return appInfo.getImages().size();
        }
    }

    public class ItemImagerViewHodler extends RecyclerView.ViewHolder {
        public ImageView  picture;

        public ItemImagerViewHodler(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            ImageLoaderUtils.setAnimation(mContext, itemView);
        }
    }
}
