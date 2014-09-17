package com.example.ninokhodabandeh.notax;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ninokhodabandeh.notax.Models.UserInputModel;
import com.example.ninokhodabandeh.notax.Ui.Constants;


public class MainActivity extends ActionBarActivity {

    private Context _context;

    // UI Components
    Spinner _spinner;
    SeekBar _seekBar;
    EditText _userInputEditText;

    private int _distance;
    private String _businessType;
    private boolean _isUserEntry = false;

    public static  final String DEFAULT_USERINPUT_ITEM = "Let me enter";
    private static  final String _notSelected = "Select";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;

        // load UI
        loadSpinner();
        loadSeekBar();

        if(savedInstanceState != null){

            String userInput = savedInstanceState.getString(Constants.USER_INPUT);

            if(userInput != null){
                _userInputEditText.setText(userInput);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String tempInput = _userInputEditText.getText().toString();
        outState.putString(Constants.USER_INPUT, tempInput);
        super.onSaveInstanceState(outState);
    }

    private void loadSpinner(){
        _spinner = (Spinner) findViewById(R.id.spinner_businessType_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(_context, R.array.businessType_array, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        _spinner.setAdapter(arrayAdapter);

        final int spinnerPosition = ((ViewGroup) _spinner.getParent()).indexOfChild(_spinner);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_mainUiWrapper);

        EditText editTextUserInput = (EditText) findViewById(R.id.editText_dynamicInput);
        _userInputEditText = editTextUserInput == null ? addEditTextForUserInput() : editTextUserInput;


        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _businessType = parent.getItemAtPosition(position).toString();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(_context.INPUT_METHOD_SERVICE);

                try{
                    if(_businessType.equals(DEFAULT_USERINPUT_ITEM)){
                        layout.addView(_userInputEditText, spinnerPosition + 1);
                        _isUserEntry = true;

                        if(_userInputEditText.requestFocus()){
                            // show keyboard
                            inputMethodManager.showSoftInput(_userInputEditText, InputMethodManager.SHOW_IMPLICIT);
                        }

                    }else{
                        layout.removeView(_userInputEditText);
                        _userInputEditText.setText(null);
                        _isUserEntry = false;
                        // hide keyboard
                        inputMethodManager.hideSoftInputFromWindow(_userInputEditText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private EditText addEditTextForUserInput(){
        EditText editView = new EditText(_context);
        editView.setId(R.id.editText_dynamicInput);
        editView.setHint(getString(R.string.userInputHint));
        editView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return editView;
    }

    private void loadSeekBar(){
        _seekBar = (SeekBar) findViewById(R.id.seekbar_radius);
        _seekBar.setMax(100);
        _seekBar.setProgress(0);

        _seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress == 100 ? progress : progress + 1;
                TextView textView = (TextView) findViewById(R.id.textview_seekBarUpdate);
                textView.setText(Integer.toString(progress) + getString(R.string.km));
                _distance = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void displayWarningDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setMessage(getString(R.string.warningMessage));
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClickSpinnerItemSelected(View view){

        if(_businessType.equals(_notSelected)){
            displayWarningDialog();
            return;
        }

        _businessType = _isUserEntry != true ? _businessType : _userInputEditText.getText().toString();
        UserInputModel userInput = new UserInputModel(_distance, _businessType);

        //todo: should we call api first and pass the content to the next activity or calling api should be next activities task
        //todo: for now we just start the activity with the fake list

        Intent resultActivity = new Intent(_context, ResultActivity.class);
        startActivity(resultActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
