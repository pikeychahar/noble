package com.square.pos.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_crm.PolicyData;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2019-10-10.
 */
public class PolicyAdaptor extends RecyclerView.Adapter<PolicyAdaptor.PolicyViewHolder> {
    ArrayList<PolicyData> policyList;
    OnDownloadClickListener downloadClickListener;

    public PolicyAdaptor(Context mContext, ArrayList<PolicyData> agentPolicyList) {
        this.policyList = agentPolicyList;
        if (mContext instanceof OnDownloadClickListener) {
            downloadClickListener = (OnDownloadClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_policy, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        PolicyData policyData = policyList.get(position);

        holder.txtVehicleNo.setText(policyData.getVehicleNo());
        holder.txtCompany.setText(policyData.getCompany());
        holder.txtPolicyNo.setText("Policy No. " + policyData.getPolicyNo());
        holder.txtLob.setText(policyData.getLob());
        holder.txtType.setText(policyData.getTypeName());
        holder.txtNet.setText(policyData.getNetPremium());
        holder.txtGross.setText(policyData.getGrossPremium());
        holder.txtBooking.setText(policyData.getBookingDate());
        holder.txtOwnerName.setText(policyData.getCustomerName());

        if (!TextUtils.isEmpty(policyData.getMakeModel())
                && policyData.getLob().equalsIgnoreCase("motor"))
            holder.txtMake.setText(policyData.getMakeModel());
        else
            holder.txtMake.setText(policyData.getSRNo());

        final String url = policyData.getDownloadUrl();
        if (TextUtils.isEmpty(url))
            holder.txtDown.setVisibility(View.GONE);

        holder.txtDown.setOnClickListener(v -> {
            if (downloadClickListener != null) {
                downloadClickListener.OnDownloadClicked(position, url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtVehicleNo, txtCompany, txtPolicyNo, txtLob, txtType, txtDown, txtGross,
                txtNet, txtBooking, txtOwnerName, txtMake;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVehicleNo = itemView.findViewById(R.id.txtVehicleNo);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtPolicyNo = itemView.findViewById(R.id.txtPolicyNo);
            txtLob = itemView.findViewById(R.id.txtLob);
            txtType = itemView.findViewById(R.id.txtType);
            txtDown = itemView.findViewById(R.id.txtType1);
            txtMake = itemView.findViewById(R.id.txtMake);

            txtGross = itemView.findViewById(R.id.txtGross);
            txtNet = itemView.findViewById(R.id.txtNet);
            txtBooking = itemView.findViewById(R.id.txtBooking);
            txtOwnerName = itemView.findViewById(R.id.txtOwnerName);
        }
    }

    public interface OnDownloadClickListener {
        void OnDownloadClicked(int position, String url);
    }
}
