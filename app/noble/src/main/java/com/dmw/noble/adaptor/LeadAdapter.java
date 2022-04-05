package com.dmw.noble.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_pos.Lead;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2019-10-31.
 */
public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.PolicyViewHolder> {
    private ArrayList<Lead> policyList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private OnViewLead onLeadViewListener;


    public LeadAdapter(Context mContext, ArrayList<Lead> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;

        if (mContext instanceof OnViewLead) {
            onLeadViewListener = (OnViewLead) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lead, parent, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        Lead lead = policyList.get(position);
        holder.txtInsurerName.setText(lead.getName());

        if (lead.getType().equalsIgnoreCase("1")) {
            holder.policyType.setText("Bike");
            holder.vehicleNumber.setText(lead.getGadiNo());
            holder.txtVehicleModel.setText(lead.getMake()+" "+lead.getModel()+" "+lead.getVariant());
        } else if (lead.getType().equalsIgnoreCase("2")) {
            holder.policyType.setText("Car");
            holder.vehicleNumber.setText(lead.getGadiNo());
            holder.txtVehicleModel.setText(lead.getMake()+" "+lead.getModel()+" "+lead.getVariant());
        } else if (lead.getType().equalsIgnoreCase("3")) {
            holder.policyType.setText("Health");
            holder.vehicleNumber.setText(lead.getPlanType());
            holder.txtVehicleModel.setText(lead.getSumAssured());
        } else if (lead.getType().equalsIgnoreCase("4")) {
            holder.policyType.setText("Corporate");
            holder.vehicleNumber.setText(lead.getCorporateInsType());
            holder.txtVehicleModel.setText(lead.getOrganisationName());
        }holder.leadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeadViewListener.OnViewLeadClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, vehicleNumber, txtVehicleModel, policyType;
        public ImageView leadInfo;

        public PolicyViewHolder(@NonNull View itemView) {

            super(itemView);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);
            vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            txtVehicleModel = itemView.findViewById(R.id.txtVehicleModel);
            policyType = itemView.findViewById(R.id.policyType);
            leadInfo = itemView.findViewById(R.id.leadInfo);
        }
    }

    public interface OnViewLead {
        void OnViewLeadClicked(int position);
    }
}
