package com.square.pos.adaptor.crm;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.square.pos.R;
import com.square.pos.model_crm.Message;

import java.util.ArrayList;


/**
 * Created by Prahalad Chahar on 2019-11-18.
 */
public class ChatAdapter
        extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    ArrayList<Message> chatList = new ArrayList<>();
    Context mContext;
    OnAttachmentListener onImageClickListener;
    OnAttachmentPDFListener onAttachmentPDFListener;


    public ChatAdapter(Context mContext, ArrayList<Message> chatList) {
        this.chatList = chatList;
        this.mContext = mContext;

        if (mContext instanceof OnAttachmentListener) {
            onImageClickListener = (OnAttachmentListener) mContext;
        }
        if (mContext instanceof OnAttachmentPDFListener) {
            onAttachmentPDFListener = (OnAttachmentPDFListener) mContext;
        }
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_chat, parent, false);

        return new ChatAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, final int position) {
        Message chat = chatList.get(position);

        if (chat.getClassName().equals("left")) {
            holder.rlSender.setVisibility(View.VISIBLE);
            holder.rlReceiver.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(chat.getMessage())) {
                holder.cvSender.setVisibility(View.VISIBLE);
                holder.txtSender.setText(chat.getMessage());
                holder.imgSender.setVisibility(View.GONE);
            } else {
                holder.cvSender.setVisibility(View.GONE);
                String path = chat.getAttachmentUrl();
                if (!TextUtils.isEmpty(path)) {
                    if (path.contains(".pdf")) {
                        holder.pdfSender.setVisibility(View.VISIBLE);
                        holder.imgSender.setVisibility(View.GONE);
                    } else {
                        holder.imgSender.setVisibility(View.VISIBLE);
                        Glide.with(mContext)
                                .load(chat.getAttachmentUrl())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .fitCenter()
                                .into(holder.imgSender);
                    }
                }
                holder.imgSender.setOnClickListener(v ->
                        onImageClickListener.onImageViewClick(holder.getBindingAdapterPosition()));
                holder.pdfSender.setOnClickListener(v ->
                        onAttachmentPDFListener.onPDFClick(holder.getBindingAdapterPosition()));
            }
//            holder.txtManager.setText(chat.getName());
            holder.txtSenderTime.setText(chat.getDateTime());

        } else if (chat.getClassName().equals("right")) {
            holder.rlSender.setVisibility(View.GONE);
            holder.rlReceiver.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(chat.getMessage())) {
                holder.cvReceiver.setVisibility(View.VISIBLE);
                holder.txtReceiver.setText(chat.getMessage());
                holder.imgReceiver.setVisibility(View.GONE);
            } else {
                holder.cvReceiver.setVisibility(View.GONE);
                String path = chat.getAttachmentUrl();
                if (!TextUtils.isEmpty(path)) {

                    if (path.contains(".pdf")) {
                        holder.imgReceiver.setVisibility(View.GONE);
                        holder.pdfReceiver.setVisibility(View.VISIBLE);
                    } else {
                        holder.imgReceiver.setVisibility(View.VISIBLE);
                        Glide.with(mContext)
                                .load(chat.getAttachmentUrl())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .fitCenter()
                                .into(holder.imgReceiver);
                    }
                }

                holder.imgReceiver.setOnClickListener(v ->
                        onImageClickListener.onImageViewClick(holder.getBindingAdapterPosition()));

                holder.pdfReceiver.setOnClickListener(v ->
                        onAttachmentPDFListener.onPDFClick(holder.getBindingAdapterPosition()));
            }
            holder.txtReceiverDate.setText(chat.getDateTime());
        } else {
            holder.rlSender.setVisibility(View.GONE);
            holder.rlReceiver.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView txtManager, txtSender, txtSenderTime, txtReceiver, txtReceiverDate;
        public RelativeLayout rlSender, rlReceiver;
        public ImageView imgUser, imgSender, imgReceiver, pdfReceiver, pdfSender;
        public CardView cvSender, cvReceiver;

        public ChatViewHolder(@NonNull View itemView) {

            super(itemView);
            rlSender = itemView.findViewById(R.id.rlSender);
            rlReceiver = itemView.findViewById(R.id.rlReceiver);
            imgUser = itemView.findViewById(R.id.imgUser);
            txtManager = itemView.findViewById(R.id.txtManager);
            cvSender = itemView.findViewById(R.id.cvSender);
            cvReceiver = itemView.findViewById(R.id.cvReceiver);
            txtSender = itemView.findViewById(R.id.txtSender);
            imgSender = itemView.findViewById(R.id.imgSender);
            imgReceiver = itemView.findViewById(R.id.imgReceiver);
            pdfReceiver = itemView.findViewById(R.id.pdfReceiver);
            pdfSender = itemView.findViewById(R.id.pdfSender);
            txtSenderTime = itemView.findViewById(R.id.txtSenderTime);
            txtReceiver = itemView.findViewById(R.id.txtReceiver);
            txtReceiverDate = itemView.findViewById(R.id.txtReceiverDate);
        }
    }

    public interface OnAttachmentListener {
        void onImageViewClick(int position);
    }

    public interface OnAttachmentPDFListener {
        void onPDFClick(int position);
    }

}
