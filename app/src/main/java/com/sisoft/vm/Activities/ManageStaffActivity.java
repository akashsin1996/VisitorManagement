package com.sisoft.vm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sisoft.vm.Adapters.AdminListAdapter;
import com.sisoft.vm.Adapters.StaffListAdapter;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.Staff;

import java.util.ArrayList;

public class ManageStaffActivity extends AppCompatActivity {

    private TextView tv_no_staffs;
    private RecyclerView recyclerView;
    private ArrayList<Staff> staffs= new ArrayList<>();
    private AdminListAdapter adapter;
    private DBHelper dbHelper;

    private LinearLayout ll_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_staff);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Manage Staffs");


        recyclerView=findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_no_staffs = findViewById(R.id.tv_no_staffs);
        dbHelper = new DBHelper(this);

        ll_operator=findViewById(R.id.ll_operator);
    }


    public void add_staff(View v){
        Intent myIntent = new Intent(ManageStaffActivity.this, StaffAddActivity.class);
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
        staffs = dbHelper.getStaff();

        StaffListAdapter adapter = new StaffListAdapter(staffs,this);
        recyclerView.setAdapter(adapter);

        Toast.makeText(this,"Record count:"+adapter.getItemCount(),Toast.LENGTH_SHORT).show();
        if(adapter.getItemCount()>0){
            tv_no_staffs.setVisibility(View.GONE);
        }
        else tv_no_staffs.setVisibility(View.VISIBLE);
    }
}
