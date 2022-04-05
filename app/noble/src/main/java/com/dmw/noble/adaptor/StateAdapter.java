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

import com.dmw.noble.model_pos.State;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends ArrayAdapter<com.dmw.noble.model_pos.State> {
    private List<com.dmw.noble.model_pos.State> list, tempItems, suggestions;
    private LayoutInflater mInflater;
    private Context mContext;
    private int mResource;

    public StateAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List list) {
        super(context, resource, 0, list);
        this.mContext = context;
        mResource = resource;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.tempItems = new ArrayList<com.dmw.noble.model_pos.State>(list);
        this.suggestions = new ArrayList<com.dmw.noble.model_pos.State>();
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        com.dmw.noble.model_pos.State object = list.get(position);
        TextView text1 = view.findViewById(android.R.id.text1);
        text1.setText(object.getStateName());
        view.setTag(object);
        return view;
    }

    @Nullable
    @Override
    public com.dmw.noble.model_pos.State getItem(int position) {
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
                for (com.dmw.noble.model_pos.State streetValue : tempItems) {
                    if (streetValue.getStateName().toLowerCase()
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
            ArrayList<com.dmw.noble.model_pos.State> tempValues =
                    (ArrayList<com.dmw.noble.model_pos.State>) filterResults.values;
            if (filterResults.count > 0) {
                clear();
                for (com.dmw.noble.model_pos.State streetValue : tempValues) {
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
            com.dmw.noble.model_pos.State street = (State) resultValue;
            return street.getStateName();
        }
    };

}