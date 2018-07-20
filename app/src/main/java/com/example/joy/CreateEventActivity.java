package com.example.joy;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class CreateEventActivity extends AppCompatActivity {
    Button datepickerbtn;
    TextView txt_when;

    EditText fname, lname, pfname, plname, where;
    String lastname, partnersfirstname, partnerslastname, firstname, place, when, todaysdate;
    DatePickerDialog datePickerDialog;
    String MyPREFERENCES = "DBVALUES";

    RelativeLayout createeventbtn;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);
        createeventbtn = (RelativeLayout) findViewById(R.id.rl_createeventbtn);
        fname = (EditText) findViewById(R.id.edttxtfname);
        lname = (EditText) findViewById(R.id.edttxtlname);
        pfname = (EditText) findViewById(R.id.edttxtpfname);
        plname = (EditText) findViewById(R.id.edttxtplname);
        where = (EditText) findViewById(R.id.txtwhere);
        txt_when = (TextView) findViewById(R.id.txt_when);
        datepickerbtn = (Button) findViewById(R.id.datepicker);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        datepickerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CreateEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the textview
                                txt_when.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                todaysdate = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis() + TimeUnit.MILLISECONDS.convert(365, TimeUnit.DAYS));

            }
        });
        createeventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = fname.getText().toString().trim();
                String lastname = lname.getText().toString().trim();
                String pfirstname = pfname.getText().toString().trim();
                String plastname = plname.getText().toString().trim();
                String place = where.getText().toString().trim();
                String when = txt_when.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (firstname.isEmpty() || lastname.isEmpty() || pfirstname.isEmpty() || plastname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill the requiered fields", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("First_Name", firstname);
                    editor.putString("Last_Name", lastname);
                    editor.putString("Partners_First_Name", pfirstname);
                    editor.putString("Partners_Last_Name", plastname);
                    editor.putString("Date", todaysdate);
                    editor.commit();
                    Intent intent=new Intent(CreateEventActivity.this,EventActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
}
