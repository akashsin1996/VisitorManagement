package com.sisoft.vm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffAddActivity extends AppCompatActivity {


    private EditText et_emp_name;
    private EditText et_dept_name;
    private EditText et_designation;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private EditText et_staff_email;
    private EditText et_staff_cemail;
    private EditText et_staff_mob;
    private Button btn_Staff_save;


    private String strEmpname;
    private String strDeptname;
    private String strDesignation;
    private String strStaff_email;
    private String strstaff_cemail;
    private String strStaff_mob;
    private String str_gender = "male";

    private DBHelper dbHelper;
    private Pattern pattern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Add Staff");

        et_emp_name = findViewById(R.id.et_emp_name);
        et_dept_name = findViewById(R.id.et_dept_name);
        et_designation = findViewById(R.id.et_designation);
        et_staff_email = findViewById(R.id.et_staff_email);
        et_staff_cemail = findViewById(R.id.et_staff_cemail);
        et_staff_mob = findViewById(R.id.et_staff_mob);
        radio_female = findViewById(R.id.radio_female);
        radio_male = findViewById(R.id.radio_male);
        btn_Staff_save = findViewById(R.id.btn_Staff_save);

        setupRadioButtons();

        pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");


        btn_Staff_save.setOnClickListener(view -> {
            if (validateText()) {

                if (dbHelper.insertStaffTable(strEmpname, strDeptname, strDesignation, str_gender, strStaff_email, strStaff_mob)) {
                    Toast.makeText(this, "Staff Created ", Toast.LENGTH_SHORT).show();
                    finish();
                } else Toast.makeText(this, "failed to insert", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setupRadioButtons() {

        radio_male.setChecked(true);

        radio_male.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                str_gender = "male";
            else str_gender = "female";

            Toast.makeText(this, str_gender, Toast.LENGTH_SHORT).show();
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

        strEmpname = et_emp_name.getText().toString();
        strDeptname = et_dept_name.getText().toString();
        strDesignation = et_designation.getText().toString();
        strStaff_email = et_staff_email.getText().toString();
        strstaff_cemail = et_staff_cemail.getText().toString();
        strStaff_mob = et_staff_mob.getText().toString();


        if (!strEmpname.isEmpty()) {

            if (!strDeptname.isEmpty()) {

                if (!strDesignation.isEmpty()) {

                    if (!strStaff_email.isEmpty()) {

                        if(validateEmail(strStaff_email.trim())) {

                            if (!strstaff_cemail.isEmpty() && strstaff_cemail.equals(strStaff_email)) {

                                if (!strStaff_mob.isEmpty()) {

                                    isValid = true;

                                    } else Toast.makeText(this, "contact can't be empty", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(this, "confirm email id can't be empty", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();

                    } else Toast.makeText(this, "email can't be empty", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(this, "Designation can't be empty", Toast.LENGTH_SHORT).show();

            } else Toast.makeText(this, "Department name can't be empty", Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this,"Employee Name can't be empty",Toast.LENGTH_SHORT).show();


        return isValid;


    }

    private boolean validateEmail(String trim) {
        Matcher matcher =pattern.matcher(strStaff_email);


        Toast.makeText(this, ""+ matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }

}
