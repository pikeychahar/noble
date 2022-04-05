package com.dmw.noble.adaptor.health;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.model_health.v2.Brochure;

import java.util.List;

/**
 * Created by Prahalad Chahar on 2021-02-04.
 */
public class BrochureAdaptor extends RecyclerView.Adapter<BrochureAdaptor.PolicyViewHolder> {
    private List<Brochure> brochureList;
    private LayoutInflater inflater;
    private Context mContext;
    private OnBrochureClickListener onBrochureClickListener;
    private OnPolicyWordingListener onPolicyWordingListener;
    private OnClaimListener onClaimListener;
    private OnProposalListener onProposalListener;


    public BrochureAdaptor(Context mContext, List<Brochure> brochureLists) {
        this.mContext = mContext;
        this.brochureList = brochureLists;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mContext instanceof OnBrochureClickListener) {
            onBrochureClickListener = (OnBrochureClickListener) mContext;
        }
        if (mContext instanceof OnPolicyWordingListener) {
            onPolicyWordingListener = (OnPolicyWordingListener) mContext;
        }
        if (mContext instanceof OnClaimListener) {
            onClaimListener = (OnClaimListener) mContext;
        }
        if (mContext instanceof OnProposalListener) {
            onProposalListener = (OnProposalListener) mContext;
        }
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_brochure, viewGroup, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, final int position) {
        final Brochure brochure = brochureList.get(position);

        holder.txtPolicyWording.setOnClickListener(v -> {
            onPolicyWordingListener.onPWordingClicked(position, brochure.getPolicyWording());
        });

        holder.txtProposal.setOnClickListener(v -> {
            onProposalListener.onProposerClicked(position, brochure.getPolicyWording());

        });
        holder.txtClaimForm.setOnClickListener(v -> {
            onClaimListener.onClaimClicked(position, brochure.getPolicyWording());

        });
        holder.txtBrochure.setOnClickListener(v -> {
            onBrochureClickListener.onBBrochureClicked(position, brochure.getPolicyWording());
        });
    }

    @Override
    public int getItemCount() {
        return brochureList.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPolicyWording, txtProposal, txtClaimForm, txtBrochure;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPolicyWording = itemView.findViewById(R.id.txtPolicyWording);
            txtProposal = itemView.findViewById(R.id.txtProposal);
            txtClaimForm = itemView.findViewById(R.id.txtClaimForm);
            txtBrochure = itemView.findViewById(R.id.txtBrochure);
        }
    }

    public interface OnBrochureClickListener {
        void onBBrochureClicked(int position, String url);
    }

    public interface OnPolicyWordingListener {
        void onPWordingClicked(int position, String url);
    }

    public interface OnProposalListener {
        void onProposerClicked(int position, String url);
    }

    public interface OnClaimListener {
        void onClaimClicked(int position, String url);
    }


}
