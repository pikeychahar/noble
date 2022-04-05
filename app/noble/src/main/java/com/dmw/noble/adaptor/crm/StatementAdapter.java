package com.dmw.noble.adaptor.crm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_crm.StateData;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;


/**
 * Created by Prahalad Chahar on 2021-11-18.
 */
public class StatementAdapter
        extends RecyclerView.Adapter<StatementAdapter.EarnViewHolder> {

    ArrayList<StateData> earningList = new ArrayList<>();
    LayoutInflater inflater;
    Context mContext;

    public StatementAdapter(Context mContext, ArrayList<StateData> earnings) {
        this.earningList = earnings;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public EarnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_statement, parent, false);

        return new EarnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarnViewHolder holder, final int position) {
        StateData earning = earningList.get(position);
        holder.txtDate.setText(earning.getAddStamp());
        holder.txtUtr.setText("UTR No: " + earning.getUTRNo());
        holder.txtGross.setText(AppUtils.round("" + earning.getTotalPayoutAmount()));
        holder.txtTds.setText("" + earning.getTds());
        holder.txtTotalFile.setText("" + earning.getTotalFiles());
        holder.txtApproved.setText(earning.getApproved());
        holder.txtRejected.setText(earning.getRejected());
        holder.txtPending.setText(earning.getPending());
        holder.txtReqId.setText("Request Id: " + earning.getGroupId());
        holder.txtTdsAmt.setText(AppUtils.round("" + earning.getTotalTDSAmount()));
        holder.txtNetAmount.setText(AppUtils.round("" + earning.getTotalPayoutNetAmount()));

    }

    @Override
    public int getItemCount() {
        return earningList.size();
    }

    public static class EarnViewHolder extends RecyclerView.ViewHolder {
        public TextView txtGross, txtDate, txtTds, txtTdsAmt, txtNetAmount, txtTotalFile, txtUtr,
                txtReqId, txtApproved, txtRejected, txtPending;

        public EarnViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTds = itemView.findViewById(R.id.txtTds);
            txtGross = itemView.findViewById(R.id.txtGross);
            txtDate = itemView.findViewById(R.id.txtTransactionDate);
            txtTdsAmt = itemView.findViewById(R.id.txtTdsAmt);
            txtNetAmount = itemView.findViewById(R.id.txtNetAmount);
            txtTotalFile = itemView.findViewById(R.id.txtTotalFile);
            txtUtr = itemView.findViewById(R.id.txtUtr);
            txtReqId = itemView.findViewById(R.id.txtReqId);
            txtApproved = itemView.findViewById(R.id.txtApproved);
            txtRejected = itemView.findViewById(R.id.txtRejected);
            txtPending = itemView.findViewById(R.id.txtPending);
        }
    }
}
