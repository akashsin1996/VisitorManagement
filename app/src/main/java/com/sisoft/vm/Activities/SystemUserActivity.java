package com.sisoft.vm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sisoft.vm.Adapters.AdminListAdapter;
import com.sisoft.vm.Adapters.DashboardAdapter;
import com.sisoft.vm.Admin;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;

import java.util.ArrayList;

public class SystemUserActivity extends AppCompatActivity {

    private TextView tv_no_admin_user;
    private RecyclerView recyclerView;
    private ArrayList<Admin> admins= new ArrayList<>();
    private AdminListAdapter adapter;
    private DBHelper dbHelper;



    private Button btn_add_sysuser;
    private LinearLayout ll_operator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("System Users");

        recyclerView=findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_no_admin_user = findViewById(R.id.tv_no_admin_user);
        dbHelper = new DBHelper(this);

        btn_add_sysuser =findViewById(R.id.btn_add_sysuser);
        ll_operator=findViewById(R.id.ll_operator);


    }

    public void AddSysUser(View v){
        Intent myIntent = new Intent(SystemUserActivity.this, SignupActivity.class);
        startActivity(myIntent);


    }
    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        admins = dbHelper.getAdmins();

        AdminListAdapter adapter = new AdminListAdapter(admins,this);
        recyclerView.setAdapter(adapter);

        Toast.makeText(this,"Record count:"+adapter.getItemCount(),Toast.LENGTH_SHORT).show();
        if(adapter.getItemCount()>0){
            tv_no_admin_user.setVisibility(View.GONE);
        }
        else tv_no_admin_user.setVisibility(View.VISIBLE);
    }
}
