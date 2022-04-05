package com.dmw.noble.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.model.Category;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    ArrayList<Category> searchList = new ArrayList<>();
    LayoutInflater mInflater;
    ItemClickListener mClickListener;
    Context mContext;
    int rowIndex = 0;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<Category> searchList) {
        this.mInflater = LayoutInflater.from(context);
        this.searchList = searchList;
        mContext = context;
        if (context instanceof ItemClickListener)
            mClickListener = (ItemClickListener) mContext;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myTextView.setText(searchList.get(position).getName());

        String path = searchList.get(position).getLogo();
        if (!TextUtils.isEmpty((path))) {
            path = R.string.base_url + path;
            Glide.with(mContext)
                    .load(path)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fitCenter()
                    .into(holder.imgIcon);
        }

        holder.imgIcon.setVisibility(View.GONE);
        holder.llMain.setOnClickListener(v -> {

            rowIndex = holder.getAbsoluteAdapterPosition();
            mClickListener.onItemClick(position);
            holder.llMain.setBackground(AppCompatResources.getDrawable(mContext,
                    R.drawable.txt_border));
            notifyDataSetChanged();
        });

        if (rowIndex == position) {
            holder.llMain
                    .setBackground(AppCompatResources.getDrawable(mContext, R.drawable.txt_border));
        } else {
            holder.llMain
                    .setBackground(AppCompatResources.getDrawable(mContext, R.drawable.img_border));
        }
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        LinearLayout llMain;
        ImageView imgIcon;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            llMain = itemView.findViewById(R.id.llMain);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                if (mClickListener != null)
                    mClickListener.onItemClick(getAbsoluteAdapterPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}