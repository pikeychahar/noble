package com.dmw.noble.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.model_health.HealthPremiumPojo;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.UtilClass;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2019-11-18.
 */
public class StarAdapter
        extends RecyclerView.Adapter<StarAdapter.PolicyViewHolder> {
    private ArrayList<HealthPremiumPojo> policyList = new ArrayList<>();
    private Context mContext;
    private OnPremiumClick onPremiumClick;
    private OnBreakClickListener onBreakClickListener;
    private OnHospitalView onHospitalViewListener;
    private OnBrochureView onBrochureViewListener;
    private OnPlanBenefitView onPlanBenefitViewListener;
    private OnCheckClickInterface onCheckClickInterface;


    public StarAdapter(Context mContext, ArrayList<HealthPremiumPojo> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;

        if (mContext instanceof OnPremiumClick) {
            onPremiumClick = (OnPremiumClick) mContext;
        }
        if (mContext instanceof OnBreakClickListener) {
            onBreakClickListener = (OnBreakClickListener) mContext;
        }
        if (mContext instanceof OnHospitalView) {
            onHospitalViewListener = (OnHospitalView) mContext;
        }
        if (mContext instanceof OnBrochureView) {
            onBrochureViewListener = (OnBrochureView) mContext;
        }
        if (mContext instanceof OnPlanBenefitView) {
            onPlanBenefitViewListener = (OnPlanBenefitView) mContext;
        }
        if (mContext instanceof OnCheckClickInterface) {
            onCheckClickInterface = (OnCheckClickInterface) mContext;
        }
    }

    @NonNull
    @Override
    public StarAdapter.PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_quote, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarAdapter.PolicyViewHolder holder, final int position) {
        HealthPremiumPojo starHealthPojo = policyList.get(position);
        holder.btnPremium.setText(AppUtils.RUPEE + starHealthPojo.getPremium());
        String imgPath = starHealthPojo.getImgPath();

        if (!TextUtils.isEmpty((imgPath))) {
            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .animate(android.R.anim.fade_in)
                    .into(holder.imgInsurer);
        }
        holder.txtTenure.setText(starHealthPojo.getPlanTenure() + " Year");
        if (!TextUtils.isEmpty(starHealthPojo.getPlanName()))
            holder.txtPlanName.setText(starHealthPojo.getPlanName());
        if (!TextUtils.isEmpty(starHealthPojo.getSumAssured()))
            holder.txtSumAssured.setText(starHealthPojo.getSumAssured());
        if (!TextUtils.isEmpty(starHealthPojo.getCompany()))
            holder.txtCompany.setText(starHealthPojo.getCompany().toUpperCase());

        holder.btnPremium.setOnClickListener(v -> onPremiumClick
                .OnPremiumButtonClick(position));

        holder.btnPremiumBreak.setOnClickListener(v -> onBreakClickListener
                .onBreakUpClick(position));

        holder.lblHospitalList.setOnClickListener(v -> onHospitalViewListener
                .onHospitalClick(position));

        holder.lblBrochure.setOnClickListener(v -> onBrochureViewListener
                .onBrochureClick(position));

        holder.lblPlanBenefit.setOnClickListener(v -> onPlanBenefitViewListener
                .onPlanBenefitClick(position));

        holder.checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            int checked = UtilClass.getInstance().getChecked();
            if (holder.checkbox.isChecked()) {
                if (checked == 3) {
                    holder.checkbox.setChecked(false);
                    Toast.makeText(mContext, "Maximum 3", Toast.LENGTH_SHORT).show();
                } else
                    onCheckClickInterface.onCheckChange(position, holder.checkbox.isChecked());
            } else
                onCheckClickInterface.onCheckChange(position, holder.checkbox.isChecked());
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPlanName, txtSumAssured, txtTenure, lblHospitalList, lblBrochure,
                lblPlanBenefit, txtCompany;
        public androidx.appcompat.widget.AppCompatButton btnPremiumBreak, btnPremium;
        public ImageView imgInsurer;
        public CheckBox checkbox;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlanName = itemView.findViewById(R.id.txtPlanName);
            txtSumAssured = itemView.findViewById(R.id.txtSumAssured);
            imgInsurer = itemView.findViewById(R.id.imgInsurer);
            txtTenure = itemView.findViewById(R.id.txtTenure);
            btnPremium = itemView.findViewById(R.id.btnPremium);
            lblHospitalList = itemView.findViewById(R.id.lblHospitalList);
            lblBrochure = itemView.findViewById(R.id.lblBrochure);
            lblPlanBenefit = itemView.findViewById(R.id.lblPlanBenefit);
            btnPremiumBreak = itemView.findViewById(R.id.btnPremiumBreak);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }

    public interface OnPremiumClick {
        void OnPremiumButtonClick(int position);
    }

    public interface OnBreakClickListener {
        void onBreakUpClick(int position);
    }

    public interface OnHospitalView {
        void onHospitalClick(int position);
    }

    public interface OnBrochureView {
        void onBrochureClick(int position);
    }

    public interface OnPlanBenefitView {
        void onPlanBenefitClick(int position);
    }

    public interface OnCheckClickInterface {
        void onCheckChange(int position, boolean status);
    }
}
