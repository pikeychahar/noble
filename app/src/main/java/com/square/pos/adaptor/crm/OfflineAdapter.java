package com.square.pos.adaptor.crm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_crm.OfflineData;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2019-10-14.
 */
public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.QuoteViewHolder> {
    ArrayList<OfflineData> policyList = new ArrayList<>();
    LayoutInflater inflater;
    Context mContext;
    OfflineAdapter.OnProceedClick onProceedClick;
    OnChatListener onChatListener;


    public OfflineAdapter(Context mContext, ArrayList<OfflineData> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OfflineAdapter.OnProceedClick) {
            onProceedClick = (OfflineAdapter.OnProceedClick) mContext;
        }
        if (mContext instanceof OnChatListener) {
            onChatListener = (OnChatListener) mContext;
        }
    }

    @NonNull
    @Override
    public OfflineAdapter.QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_offline, parent, false);

        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineAdapter.QuoteViewHolder holder, final int position) {
        OfflineData agentPolicy = policyList.get(position);
        final String quotationId = agentPolicy.getQuotationId();
        holder.txtInsurerName.setText(quotationId);
        holder.vehicleNumber.setText(agentPolicy.getVehicleNo());

        holder.txtVehicleModel.setText(agentPolicy.getCustomerName());
        holder.policyType.setText(agentPolicy.getStatus());

        holder.txtProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProceedClick != null) {
                    onProceedClick.OnProceedListener(holder.getAbsoluteAdapterPosition());
                }
            }
        });

        holder.txtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChatListener != null) {
                    onChatListener.onChatClick(holder.getAbsoluteAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, vehicleNumber, txtVehicleModel, policyType, txtProceed,
                txtChat;

        public QuoteViewHolder(@NonNull View itemView) {

            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            txtVehicleModel = itemView.findViewById(R.id.txtVehicleModel);
            policyType = itemView.findViewById(R.id.policyType);
            txtProceed = itemView.findViewById(R.id.txtProceed);
            txtChat = itemView.findViewById(R.id.txtChat);
        }
    }

    public interface OnProceedClick {
        void OnProceedListener(int position);
    }

    public interface OnChatListener {
        void onChatClick(int position);
    }
}
