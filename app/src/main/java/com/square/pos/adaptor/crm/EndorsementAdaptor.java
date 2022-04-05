package com.square.pos.adaptor.crm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_crm.EndorseData;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-10-01.
 */
public class EndorsementAdaptor extends RecyclerView.Adapter<EndorsementAdaptor.ViewHolder> {
    ArrayList<EndorseData> claimList;
    Context mContext;
    OnDownloadClickListener downloadClickListener;
    OnCreateClaimListener createClaimListener;


    public EndorsementAdaptor(Context mContext, ArrayList<EndorseData> claimList) {
        this.claimList = claimList;
        this.mContext = mContext;
        if (mContext instanceof OnDownloadClickListener) {
            downloadClickListener = (OnDownloadClickListener) mContext;
        }
        if (mContext instanceof OnCreateClaimListener) {
            createClaimListener = (OnCreateClaimListener) mContext;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_endorsement, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        EndorseData policy = claimList.get(position);
        holder.txtInsurerName.setText(policy.getCustomerName());
        holder.vehicleNumber.setText(policy.getVehicleNo());
        holder.txtPolicyNo.setText(policy.getPolicyNo());
        holder.txtCompanyName.setText(policy.getCompany());
        holder.txtLob.setText(policy.getLob());
        holder.txtType.setText(policy.getTypeName());

        final String url = policy.getDownloadUrl();

        if (TextUtils.isEmpty(policy.getDownloadUrl()))
            holder.txtDownload.setVisibility(View.GONE);
        else holder.txtDownload.setVisibility(View.VISIBLE);

        holder.txtDownload.setOnClickListener(view -> {
            if (downloadClickListener != null) {
                downloadClickListener.OnDownloadClicked(position, url);
            }
        });

        holder.txtCreate.setOnClickListener(view -> {
            createClaimListener.onCreateClaim(position);
        });
    }

    @Override
    public int getItemCount() {
        return claimList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, vehicleNumber, txtCompanyName, txtPolicyNo,
                txtCreate, txtLob, txtType;
        public ImageView txtDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            vehicleNumber = itemView.findViewById(R.id.txtVehicleNumber);
            txtDownload = itemView.findViewById(R.id.txtDownload);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtPolicyNo = itemView.findViewById(R.id.txtPolicyNo);
            txtCreate = itemView.findViewById(R.id.txtCreate);
            txtLob = itemView.findViewById(R.id.txtLob);
            txtType = itemView.findViewById(R.id.txtType);
        }
    }

    public interface OnDownloadClickListener {
        void OnDownloadClicked(int position, String url);
    }

    public interface OnCreateClaimListener {
        void onCreateClaim(int position);
    }

}
