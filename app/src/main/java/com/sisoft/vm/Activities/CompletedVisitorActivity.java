package com.sisoft.vm.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sisoft.vm.Adapters.CompletedVisitorAdapter;
import com.sisoft.vm.Adapters.CompletedVisitorAdapter;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;
import java.util.ArrayList;

public class CompletedVisitorActivity extends AppCompatActivity {



    private TextView tv_visitor_user;
    private RecyclerView recyclerView;
    private ArrayList<VisitDetails> visitDetails= new ArrayList<>();
    private CompletedVisitorAdapter adapter;
    private DBHelper dbHelper;

    public CompletedVisitorActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_visitor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle("Complete List");


        recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_visitor_user = findViewById(R.id.tv_visitor_user);
        dbHelper = new DBHelper(this);




    }
    public void onResume() {
        super.onResume();
        visitDetails = dbHelper.getCompleted();
        adapter = new CompletedVisitorAdapter(visitDetails,this);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount()>0){
            tv_visitor_user.setVisibility(View.GONE);
        }
        else tv_visitor_user.setVisibility(View.VISIBLE);
    }
}
