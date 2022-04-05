package com.square.pos.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.square.pos.R;
import com.square.pos.model.PosterData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Prahalad Chahar on 2021-12-16.
 */
public class PosterAdaptor extends RecyclerView.Adapter<PosterAdaptor.PolicyViewHolder> {
    List<PosterData> policyList;
    LayoutInflater inflater;
    Context mContext;
    OnBlogClickListener onBlogClick;

    public PosterAdaptor(Context mContext, List<PosterData> policyLists,
                         OnBlogClickListener onBlogClick) {
        this.mContext = mContext;
        this.policyList = policyLists;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onBlogClick = onBlogClick;
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_share_poster, viewGroup, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        final PosterData policy = policyList.get(position);

        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1300)
                .setBaseAlpha(0.8f)
                .setHighlightAlpha(0.9f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        String url = policy.getImage();
        if (!TextUtils.isEmpty(url))
            Picasso.with(mContext)
                    .load(url)
                    .placeholder(shimmerDrawable)
                    .error(R.drawable.placeholder)
                    .into(holder.imgBlog);
        holder.imgBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBlogClick.onPosterClick(holder.getAbsoluteAdapterPosition(),
                        policy.getImage(), policy.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBlog;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBlog = itemView.findViewById(R.id.imgBlog);
        }
    }

    public interface OnBlogClickListener {
        void onPosterClick(int position, String url, String title);
    }
}
