package com.square.pos.adaptor;

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

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends ArrayAdapter<com.square.pos.model_pos.City> {
    private List<com.square.pos.model_pos.City> list, tempItems, suggestions;
    private LayoutInflater mInflater;
    private Context mContext;
    private int mResource;

    public CityAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List list) {
        super(context, resource, 0, list);
        this.mContext = context;
        mResource = resource;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.tempItems = new ArrayList<com.square.pos.model_pos.City>(list);
        this.suggestions = new ArrayList<com.square.pos.model_pos.City>();
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        com.square.pos.model_pos.City object = list.get(position);
        TextView text1 = view.findViewById(android.R.id.text1);
        text1.setText(object.getCityName());
        view.setTag(object);
        return view;
    }

    @Nullable
    @Override
    public com.square.pos.model_pos.City getItem(int position) {
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
                for (com.square.pos.model_pos.City streetValue : tempItems) {
                    if (streetValue.getCityName().toLowerCase()
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
            ArrayList<com.square.pos.model_pos.City> tempValues = (ArrayList<com.square.pos.model_pos.City>) filterResults.values;
            if (filterResults.count > 0) {
                clear();
                for (com.square.pos.model_pos.City streetValue : tempValues) {
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
            com.square.pos.model_pos.City street = (com.square.pos.model_pos.City) resultValue;
            return street.getCityName();
        }
    };

}