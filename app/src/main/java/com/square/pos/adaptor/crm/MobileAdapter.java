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
import com.square.pos.model_crm.MobilePos;

import java.util.ArrayList;


/**
 * Created by Prahalad Chahar on 2021-08-27.
 */
public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.MobileViewHolder> {
    ArrayList<MobilePos> chatList;
    Context mContext;

    public MobileAdapter(Context mContext, ArrayList<MobilePos> chatList) {
        this.chatList = chatList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MobileAdapter.MobileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_mobile_pos, parent, false);

        return new MobileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MobileAdapter.MobileViewHolder holder, final int position) {
        MobilePos ticket = chatList.get(position);

        holder.txtTicketId.setText(ticket.getMobile());
        holder.txtTicketType.setText(ticket.getAddStamp());
        if (!TextUtils.isEmpty(ticket.getManager()))
        holder.txtTicketManager.setText(ticket.getManager());
        else holder.txtTicketManager.setVisibility(View.GONE);

        String status = ticket.getStatus();
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "0":
                    holder.txtStatus.setText(R.string.pending);
                    break;
                case "1":
                    holder.txtStatus.setText(R.string.approved);
                    break;
                case "2":
                    holder.txtStatus.setText(R.string.rejected);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class MobileViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTicketManager, txtTicketId, txtStatus, txtTicketType, txtPending,
                txtProgress;
        LinearLayout linearProgress, linearComplete;

        public MobileViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTicketManager = itemView.findViewById(R.id.txtTicketManager);
            txtTicketId = itemView.findViewById(R.id.txtTicketId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            linearProgress = itemView.findViewById(R.id.llSI1);
            linearComplete = itemView.findViewById(R.id.llC);
            txtTicketType = itemView.findViewById(R.id.txtTicketType);
            txtPending = itemView.findViewById(R.id.txtPending);
            txtProgress = itemView.findViewById(R.id.txtProgress);
        }
    }
}
