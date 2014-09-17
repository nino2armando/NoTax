package com.example.ninokhodabandeh.notax;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;

/**
 * Created by nino.khodabandeh on 9/16/2014.
 */
public class ApiResultDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        ApiResultModel selectedItem = args.getParcelable(Constants.SELECTED_LIST_ITEM);
        View v = inflater.inflate(R.layout.fragment_apiresultdetail, null);
        TextView tv = (TextView) v.findViewById(R.id.test);
        tv.setText(String.format("$1%s $2%s $3%s", selectedItem._id, selectedItem._distance , selectedItem._content));
        return v;
    }
}
