package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button calculate;
    Button reset;
    TextView date;
    TextView bmi;
    TextView date1;
    TextView bmi1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = (EditText) findViewById(R.id.editTextWeight);
        etHeight = (EditText) findViewById(R.id.editTextHeight);
        calculate = (Button) findViewById(R.id.buttonCalculate);
        reset = (Button) findViewById(R.id.buttonReset);
        date = (TextView) findViewById(R.id.textViewDate);
        bmi = (TextView) findViewById(R.id.textViewBMI);
        date1 = (TextView) findViewById(R.id.textViewDate1);
        bmi1 = (TextView) findViewById(R.id.textViewBMI1);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for the action
                String strWeight = etWeight.getText().toString();
                Float flWeight = Float.parseFloat(strWeight);
                String strHeight = etHeight.getText().toString();
                Float flHeight = Float.parseFloat(strHeight);

                Float bmi = flWeight / (flHeight * flHeight);
                bmi1.setText(bmi + "");

                Calendar now = Calendar.getInstance(); //Create a Calendar object with current date/time
                String datetime = now.get(Calendar.DAY_OF_MONTH)+ "/"+
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY)+":"+
                        now.get(Calendar.MINUTE);
                date1.setText(datetime);



            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for the action
                etWeight.setText("");
                etHeight.setText("");
                date1.setText("");
                bmi1.setText("");


            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        //Step 1a: Retrieve data input of the user
        String strWeight = etWeight.getText().toString();
        Float flWeight = Float.parseFloat(strWeight);
        String strHeight = etHeight.getText().toString();
        Float flHeight = Float.parseFloat(strHeight);

        Calendar now = Calendar.getInstance(); //Create a Calendar object with current date/time
        String datetime = now.get(Calendar.DAY_OF_MONTH)+ "/"+
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY)+":"+
                now.get(Calendar.MINUTE);

        Float bmi = flWeight / (flHeight * flHeight);


        //Step 1b: Obtain an instance of the Shared Preference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Step 1c: Obtain an instance of the Shared Preference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();
        //Step 1d: Add the key-value pair
        prefEdit.putFloat("weight", flWeight);
        prefEdit.putFloat("height", flHeight);
        prefEdit.putString("date", datetime);
        prefEdit.putFloat("bmi", bmi);

        //Step 1e: Call commit() method to save the changes into the Shared Preference
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Step 2a: Obtain an instance of the Shared Preference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Step 2b: Retrieve the saved data with the key, greeting from the SharedPreferences object
        Float flWeight = prefs.getFloat("weight", 0);
        Float flHeight = prefs.getFloat("height", 0);
        String strDate = prefs.getString("date", "");
        Float flBmi = prefs.getFloat("bmi", 0);

        //Step 2c: Update the UI element with the value
        etWeight.setText(flWeight + "");
        etHeight.setText("" + flHeight);
        date1.setText(strDate);
        bmi1.setText("" + flBmi);

    }




}
