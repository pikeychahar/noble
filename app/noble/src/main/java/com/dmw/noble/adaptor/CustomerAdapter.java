package com.dmw.noble.adaptor;

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
import com.dmw.noble.model_pos.Customer;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2020-01-12.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.PolicyViewHolder> {
    private ArrayList<Customer> customerList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private OnCallClickListener onCallClickListener;


    public CustomerAdapter(Context mContext, ArrayList<Customer> customerList) {
        this.customerList = customerList;
        this.mContext = mContext;
        if (mContext instanceof OnCallClickListener) {
            onCallClickListener = (OnCallClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_customer, parent, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        Customer customerObj = customerList.get(position);
        holder.txtAgentName.setText(customerObj.getName());
        holder.txtAgentCity.setText(customerObj.getCity());
        holder.txtLastModified.setText("Last Modified Date: " + customerObj.getLastModifiedDate());

        String pos_status = customerObj.getPosStatus();
        switch (pos_status) {
            case "0":
                holder.txtPosStatus.setText("Pending");
                holder.txtPosCertified.setVisibility(View.GONE);
                holder.txtRejected.setVisibility(View.GONE);
                holder.txtPosStatus.setBackgroundResource(R.drawable.yellow_theme);
                break;
            case "1":
                holder.txtPosCertified.setVisibility(View.GONE);
                holder.txtRejected.setVisibility(View.GONE);
                holder.txtPosStatus.setText("Verified Details");
                holder.txtPosStatus.setBackgroundResource(R.drawable.bg_green);
                break;
            case "2":
                holder.txtPosCertified.setVisibility(View.GONE);
                holder.txtRejected.setVisibility(View.GONE);
                holder.txtPosStatus.setText("Certified");
                holder.txtPosCertified.setVisibility(View.VISIBLE);
                holder.txtPosCertified.setText(customerObj.getPosId());
                holder.txtPosStatus.setBackgroundResource(R.drawable.bg_green_dark);
                break;
            case "3":
                holder.txtPosCertified.setVisibility(View.GONE);
                holder.txtRejected.setVisibility(View.GONE);
                holder.txtPosStatus.setText("Incomplete");
                holder.txtPosStatus.setBackgroundResource(R.drawable.bg_badge);
                break;
            case "4":
                holder.txtPosCertified.setVisibility(View.GONE);
                holder.txtRejected.setVisibility(View.GONE);
                holder.txtPosStatus.setText("Under Training");
                holder.txtPosStatus.setBackgroundResource(R.drawable.yellow_theme);
                break;
            case "6":
                holder.txtPosStatus.setText("Rejected");
                holder.txtRejected.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(customerObj.getRejectedReason()))
                holder.txtRejected.setText(customerObj.getRejectedReason());
                else holder.txtRejected.setText("Reason not mentioned");
                holder.txtPosStatus.setBackgroundResource(R.drawable.bg_badge);
                break;
        }

        String mobile = customerObj.getMobile();
        holder.txtPosNumber.setOnClickListener(view -> {
            if (onCallClickListener != null) {
                onCallClickListener.OnCallClicked(position, mobile);
            }
        });

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtAgentName, txtLastModified, txtPosStatus, txtAgentCity,txtRejected,
                txtPosCertified;
        public ImageView txtPosNumber;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAgentName = itemView.findViewById(R.id.txtAgentName);
            txtLastModified = itemView.findViewById(R.id.txtLastModified);
            txtPosStatus = itemView.findViewById(R.id.txtPosStatus);
            txtAgentCity = itemView.findViewById(R.id.txtAgentCity);
            txtRejected = itemView.findViewById(R.id.txtRejected);
            txtPosNumber = itemView.findViewById(R.id.txtPosNumber);
            txtPosCertified = itemView.findViewById(R.id.txtPosCertified);

        }
    }

    public interface OnCallClickListener {
        void OnCallClicked(int position, String phone);
    }
}
