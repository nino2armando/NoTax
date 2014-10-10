package com.example.ninokhodabandeh.notax;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ninokhodabandeh.notax.Helpers.MasterDetailFragmentHelper;
import com.example.ninokhodabandeh.notax.Helpers.MasterDetailFragments;

/**
 * Created by nino.khodabandeh on 10/2/2014.
 */
public class ResultMainFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.results, container, false);

        MasterDetailFragments currentFragment = MasterDetailFragmentHelper.getCurrentFragments(R.id.resultmain_fragment,
                R.id.resultdetail_fragment,
                ResultDetailFragment.class,
                getChildFragmentManager());

        if(currentFragment.master == null){
            currentFragment.master = ResultListFragment.newInstance();
        }

        MasterDetailFragmentHelper.initFragments(currentFragment,
                R.id.resultmain_fragment,
                R.id.resultdetail_fragment,
                getResources().getConfiguration(),
                getChildFragmentManager());

        return rootView;
    }
}
