package com.sisoft.vm.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.Staff;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffUpdateActivity extends AppCompatActivity {


    private EditText et_emp_name;
    private EditText et_dept_name;
    private EditText et_designation;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private EditText et_staff_email;
    private EditText et_staff_cemail;
    private EditText et_staff_mob;
    private Button btn_Staff_update;


    private String strEmpname;
    private String strDeptname;
    private String strDesignation;
    private String strStaff_email;
    private String strstaff_cemail;
    private String strStaff_mob;
    private String str_gender = "male";

    private DBHelper dbHelper;
    private Pattern pattern;
    private Pattern pattern1;


    private Staff staff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Staff Update");




        pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        pattern1 = Pattern.compile("[0-9]{10}");


        dbHelper = new DBHelper(this);
        et_emp_name = findViewById(R.id.et_emp_name);
        et_dept_name = findViewById(R.id.et_dept_name);
        et_designation = findViewById(R.id.et_designation);
        et_staff_email = findViewById(R.id.et_staff_email);
        et_staff_cemail = findViewById(R.id.et_staff_cemail);
        et_staff_mob = findViewById(R.id.et_staff_mob);
        radio_female = findViewById(R.id.radio_female);
        radio_male = findViewById(R.id.radio_male);
        btn_Staff_update = findViewById(R.id.btn_Staff_update);

        staff = getIntent().getParcelableExtra("staff");

        et_emp_name.setText(staff.getEmp_name());
        et_dept_name.setText(staff.getDept_name());
        et_designation.setText(staff.getDesignation());
        et_staff_email.setText(staff.getEmail());
        et_staff_mob.setText(staff.getMobile());

        if(staff.getGender().equalsIgnoreCase("male")){
            radio_male.setChecked(true);
            str_gender = "male";
        }
        else {
            radio_female.setChecked(true);
            str_gender = "female";
        }


        btn_Staff_update.setOnClickListener(view->{

            if(validateText()){
                if(dbHelper.updateStaff(staff.getId(),strEmpname,strDeptname,strDesignation,
                        str_gender,strStaff_email,strStaff_mob)){
                    Toast.makeText(this, "visitor update", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(this, "failed to update", Toast.LENGTH_SHORT).show();
            }

        });

        setupRadioButtons();




    }

    private void setupRadioButtons() {

        radio_male.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                str_gender="male";
            else str_gender = "female";
        });
    }

    private boolean validateText() {

        boolean isValid = false;

        strEmpname = et_emp_name.getText().toString();
        strDeptname = et_dept_name.getText().toString();
        strDesignation = et_designation.getText().toString();
        strStaff_email = et_staff_email.getText().toString();
        strStaff_mob = et_staff_mob.getText().toString();

        if (!strEmpname.isEmpty()) {

            if (!strDeptname.isEmpty()) {
                if (!strDesignation.isEmpty()) {

                if (!strStaff_email.isEmpty()) {
                    if(validateCEmail(strStaff_email.trim())) {


                            if (!strStaff_mob.isEmpty()) {
                                if (ValidStaffMobile(strStaff_mob.trim())) {

                                    isValid = true;

                                } else Toast.makeText(this, "contact can't be empty", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(this, "contact is not valid", Toast.LENGTH_SHORT).show();

                    } else Toast.makeText(this, "email can't be empty", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(this, "Designation can't be empty", Toast.LENGTH_SHORT).show();

            } else Toast.makeText(this, "Department name can't be empty", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(this, "Employee name can't be empty", Toast.LENGTH_SHORT).show();




        return isValid;

    }

    private boolean ValidStaffMobile(String strStaff_mob) {
        Matcher matcher =pattern1.matcher(strStaff_mob);
        Toast.makeText(this, ""+ matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }

    private boolean validateCEmail(String strstaff_cemail) {
        Matcher matcher = pattern.matcher(strstaff_cemail);
        Toast.makeText(this, "" + matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }
    }

