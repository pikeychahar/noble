package com.dmw.noble.adaptor.crm;

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

import com.dmw.noble.R;
import com.dmw.noble.model_crm.Claim;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2019-10-10.
 */
public class ClaimViewAdaptor extends RecyclerView.Adapter<ClaimViewAdaptor.ClaimViewHolder> {
    ArrayList<Claim> claimList;
    Context mContext;
    OnDownloadClickListener downloadClickListener;
    OnCreateClaimListener createClaimListener;


    public ClaimViewAdaptor(Context mContext, ArrayList<Claim> claimList) {
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
    public ClaimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_claim, parent, false);

        return new ClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Claim policy = claimList.get(position);
        holder.txtInsurerName.setText(policy.getCustomerName());
        holder.vehicleNumber.setText(policy.getVehicleNo());
        holder.txtQuote.setText(policy.getPolicyNo());
        holder.txtCompanyName.setText(policy.getCompany());
        holder.txtVehicleModel.setText(policy.getMakeModel());
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

    public static class ClaimViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, vehicleNumber, txtVehicleModel, txtCompanyName, txtQuote,
                txtCreate, txtLob, txtType;
        public ImageView txtDownload;

        public ClaimViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            txtVehicleModel = itemView.findViewById(R.id.txtVehicleModel);
            txtDownload = itemView.findViewById(R.id.txtDownload);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtQuote = itemView.findViewById(R.id.txtQuote);
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
