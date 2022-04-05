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
import com.dmw.noble.model.PremiumObjTw;
import com.dmw.noble.utils.AppUtils;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2020-10-28.
 */
public class PremiumBikeAdapter
        extends RecyclerView.Adapter<PremiumBikeAdapter.PolicyViewHolder> {

    ArrayList<PremiumObjTw> policyList;
    Context mContext;
    private OnPremiumBikeClick onPremiumClick;
    private OnBikePremiumBreakClickListener onPremiumBreakClickListener;


    public PremiumBikeAdapter(Context mContext, ArrayList<PremiumObjTw> agentPolicyList) {

        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnPremiumBikeClick) {
            onPremiumClick = (OnPremiumBikeClick) mContext;
        }
        if (mContext instanceof OnBikePremiumBreakClickListener) {
            onPremiumBreakClickListener = (OnBikePremiumBreakClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public com.dmw.noble.adaptor.PremiumBikeAdapter.PolicyViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tw_quote, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final com.dmw.noble.adaptor.PremiumBikeAdapter
            .PolicyViewHolder holder, final int position) {
        PremiumObjTw premiumQuote = policyList.get(position);

        holder.btnPremium.setText(String.format("%s%s%s", AppUtils.RUPEE, premiumQuote.getNet(),
                AppUtils.STAR));

        String digitImgPath = premiumQuote.getLogo();
        String policyType = premiumQuote.getPolicyType();

        if (!TextUtils.isEmpty((digitImgPath))) {
            Glide.with(mContext)
                    .load(digitImgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fitCenter()
                    .into(holder.imgInsurer);

        }

        String digitIdv = premiumQuote.getIdv();

        if (!TextUtils.isEmpty(digitIdv) && !digitIdv.equalsIgnoreCase("0")
                && !digitIdv.equalsIgnoreCase("1")) {
            holder.txtIdv.setText(String.format("₹ %s", digitIdv));
            holder.txtIdvRange.setText(MessageFormat.format("{0} - {1}",
                    premiumQuote.getIdvMin(), premiumQuote.getIdvMax()));

        } else {
            holder.txtIdv.setText(R.string.third_party);
            holder.txtIdvRange.setVisibility(View.GONE);
        }
        if (policyType.contains("Third")) {
            holder.txtIdv.setText(R.string.third_party);
            holder.txtIdvRange.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(premiumQuote.getCurrentNcb())) {
            holder.txtNcb.setText(MessageFormat.format("NCB: {0}%",
                    premiumQuote.getCurrentNcb()));
            holder.txtNcb.setVisibility(View.VISIBLE);
        }
        String digitTenure1 = premiumQuote.getTenure();

        if (!TextUtils.isEmpty(digitTenure1)) {
            if (digitTenure1.equalsIgnoreCase("1+5")) {
                digitTenure1 = "1 Y OD + 5 Yrs TP";
            }

            holder.txtTenure.setVisibility(View.VISIBLE);
            holder.txtTenure.setText(digitTenure1);

        } else holder.txtTenure.setVisibility(View.GONE);

        holder.btnPremiumBreak.setOnClickListener(v ->
                onPremiumBreakClickListener.OnBreakUpClicked(position));
        holder.btnPremium.setOnClickListener(v -> onPremiumClick.OnPremiumButtonClick(position));
        holder.btnBuyNow.setOnClickListener(v -> onPremiumClick.OnPremiumButtonClick(position));

    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTenure, txtIdv, txtIdvRange, txtNcb;
        public androidx.appcompat.widget.AppCompatButton btnPremium, btnBuyNow, btnPremiumBreak;
        public ImageView imgInsurer;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);

            btnPremium = itemView.findViewById(R.id.buttonPremiumBike);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            btnPremiumBreak = itemView.findViewById(R.id.btnPremiumBreakBike);
            imgInsurer = itemView.findViewById(R.id.imgInsurer);
            txtTenure = itemView.findViewById(R.id.txtTenure);
            txtIdv = itemView.findViewById(R.id.txtIdv);
            txtNcb = itemView.findViewById(R.id.txtNcb);
            txtIdvRange = itemView.findViewById(R.id.txtIdvRange);
        }
    }

    public interface OnPremiumBikeClick {
        void OnPremiumButtonClick(int position);
    }

    public interface OnBikePremiumBreakClickListener {
        void OnBreakUpClicked(int position);
    }
}
