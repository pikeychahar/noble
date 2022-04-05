package com.dmw.noble.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.model_life.LifePremiumQuote;
import com.dmw.noble.utils.AppUtils;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-09-27.
 */
public class LifeAdapter
        extends RecyclerView.Adapter<LifeAdapter.PolicyViewHolder> {
    ArrayList<LifePremiumQuote> policyList = new ArrayList<>();
    Context mContext;
    OnPremiumClick onPremiumClick;


    public LifeAdapter(Context mContext, ArrayList<LifePremiumQuote> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;

        if (mContext instanceof OnPremiumClick) {
            onPremiumClick = (OnPremiumClick) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.life_quote, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        LifePremiumQuote lifePremiumQuote = policyList.get(position);
        holder.btnPremium.setText(MessageFormat.format("{0}{1}{2}",
                AppUtils.RUPEE, lifePremiumQuote.getPremium(), "*"));
        String imgPath = lifePremiumQuote.getImgPath();

        if (!TextUtils.isEmpty((imgPath))) {
            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .animate(android.R.anim.fade_in)
                    .into(holder.imgInsurer);
        }
        if (!TextUtils.isEmpty(lifePremiumQuote.getPlanName()))
            holder.txtPlanName.setText(lifePremiumQuote.getPlanName());
        if (!TextUtils.isEmpty(lifePremiumQuote.getSumAssured()))
            holder.txtSumAssured.setText(MessageFormat.format("SumInsured: {0}",
                    lifePremiumQuote.getSumAssured()));

        holder.txtAge.setText(MessageFormat.format("{0}{1}{2}{3}",
                lifePremiumQuote.getAgeYears(), " Yrs (Max Limit: ",
                lifePremiumQuote.getMaxAge(), " Yrs)"));

        holder.btnBuyNow.setOnClickListener(v -> onPremiumClick
                .OnPremiumButtonClick(position));
        holder.btnPremium.setOnClickListener(v -> onPremiumClick
                .OnPremiumButtonClick(position));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.layout_list,
                lifePremiumQuote.getArrayCover());

        holder.gridCover.setAdapter(adapter);
        holder.llCover.getLayoutParams().height = (lifePremiumQuote.getArrayCover().size()) * 65;
    }

    //Years (Max Limit: 85 yrs)
    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPlanName, txtSumAssured, txtTenure, txtAge;
        public androidx.appcompat.widget.AppCompatButton btnBuyNow, btnPremium;
        public ImageView imgInsurer;
        public GridView gridCover;
        public LinearLayout llCover;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlanName = itemView.findViewById(R.id.txtPlanName);
            txtSumAssured = itemView.findViewById(R.id.txtSumInsured);
            imgInsurer = itemView.findViewById(R.id.imgInsurer);
            btnPremium = itemView.findViewById(R.id.btnPremium);
            txtAge = itemView.findViewById(R.id.txtAge);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            gridCover = itemView.findViewById(R.id.gridCover);
            llCover = itemView.findViewById(R.id.llCover);
        }
    }

    public interface OnPremiumClick {
        void OnPremiumButtonClick(int position);
    }
}
