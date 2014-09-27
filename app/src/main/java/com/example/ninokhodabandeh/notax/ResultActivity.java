package com.example.ninokhodabandeh.notax;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ninokhodabandeh.notax.Fakes.FakeContent;
import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;

import java.sql.Savepoint;
import java.util.ArrayList;


public class ResultActivity extends ActionBarActivity implements ApiResultListFragment.OnFragmentInteractionListener {

    ArrayList<ApiResultModel> _testArray;
    int _orientation;
    int _selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        boolean userInteractedWithList = false;

        if(savedInstanceState != null){
            _testArray = savedInstanceState.getParcelableArrayList(Constants.API_RESULT);
            _selectedItemId = savedInstanceState.getInt(Constants.SELECTED_LIST_ITEM_ID);
            userInteractedWithList = true;
        }else{
            // todo: here we will call the api
            _testArray = FakeContent.getFakeApiContent();
        }

        FragmentManager fragmentManager = getFragmentManager();

        ApiResultListFragment listFragment = new ApiResultListFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.API_RESULT, _testArray);

        if(userInteractedWithList)
            args.putInt(Constants.SELECTED_LIST_ITEM_ID, _selectedItemId);

        listFragment.setArguments(args);

        _orientation = getResources().getConfiguration().orientation;
        if(_orientation == Configuration.ORIENTATION_LANDSCAPE){
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
            if(currentFragment != null){
                if(currentFragment instanceof ApiResultListFragment){
                    fragmentManager.beginTransaction().remove(currentFragment).commit();
                }
            }
            listFragment.setArguments(args);
            fragmentManager.beginTransaction().replace(R.id.listFragment_container, listFragment).addToBackStack(null).commit();

        }else{
            if(userInteractedWithList){
                ApiResultDetailFragment detailFragment = new ApiResultDetailFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
            }else {
                listFragment.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, listFragment).addToBackStack(null).commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // todo: we should store the api result in the bundle and pass it around
        if(!_testArray.isEmpty()){
            outState.putParcelableArrayList(Constants.API_RESULT, _testArray);
        }
        outState.putInt(Constants.SELECTED_LIST_ITEM_ID, _selectedItemId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == android.R.id.home){
            // this is to decide how the up button should behaved depending on the fragment
            Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
            if(currentFragment instanceof ApiResultDetailFragment){
                getFragmentManager().popBackStack();
            }else{
                NavUtils.navigateUpFromSameTask(this);
            }


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int itemId) {
        // we start the detail fragment here
        _selectedItemId = itemId;
        ApiResultModel selectedItem = _testArray.get(itemId);

        FragmentManager fragmentManager = getFragmentManager();


        // todo: after the map works properly invistigate if we need this
        //todo: begin
        Fragment previousFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(previousFragment != null){
            fragmentManager.beginTransaction().remove(previousFragment);
        }
        // todo: end

        ApiResultDetailFragment detailFragment = new ApiResultDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.SELECTED_LIST_ITEM, selectedItem);
        detailFragment.setArguments(args);

        if(_orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.beginTransaction().replace(R.id.detailFragment_container, detailFragment).addToBackStack(null).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
        }
    }
}
