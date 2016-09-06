package sitv.s003.dianbo.com.appmarkettwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.base.BaseViewHoder;
import sitv.s003.dianbo.com.appmarkettwo.baseadapter.OnItemClickListener;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.bean.CategoryInfo;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by ly on 2016/8/11.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHodler> {
    public static final int TYPE_ITEM_PARENT = 0;//第1个条目
    public static final int TYPE_ITEM_LARGE = 1;//长条目
    public static final int TYPE_OTHER = 2; //短条目
    private List<CategoryInfo> mIndexList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, CategoryInfo mCategoryInfo);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public GameAdapter(Context mContext, List<CategoryInfo> mIndexList) {
        this.mContext = mContext;
        this.mIndexList = mIndexList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_ITEM_PARENT) return TYPE_ITEM_PARENT;
        if (position == 1 || position == 2 || position == 6 || position == 7 || position == 8 || position == 9)
            return TYPE_ITEM_LARGE;
        return TYPE_OTHER;
    }

    @Override
    public int getItemCount() {
        return mIndexList == null ? 0 : mIndexList.size();
    }

    @Override
    public GameViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = null;
        if (viewType == TYPE_ITEM_PARENT) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.category_element_item_parent, parent, false);
            return new GameViewHodler(mView);
        }
        if (viewType == TYPE_ITEM_LARGE) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.category_element_item_large, parent, false);
            return new GameViewHodler(mView);
        }
        if (viewType == TYPE_OTHER) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.category_element_item, parent, false);
            return new GameViewHodler(mView);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final GameViewHodler holder, final int position) {
        holder.game_name.setText(mIndexList.get(position).getName()+ position);
        holder.game_count.setText(String.valueOf(mIndexList.get(position).getSum_soft()));
        ImageLoaderUtils.showPictureWithApplication(mContext,mIndexList.get(position).getImage(),holder.picture);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, mIndexList.get(position));
                }
            });
        }
    }


    public class GameViewHodler extends RecyclerView.ViewHolder {
        public TextView game_name;
        public TextView game_count;
        public ImageView picture;

        public GameViewHodler(View itemView) {
            super(itemView);
            game_name = (TextView) itemView.findViewById(R.id.game_name);
            game_count = (TextView) itemView.findViewById(R.id.game_count);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            ImageLoaderUtils.setAnimation(mContext, itemView);
        }
    }
}
