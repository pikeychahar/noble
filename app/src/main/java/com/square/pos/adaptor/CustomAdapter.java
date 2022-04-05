package com.square.pos.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.square.pos.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> myArray = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<String> myArray) {
        inflater = LayoutInflater.from(context);
        this.myArray = myArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.serial_number.setText(myArray.get(position));
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView serial_number;

        MyViewHolder(View itemView) {
            super(itemView);
            serial_number = (TextView) itemView.findViewById(R.id.text1);
        }
    }
}