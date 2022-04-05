package com.square.pos.adaptor;

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
import com.square.pos.model_health.Health;

import java.util.List;

/**
 * Created by Prahalad Chahar on 2021-03-01.
 */
public class HealthPolicyAdaptor extends RecyclerView.Adapter<HealthPolicyAdaptor.PolicyViewHolder> {
    private List<Health> policyList;
    private LayoutInflater inflater;
    private Context mContext;
    private OnDownloadHealthClickListener downloadHealthClickListener;


    public HealthPolicyAdaptor(Context mContext, List<Health> policyLists) {
        this.mContext = mContext;
        this.policyList = policyLists;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mContext instanceof OnDownloadHealthClickListener) {
            downloadHealthClickListener = (OnDownloadHealthClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_policy_health, viewGroup, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        Health policy = policyList.get(position);
        holder.txtInsurerName.setText(policy.getName());
        holder.txtQuote.setText(policy.getQuotationId());

        holder.txtCompanyName.setText(policy.getCompany());
        holder.txtPlanType.setText(policy.getPolicyType());

        final String url = policy.getPdf();

        if (TextUtils.isEmpty(policy.getPdf()))
            holder.txtDownload.setVisibility(View.GONE);
        else holder.txtDownload.setVisibility(View.VISIBLE);

        holder.txtDownload.setOnClickListener(view -> {
            if (downloadHealthClickListener != null) {
                downloadHealthClickListener.OnDownloadHealthClicked(position, url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, txtPlanType, txtCompanyName, txtQuote;
        public ImageView txtDownload;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            txtPlanType = itemView.findViewById(R.id.txtPlanType);
            txtDownload = itemView.findViewById(R.id.txtDownload);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtQuote = itemView.findViewById(R.id.txtQuote);
        }
    }

    public interface OnDownloadHealthClickListener {
        void OnDownloadHealthClicked(int position, String url);
    }
}
