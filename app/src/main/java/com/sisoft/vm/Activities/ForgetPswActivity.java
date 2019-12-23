package com.sisoft.vm.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sisoft.vm.Admin;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;

public class ForgetPswActivity extends AppCompatActivity {

    private EditText et_username;
    private TextView tv_psw;
    private Button btn_get_psw;
    private Button btn_cancel;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);

        et_username =findViewById(R.id.et_username);
        tv_psw =findViewById(R.id.tv_psw);
        btn_get_psw =findViewById(R.id.btn_get_psw);
        btn_cancel = findViewById(R.id.btn_cancel);

        dbHelper = new DBHelper(this);



        btn_get_psw.setOnClickListener(viewHolder->{
            String uname = et_username.getText().toString();
        Admin admin = dbHelper.getAdminForPsw(uname);
            Toast.makeText(this, "psw"+admin.getPsw(), Toast.LENGTH_SHORT).show();

          //  Toast.makeText(this, "uname"+admin.getUsername(), Toast.LENGTH_SHORT).show();

        if(uname.equals(admin.getUsername())){

            tv_psw.setText(admin.getPsw());
        }

    });




    }


}

