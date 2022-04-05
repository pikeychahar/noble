package com.square.pos.adaptor.crm;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.square.pos.R;
import com.square.pos.model.MobileObj;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2019-10-14.
 */
public class NewMobileAdaptor extends RecyclerView.Adapter<NewMobileAdaptor.QuoteViewHolder> {
    public ArrayList<Integer> policyList;
    public ArrayList<MobileObj> commentList = new ArrayList<>();
    Context mContext;
    NewMobileAdaptor.OnProceedClick onProceedClick;
    NewMobileAdaptor.OnRemoveClick onRemoveClick;

    public NewMobileAdaptor(Context mContext, ArrayList<Integer> agentPolicyList) {
        this.policyList = agentPolicyList;
        this.mContext = mContext;
        if (mContext instanceof NewMobileAdaptor.OnProceedClick) {
            onProceedClick = (NewMobileAdaptor.OnProceedClick) mContext;
        }
        if (mContext instanceof NewMobileAdaptor.OnRemoveClick) {
            onRemoveClick = (NewMobileAdaptor.OnRemoveClick) mContext;
        }
    }

    @NonNull
    @Override
    public NewMobileAdaptor.QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_create_mobile, parent, false);

        return new QuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewMobileAdaptor.QuoteViewHolder holder,
                                 final int position) {

        if (position == (policyList.size() - 1)) {
            holder.btnSubmit.setVisibility(View.VISIBLE);
        } else {
            holder.btnRemove.setVisibility(View.GONE);
            holder.btnSubmit.setVisibility(View.GONE);
        }

        holder.btnSubmit.setOnClickListener(v -> {
            if (onProceedClick != null) {
                onProceedClick.OnProceedListener(holder.getAbsoluteAdapterPosition());
            }
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (onRemoveClick != null) {
                holder.edtAddMobile.setText("");
                onRemoveClick.OnRemoveListener(holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    public static class QuoteViewHolder extends RecyclerView.ViewHolder {
        public EditText edtAddMobile, edtOtp;
        public ImageView btnRemove;
        Button btnSubmit;
        TextInputLayout txtInput, txtOtp;

        public QuoteViewHolder(@NonNull View itemView) {

            super(itemView);
            edtAddMobile = itemView.findViewById(R.id.edtAddMobile);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnSubmit = itemView.findViewById(R.id.btnSubmit);
            txtInput = itemView.findViewById(R.id.txtInput);
            txtOtp = itemView.findViewById(R.id.txtOtp);
            edtOtp = itemView.findViewById(R.id.edtOtp);

            edtAddMobile.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                public void afterTextChanged(Editable s) {
                    String mobile = edtAddMobile.getText().toString();
                    if (!(AppUtils.isValidMobile(mobile))) {
                        txtInput.setErrorEnabled(true);
                        txtInput.setError("Invalid Phone Number");
                    } else {
                        txtInput.setErrorEnabled(false);
                    }
                }
            });
        }
    }

    public interface OnProceedClick {
        void OnProceedListener(int position);
    }

    public interface OnRemoveClick {
        void OnRemoveListener(int position);
    }

    public MobileObj getValue(int position){
        return  commentList.get(position);
    }
}
