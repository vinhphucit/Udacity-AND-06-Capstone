package com.phuctv.englishpodcast.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phuctv.englishpodcast.R;
import com.phuctv.englishpodcast.domain.models.PodcastModel;
import com.phuctv.englishpodcast.domain.models.TranscriptModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

/**
 * Created by phuctran on 11/7/17.
 */

public class TranscriptAdapter extends RecyclerView.Adapter<TranscriptAdapter.ChannelViewHolder> {

    private List<TranscriptModel> mChannelItems;
    final private ListItemClickListener mOnClickListener;
    private Context mContext;
    private int currentPlayingIndex = -1;

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_list_transcript;
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

    public void notifyCurrentPlayingChanged(int currentPlaying) {
        currentPlayingIndex = -1;
        for (int i = 0; i < mChannelItems.size(); i++) {
            TranscriptModel mTranscriptModel = mChannelItems.get(i);
            if (mTranscriptModel.getTime() > currentPlaying) {
                currentPlayingIndex = i > 0 ? i - 1 : 0;
                notifyDataSetChanged();
                return;
            }
        }
    }


    public interface ListItemClickListener {
        void onListItemClick(TranscriptModel movieModel);
    }


    public TranscriptAdapter(Context context, List<TranscriptModel> mChannelItems, ListItemClickListener listener) {
        this.mContext = context;
        this.mChannelItems = mChannelItems;
        this.mOnClickListener = listener;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_description)
        TextView recipe_description;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            final TranscriptModel podcastModel = mChannelItems.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                recipe_description.setText(Html.fromHtml(podcastModel.getText(), FROM_HTML_MODE_LEGACY));
            } else {
                recipe_description.setText(Html.fromHtml(podcastModel.getText()));
            }

            if (position == currentPlayingIndex) {
                recipe_description.setTextColor(mContext.getResources().getColor(R.color.yellow_login));
            } else {
                recipe_description.setTextColor(mContext.getResources().getColor(R.color.white));
            }

        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(mChannelItems.get(clickPosition));
        }
    }
}
