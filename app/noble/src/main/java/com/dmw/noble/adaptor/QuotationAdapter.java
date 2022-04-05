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
 * Created by Prahalad Chahar on 2019-10-14.
 */
public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.QuoteViewHolder> {
    private ArrayList<Quotation> policyList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private OnProceedClick onProceedClick;


    public QuotationAdapter(Context mContext, ArrayList<Quotation> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnProceedClick) {
            onProceedClick = (OnProceedClick) mContext;
        }
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_quotation, parent, false);
        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, final int position) {
        try {
            Quotation agentPolicy = policyList.get(position);
            final String quotationId = agentPolicy.getQuotationId();
            final String pdf = agentPolicy.getPdfUrl();
            holder.txtInsurerName.setText(quotationId);
            holder.vehicleNumber.setText(agentPolicy.getRegistrationNo());
            if (agentPolicy.getTpOnly().equalsIgnoreCase("0"))
                holder.policyType.setText("Comprehensive");
            else if (agentPolicy.getTpOnly().equalsIgnoreCase("1"))
                holder.policyType.setText("Third Party");
            else holder.policyType.setText("Standalone OD");

            holder.txtVehicleModel.setText(agentPolicy.getVehicle());
            holder.txtDes.setVisibility(View.GONE);
            String str = policyList.get(position).getPaymentStatus();

            if (!TextUtils.isEmpty(str) && str.equals("1")) {
                holder.txtProceed.setVisibility(View.GONE);
                holder.txtDes.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(pdf))
                    holder.txtDownLoad.setVisibility(View.VISIBLE);
                else holder.txtDownLoad.setVisibility(View.GONE);
            } else {
                holder.txtProceed.setVisibility(View.VISIBLE);
                holder.txtDes.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(agentPolicy.getCompany())) {
                holder.txtCompany.setVisibility(View.VISIBLE);
                holder.txtCompany.setText(agentPolicy.getCompany().toUpperCase());
            } else holder.txtCompany.setVisibility(View.GONE);

            holder.txtVehicleModel.setText(agentPolicy.getVehicle());

            holder.txtProceed.setOnClickListener(v -> {
                if (onProceedClick != null) {
                    onProceedClick.OnProceedListener(holder.getAbsoluteAdapterPosition(), 0);
                }
            });
            holder.txtDownLoad.setOnClickListener(v -> {
                if (onProceedClick != null) {
                    onProceedClick.OnProceedListener(holder.getAbsoluteAdapterPosition(), 1);
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

    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, vehicleNumber, txtVehicleModel, policyType, txtProceed,
                txtDes, txtCompany, txtDownLoad;

        public QuoteViewHolder(@NonNull View itemView) {

            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            txtVehicleModel = itemView.findViewById(R.id.txtVehicleModel);
            policyType = itemView.findViewById(R.id.policyType);
            txtProceed = itemView.findViewById(R.id.txtProceed);
            txtDes = itemView.findViewById(R.id.txtDes);
            txtCompany = itemView.findViewById(R.id.txtCompany);
            txtDownLoad = itemView.findViewById(R.id.txtDownLoad);
        }
    }

    public interface OnProceedClick {
        void OnProceedListener(int position, int flag);
    }
}
