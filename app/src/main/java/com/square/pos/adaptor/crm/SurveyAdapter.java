package com.square.pos.adaptor.crm;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_crm.SurveyData;

import java.util.ArrayList;


/**
 * Created by Prahalad Chahar on 2021-11-01.
 */
public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {
    ArrayList<SurveyData> chatList;
    Context mContext;
    OnStatusStatusListener onStatusStatusListener;
    OnInfoClickListener onInfoClickListener;

    public SurveyAdapter(Context mContext, ArrayList<SurveyData> chatList) {
        this.chatList = chatList;
        this.mContext = mContext;

        if (mContext instanceof OnStatusStatusListener) {
            onStatusStatusListener = (OnStatusStatusListener) mContext;
        }
        if (mContext instanceof OnInfoClickListener) {
            onInfoClickListener = (OnInfoClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public SurveyAdapter.SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_my_survey, parent, false);

        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyAdapter.SurveyViewHolder holder, final int position) {
        SurveyData ticket = chatList.get(position);

        holder.txtTicketId.setText(ticket.getVehicleNo());
        holder.txtTicketType.setText(ticket.getProduct());
        holder.txtId.setText(ticket.getSurveyId());
        if (!TextUtils.isEmpty(ticket.getAssignedTo()))
        holder.txtTicketManager.setText(ticket.getAssignedTo());
        else holder.txtTicketManager.setVisibility(View.GONE);

        String status = ticket.getStatus();
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "0":
                    holder.txtStatus.setText(R.string.pending);
                    break;
                case "1":
                    holder.txtStatus.setText(R.string.in_process);
                    break;
                case "2":
                    holder.txtStatus.setText(R.string.completed);
                    break;
                case "3":
                    holder.txtStatus.setText(R.string.rejected);
                    break;
            }
        }

        holder.txtStatus.setOnClickListener(v -> {
            onStatusStatusListener.onStatusClick(holder.getAbsoluteAdapterPosition());
        });
        holder.txtInfo.setOnClickListener(v -> {
            onInfoClickListener.onInfoClick(holder.getAbsoluteAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class SurveyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTicketManager, txtTicketId, txtStatus, txtTicketType, txtId, txtInfo;
        LinearLayout linearProgress, linearComplete;

        public SurveyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTicketManager = itemView.findViewById(R.id.txtTicketManager);
            txtTicketId = itemView.findViewById(R.id.txtTicketId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            linearProgress = itemView.findViewById(R.id.llSI1);
            linearComplete = itemView.findViewById(R.id.llC);
            txtTicketType = itemView.findViewById(R.id.txtTicketType);
            txtId = itemView.findViewById(R.id.txtId);
            txtInfo = itemView.findViewById(R.id.txtInfo);
        }
    }

    public interface OnStatusStatusListener {
        void onStatusClick(int position);
    }
    public interface OnInfoClickListener {
        void onInfoClick(int position);
    }
}
