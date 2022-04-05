package com.dmw.noble.adaptor.crm;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_crm.StatusDatum;
import com.dmw.noble.model_crm.TicketData;

import java.util.ArrayList;


/**
 * Created by Prahalad Chahar on 2021-08-27.
 */
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    ArrayList<TicketData> chatList;
    Context mContext;
    OnTicketStatusListener onTicketStatusListener;

    public TicketAdapter(Context mContext, ArrayList<TicketData> chatList) {
        this.chatList = chatList;
        this.mContext = mContext;

        if (mContext instanceof OnTicketStatusListener) {
            onTicketStatusListener = (OnTicketStatusListener) mContext;
        }
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_my_ticket, parent, false);

        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, final int position) {
        TicketData ticket = chatList.get(position);

        holder.txtTicketId.setText(ticket.getTicketId());
        holder.txtTicketType.setText(ticket.getTicketTypeDes());
        if (!TextUtils.isEmpty(ticket.getTicketManager()))
            holder.txtTicketManager.setText(ticket.getTicketManager());
        else holder.txtTicketManager.setVisibility(View.GONE);

        String status = ticket.getStatus();
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "1":
                    holder.txtStatus.setText(R.string.pending);
                    break;
                case "2":
                    holder.txtStatus.setText(R.string.in_process);
                    break;
                case "3":
                    holder.txtStatus.setText(R.string.completed);
                    break;
            }
        }

        holder.txtPending.setText("");
        holder.linearProgress.setVisibility(View.GONE);
        holder.linearComplete.setVisibility(View.GONE);

        if (ticket.getStatusData() != null) {
            ArrayList<StatusDatum> list = new ArrayList<>(ticket.getStatusData());
            if (list.size() > 0) {
                for (int index = 0; index < list.size(); index++) {
                    if (index == 0) {
                        holder.txtPending.setText(list.get(index).getTimestamp());
                    } else if (index == 1) {
                        holder.linearProgress.setVisibility(View.VISIBLE);
                        holder.txtProgress.setText(list.get(index).getTimestamp());
                    } else if (index == 2) {
                        holder.linearComplete.setVisibility(View.VISIBLE);
                        holder.txtTicketClosed.setText(list.get(index).getTimestamp());
                    }
                }
            }
        }

        holder.txtStatus.setOnClickListener(v -> {
            onTicketStatusListener.onTicketClick(holder.getAbsoluteAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTicketManager, txtTicketId, txtStatus, txtTicketType, txtPending,
                txtProgress, txtTicketClosed;
        LinearLayout linearProgress, linearComplete;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTicketManager = itemView.findViewById(R.id.txtTicketManager);
            txtTicketId = itemView.findViewById(R.id.txtTicketId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            linearProgress = itemView.findViewById(R.id.llSI1);
            linearComplete = itemView.findViewById(R.id.llC);
            txtTicketType = itemView.findViewById(R.id.txtTicketType);
            txtPending = itemView.findViewById(R.id.txtPending);
            txtProgress = itemView.findViewById(R.id.txtProgress);
            txtTicketClosed = itemView.findViewById(R.id.txtTicketClosed);
        }
    }

    public interface OnTicketStatusListener {
        void onTicketClick(int position);
    }
}
