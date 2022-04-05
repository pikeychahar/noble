package com.dmw.noble.adaptor.crm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_crm.CancelledData;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-08-27.
 */
public class CancelledAdaptor extends RecyclerView.Adapter<CancelledAdaptor.ClaimViewHolder> {

    ArrayList<CancelledData> policyList;
    Context mContext;
    OnDownloadClickListener onDownloadClickListener;
    OnInfoClickListener onInfoClickListener;

    public CancelledAdaptor(Context mContext, ArrayList<CancelledData> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnDownloadClickListener) {
            onDownloadClickListener = (OnDownloadClickListener) mContext;
        }
if (mContext instanceof OnInfoClickListener) {
    onInfoClickListener = (OnInfoClickListener) mContext;
        }

    }

    @NonNull
    @Override
    public ClaimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cancelled, parent, false);
        return new ClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimViewHolder holder, final int position) {
        CancelledData policy = policyList.get(position);

        String claimId = policy.getCancelId();
        holder.txtId.setText(claimId);
        holder.txtManager.setText(policy.getAssignedTo());
        holder.txtLob.setText(policy.getLob());
        holder.txtType.setText(policy.getTypeName());
        holder.txtCompanyName.setText(policy.getCompany());
        holder.txtVehicleNumber.setText(policy.getVehicleNo());
        holder.txtPolicyNo.setText(policy.getPolicyNo());
        final String url = policy.getDownloadUrl();

        holder.txtDownload.setOnClickListener(view -> {
            if (onDownloadClickListener != null) {
                onDownloadClickListener.OnDownloadClicked(position, url);
            }
        });

        holder.txtStatus.setOnClickListener(view -> {
            if (onInfoClickListener != null) {
                onInfoClickListener.onInfo(position);
            }
        });

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

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class ClaimViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId, txtManager, txtCompanyName, txtVehicleNumber, txtPolicyNo,
                txtLob, txtType, txtStatus;
        ImageView txtDownload;

        public ClaimViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtManager = itemView.findViewById(R.id.txtManager);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtVehicleNumber = itemView.findViewById(R.id.txtVehicleNumber);
            txtPolicyNo = itemView.findViewById(R.id.txtPolicyNo);

            txtLob = itemView.findViewById(R.id.txtLob);
            txtType = itemView.findViewById(R.id.txtType);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDownload = itemView.findViewById(R.id.txtDownload);
        }
    }

    public interface OnDownloadClickListener {
        void OnDownloadClicked(int position, String url);
    }
    public interface OnInfoClickListener {
        void onInfo(int position);
    }
}