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
import com.dmw.noble.model.DocsWallet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Prahalad Chahar on 2019-11-18.
 */
public class DocsAdapter
        extends RecyclerView.Adapter<DocsAdapter.PolicyViewHolder> {
    private ArrayList<DocsWallet> policyList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private OnDocClickListener onDocClickListener;
    private OnRemoveClickListener onRemoveClickListener;


    public DocsAdapter(Context mContext, ArrayList<DocsWallet> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof OnDocClickListener) {
            onDocClickListener = (OnDocClickListener) mContext;
        }
        if (mContext instanceof OnRemoveClickListener) {
            onRemoveClickListener = (OnRemoveClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_doc, parent, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        DocsWallet agentPolicy = policyList.get(position);

        if (agentPolicy.getType().equalsIgnoreCase("Motor")) {
            holder.txtDocType.setText("Vehicle");
            holder.txtParty.setText(agentPolicy.getVehicleRegNo());
        } else if (agentPolicy.getType().equalsIgnoreCase("Health")) {
            holder.txtDocType.setText("Health");
            holder.txtParty.setText(agentPolicy.getClientPartyName());
        } else {
            holder.txtDocType.setText("Non Motor");
            holder.txtParty.setText(agentPolicy.getClientPartyName());
        }
        String input = agentPolicy.getInsertDate();
        if (!TextUtils.isEmpty(input)) {
            DateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            try {
                date = inputFormatter.parse(input);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyyy");
            assert date != null;
            String output = outputFormatter.format(date);

            holder.txtInsertdate.setText(output);
        }
        holder.vehicleNumber.setText(agentPolicy.getPolicyNo());
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDocClickListener.OnDocClicked(position);
            }
        });

        holder.txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveClickListener.OnRemoveClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDocType, vehicleNumber, txtParty, txtInsertdate, txtView, txtRemove;

        public PolicyViewHolder(@NonNull View itemView) {

            super(itemView);
            txtDocType = itemView.findViewById(R.id.txtDocumentType);
            vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            txtParty = itemView.findViewById(R.id.txtPartyNameType);
            txtInsertdate = itemView.findViewById(R.id.txtInsertDate);
            txtView = itemView.findViewById(R.id.txtView);
            txtRemove = itemView.findViewById(R.id.remove);
        }
    }

    public interface OnDocClickListener {
        void OnDocClicked(int position);
    }
    public interface OnRemoveClickListener {
        void OnRemoveClicked(int position);
    }
}
