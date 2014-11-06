package com.example.ninokhodabandeh.notax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.example.ninokhodabandeh.notax.Fakes.FakeContent;
import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Models.SimpleLocation;
import com.example.ninokhodabandeh.notax.Models.UserInputModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;
import com.example.ninokhodabandeh.notax.util.LocationUtils;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * An activity representing a list of results. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ResultDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ResultListFragment} and the item details
 * (if present) is a {@link ResultDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ResultListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ResultListActivity extends FragmentActivity
        implements ResultListFragment.Callbacks {

    private ArrayList<ApiResultModel> mApiResult;

    private UserInputModel mUserInput;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // retrieve location
        SharedPreferences sharedPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String simpleLocationJson = sharedPrefs.getString(Constants.RETRIEVED_LOCATION, "");
        SimpleLocation location = gson.fromJson(simpleLocationJson, SimpleLocation.class);

        Intent extraData = getIntent();

        if(extraData != null){
            // todo: do something with userInput + location e.g. call the api
            mUserInput = extraData.getParcelableExtra(Constants.USER_INPUT);
            // todo: if mUserInput is null then reuse the arrayList else request a new one base on the mUserInput
            mApiResult = FakeContent.getFakeApiContent();
        }

        // register the fragment here
        ResultListFragment currentFragment = (ResultListFragment)getSupportFragmentManager().findFragmentById(R.id.result_list);
        if(currentFragment == null){
            Bundle args = new Bundle();
            args.putParcelableArrayList(Constants.API_RESULT, mApiResult);
            ResultListFragment listFragment = new ResultListFragment();
            listFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.result_list, listFragment).commit();
        }

        // we are calling the choice mode in the onViewCreated of the ListFragment
/*        ((ResultListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.result_list))
                .setActivateOnItemClick(true);*/

        if (findViewById(R.id.result_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        // TODO: If exposing deep links into your app, handle intents here.

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link ResultListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(ApiResultModel item) {

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(ResultDetailFragment.ARG_ITEM, item);
            ResultDetailFragment fragment = new ResultDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.result_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ResultDetailActivity.class);
            detailIntent.putExtra(ResultDetailFragment.ARG_ITEM, item);
            startActivity(detailIntent);
        }
    }
}
