package com.example.ninokhodabandeh.notax;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    private Context _context;

    // UI Components
    Spinner _spinner;
    SeekBar _seekBar;
    EditText _userInputEditText;

    private int _distance;
    private String _businessType;

    private static  final String _userInputItem = "Let me enter";
    private static  final String _notSelected = "Select";
    private static  final String USER_INPUT = "com.example.ninokhodabandeh.notax.MainActivity.UserInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;

        // load UI
        loadSpinner();
        loadSeekBar();

        if(savedInstanceState != null){
            String userInput = savedInstanceState.getString(USER_INPUT);
            if(userInput != null){
                _userInputEditText.setText(userInput);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempInput = _userInputEditText.getText().toString();

        if(tempInput != null && !tempInput.isEmpty())
            outState.putString(USER_INPUT, tempInput);
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

                try{
                    if(_businessType.equals(_userInputItem)){
                        layout.addView(_userInputEditText, spinnerPosition + 1);
                    }else{
                        layout.removeView(_userInputEditText);
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
        }
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
