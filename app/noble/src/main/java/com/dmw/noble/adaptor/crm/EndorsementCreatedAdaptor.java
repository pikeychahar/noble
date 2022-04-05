package com.dmw.noble.adaptor.crm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_crm.EndorsementCreatedData;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-08-21.
 */
public class EndorsementCreatedAdaptor extends RecyclerView.Adapter<EndorsementCreatedAdaptor.ClaimViewHolder> {

    ArrayList<EndorsementCreatedData> policyList;
    Context mContext;
    OnChatListener onChatListener;

    public EndorsementCreatedAdaptor(Context mContext, ArrayList<EndorsementCreatedData> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnChatListener) {
            onChatListener = (OnChatListener) mContext;
        }
    }

    @NonNull
    @Override
    public ClaimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_endorsement_view, parent, false);
        return new ClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimViewHolder holder, final int position) {
        EndorsementCreatedData policy = policyList.get(position);

        String claimId = policy.getEndosmentId();
        String pType = policy.getPolicyType();
        holder.txtEndorseId.setText(claimId);
        holder.txtInsurerName.setText(policy.getCustomerName());
        holder.txtVehicleNumber.setText(policy.getVehicleNo());
        holder.txtClaimManager.setText(policy.getAssignedTo());

        holder.txtCompanyName.setText(policy.getCompany());
        holder.txtLob.setText(policy.getLob());
        holder.txtType.setText(policy.getTypeName());
        holder.txtPending.setText(policy.getPolicyNo());

        if (policy.getStatus() != null) {
            switch (policy.getStatus()) {
                case "0":
                    holder.txtStatus.setText(R.string.pending);
                    break;
                case "1":
                    holder.txtStatus.setText(R.string.in_process);
                    break;
                case "2":
                    holder.txtStatus.setText(R.string.completed);
                    break;
                case "3":
                    holder.txtStatus.setText(R.string.rejected);
                    break;
            }
        }

        holder.txtStatus.setOnClickListener(view -> {
            onChatListener.onChatClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class ClaimViewHolder extends RecyclerView.ViewHolder {
        public TextView txtEndorseId, txtClaimManager, txtCompanyName, txtStatus, txtInsurerName,
                txtLob, txtType, txtVehicleNumber, txtPending, txtProgress;
        LinearLayout linearProgress, linearComplete;

        public ClaimViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            txtEndorseId = itemView.findViewById(R.id.txtEndorseId);
            txtClaimManager = itemView.findViewById(R.id.txtClaimManager);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtLob = itemView.findViewById(R.id.txtLob);
            txtType = itemView.findViewById(R.id.txtType);
            txtVehicleNumber = itemView.findViewById(R.id.txtVehicleNumber);
            txtPending = itemView.findViewById(R.id.txtPending);
            txtProgress = itemView.findViewById(R.id.txtProgress);
            linearProgress = itemView.findViewById(R.id.llSI1);
            linearComplete = itemView.findViewById(R.id.llC);
        }
    }

    public interface OnChatListener {
        void onChatClick(int position);
    }
}