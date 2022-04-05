package com.square.pos.adaptor.health;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.model_health.v2.Cover;

import java.util.List;

/**
 * Created by Prahalad Chahar on 2021-02-04.
 */
public class PlanCoverAdaptor extends RecyclerView.Adapter<PlanCoverAdaptor.PolicyViewHolder> {
    private List<Cover> planList;
    private LayoutInflater inflater;
    private Context mContext;


    public PlanCoverAdaptor(Context mContext, List<Cover> planLists) {
        this.mContext = mContext;
        this.planList = planLists;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_plan, viewGroup, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        final Cover plan = planList.get(position);

        holder.txtPlanCat.setText(plan.getPlanBenifitCategory());
        holder.txtPlanDes.setText(plan.getPlanBenifitDesc());
        if (!TextUtils.isEmpty(plan.getPlanBenifit())) {
            holder.txtPlanBenefit.setText(plan.getPlanBenifit().toLowerCase());
        }
        holder.txtCover.setText(plan.getFeatures());

        if (plan.getFeatures().contains("Not"))
            holder.txtCover.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
        else holder.txtCover.setTextColor(ContextCompat.getColor(mContext, R.color.colorGreen));
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPlanBenefit,txtPlanDes,txtPlanCat,txtCover;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlanCat = itemView.findViewById(R.id.txtPlanCat);
            txtPlanDes = itemView.findViewById(R.id.txtPlanDes);
            txtPlanBenefit = itemView.findViewById(R.id.txtPlanBenefit);
            txtCover = itemView.findViewById(R.id.txtCover);

        }
    }
}
