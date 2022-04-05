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
 * Created by Prahalad Chahar on 2019-11-18.
 */
public class EarningAdapter
        extends RecyclerView.Adapter<EarningAdapter.EarnViewHolder> {

    ArrayList<PolicyData> earningList;
    LayoutInflater inflater;
    Context mContext;
    OnDownloadClickListener downloadClickListener;


    public EarningAdapter(Context mContext, ArrayList<PolicyData> earnings) {
        this.earningList = earnings;
        this.mContext = mContext;
        if (mContext instanceof OnDownloadClickListener)
            downloadClickListener = (OnDownloadClickListener) mContext;
    }

    @NonNull
    @Override
    public EarningAdapter.EarnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_earning, parent, false);

        return new EarningAdapter.EarnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarningAdapter.EarnViewHolder holder, final int position) {
        PolicyData policyData = earningList.get(position);

        holder.txtVehicleNo.setText(policyData.getVehicleNo());

        if (!TextUtils.isEmpty(policyData.getVehicleNo()))
            holder.txtVehicleNo.setText(policyData.getVehicleNo());
        else holder.txtVehicleNo.setText(policyData.getCustomerName());

        holder.txtCompany.setText(policyData.getCompany());
        holder.txtPolicyNo.setText("Policy No. " + policyData.getPolicyNo());
        holder.txtLob.setText(policyData.getLob());
        holder.txtType.setText(policyData.getTypeName());
        holder.txtOd.setText(policyData.getWebAgentPayoutODAmount());
        holder.txtTp.setText(policyData.getWebAgentPayoutTPAmount());
        holder.txtNet.setText(policyData.getNetPremium());
        holder.txtPayout.setText(policyData.getPayout());

        String status = policyData.getPostingStatusWeb();
        switch (status) {
            case "0":
                holder.txtStatus.setText("Pending For Posting");
                break;
            case "1":
                holder.txtStatus.setText("Pending For Accounts");
                break;
            case "2":
                holder.txtStatus.setText("Reject By Accounts");
                break;
            case "3":
                holder.txtStatus.setText("Pending For Banking");
                break;
            case "4":
                holder.txtStatus.setText("Reject By Banking");
                break;
            case "5":
                holder.txtStatus.setText("Approved");
                break;
            case "6":
                holder.txtStatus.setText("Paid/Payout Transferred");
                break;
        }
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
        return earningList.size();
    }

    public class EarnViewHolder extends RecyclerView.ViewHolder {
        public TextView txtVehicleNo, txtStatus, txtCompany, txtPolicyNo, txtLob, txtType, txtDown,
                txtOd, txtTp, txtNet, txtPayout;

        public EarnViewHolder(@NonNull View itemView) {
            super(itemView);

            txtVehicleNo = itemView.findViewById(R.id.txtVehicleNo);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtPolicyNo = itemView.findViewById(R.id.txtPolicyNo);
            txtLob = itemView.findViewById(R.id.txtLob);
            txtType = itemView.findViewById(R.id.txtType);
            txtDown = itemView.findViewById(R.id.txtType1);

            txtOd = itemView.findViewById(R.id.txtOd);
            txtTp = itemView.findViewById(R.id.txtTp);
            txtNet = itemView.findViewById(R.id.txtNet);
            txtPayout = itemView.findViewById(R.id.txtPayout);
        }
    }

    public interface OnDownloadClickListener {
        void OnDownloadClicked(int position, String url);
    }
}
