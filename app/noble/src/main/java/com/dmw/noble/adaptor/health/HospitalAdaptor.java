package com.dmw.noble.adaptor.health;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_health.v2.Hospital;

import java.util.List;

/**
 * Created by Prahalad Chahar on 2021-02-04.
 */
public class HospitalAdaptor extends RecyclerView.Adapter<HospitalAdaptor.PolicyViewHolder> {
    private List<Hospital> hospitalList;
    private LayoutInflater inflater;
    private Context mContext;
    private OnBrochureClickListener onBrochureClickListener;


    public HospitalAdaptor(Context mContext, List<Hospital> hospitalLists) {
        this.mContext = mContext;
        this.hospitalList = hospitalLists;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mContext instanceof OnBrochureClickListener) {
            onBrochureClickListener = (OnBrochureClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_hospital, viewGroup, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        final Hospital hospital = hospitalList.get(position);
        holder.txtHospitalName.setText(hospital.getHospitalName());
        holder.txtHospitalAddress.setText(hospital.getHospitalAddress());
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHospitalAddress,txtHospitalName;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHospitalAddress = itemView.findViewById(R.id.txtHospitalAddress);
            txtHospitalName = itemView.findViewById(R.id.txtHospitalName);
        }
    }

    public interface OnBrochureClickListener {
        void onBBrochureClicked(int position, String url);
    }


}
