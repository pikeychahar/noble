package com.square.pos.adaptor.health;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.square.pos.R;
import com.square.pos.model_health.compare.Feature;
import com.square.pos.utils.UtilClass;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-05-05.
 */
public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.PolicyViewHolder> {
    private ArrayList<Feature> customerList = new ArrayList<>();
     LayoutInflater inflater;
     Context mContext;
    private OnCallClickListener onCallClickListener;

    public CompareAdapter(Context mContext, ArrayList<Feature> customerList) {
        this.customerList = customerList;
        this.mContext = mContext;
        if (mContext instanceof OnCallClickListener) {
            onCallClickListener = (OnCallClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_comapre, parent, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        Feature featureObj = customerList.get(position);
        holder.txtFeature.setText(featureObj.getFeature());

        holder.txtFeature1.setText(featureObj.getCompany1());
        holder.txtFeature2.setText(featureObj.getCompany2());
        holder.txtFeature3.setText(featureObj.getCompany3());
        int checked = UtilClass.getInstance().getChecked();
        if (checked == 2){
            holder.txtFeature3.setVisibility(View.GONE);
            LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams) holder.txtFeature3.getLayoutParams();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.txtFeature1.getLayoutParams();
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) holder.txtFeature2.getLayoutParams();
            params.weight = 1.5f;
            params1.weight = 1.5f;
            params0.weight = 0f;

            holder.txtFeature1.setLayoutParams(params);
            holder.txtFeature2.setLayoutParams(params1);
            holder.txtFeature3.setLayoutParams(params0);
        }

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFeature, txtFeature1, txtFeature2, txtFeature3;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFeature = itemView.findViewById(R.id.txtFeature);
            txtFeature1 = itemView.findViewById(R.id.txtFeature1);
            txtFeature2 = itemView.findViewById(R.id.txtFeature2);
            txtFeature3 = itemView.findViewById(R.id.txtFeature3);
        }
    }

    public interface OnCallClickListener {
        void OnCallClicked(int position, String phone);
    }
}
