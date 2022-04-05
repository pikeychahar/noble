package com.dmw.noble.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.model.blog.Blog;

import java.util.List;

/**
 * Created by Prahalad Chahar on 2019-07-16.
 */
public class BlogAdaptor extends RecyclerView.Adapter<BlogAdaptor.PolicyViewHolder> {
     List<Blog> policyList;
     LayoutInflater inflater;
     Context mContext;
     OnBlogClickListener onBlogClick;


    public BlogAdaptor(Context mContext, List<Blog> policyLists, OnBlogClickListener onBlogClick) {
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
                .inflate(R.layout.layout_blog, viewGroup, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        final Blog policy = policyList.get(position);
        holder.blogTitle.setText(policy.getBlogTitle());

        String url = policy.getImage();

        if (!TextUtils.isEmpty(url))
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imgBlog);
        holder.imgBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBlogClick.onBlogClicked(holder.getAbsoluteAdapterPosition(),
                        policy.getBlogUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView blogTitle;
        public ImageView imgBlog;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            blogTitle = itemView.findViewById(R.id.blogTitle);
            imgBlog = itemView.findViewById(R.id.imgBlog);

        }
    }

       public interface OnBlogClickListener {
        void onBlogClicked(int position, String url);
    }


}
