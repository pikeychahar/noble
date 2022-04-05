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
import com.dmw.noble.model_travel.TravelPremiumQuote;
import com.dmw.noble.utils.AppUtils;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-09-27.
 */
public class TravelAdapter
        extends RecyclerView.Adapter<TravelAdapter.PolicyViewHolder> {
    ArrayList<TravelPremiumQuote> policyList = new ArrayList<>();
    Context mContext;
    OnPremiumClick onPremiumClick;


    public TravelAdapter(Context mContext, ArrayList<TravelPremiumQuote> agentPolicyList) {
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
                .inflate(R.layout.travel_quote, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        TravelPremiumQuote lifePremiumQuote = policyList.get(position);
        holder.btnPremium.setText(MessageFormat.format("{0}{1}{2}",
                AppUtils.RUPEE, lifePremiumQuote.getPremium(), "*"));
        String imgPath = lifePremiumQuote.getImgPath();

        if (!TextUtils.isEmpty((imgPath))) {
            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imgInsurer);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.layout_list,
                lifePremiumQuote.getArrayCover());

        holder.gridCover.setAdapter(adapter);
        holder.llCover.getLayoutParams().height = (lifePremiumQuote.getArrayCover().size()) * 65;

        holder.txtPlanBenefit.setOnClickListener(v -> {
            if (holder.llCover.getVisibility() == View.VISIBLE)
                holder.llCover.setVisibility(View.GONE);
            else  holder.llCover.setVisibility(View.VISIBLE);
        });
        holder.btnBuyNow.setOnClickListener(v -> onPremiumClick
                .OnPremiumButtonClick(position, 0));
        holder.btnPremium.setOnClickListener(v -> onPremiumClick
                .OnPremiumButtonClick(position, 1));
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPlanName, txtSumAssured, txtPolicyWording, txtPlanBenefit;
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
            txtPlanBenefit = itemView.findViewById(R.id.txtPlanBenefit);
            txtPolicyWording = itemView.findViewById(R.id.txtPolicyWording);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            gridCover = itemView.findViewById(R.id.gridCover);
            llCover = itemView.findViewById(R.id.llCover);
        }
    }

    public interface OnPremiumClick {
        void OnPremiumButtonClick(int position, int tag);
    }
}
