package com.dmw.noble.adaptor.crm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_crm.ClaimDatum;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-08-21.
 */
public class ClaimAdaptor extends RecyclerView.Adapter<ClaimAdaptor.ClaimViewHolder> {

    ArrayList<ClaimDatum> policyList;
    Context mContext;
    OnChatListener onChatListener;
    OnInfoListener onInfoListener;

    public ClaimAdaptor(Context mContext, ArrayList<ClaimDatum> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnChatListener) {
            onChatListener = (OnChatListener) mContext;
        }
        if (mContext instanceof OnInfoListener) {
            onInfoListener = (OnInfoListener) mContext;
        }
    }

    @NonNull
    @Override
    public ClaimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_my_claim, parent, false);
        return new ClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimViewHolder holder, final int position) {
        ClaimDatum policy = policyList.get(position);

        String claimId = policy.getClaimId();
        String pType = policy.getLossType();
        holder.txtClaimId.setText(claimId);
        holder.txtClaimManager.setText(policy.getClaimManager());
        holder.txtCompanyName.setText("Loss Type:" + policy.getLossType());
        holder.txtIntimation.setText("Intimate To Insurer: " + policy.getIntimatedToInsurer());
        holder.txtSurvey.setText("Survey: " + policy.getSurveyStatus());
        holder.txtClaimCreated.setText("Created On: " + policy.getAddStamp());

        holder.txtCreate.setOnClickListener(view -> {
            onChatListener.onChatClick(position);
        });

        holder.txtInfo.setOnClickListener(view -> {
            onInfoListener.onInfoClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class ClaimViewHolder extends RecyclerView.ViewHolder {
        public TextView txtClaimId, txtClaimManager, txtCompanyName, txtIntimation, txtSurvey,
                txtClaimCreated, txtCreate, txtInfo;

        public ClaimViewHolder(@NonNull View itemView) {
            super(itemView);
            txtClaimId = itemView.findViewById(R.id.txtClaimId);
            txtClaimManager = itemView.findViewById(R.id.txtClaimManager);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtIntimation = itemView.findViewById(R.id.txtIntimation);
            txtSurvey = itemView.findViewById(R.id.txtSurvey);
            txtClaimCreated = itemView.findViewById(R.id.txtClaimCreated);
            txtCreate = itemView.findViewById(R.id.txtCreate);
            txtInfo = itemView.findViewById(R.id.txtInfo);
        }
    }

    public interface OnChatListener {
        void onChatClick(int position);
    }

    public interface OnInfoListener {
        void onInfoClick(int position);
    }
}