package com.sisoft.vm.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sisoft.vm.Adapters.AdminListAdapter;
import com.sisoft.vm.Adapters.DashboardAdapter;
import com.sisoft.vm.Adapters.VisitorListAdapter;
import com.sisoft.vm.Admin;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUserUpdateActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText et_username;
    private EditText et_firstname;
    private EditText et_lastname;
    private EditText et_email;
    private EditText et_cemail;
    private EditText et_city;
    private EditText et_mob;
    private EditText et_psw;
    private EditText et_cpsw;
    private RadioButton radio_male;
    private RadioButton radio_female;


    private RadioButton radio_admin;
    private RadioButton radio_operator;


    private Button btn_upload;
    private Button btn_update;
    private ImageView img_upload_preview;

    private DBHelper dbHelper;
    private String strUsername;
    private String strFirstName;
    private String strLastName;
    private String strEmail;
    private String strCEmail;
    private String strMobile;
    private String strCity;
    private String strPassword;
    private String strCPassword;
    private String str_gender;

    private String str_role;

    private byte[] imageInByte;
    private Pattern pattern;

    private TextView tv_no_admin_user;
    private RecyclerView recyclerView;
    private ArrayList<Admin> admins= new ArrayList<>();
    private AdminListAdapter adapter;

    private Admin admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_user_update);

       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Users Profile");*/

        et_username = findViewById(R.id.et_username);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_email = findViewById(R.id.et_email);
        et_cemail =findViewById(R.id.et_cemail);
        et_mob = findViewById(R.id.et_mob);
        et_city = findViewById(R.id.et_city);
        et_psw = findViewById(R.id.et_psw);
        et_cpsw =findViewById(R.id.et_cpsw);
        radio_female = findViewById(R.id.radio_female);
        radio_male = findViewById(R.id.radio_male);

        radio_admin =findViewById(R.id.radio_admin);
        radio_operator =findViewById(R.id.radio_operator);


        btn_upload = findViewById(R.id.btn_upload);
        btn_update = findViewById(R.id.btn_update);
        img_upload_preview = findViewById(R.id.img_upload_preview);

        pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");


        dbHelper = new DBHelper(this);

      //  Toast.makeText(this, "" + admin.getFirstname(), Toast.LENGTH_SHORT).show();


        //Bundle bundle = getIntent().getExtras();

        Intent intent1 =getIntent();
     //   String fname=intent1.getStringExtra("ADMIN_ID");
        admin = (Admin) intent1.getSerializableExtra("admin");


        if(admin!= null) {


            et_username.setText(admin.getUsername());
            et_firstname.setText(admin.getFirstname());
            et_lastname.setText(admin.getLastname());
            et_email.setText(admin.getEmail());
            et_city.setText(admin.getCity());
            et_mob.setText(admin.getMob());
            et_psw.setText(admin.getPsw());

            if (admin.getGender().equalsIgnoreCase("male")) {
                str_gender = "male";
                radio_male.setChecked(true);
            } else {
                str_gender = "female";
                radio_female.setChecked(true);
            }


            if (admin.getImageInByte() != null) {
                img_upload_preview.setVisibility(View.VISIBLE);
                img_upload_preview.setImageBitmap(getImage(admin.getImageInByte()));
            }

            if (admin.getRole().equalsIgnoreCase("operator")) {
                str_role = "operator";
                radio_operator.setChecked(true);
            } else {
                str_role = "admin";
                radio_admin.setChecked(true);
            }

            btn_update.setOnClickListener(view -> {
                if (validateText()) {

                    if (dbHelper.updateAdmin(admin.getId(), strUsername,str_role, strFirstName, strLastName, strEmail,
                            strCity, strMobile, strPassword, getDate(), imageInByte, str_gender)) {
                        Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(this, "failed to insert", Toast.LENGTH_SHORT).show();
                }
            });

        }
        btn_upload.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 10);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 0);
            intent.putExtra("aspectY", 0);
            intent.putExtra("outputX", "250");
            intent.putExtra("outputY", "200");
        });

        setupRadioButtons();
    }



//    @Override
//    public void onResume() {
//        super.onResume();
//        admins = dbHelper.getAdmins();
//
//        AdminListAdapter adapter = new AdminListAdapter(admins,this);
//
//        recyclerView.setAdapter(adapter);
//
//        Toast.makeText(this,"Record count:"+adapter.getItemCount(),Toast.LENGTH_SHORT).show();
//        if(adapter.getItemCount()>0){
//            tv_no_admin_user.setVisibility(View.GONE);
//        }
//        else tv_no_admin_user.setVisibility(View.VISIBLE);
//    }

    private Bitmap getImage(byte[] imageInByte) {
        ByteArrayInputStream inputstream = new ByteArrayInputStream(imageInByte);
        return BitmapFactory.decodeStream(inputstream);

    }

    private void setupRadioButtons() {

        radio_male.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                str_gender="male";
            else str_gender = "female";

        });

        radio_operator.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                str_role="operator";
            else
                str_role ="Admin";

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK){

            if(requestCode==10){
                assert data != null;
                Bundle extras =data.getExtras();
                if(extras!=null){
                    Bitmap yourImage =extras.getParcelable("data");
                    ByteArrayOutputStream stream= new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                    imageInByte = stream.toByteArray();
                    img_upload_preview.setImageBitmap(getImage(imageInByte));
                    img_upload_preview.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private boolean validateText() {

        boolean isValid = false;

        strUsername = et_username.getText().toString();
        strFirstName = et_firstname.getText().toString();
        strLastName = et_lastname.getText().toString();
        strEmail = et_email.getText().toString();
        strCEmail = et_cemail.getText().toString();
        strCity = et_city.getText().toString();
        strMobile = et_mob.getText().toString();
        strPassword = et_psw.getText().toString();
        strCPassword = et_cpsw.getText().toString();


        if (!strUsername.isEmpty()) {

            if (!strFirstName.isEmpty()) {

                if (!strLastName.isEmpty()) {

                    if (!strEmail.isEmpty()) {

                        if(validateEmail(strEmail.trim())) {

                            if (!strCEmail.isEmpty() && strCEmail.equals(strEmail)) {

                                if (!strCity.isEmpty()) {

                                    if (!strMobile.isEmpty()) {

                                        if (!strPassword.isEmpty()) {

                                            if (!strCPassword.isEmpty() && strPassword.equals(strCPassword)) {

                                                isValid = true;

                                            } else
                                                Toast.makeText(this, "confirm password can't be empty", Toast.LENGTH_SHORT).show();

                                        } else
                                            Toast.makeText(this, "password can't be  empty", Toast.LENGTH_SHORT).show();

                                    } else
                                        Toast.makeText(this, "contact can't be empty", Toast.LENGTH_SHORT).show();

                                } else
                                    Toast.makeText(this, "city can't be empty", Toast.LENGTH_SHORT).show();

                            } else
                                Toast.makeText(this, "confirm email id can't be empty", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();

                    } else Toast.makeText(this, "email can't be empty", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(this, "last name can't be empty", Toast.LENGTH_SHORT).show();

            } else Toast.makeText(this, "first name can't be empty", Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this,"username can't be empty",Toast.LENGTH_SHORT).show();


        return isValid;
    }

    private boolean validateEmail(String strEmail) {
        Matcher matcher =pattern.matcher(strEmail);


        Toast.makeText(this, ""+ matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }
    private String getDate(){

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }



}

