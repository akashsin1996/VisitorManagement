package com.sisoft.vm.Activities;

import android.app.DatePickerDialog;
import android.widget.TextView;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;

import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.Visitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VisitDetailActivity extends AppCompatActivity {

    private EditText et_purpose;
    private EditText et_meet_to;
    private EditText et_item_carried;
    private TextView tv_date;

    private String strPurpose;
    private String strMeet_to;
    private String strItem_carried;
    private String strVisit_date;

    private Button btn_visit_req;

    private DBHelper dbHelper;
    private Visitor visitor;
    private Visitor visitorID;
    private TextView tv;

    Spinner spinner;

    TextView date;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_detail);

        btn_visit_req = findViewById(R.id.btn_visit_req);

        date = (TextView) findViewById(R.id.tv_date);
        et_purpose = findViewById(R.id.et_purpose);
    //    et_meet_to = findViewById(R.id.et_meet_to);
        et_item_carried = findViewById(R.id.et_item_carried);
        tv_date=findViewById(R.id.tv_date);


        dbHelper = new DBHelper(this);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> list=dbHelper.getemp_name();
        ArrayAdapter<String> sAdapter=new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.tv_spinner, list);
        spinner.setAdapter(sAdapter);
        //Spinner item selection when use the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup vg=(ViewGroup)view;
                TextView tv_spinner=(TextView)vg.findViewById(R.id.tv_spinner);
                 strMeet_to = tv_spinner.getText().toString();

              Toast.makeText(VisitDetailActivity.this, tv_spinner.getText().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        Intent intent = getIntent();
        String visitorID = intent.getStringExtra("visitorID");
        Toast.makeText(this,"Visitor ID:"+visitorID,Toast.LENGTH_SHORT).show();
        int int_visitor = Integer.parseInt(visitorID);

        btn_visit_req.setOnClickListener(view -> {
     //       if(validateText()){
            // int visitorID = visitor.getId();
               // Intent intent = getIntent();
               // String visitorID = intent.getStringExtra("visitorID");
              //  int visitorID = Integer.parseInt(visitorID);
                strVisit_date = tv_date.getText().toString();
                strPurpose = et_purpose.getText().toString();
                strItem_carried = et_item_carried.getText().toString();
              //  strMeet_to =tv_spinner.getText().toString();

                if(dbHelper.insertVisitDetails(int_visitor, strMeet_to, strVisit_date, strPurpose, strItem_carried, getDateAndTime())) {
                    Toast.makeText(this, "Visit Details inserted ", Toast.LENGTH_SHORT).show();
                    finish();
                }else Toast.makeText(this, "failed to insert", Toast.LENGTH_SHORT).show();
          // }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(VisitDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        }



    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbHelper!=null){
            dbHelper.close();
        }


    }



    private boolean validateText() {
        boolean isValid = false;

        strPurpose = et_purpose.getText().toString();
        //strMeet_to = et_meet_to.getText().toString();
//        strMeet_to =tv_spinner.getText().toString();
        strItem_carried= et_item_carried.getText().toString();
        strVisit_date = tv_date.getText().toString();


        if (!strPurpose.isEmpty()) {

            if (!strMeet_to.isEmpty()) {
                        if (!strItem_carried.isEmpty()) {
                            if (!strVisit_date.isEmpty()) {
                                isValid = true;
                            } else
                                Toast.makeText(this, "Visit date is not valid", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "Item Carried is not valid", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Meet to Whom can't be empty", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, " Reason can't be empty", Toast.LENGTH_SHORT).show();




        return isValid;

    }


    private String getDateAndTime(){

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

}
