package com.sisoft.vm.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.MySharedPrefrenced;
import com.sisoft.vm.R;
import com.sisoft.vm.Visitor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisitorRegActivity extends AppCompatActivity {
    private EditText et_vfirstname;
    private EditText et_vlastname;
    private EditText et_vemail;
    private EditText et_vmob;
    private EditText et_vcity;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private Button btn_visitadd;


    //variables
    private String strVFirstName;
    private String strVLastName;
    private String strVEmail;
    private String strVMobile;
    private String strVCity;
    private String str_gender = "male";

    private String strotp;


    private LinearLayout ll_otp;
    private LinearLayout ll_btn_verify;

    private DBHelper dbHelper;
    private Pattern pattern;
    private Pattern pattern1;

    private Button btn_upload;
    private ImageView img_upload_preview;
    private byte[] imageInByte;

    private EditText et_check_mob;
    private EditText et_otp;
    private  Button btn_otp_verify;
    private Button btn_available;

   /* TextView date;
    DatePickerDialog datePickerDialog;*/
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_reg);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Add Visitor");

        et_vfirstname = findViewById(R.id.et_vfirstname);
        et_vlastname = findViewById(R.id.et_vlastname);
        et_vemail = findViewById(R.id.et_vemail);
        et_vmob = findViewById(R.id.et_vmob);
        et_vcity = findViewById(R.id.et_vcity);
        radio_female = findViewById(R.id.radio_female);
        radio_male = findViewById(R.id.radio_male);
        btn_visitadd = findViewById(R.id.btn_visitadd);

        et_check_mob = findViewById(R.id.et_check_mob);
        btn_available = findViewById(R.id.btn_available);
        btn_otp_verify =findViewById(R.id.btn_otp_verify);

        btn_upload = findViewById(R.id.btn_upload);
        img_upload_preview = findViewById(R.id.img_upload_preview);

        et_otp = findViewById(R.id.et_otp);

        ll_otp = findViewById(R.id.ll_otp);
        ll_btn_verify = findViewById(R.id.ll_btn_verify);

        pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        pattern1 = Pattern.compile("[0-9]{10}");

        dbHelper = new DBHelper(this);
        int admin_id = dbHelper.getAdminId(MySharedPrefrenced.getInstance(this).getUser(),
                MySharedPrefrenced.getInstance(this).getPassword());
        ll_btn_verify.setVisibility(View.GONE);
        ll_otp.setVisibility(View.GONE);
        btn_visitadd.setVisibility(View.VISIBLE);

        btn_available.setOnClickListener(view -> {
            if (validateData()) {
                if (dbHelper.isVisitorUserExist((strVMobile))) {

                    Visitor v1 = dbHelper.getVisitor(strVMobile);
                    String visitorID=String.valueOf(v1.getId());
                 /*   et_vfirstname.setText(v1.getFirstname());
                    et_vlastname.setText(v1.getLastname());
                    et_vemail.setText(v1.getEmail());
                    et_vcity.setText(v1.getCity());
                    et_vmob.setText(v1.getMob());*/
                    Intent intent = new Intent(VisitorRegActivity.this,VisitDetailActivity.class);
                    intent.putExtra("visitorID",visitorID );
                    startActivity(intent);
                    Toast.makeText(this, "welcome :"+v1.getId(), Toast.LENGTH_SHORT).show();
                    finish();

                }

                else {
                    Toast.makeText(this, "Visiter user are not exist", Toast.LENGTH_SHORT).show();
                    ll_otp.setVisibility(View.GONE);
                    ll_btn_verify.setVisibility(View.GONE);
                    btn_visitadd.setVisibility(View.VISIBLE);

                }
               /* else {

                }*/
            }

        });

        btn_upload.setOnClickListener(view->{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,10);
            intent.setType("image/*");
            intent.putExtra("crop","true");
            intent.putExtra("aspectX",0);
            intent.putExtra("aspectY",0);
            intent.putExtra("outputX","250");
            intent.putExtra("outputY","200");
        });


        btn_otp_verify.setOnClickListener(view ->{
            strotp=et_otp.getText().toString();
          //  Toast.makeText(getApplicationContext(), "check otp"+otp, Toast.LENGTH_SHORT).show();
           // Log.d("Verify OTP", otp+":"+strotp);
            // Toast.makeText(getApplicationContext(), "db otp"+strotp, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "check random otp"+otp, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "check text otp"+strotp, Toast.LENGTH_SHORT).show();

            if (otp.equalsIgnoreCase(strotp)) {
                Toast.makeText(this, "check random otp"+otp, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "check text otp"+strotp, Toast.LENGTH_SHORT).show();
              //  dbHelper.insertVisitorOtp(strVMobile,strotp,getDateAndTime());
                Toast.makeText(getApplicationContext(), "Otp Verified Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }else Toast.makeText(getApplicationContext(), "otp incorrect", Toast.LENGTH_SHORT).show();

        });


        btn_visitadd.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "check"+btn_visitadd.getText().toString(), Toast.LENGTH_SHORT).show();
            if (validateText()) {
                    int vid = (dbHelper.insertVisitor(strVFirstName, strVLastName, strVEmail, strVCity, strVMobile, str_gender,imageInByte, admin_id));
                 Toast.makeText(getApplicationContext(), "visitor id"+vid, Toast.LENGTH_SHORT).show();
                btn_visitadd.setVisibility(View.GONE);
              ll_otp.setVisibility(View.VISIBLE);
              ll_btn_verify.setVisibility(View.VISIBLE);
              if (vid > 0 ){
                            Random rand = new Random();
                            otp = String.format("%04d", rand.nextInt(10000));
                            sendSMSMessage();
                            dbHelper.insertVisitorOtp(strVMobile, otp, getDateAndTime());
                      //  if(dbHelper.isVisitorOtpExist(strotp)) {


                    } else Toast.makeText(getApplicationContext(), "failed to insert", Toast.LENGTH_SHORT).show();
                }

        });


        setupRadioButtons();


    }

    private String getDateAndTime() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strVMobile, null, "This is One Time " +
                    "Password by VM OTP is "+otp, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(strVMobile, null, "This is One Time " +
                            "Password by VM OTP is "+otp, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }




    private boolean validateData() {
            strVMobile = et_check_mob.getText().toString();
        Toast.makeText(this, "mob"+strVMobile, Toast.LENGTH_SHORT).show();
                if (!strVMobile.isEmpty()) {
                    return true;
                } else
                    Toast.makeText(this, "Mobile number can't be empty", Toast.LENGTH_SHORT).show();

            return false;
        }





    private void setupRadioButtons() {
        radio_male.setChecked(true);

        radio_male.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                str_gender="male";
            else str_gender = "female";
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

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

        strVFirstName = et_vfirstname.getText().toString();
        strVLastName = et_vlastname.getText().toString();
        strVEmail = et_vemail.getText().toString();
        strVCity = et_vcity.getText().toString();
        strVMobile = et_vmob.getText().toString();

            if (!strVFirstName.isEmpty()) {

                if (!strVLastName.isEmpty()) {


                    if (!strVEmail.isEmpty()) {
                        if(validateVEmail(strVEmail.trim())) {

                            if (!strVCity.isEmpty()) {
                                    if (!strVMobile.isEmpty()) {
                                        if (ValidVMobile(strVMobile.trim())) {

                                            isValid = true;

                                        } else
                                            Toast.makeText(this, "contact can't be empty", Toast.LENGTH_SHORT).show();

                                    } else
                                        Toast.makeText(this, "contact is not valid", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(this, "city can't be empty", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(this, "email can't be empty", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(this, "last name can't be empty", Toast.LENGTH_SHORT).show();

            } else Toast.makeText(this, "first name can't be empty", Toast.LENGTH_SHORT).show();




        return isValid;

    }
    private boolean validateVEmail(String strVEmail) {
        Matcher matcher =pattern.matcher(strVEmail);
        Toast.makeText(this, ""+ matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }

    private boolean ValidVMobile(String strVMobile) {
        Matcher matcher =pattern1.matcher(strVMobile);
        Toast.makeText(this, ""+ matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
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

    private Bitmap getImage(byte[] imageInByte) {
        ByteArrayInputStream inputstream =new ByteArrayInputStream(imageInByte);
        return BitmapFactory.decodeStream(inputstream);
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

}

