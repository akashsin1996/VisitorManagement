package com.sisoft.vm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.sisoft.vm.MySharedPrefrenced;
import com.sisoft.vm.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    Toast.makeText(SplashActivity.this, ""
                            +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finally {

                    Intent intent;
                    if(!MySharedPrefrenced.getInstance(SplashActivity.this).getUser().equals("")){
                        intent = new Intent(SplashActivity.this,HomeActivity.class);
                    }
                    else  /*intent = new Intent(SplashActivity.this,LoginActivity.class);*/
                        intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };thread.start();
    }
}
