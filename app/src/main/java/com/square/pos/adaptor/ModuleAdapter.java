package com.square.pos.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.training.model.Module;
import com.square.pos.training.model.Module1;
import com.square.pos.training.model.Module3;
import com.square.pos.training.model.Module4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prahalad Chahar on 2020-07-23.
 */
public class ModuleAdapter
        extends RecyclerView.Adapter<ModuleAdapter.PolicyViewHolder> {
    private List<Module1> module1List1 = new ArrayList<>();
    private List<Module.Module2> module1List2 = new ArrayList<>();
    private List<Module3> module1List3 = new ArrayList<>();
    private List<Module4> module1List4 = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private onSubModuleClickListener onSubModuleClickListener;
    private int mNumber;

    public ModuleAdapter(Context mContext, List<Module1> agentPolicyList, List<Module.Module2> module2List,
                         List<Module3> module3List, List<Module4> module4List, int moduleNumber) {

        this.module1List1 = agentPolicyList;
        this.module1List2 = module2List;
        this.module1List3 = module3List;
        this.module1List4 = module4List;
        this.mNumber = moduleNumber;
        this.mContext = mContext;

        if (mContext instanceof ModuleAdapter.onSubModuleClickListener) {
            onSubModuleClickListener = (ModuleAdapter.onSubModuleClickListener) mContext;
        }

    }

    @NonNull
    @Override
    public ModuleAdapter.PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_module, parent, false);

        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapter.PolicyViewHolder holder, final int position) {


        if (mNumber == 1) {
            Module1 agentPolicy = module1List1.get(position);
            holder.txtSubModuleName.setText(agentPolicy.getTitle());

            holder.txtSubModuleName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubModuleClickListener.onSubModuleClick(position);
                }
            });
        } else if (mNumber == 2) {
            Module.Module2 agentPolicy = module1List2.get(position);
            holder.txtSubModuleName.setText(agentPolicy.getTitle());

            holder.txtSubModuleName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubModuleClickListener.onSubModuleClick(position);
                }
            });
        } else if (mNumber == 3) {
            Module3 agentPolicy = module1List3.get(position);
            holder.txtSubModuleName.setText(agentPolicy.getTitle());

            holder.txtSubModuleName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubModuleClickListener.onSubModuleClick(position);
                }
            });
        } else {
            Module.Module2 module2 = module1List2.get(position);
            holder.txtSubModuleName.setText(module2.getTitle());

            holder.txtSubModuleName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        onSubModuleClickListener.onSubModuleClick(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mNumber == 1)
            return module1List1.size();
        else if (mNumber == 2)
            return module1List2.size();
        else if (mNumber == 3)
            return module1List3.size();
        else
            return module1List4.size();
    }

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtSubModuleName;
        public CardView cvModule;

        public PolicyViewHolder(@NonNull View itemView) {

            super(itemView);
            txtSubModuleName = itemView.findViewById(R.id.txtSubModuleName);
            cvModule = itemView.findViewById(R.id.cvModule);
        }
    }

    public interface onSubModuleClickListener {
        void onSubModuleClick(int position);
    }
}
