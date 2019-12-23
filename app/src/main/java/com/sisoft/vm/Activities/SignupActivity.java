package com.sisoft.vm.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_firstname;
    private EditText et_lastname;
    private EditText et_email;
    private EditText et_cemail;
    private EditText et_mob;
    private EditText et_city;
    private EditText et_psw;
    private EditText et_cpsw;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private Button btn_signup;
    private Button btn_login;

    private RadioButton radio_admin;
    private RadioButton radio_operator;

    private Button btn_upload;
    private ImageView img_upload_preview;
    //variables
    private String strUsername;
    private String strFirstName;
    private String strLastName;
    private String strEmail;
    private String strCEmail;
    private String strMobile;
    private String strCity;
    private String strPassword;
    private String strCPassword;
    private String str_gender ="male";

    private String str_role="operator";

    private DBHelper dbHelper;
    private Pattern pattern;

    private byte[] imageInByte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        et_username = findViewById(R.id.et_username);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_email = findViewById(R.id.et_email);
        et_cemail = findViewById(R.id.et_cemail);
        et_mob = findViewById(R.id.et_mob);
        et_city = findViewById(R.id.et_city);
        et_psw = findViewById(R.id.et_psw);
        et_cpsw = findViewById(R.id.et_cpsw);
        radio_female = findViewById(R.id.radio_female);
        radio_male = findViewById(R.id.radio_male);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_upload = findViewById(R.id.btn_upload);
        img_upload_preview = findViewById(R.id.img_upload_preview);

        radio_admin =findViewById(R.id.radio_admin);
        radio_operator =findViewById(R.id.radio_operator);

        setupRadioButtons();

        pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");

        btn_signup.setOnClickListener(view -> {
            if(validateText()){

                if(dbHelper.insertAdmin(strUsername,str_role,strFirstName,strLastName,strEmail,strCity,strMobile,
                        strPassword,getDate(),imageInByte,str_gender)) {
                    Toast.makeText(this, "User Created ", Toast.LENGTH_SHORT).show();
                  /*  Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);*/
                    finish();
                }else Toast.makeText(this, "failed to insert", Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
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







    }

    private void setupRadioButtons() {
        radio_male.setChecked(true);

        radio_male.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                str_gender="male";
            else str_gender = "female";

            Toast.makeText(this, str_gender, Toast.LENGTH_SHORT).show();
        });

        radio_operator.setChecked(true);
        radio_operator.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                str_role="operator";
            else
                str_role ="Admin";

            Toast.makeText(this, str_role, Toast.LENGTH_SHORT).show();
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

    private Bitmap getImage(byte[] imageInByte) {
        ByteArrayInputStream inputstream =new ByteArrayInputStream(imageInByte);
        return BitmapFactory.decodeStream(inputstream);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
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
