package com.square.pos.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.square.pos.R;
import com.square.pos.training.model.QuestionDatum;

import java.util.ArrayList;

/**
 * Created by Prahalad Chahar on 2020-08-01.
 */
public class ExamAdaptor extends BaseAdapter {
    private ArrayList<QuestionDatum> queList;
    private LayoutInflater inflater;
    private Context mContext;
    private OnItemTapListener onItemTapListener;
    private OnTotalNumber onTotalNumber;
    private int totalNumber = 0;

    public ExamAdaptor(Context mContext, ArrayList<QuestionDatum> questionData) {
        this.mContext = mContext;
        this.queList = questionData;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mContext instanceof ExamAdaptor.OnItemTapListener) {
            onItemTapListener = (ExamAdaptor.OnItemTapListener) mContext;
        } if (mContext instanceof ExamAdaptor.OnTotalNumber) {
            onTotalNumber = (ExamAdaptor.OnTotalNumber) mContext;
        }
    }

    @Override
    public int getCount() {
        return queList.size();
    }

    @Override
    public QuestionDatum getItem(int position) {
        return queList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        convertView = inflater.inflate(R.layout.layout_exam, viewGroup, false);

        TextView txtQueNumber;
        txtQueNumber = convertView.findViewById(R.id.txtQueNumber);
        QuestionDatum datum = getItem(position);
        txtQueNumber.setText(datum.getId());
        txtQueNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemTapListener.onItemClicked(position);
            }
        });

        if (datum.getAttempt().equalsIgnoreCase("yes")) {
            txtQueNumber.setBackgroundResource(R.drawable.border_fill);
            if (datum.getMarkGet().equalsIgnoreCase("2"))
                totalNumber = totalNumber + 2;

        }
        onTotalNumber.getTotalNumber(totalNumber);

        return convertView;
    }

    public interface OnItemTapListener {
        void onItemClicked(int position);
    }
    public interface OnTotalNumber {
        void getTotalNumber(int total);
    }
}
