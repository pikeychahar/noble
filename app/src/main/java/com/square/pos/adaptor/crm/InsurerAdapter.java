package com.square.pos.adaptor.crm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_crm.InsCompany;
import com.square.pos.utils.UtilClass;

import java.util.ArrayList;


/**
 * Created by Prahalad Chahar on 2021-10-17.
 */
public class InsurerAdapter extends RecyclerView.Adapter<InsurerAdapter.InsurerViewHolder> {
    ArrayList<InsCompany> insurerList;
    Context mContext;
    private OnCheckClickInterface onCheckClickInterface;

    public InsurerAdapter(Context mContext, ArrayList<InsCompany> insurerList) {
        this.insurerList = insurerList;
        this.mContext = mContext;

        if (mContext instanceof OnCheckClickInterface) {
            onCheckClickInterface = (OnCheckClickInterface) mContext;
        }
    }

    @NonNull
    @Override
    public InsurerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_checkbox, parent, false);

        return new InsurerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsurerViewHolder holder, final int position) {
        InsCompany ticket = insurerList.get(position);
        holder.txtInsurerName.setText(ticket.getName());

        holder.mCheckbox.setOnCheckedChangeListener((compoundButton, b) -> {
            int checked = UtilClass.getInstance().getChecked();
            if (holder.mCheckbox.isChecked()) {
                if (checked == 5) {
                    holder.mCheckbox.setChecked(false);
                    Toast.makeText(mContext, "Maximum 5", Toast.LENGTH_SHORT).show();
                } else
                    onCheckClickInterface.onCheckChange(position, holder.mCheckbox.isChecked());
            } else
                onCheckClickInterface.onCheckChange(position, holder.mCheckbox.isChecked());
        });
    }

    @Override
    public int getItemCount() {
        return insurerList.size();
    }

    public static class InsurerViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mCheckbox;
        public TextView txtInsurerName;

        public InsurerViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckbox = itemView.findViewById(R.id.mCheckbox);
            txtInsurerName = itemView.findViewById(R.id.txtInsurerName);

        }
    }

    public interface OnCheckClickInterface {
        void onCheckChange(int position, boolean status);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
