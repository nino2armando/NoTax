package com.example.ninokhodabandeh.notax;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ninokhodabandeh.notax.Fakes.FakeContent;
import com.example.ninokhodabandeh.notax.Helpers.FragmentHelper;
import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;
import com.example.ninokhodabandeh.notax.Ui.CustomAdapter;

import java.util.ArrayList;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class ResultListFragment extends ListFragment {
    private OnFragmentInteractionListener _callback;
    private int _selectedListItem;


    private boolean mDualPanel;

    public static ResultListFragment newInstance(){

        // todo: we might want to do this in the parent class?? and pass the bundle as a parameter
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.API_RESULT, FakeContent.getFakeApiContent());
        ResultListFragment listFragment = new ResultListFragment();
        listFragment.setArguments(args);
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();

        ArrayList<ApiResultModel> _apiResults = args.getParcelableArrayList(Constants.API_RESULT);
        setListAdapter(new CustomAdapter(getActivity(), _apiResults));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mDualPanel = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(this.getListView().getSelectedView() != null){
            ensureVisible(this.getListView(), this.getListView().getSelectedView());
        }
    }

    private void ensureVisible(ListView parent, View view){
        parent.smoothScrollToPosition(parent.getSelectedItemPosition());
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApiResultModel selectedItem = FakeContent.getFakeApiContent().get((int) id);

        ResultDetailFragment detailFragment = ResultDetailFragment.newInstance(selectedItem);

        if(!this.mDualPanel){
            FragmentHelper.initFragmentWithBackstack(detailFragment, R.id.resultmain_fragment, this.getParentFragment().getChildFragmentManager());
        }else {
            FragmentHelper.initFragment(detailFragment, R.id.resultdetail_fragment, this.getParentFragment().getChildFragmentManager());
        }

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int id);
    }

}
