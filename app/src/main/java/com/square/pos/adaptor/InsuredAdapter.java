package com.square.pos.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_health.v2.InsuredMember;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2019-11-18.
 */
public class InsuredAdapter
        extends RecyclerView.Adapter<InsuredAdapter.PolicyViewHolder> {
    private ArrayList<InsuredMember> insuredList = new ArrayList<>();
    private LayoutInflater inflater;
    Context mContext;

    public InsuredAdapter(Context mContext, ArrayList<InsuredMember> agentPolicyList) {
        this.insuredList = agentPolicyList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public InsuredAdapter.PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_insured, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsuredAdapter.PolicyViewHolder holder, final int position) {

        InsuredMember insuredMember = insuredList.get(position);
        holder.txtInsuredMember.setText(insuredMember.getInsuredPerson());
        holder.txtName.setText(insuredMember.getName());
        holder.txtGender.setText(insuredMember.getGender());
        holder.txtDob.setText(insuredMember.getDob());
    }

    @Override
    public int getItemCount() {
        return insuredList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtInsuredMember, txtName, txtGender, txtDob;

        public PolicyViewHolder(@NonNull View itemView) {

            super(itemView);
            txtInsuredMember = itemView.findViewById(R.id.txtInsuredMember);
            txtName = itemView.findViewById(R.id.txtName);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtDob = itemView.findViewById(R.id.txtDob);
        }
    }
}
