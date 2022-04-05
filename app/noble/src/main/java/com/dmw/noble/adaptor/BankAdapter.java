package com.dmw.noble.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dmw.noble.model.BankList;

import java.util.ArrayList;
import java.util.List;

public class BankAdapter extends ArrayAdapter<BankList> {
    private List<BankList> list, tempItems, suggestions;
    private LayoutInflater mInflater;
    private Context mContext;
    private int mResource;

    public BankAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List list) {
        super(context, resource, 0, list);
        this.mContext = context;
        mResource = resource;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.tempItems = new ArrayList<BankList>(list);
        this.suggestions = new ArrayList<BankList>();
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        BankList object = list.get(position);
        TextView text1 = view.findViewById(android.R.id.text1);
        text1.setText(object.getBankName());
        view.setTag(object);
        return view;
    }

    @Nullable
    @Override
    public BankList getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return streetFilter;
    }

    private Filter streetFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (BankList streetValue : tempItems) {
                    if (streetValue.getBankName().toLowerCase()
                            .startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(streetValue);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            ArrayList<BankList> tempValues = (ArrayList<BankList>) filterResults.values;
            if (filterResults.count > 0) {
                clear();
                for (BankList streetValue : tempValues) {
                    add(streetValue);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            BankList street = (BankList) resultValue;
            return street.getBankName();
        }
    };

}