package sitv.s003.dianbo.com.appmarkettwo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sitv.s003.dianbo.com.appmarkettwo.R;
import sitv.s003.dianbo.com.appmarkettwo.bean.AppInfo;
import sitv.s003.dianbo.com.appmarkettwo.util.ImageLoaderUtils;

/**
 * Created by ly on 2016/8/19.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHoler> {
    private Context mContext;
    public static final int PAGE_SIZE = 8; // 每一屏幕显示8 Button
    private List<AppInfo> mDatas = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, AppInfo data);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public CategoryListAdapter(Context mContext, List<AppInfo> Categorys, int page) {
        this.mContext = mContext;
        int i = page * PAGE_SIZE;
        int end = i + PAGE_SIZE;

        while ((i < Categorys.size()) && (i < end)) {
            mDatas.add(Categorys.get(i));
            i++;
        }
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CategoryListViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.item_app, parent, false);
        return new CategoryListViewHoler(mView);
    }


    @Override
    public void onBindViewHolder(final CategoryListViewHoler holder, final int position) {
        ImageLoaderUtils.showApplicationIcon(mContext, mDatas.get(position), holder.app_image);
        ImageLoaderUtils.setAnimation(mContext, holder.itemView);
        holder.app_text_title.setText(mDatas.get(position).getName());
        holder.app_text_belong.setText(mDatas.get(position).getCompany());
        setStarScore(mDatas.get(position), holder);
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, mDatas.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class CategoryListViewHoler extends RecyclerView.ViewHolder {
        public ImageView app_image;
        public TextView app_text_title, app_text_belong;
        public RatingBar starscore;

        public CategoryListViewHoler(View itemView) {
            super(itemView);
            app_image = (ImageView) itemView.findViewById(R.id.app_image);
            app_text_title = (TextView) itemView.findViewById(R.id.app_text_title);
            app_text_belong = (TextView) itemView.findViewById(R.id.app_text_belong);
            starscore = (RatingBar) itemView.findViewById(R.id.starscore);
        }
    }

    private void setStarScore(AppInfo appInfo, CategoryListViewHoler holde) {
        if (appInfo.getScore() > 0.5) {
            holde.starscore.setRating(appInfo.getScore());
        } else {
            holde.starscore.setRating((float) 0.5);
        }
    }

}
