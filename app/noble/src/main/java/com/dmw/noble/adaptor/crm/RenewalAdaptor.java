package com.dmw.noble.adaptor.crm;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_crm.RenewData;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-11-11.
 */
public class RenewalAdaptor extends RecyclerView.Adapter<RenewalAdaptor.RenewalViewHolder> {

    ArrayList<RenewData> policyList;
    Context mContext;
    OnProceedListener onChatListener;
    OnQuickRenewal onQuickRenewalListener;
    OnDownloadClickListener downloadClickListener;
    OnRenewalAction onRenewalActionListener;

    public RenewalAdaptor(Context mContext, ArrayList<RenewData> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnProceedListener) {
            onChatListener = (OnProceedListener) mContext;
        }
        if (mContext instanceof OnQuickRenewal) {
            onQuickRenewalListener = (OnQuickRenewal) mContext;
        }
        if (mContext instanceof OnDownloadClickListener) {
            downloadClickListener = (OnDownloadClickListener) mContext;
        }
        if (mContext instanceof OnRenewalAction) {
            onRenewalActionListener = (OnRenewalAction) mContext;
        }
    }

    @NonNull
    @Override
    public RenewalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_renewal, parent, false);
        return new RenewalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RenewalViewHolder holder, final int position) {
        RenewData policy = policyList.get(position);

        try {
            String lob = policy.getLob();
            String source = policy.getSource();
            String status = policy.getStatus();
            holder.txtCustomerName.setText(policy.getCustomerName());
            holder.txtRenewal.setText(policy.getExpiryDays());

            if (!TextUtils.isEmpty(policy.getVehicleNo()))
                holder.txtVehicleNo.setText(policy.getVehicleNo());
            else holder.txtVehicleNo.setText(policy.getProductName());

            holder.txtPolicyNo.setText("Policy No." + policy.getPolicyNo());

            holder.txtFileType.setText(policy.getFileType());
            holder.txtLob.setText(lob);
            holder.txtType.setText(source);

            final String url = policy.getDownloadUrl();
            if (TextUtils.isEmpty(url))
                holder.txtDownload.setVisibility(View.GONE);
            else holder.txtDownload.setVisibility(View.VISIBLE);

            holder.txtDownload.setOnClickListener(v -> {
                if (downloadClickListener != null) {
                    downloadClickListener.OnDownloadClicked(position, url);
                }
            });

            if (lob.equalsIgnoreCase("motor")
                    && source.equalsIgnoreCase("online")) {
                holder.txtProceed.setVisibility(View.VISIBLE);
                holder.txtQuickRenewal.setVisibility(View.VISIBLE);
            } else {
                holder.txtProceed.setVisibility(View.GONE);
                holder.txtQuickRenewal.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(status)) {
                holder.txtRenewalStatus.setText(status);
                if (status.equalsIgnoreCase("renewed")
                        || status.equalsIgnoreCase("lost")) {
                    holder.lblRenewal.setVisibility(View.INVISIBLE);
                    holder.txtRenewal.setVisibility(View.INVISIBLE);
                    if (status.equalsIgnoreCase("renewed")) {
                        holder.spnRenewalStatus.setVisibility(View.GONE);
                        holder.txtProceed.setVisibility(View.GONE);
                        holder.txtQuickRenewal.setVisibility(View.GONE);
                    } else {
                        holder.spnRenewalStatus.setVisibility(View.VISIBLE);
                        holder.txtProceed.setVisibility(View.VISIBLE);
                        holder.txtQuickRenewal.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.lblRenewal.setVisibility(View.VISIBLE);
                    holder.txtRenewal.setVisibility(View.VISIBLE);
                }
            } else {
                holder.txtRenewalStatus.setText("Pending");
            }

            holder.txtProceed.setOnClickListener(view -> {
                onChatListener.onProceedClick(position);
            });

            holder.txtQuickRenewal.setOnClickListener(view -> {
                onQuickRenewalListener.onQuickRenewalClick(position);
            });

            holder.spnRenewalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0)
                        onRenewalActionListener.onRenewalActionClick(holder.getAbsoluteAdapterPosition(),
                                position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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

    public static class RenewalViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCustomerName, lblRenewal, txtRenewal, txtVehicleNo, txtPolicyNo,
                txtRenewalStatus, txtLob, txtType, txtDownload, txtCall, txtQuickRenewal, txtProceed,
                txtFileType;
        Spinner spnRenewalStatus;

        public RenewalViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            lblRenewal = itemView.findViewById(R.id.lblRenewal);
            txtRenewal = itemView.findViewById(R.id.txtRenewal);
            txtVehicleNo = itemView.findViewById(R.id.txtVehicleNo);
            txtPolicyNo = itemView.findViewById(R.id.txtPolicyNo);
            txtRenewalStatus = itemView.findViewById(R.id.txtRenewalStatus);
            txtLob = itemView.findViewById(R.id.txtLob);
            txtType = itemView.findViewById(R.id.txtType);
            txtDownload = itemView.findViewById(R.id.txtDownload);
            txtCall = itemView.findViewById(R.id.txtCall);
            spnRenewalStatus = itemView.findViewById(R.id.spnRenewalStatus);
            txtQuickRenewal = itemView.findViewById(R.id.txtQuickRenewal);
            txtProceed = itemView.findViewById(R.id.txtProceed);
            txtFileType = itemView.findViewById(R.id.txtFileType);
        }
    }

    public interface OnProceedListener {
        void onProceedClick(int position);
    }

    public interface OnQuickRenewal {
        void onQuickRenewalClick(int position);
    }

    public interface OnRenewalAction {
        void onRenewalActionClick(int position, int action);
    }

    public interface OnDownloadClickListener {
        void OnDownloadClicked(int position, String url);
    }
}