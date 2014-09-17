package com.example.ninokhodabandeh.notax;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ninokhodabandeh.notax.Fakes.FakeContent;
import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;
import com.example.ninokhodabandeh.notax.Ui.CustomAdapter;

import java.util.ArrayList;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class ApiResultListFragment extends ListFragment {
    private OnFragmentInteractionListener _callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        ArrayList<ApiResultModel> _apiResults = args.getParcelableArrayList(Constants.API_RESULT);
        setListAdapter(new CustomAdapter(getActivity(), _apiResults));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(_callback != null){
            _callback.onFragmentInteraction((int)id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            _callback = (OnFragmentInteractionListener) activity;
        }catch (ClassCastException ex){
            ex.printStackTrace();
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _callback = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int id);
    }

}
