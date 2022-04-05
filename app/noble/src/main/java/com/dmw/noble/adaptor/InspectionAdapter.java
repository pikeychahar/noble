package com.dmw.noble.adaptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_pos.Quotation;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2020-10-14.
 */
public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.PolicyViewHolder> {

    ArrayList<Quotation> policyList;
    Context mContext;
    private OnProceedClick onProceedClick;

    public InspectionAdapter(Context mContext, ArrayList<Quotation> agentPolicyList) {
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
                .inflate(R.layout.layout_inspection, parent, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder,
                                 final int position) {
        Quotation agentPolicy = policyList.get(position);
        final String quotationId = agentPolicy.getQuotationId();
        final String inspectionStatus = agentPolicy.getInspectionStatus();
        final String company = agentPolicy.getCompany();
        final String regNo = agentPolicy.getRegistrationNo();
        final String created = agentPolicy.getCreated();
        final String updated = agentPolicy.getUpdated();
        final String forward = agentPolicy.getForward();
        holder.txtInsurerName.setText(quotationId);

        holder.txtCreated.setText("Created: " + created);
        holder.txtUpdated.setText("Updated: " + updated);

        if (!TextUtils.isEmpty(company))
            holder.vehicleNumber.setText(regNo + " - " + company.toUpperCase());
        else
            holder.vehicleNumber.setText(regNo);

        if (!TextUtils.isEmpty(inspectionStatus))
            holder.txtInspectionStatus.setText(inspectionStatus.toUpperCase());
        holder.txtVehicleModel.setText(agentPolicy.getVehicle());

        if (!inspectionStatus.equalsIgnoreCase("APPROVED")) {
            holder.txtProceed.setVisibility(View.GONE);
            holder.txtUpdated.setVisibility(View.GONE);
            if (inspectionStatus.equalsIgnoreCase("rejected"))
                holder.txtInspectionStatus.setBackgroundColor(mContext.getColor(R.color.colorRed));
            if (inspectionStatus.equalsIgnoreCase("pending"))
                holder.txtInspectionStatus.setBackgroundColor(mContext.getColor(R.color.colorGray));
        } else {
            if (forward.equalsIgnoreCase("yes"))
                holder.txtProceed.setVisibility(View.VISIBLE);
            holder.txtInspectionStatus.setBackgroundColor(mContext.getColor(R.color.colorGreen));
        }

        holder.txtProceed.setOnClickListener(v -> {
            if (onProceedClick != null) {
                onProceedClick.OnProceedListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, vehicleNumber, txtVehicleModel, txtProceed,
                txtInspectionStatus, txtCreated, txtUpdated;

        public PolicyViewHolder(@NonNull View itemView) {

            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            txtVehicleModel = itemView.findViewById(R.id.txtVehicleModel);
            txtProceed = itemView.findViewById(R.id.txtProceed);
            txtCreated = itemView.findViewById(R.id.txtCreated);
            txtUpdated = itemView.findViewById(R.id.txtUpdated);
            txtInspectionStatus = itemView.findViewById(R.id.txtInspectionStatus);
        }
    }

    public interface OnProceedClick {
        void OnProceedListener(int position);
    }
}
