package com.square.pos.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.square.pos.R;
import com.square.pos.model.PremiumObj;
import com.square.pos.utils.AppUtils;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2020-08-28.
 */
public class PremiumAdapter
        extends RecyclerView.Adapter<PremiumAdapter.PolicyViewHolder> {

    ArrayList<PremiumObj> policyList;
    Context mContext;
    OnPremiumClick onPremiumClick;
    OnPremiumBreakClickListener onPremiumBreakClickListener;
    OnAddonClickListener onAddonClickListener;


    public PremiumAdapter(Context mContext, ArrayList<PremiumObj> agentPolicyList) {

        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnPremiumClick) {
            onPremiumClick = (OnPremiumClick) mContext;
        }
        if (mContext instanceof PremiumAdapter.OnPremiumBreakClickListener) {
            onPremiumBreakClickListener = (PremiumAdapter.OnPremiumBreakClickListener) mContext;
        }
        if (mContext instanceof PremiumAdapter.OnAddonClickListener) {
            onAddonClickListener = (PremiumAdapter.OnAddonClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public PremiumAdapter.PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pc_quote, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PremiumAdapter.PolicyViewHolder holder, final int position) {
        PremiumObj premiumQuote = policyList.get(position);

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

        String inspection = policyList.get(position).getBreakingAllowStatus();
        String gd = policyList.get(position).getCompany();

        if (inspection.equals("1")) {
            holder.txtIns.setVisibility(View.VISIBLE);
            AppUtils.bounceAnim(holder.txtIns, 400);
        }
        if (gd.equals("digitNew")) {
            holder.txtGoodDriver.setVisibility(View.VISIBLE);
            holder.txtIns.setVisibility(View.VISIBLE);
            AppUtils.bounceAnim(holder.txtIns, 400);
        }

        String digitIdv = premiumQuote.getIdv();

        if (!TextUtils.isEmpty(digitIdv) && !digitIdv.equalsIgnoreCase("0")
                && !digitIdv.equalsIgnoreCase("1")) {
            holder.txtIdv.setText("₹ " + digitIdv);
            holder.txtIdvRange.setText(premiumQuote.getIdvMin() + " - " + premiumQuote.getIdvMax());
        } else {
            holder.txtIdv.setText(R.string.third_party);
            holder.txtIdvRange.setVisibility(View.INVISIBLE);
        }
        if (policyType.contains("Third")) {
            holder.txtIdv.setText(R.string.third_party);
            holder.txtIdvRange.setVisibility(View.INVISIBLE);
        }

        String digitTenure1 = premiumQuote.getTenure();

        if (!TextUtils.isEmpty(digitTenure1)) {
            if (digitTenure1.equalsIgnoreCase("1+3")) {
                digitTenure1 = "1 Y OD + 3 Yrs TP";
            }

            holder.txtTenure.setVisibility(View.VISIBLE);
            holder.txtTenure.setText(digitTenure1);

        } else holder.txtTenure.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(premiumQuote.getCurrentNcb())) {
            holder.txtNcb.setText(MessageFormat.format("NCB: {0}%",
                    premiumQuote.getCurrentNcb()));
            holder.txtNcb.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.layout_list,
                premiumQuote.getArrayCover());

        holder.gridCover.setAdapter(adapter);
        holder.llCover.getLayoutParams().height = (premiumQuote.getArrayCover().size() / 2) * 95;

        if (policyList.size() > 0 && policyList.size() != position)
            holder.btnPremiumBreak.setOnClickListener(v ->
                    onPremiumBreakClickListener.OnBreakUpClicked(position));

        holder.btnPremium.setOnClickListener(v -> onPremiumClick.OnPremiumButtonClick(position));
        holder.btnBuyNow.setOnClickListener(v -> onPremiumClick.OnPremiumButtonClick(position));

        holder.txtAddonCover.setOnClickListener(v -> {

            if (holder.llCover.getVisibility() == View.VISIBLE) {
                holder.imgDotted.setVisibility(View.GONE);
                holder.llCover.setVisibility(View.GONE);
            } else {
                holder.llCover.setVisibility(View.VISIBLE);
                holder.imgDotted.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtAddonCover, txtTenure, txtIdv, txtIdvRange, txtIns, txtNcb, txtGoodDriver;
        public Button btnPremium, btnBuyNow, btnPremiumBreak;
        public ImageView imgInsurer, imgDotted;
        public GridView gridCover;
        public LinearLayout llCover;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnPremium = itemView.findViewById(R.id.buttonPremium);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
            btnPremiumBreak = itemView.findViewById(R.id.btnPremiumBreak);
            imgInsurer = itemView.findViewById(R.id.imgInsurer);
            imgDotted = itemView.findViewById(R.id.imgDotted);
            txtAddonCover = itemView.findViewById(R.id.txtAddonCover);
            txtGoodDriver = itemView.findViewById(R.id.txtGoodDriver);
            txtTenure = itemView.findViewById(R.id.txtTenure);
            llCover = itemView.findViewById(R.id.llCover);
            txtIdv = itemView.findViewById(R.id.txtIdv);
            txtIns = itemView.findViewById(R.id.txtIns);
            txtIdvRange = itemView.findViewById(R.id.txtIdvRange);
            txtNcb = itemView.findViewById(R.id.txtNcb);
            gridCover = itemView.findViewById(R.id.gridCover);
        }
    }

    public interface OnPremiumClick {
        void OnPremiumButtonClick(int position);
    }

    public interface OnPremiumBreakClickListener {
        void OnBreakUpClicked(int position);
    }

    public interface OnAddonClickListener {
        void onAddonView(int position);
    }
}
