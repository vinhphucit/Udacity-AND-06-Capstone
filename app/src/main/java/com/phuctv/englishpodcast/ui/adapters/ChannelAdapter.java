package com.phuctv.englishpodcast.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.domain.models.ChannelModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phuctran on 11/7/17.
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    private List<ChannelModel> mChannelItems;
    final private ListItemClickListener mOnClickListener;
    private Context mContext;

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_list_channel;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediatelly = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediatelly);
        ChannelViewHolder viewHolder = new ChannelViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mChannelItems.size();
    }


    public interface ListItemClickListener {
        void onListItemClick(ChannelModel movieModel);
    }


    public ChannelAdapter(Context context, List<ChannelModel> mChannelItems, ListItemClickListener listener) {
        this.mContext = context;
        this.mChannelItems = mChannelItems;
        this.mOnClickListener = listener;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_title)
        TextView recipe_title;
        @BindView(R.id.recipe_image)
        ImageView recipe_image;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            final ChannelModel channelModel = mChannelItems.get(position);

            recipe_title.setText(channelModel.getTitle());
            if (channelModel.getLogo().isEmpty()) {
                recipe_image.setImageResource(R.color.photo_placeholder);
            } else {
                Glide.with(mContext)
                        .load(channelModel.getLogo())
                        .placeholder(R.color.photo_placeholder)
                        .error(R.color.photo_placeholder)
                        .into(recipe_image);
            }


        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(mChannelItems.get(clickPosition));
        }
    }
}
