package sitv.s003.dianbo.com.appmarkettwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by wx on 2016/1/27.
 * 应用适配器
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHodler> {
    public static final int TYPE_ITEM_LARGE = 0;//第0个长条目类型
    public static final int TYPE_ITEM_NORMAL = 1;//短条目类型很长的条目
    public static final int TYPE_OTHER = 2; //其他条目类型
    private List<CategoryInfo> mIndexList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, CategoryInfo mCategoryInfo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public AppAdapter(Context mContext, List<CategoryInfo> mIndexList) {
        this.mContext = mContext;
        this.mIndexList = mIndexList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2 || position == 3 || position == 5 || position == 6 || position == 7 || position == 9)
            return 0;
        if (position == 0) return 1;
        return 2;
    }

    @Override
    public AppViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = null;
        final AppViewHodler viewHolder;
        if (viewType == TYPE_ITEM_LARGE) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.category_element_item_large, parent, false);
            viewHolder = new AppViewHodler(mView);
            return viewHolder;
        }
        if (viewType == TYPE_ITEM_NORMAL) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.category_element_item_parent, parent, false);
            viewHolder = new AppViewHodler(mView);
            return viewHolder;
        }
        if (viewType == TYPE_OTHER) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.category_element_item, parent, false);
            viewHolder = new AppViewHodler(mView);
            return viewHolder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final AppViewHodler holder, final int position) {

        holder.App_name.setText(mIndexList.get(position).getName());
        holder.App_count.setText(String.valueOf(mIndexList.get(position).getSum_soft()));
        ImageLoaderUtils.showPictureWithApplication(mContext, mIndexList.get(position).getImage(), holder.picture);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, mIndexList.get(position));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mIndexList == null ? 0 : mIndexList.size();
    }


    public class AppViewHodler extends RecyclerView.ViewHolder {
        public TextView App_name;
        public TextView App_count;
        public ImageView picture;

        public AppViewHodler(View itemView) {
            super(itemView);
            App_name = (TextView) itemView.findViewById(R.id.game_name);
            App_count = (TextView) itemView.findViewById(R.id.game_count);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            ImageLoaderUtils.setAnimation(mContext, itemView);
        }
    }
}
