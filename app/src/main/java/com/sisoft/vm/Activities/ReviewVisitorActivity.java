package com.sisoft.vm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sisoft.vm.Adapters.DashboardAdapter;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.Visitor;

import java.util.ArrayList;

public class ReviewVisitorActivity extends AppCompatActivity {

    private TextView tv_no_visitor;
    private RecyclerView recyclerView;
    private ArrayList<Visitor> visitors = new ArrayList<>();
    private DashboardAdapter adapter;
    private DBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiter_review);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle(" Visitor CheckIn");

        recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv_no_visitor = findViewById(R.id.tv_no_visit);

        dbHelper = new DBHelper(this);

    }

    public void Mng_visitor(View v){
        Intent myIntent = new Intent(ReviewVisitorActivity.this, VisitorRegActivity.class);
        startActivity(myIntent);


    }

    @Override
    public void onResume() {
        super.onResume();
        visitors = dbHelper.getVisitor();
        adapter = new DashboardAdapter(visitors,this);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount()>0){
            tv_no_visitor.setVisibility(View.GONE);
        }
        else tv_no_visitor.setVisibility(View.VISIBLE);
    }
}
