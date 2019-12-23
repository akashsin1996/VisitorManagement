package com.sisoft.vm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sisoft.vm.Activities.ManageStaffActivity;

import com.sisoft.vm.Activities.StaffUpdateActivity;
import com.sisoft.vm.Activities.VisitorUpdateActivity;
import com.sisoft.vm.R;
import com.sisoft.vm.Staff;

import java.util.ArrayList;

public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Staff> staffs;

    public StaffListAdapter(ArrayList<Staff> staffs, Context context){

        this.context = context;
        this.staffs=staffs;

    }


    @NonNull
    @Override
    public StaffListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_staffs,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffListAdapter.ViewHolder viewHolder, int i) {

        Staff staff = staffs.get(i);
        viewHolder.tv_emp_name.setText(staff.emp_name+"");
        viewHolder.tv_dept_name.setText(staff.dept_name+"");
        viewHolder.tv_designation.setText(staff.designation+"");
        //  viewHolder.tv_psw.setText(admins.psw+"");

        viewHolder.list2.setOnClickListener(view->{

            Intent intent =new Intent(context, StaffUpdateActivity.class);
            intent.putExtra("staff",staff);
            context.startActivity(intent);


        });
    }




    @Override
    public int getItemCount() {
        return staffs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_emp_name;
        private TextView tv_dept_name;
        private TextView tv_designation;
        private LinearLayout list2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_emp_name = itemView.findViewById(R.id.tv_emp_name);
            tv_dept_name = itemView.findViewById(R.id.tv_dept_name);
            tv_designation = itemView.findViewById(R.id.tv_designation);
            list2 = itemView.findViewById(R.id.list2);
        }


    }
}