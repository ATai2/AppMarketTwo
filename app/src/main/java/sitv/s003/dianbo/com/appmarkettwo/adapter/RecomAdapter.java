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
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.fragment.RecomFrament;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by Administrator on 2016/8/11.
 */

public class RecomAdapter extends RecyclerView.Adapter<RecomAdapter.RecomViewHoler> {

    private Context mContext;
    private List<AppInfo> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickLitener mOnItemClickLitener;
    private RecomFrament recomFrament;

    public interface OnItemClickLitener {
        void onItemClick(View view, AppInfo data);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public RecomAdapter(Context mContext, List<AppInfo> mDatas ,RecomFrament recomFrament) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.recomFrament = recomFrament;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecomViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.category_recom_item, parent, false);
        return new RecomViewHoler(mView);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : getLimitSize();
    }

    private int getLimitSize() {
        if (mDatas.size() >= 8) {
            return 8;
        }
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final RecomViewHoler holder, final int position) {
        holder.name.setText(mDatas.get(position).getName());
        ImageLoaderUtils.IconPicture(mContext, mDatas.get(position), holder.picture);
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, mDatas.get(position));
                }
            });
        }
    }


    public class RecomViewHoler extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView picture;

        public RecomViewHoler(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            name = (TextView) itemView.findViewById(R.id.name);
            ImageLoaderUtils.setAnimation(mContext, itemView);
        }
    }
}
