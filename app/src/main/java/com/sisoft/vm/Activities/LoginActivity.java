package com.sisoft.vm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.MySharedPrefrenced;
import com.sisoft.vm.R;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_psw;
    private Button btn_login;
    private TextView tv_signup;
    private Button btn_forget;

    private DBHelper dbHelper;
    private LinearLayout lin_signup;

    private String strUsername;
    private String strPassword;
    private String strRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_username=findViewById(R.id.et_username);
        et_psw=findViewById(R.id.et_psw);
        btn_login=findViewById(R.id.btn_login);
      /*  tv_signup=findViewById(R.id.tv_signup);*/
        btn_forget =findViewById(R.id.btn_forget);
       /* lin_signup=findViewById(R.id.lin_signup);*/
     //   lin_signup.setVisibility(View.GONE);



        btn_login.setOnClickListener(view->{

            if(validateData()){
                if(dbHelper.isUserExist(strUsername,strPassword)){

                    String role = dbHelper.getUserRole(strUsername,strPassword);

                    Toast.makeText(this, "check role"+role, Toast.LENGTH_SHORT).show();
                    if(role.equalsIgnoreCase("admin")){

                        Intent intent = new Intent(this,HomeActivity.class);
                    startActivity(intent);
                    }
                    else{

                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                    }
                    MySharedPrefrenced.getInstance(this).setUser(strUsername);
                    MySharedPrefrenced.getInstance(this).setPassword(strPassword);
                    MySharedPrefrenced.getInstance(this).setRole(role);
                    finish();
                    Toast.makeText(this, "welcome "+strUsername, Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this, "username name password are not exist", Toast.LENGTH_SHORT).show();
            }




        });

     /*   tv_signup.setOnClickListener(view->{
            Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
            startActivity(intent);
        });*/

        btn_forget.setOnClickListener(view->{
            Intent intent = new Intent(LoginActivity.this,ForgetPswActivity.class);
            startActivity(intent);
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = new DBHelper(this);
    }

    private boolean validateData(){
        strUsername = et_username.getText().toString();
        strPassword= et_psw.getText().toString();

        if(!strUsername.isEmpty()){

            if(!strPassword.isEmpty()){
                return true;

            }else Toast.makeText(this, "password can't be empty", Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this, "username can't be empty", Toast.LENGTH_SHORT).show();

        return false;
    }
}
