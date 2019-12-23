package com.sisoft.vm.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.sisoft.vm.Adapters.VisitorListAdapter;
import com.sisoft.vm.Admin;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;
import com.sisoft.vm.Visitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VisitorStatusActivity extends AppCompatActivity {

    private TextView tv_purpose;
    private TextView tv_visitor_name;
    private TextView tv_meet_to;
    private TextView tv_item_carried;
    private TextView tv_entry_date_time;
    private TextView tv_exit_date_time;

    private String strPurpose;
    private String strMeet_to;
    private String strItem_carried;
    private String strVisit_date;
    private String strExit_Date_Time;
    private DBHelper dbHelper;
    private Visitor visitorID;
    private TextView tv;

    private Button btn_visit_checkin;
    private Button btn_slip;

    private VisitDetails visitDetails;

    Spinner spinner;

   // TextView date;
    DatePickerDialog datePickerDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_status);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Visitor Status");


        visitDetails = getIntent().getParcelableExtra("visitDetails");

        Toast.makeText(this, ""+visitDetails.getId(), Toast.LENGTH_SHORT).show();

      //  date = findViewById(R.id.tv_date);
        tv_visitor_name= findViewById(R.id.tv_visitor_name);
        tv_purpose = findViewById(R.id.tv_purpose);
        tv_meet_to = findViewById(R.id.tv_meet_to);
        tv_entry_date_time=findViewById(R.id.tv_entry_date_time);
        tv_exit_date_time=findViewById(R.id.tv_exit_date_time);
        tv_item_carried = findViewById(R.id.tv_item_carried);
        btn_visit_checkin =findViewById(R.id.btn_visit_checkin);
        btn_slip =findViewById(R.id.btn_slip);

        dbHelper = new DBHelper(this);



            tv_visitor_name.setText(visitDetails.getVisitor_name());
        Toast.makeText(this, "visi_name"+visitDetails.getVisitor_name(), Toast.LENGTH_SHORT).show();


            tv_meet_to.setText(visitDetails.getMeet_to());
            tv_purpose.setText(visitDetails.getPurpose());
           tv_item_carried.setText(visitDetails.getItem_carried());
            tv_entry_date_time.setText(visitDetails.getEntry_time());
            tv_exit_date_time.setText(visitDetails.getExit_time());
            tv_exit_date_time.setText(exitDateAndTime());



        btn_visit_checkin.setOnClickListener(view->{
                if(dbHelper.updateVisitDetails(visitDetails.getId(),exitDateAndTime())){
                    Toast.makeText(this, "visitor update", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(this, "failed to update", Toast.LENGTH_SHORT).show();
        });
       /* tv_exit_date_time.setOnClickListener(v -> {
            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog(VisitorStatusActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        tv_exit_date_time.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });*/

        btn_slip.setOnClickListener(view->{

            Intent intent =new Intent(this, PrintActivity.class);
            intent.putExtra("visitDetails",visitDetails);
            this.startActivity(intent);
        });

    }

    private String exitDateAndTime(){

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(calendar.getTime());
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


}
