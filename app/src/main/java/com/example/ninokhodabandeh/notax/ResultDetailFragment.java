package com.example.ninokhodabandeh.notax;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;

/**
 * Created by nino.khodabandeh on 9/16/2014.
 */
public class ResultDetailFragment extends Fragment {

    private ApiResultModel mApiResultModel;

    public static ResultDetailFragment newInstance(){
        ResultDetailFragment detailFragment = new ResultDetailFragment();
        return detailFragment;
    }

    public static ResultDetailFragment newInstance(ApiResultModel model){
        Bundle args = new Bundle();
        args.putParcelable(Constants.SELECTED_LIST_ITEM, model);
        ResultDetailFragment detailFragment = new ResultDetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resultdetail, container, false);

        Bundle args = getArguments();


        if(args != null){
            mApiResultModel = args.getParcelable(Constants.SELECTED_LIST_ITEM);
        }

        if(mApiResultModel != null){
            TextView tv = (TextView) view.findViewById(R.id.temp);
            tv.setText(mApiResultModel.getContent());
        }

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.SELECTED_LIST_ITEM, mApiResultModel);
    }
}
