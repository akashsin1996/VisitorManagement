package com.sisoft.vm.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sisoft.vm.Adapters.VisitDetailAdapter;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private TextView tv_visitor_user;
    private RecyclerView recyclerView;
    private ArrayList<VisitDetails> visitDetails= new ArrayList<>();
    private VisitDetailAdapter adapter;
    private DBHelper dbHelper;


    TextView tv_date;
    DatePickerDialog datePickerDialog;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);


        tv_date = view.findViewById(R.id.tv_date);
        Date todate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String str_todate = sdf.format(todate);
        tv_date.setText(str_todate);
        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tv_visitor_user = view.findViewById(R.id.tv_visitor_user);
        dbHelper = new DBHelper(getActivity());
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);// current day
                int mMonth = c.get(Calendar.MONTH); // current month
                int mYear = c.get(Calendar.YEAR); // current year
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year,

                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                tv_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                                getVisitors(tv_date.getText().toString());
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        return view;
    }






    public void getVisitors(String date) {
        visitDetails = dbHelper.getVisitorForDate(date);
        adapter = new VisitDetailAdapter(visitDetails,getActivity());
        recyclerView.setAdapter(adapter);

        if (adapter.getItemCount() > 0) {
            tv_visitor_user.setVisibility(View.GONE);
        } else tv_visitor_user.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getVisitors(tv_date.getText().toString());
    }
}
