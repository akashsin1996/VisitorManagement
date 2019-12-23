 package com.sisoft.vm.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sisoft.vm.Adapters.CompletedVisitorAdapter;
import com.sisoft.vm.Adapters.CurrentVisitorAdapter;
import com.sisoft.vm.Adapters.DashboardAdapter;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;
import com.sisoft.vm.Visitor;

import java.util.ArrayList;

public class CurrentVisitorActivity extends AppCompatActivity {


    private TextView tv_visitor_user;
    private RecyclerView recyclerView;
    private ArrayList<VisitDetails> visitDetails= new ArrayList<>();
    private CurrentVisitorAdapter adapter;
    private DBHelper dbHelper;

    public CurrentVisitorActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_visitor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Current Premises list");



        recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_visitor_user = findViewById(R.id.tv_visitor_user);
        dbHelper = new DBHelper(this);


    }

    public void onResume() {
        super.onResume();
        visitDetails = dbHelper.getCurrent();
        adapter = new CurrentVisitorAdapter(visitDetails,this);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount()>0){
            tv_visitor_user.setVisibility(View.GONE);
        }
        else tv_visitor_user.setVisibility(View.VISIBLE);
    }

}
