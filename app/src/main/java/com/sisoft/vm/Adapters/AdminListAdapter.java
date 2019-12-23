package com.sisoft.vm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sisoft.vm.Activities.SystemUserUpdateActivity;
import com.sisoft.vm.Activities.VisitorUpdateActivity;
import com.sisoft.vm.Admin;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;

import java.util.ArrayList;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Admin> listAdminDetails;

    public AdminListAdapter(ArrayList<Admin> admins, Context context){

        this.context = context;
        this.listAdminDetails=admins;

    }


    @NonNull
    @Override
    public AdminListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_admins,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminListAdapter.ViewHolder viewHolder, int i) {

        Admin admins = listAdminDetails.get(i);

        viewHolder.tv_admin_id.setText(admins.id+"");
        viewHolder.tv_user_name.setText(admins.username+"");
        viewHolder.tv_role.setText(admins.role+"");
      //  viewHolder.tv_psw.setText(admins.psw+"");

        viewHolder.list1.setOnClickListener(view->{

           // Intent intent =new Intent(context, SystemUserUpdateActivity.class);
          //intent.putExtra("admin",listAdminDetails);
            // Bundle bundle = new Bundle();
         //   Bundle extras = new Bundle();
           // extras.putParcelable("admin",admins);
           // intent.putExtras(extras);
            //context.startActivity(intent);


            Intent intent =new Intent(context, SystemUserUpdateActivity.class);
            intent.putExtra("admin",admins);
            //intent.putExtra("ADMIN_ID", admins.getFirstname());
            context.startActivity(intent);





        });
    }




    @Override
    public int getItemCount() {
        return listAdminDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_admin_id;
        private TextView tv_user_name;
      //  private TextView tv_psw;
        private TextView tv_role;
        private LinearLayout list1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_admin_id = itemView.findViewById(R.id.tv_admin_id);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_role = itemView.findViewById(R.id.tv_role);
         //   tv_psw = itemView.findViewById(R.id.tv_psw);
            list1 = itemView.findViewById(R.id.list1);
        }


    }
}