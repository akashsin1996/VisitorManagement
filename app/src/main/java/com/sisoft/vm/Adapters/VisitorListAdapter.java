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

import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;

import java.util.ArrayList;

public class VisitorListAdapter extends RecyclerView.Adapter<VisitorListAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<VisitDetails> listVisitDetails;

    public VisitorListAdapter(ArrayList<VisitDetails> visitDetails, Context context){

        this.context = context;
        this.listVisitDetails=visitDetails;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_visitor,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        VisitDetails visitDetails = listVisitDetails.get(i);

        viewHolder.tv_visitor_id.setText(visitDetails.id+"");
        viewHolder.tv_visit_date.setText(visitDetails.visit_date+"");
        viewHolder.tv_purpose.setText(visitDetails.purpose+"");
      //  viewHolder.tv_status.setText(visitDetails.status+"");





    }
    @Override
    public int getItemCount() {

        return listVisitDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_visitor_id;
        private TextView tv_visit_date;
        private TextView tv_purpose;
       // private TextView tv_status;
        private LinearLayout list1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_visitor_id = itemView.findViewById(R.id.tv_Visitor_id);
            tv_visit_date = itemView.findViewById(R.id.tv_visit_date);
            tv_purpose = itemView.findViewById(R.id.tv_purpose);
           // tv_status = itemView.findViewById(R.id.tv_status);
            list1 = itemView.findViewById(R.id.list1);

        }
    }
}

