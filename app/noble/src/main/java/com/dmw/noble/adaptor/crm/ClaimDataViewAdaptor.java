package com.dmw.noble.adaptor.crm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_crm.ClaimObj;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2021-08-21.
 */
public class ClaimDataViewAdaptor extends RecyclerView.Adapter<ClaimDataViewAdaptor.ClaimViewHolder> {

    ArrayList<ClaimObj> policyList;
    Context mContext;
    OnChatListener onChatListener;

    public ClaimDataViewAdaptor(Context mContext, ArrayList<ClaimObj> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnChatListener) {
            onChatListener = (OnChatListener) mContext;
        }
    }

    @NonNull
    @Override
    public ClaimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_claim_single_data, parent, false);
        return new ClaimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimViewHolder holder, final int position) {
        ClaimObj claimObj = policyList.get(position);
        if (claimObj.getLabel().equals("Intimated To Insurer")) {
            holder.txtTitle.setVisibility(View.VISIBLE);
            holder.txtTitle.setText("Intimator Details");
        } else if (claimObj.getLabel().equals("Loss Type")) {
            holder.txtTitle.setVisibility(View.VISIBLE);
            holder.txtTitle.setText("Accidental Details");
        }

        holder.textInputLayout.setHint(claimObj.getLabel());
        holder.edtValue.setText(claimObj.getValue());
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class ClaimViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public EditText edtValue;
        public com.google.android.material.textfield.TextInputLayout textInputLayout;

        public ClaimViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            textInputLayout = itemView.findViewById(R.id.tIValue);
            edtValue = itemView.findViewById(R.id.edtValue);
        }
    }

    public interface OnChatListener {
        void onChatClick(int position);
    }
}