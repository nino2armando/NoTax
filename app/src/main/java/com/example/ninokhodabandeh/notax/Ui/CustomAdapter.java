package com.example.ninokhodabandeh.notax.Ui;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.R;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class CustomAdapter extends ArrayAdapter<ApiResultModel> {

    private final Context _context;
    private final ArrayList<ApiResultModel> _itemArrayList;

    public CustomAdapter(Context context, ArrayList<ApiResultModel> itemArrayList) {
        super(context, R.layout.row);
        _context = context;
        _itemArrayList = itemArrayList;
    }

    @Override
    public int getCount() {
        return _itemArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row, parent, false);
        rowView.setId(_itemArrayList.get(position).getId());

        TextView labelView = (TextView) rowView.findViewById(R.id.modelDistance);
        TextView valueView = (TextView) rowView.findViewById(R.id.modelContent);

        labelView.setText(_itemArrayList.get(position).getDistance());
        valueView.setText(_itemArrayList.get(position).getContent());

        int orientation = _context.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            labelView.setTextSize(20);
            valueView.setTextSize(10);
        }


        return rowView;
    }
}
