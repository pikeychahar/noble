package com.square.pos.adaptor.health;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_health.HealthQuote;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2022-03-29.
 */
public class HealthQuoteAdapter extends RecyclerView.Adapter<HealthQuoteAdapter.PolicyViewHolder> {
    ArrayList<HealthQuote> policyList = new ArrayList<>();
    LayoutInflater inflater;
    Context mContext;
    OnProceedClick onProceedClick;

    public HealthQuoteAdapter(Context mContext, ArrayList<HealthQuote> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnProceedClick) {
            onProceedClick = (OnProceedClick) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_quotation, parent, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder,
                                 final int position) {
        try {
            HealthQuote agentPolicy = policyList.get(position);
            final String quotationId = agentPolicy.getQuoteId();
            final String pdf = agentPolicy.getPdfUrl();
            holder.txtInsurerName.setText("Sum Insured: " + agentPolicy.getSumInsured());
            holder.vehicleNumber.setText(quotationId);
            holder.policyType.setText(agentPolicy.getPlanType().toUpperCase());


            holder.txtDes.setVisibility(View.GONE);
            String str = policyList.get(position).getPaymentStatus();

            if (!TextUtils.isEmpty(str) && str.equals("1")) {
                holder.txtProceed.setVisibility(View.GONE);
                holder.txtDes.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(pdf))
                    holder.txtDownLoad.setVisibility(View.VISIBLE);
                else holder.txtDownLoad.setVisibility(View.GONE);
            } else {
                holder.txtProceed.setVisibility(View.VISIBLE);
                holder.txtDes.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(agentPolicy.getFather())) {
                holder.txtCompany.setVisibility(View.VISIBLE);
                holder.txtCompany.setText(agentPolicy.getCompany().toUpperCase());
            } else holder.txtCompany.setVisibility(View.GONE);

            holder.txtVehicleModel.setText("Pincode: " + agentPolicy.getPincode());
            holder.txtProceed.setOnClickListener(v -> {
                if (onProceedClick != null) {
                    onProceedClick.OnProceedListener(holder.getAbsoluteAdapterPosition(), 0);
                }
            });
            holder.txtDownLoad.setOnClickListener(v -> {
                if (onProceedClick != null) {
                    onProceedClick.OnProceedListener(holder.getAbsoluteAdapterPosition(), 1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, vehicleNumber, txtVehicleModel, policyType, txtProceed,
                txtDes, txtCompany, txtDownLoad;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            txtVehicleModel = itemView.findViewById(R.id.txtVehicleModel);
            policyType = itemView.findViewById(R.id.policyType);
            txtProceed = itemView.findViewById(R.id.txtProceed);
            txtDes = itemView.findViewById(R.id.txtDes);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtDownLoad = itemView.findViewById(R.id.txtDownLoad);
        }
    }

    public interface OnProceedClick {
        void OnProceedListener(int position, int flag);
    }
}
