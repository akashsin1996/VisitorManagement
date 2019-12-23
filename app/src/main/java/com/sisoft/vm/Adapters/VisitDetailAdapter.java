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

import com.sisoft.vm.Activities.VisitorStatusActivity;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;
import com.sisoft.vm.Visitor;

import java.util.ArrayList;

public class VisitDetailAdapter extends RecyclerView.Adapter<VisitDetailAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<VisitDetails> visitDetails;
   // private ArrayList<Visitor> visitors;
    public VisitDetailAdapter(ArrayList<VisitDetails> visitDetails, Context context){

        this.context = context;
        this.visitDetails=visitDetails;
       // this.visitors=visitor;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_visit_details,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {



        VisitDetails visitDetail= visitDetails.get(i);
        viewHolder.tv_name.setText(visitDetail.visitor_name+"");
        viewHolder.tv_meet.setText(visitDetail.meet_to+"");
        viewHolder.tv_entry_time.setText(visitDetail.entry_time+"");
        viewHolder.tv_exit_time.setText(visitDetail.exit_time+"");


        viewHolder.lin1.setOnClickListener(view->{

            Intent intent =new Intent(context, VisitorStatusActivity.class);
            intent.putExtra("visitDetails",visitDetail);
            context.startActivity(intent);

        });


    }
    @Override
    public int getItemCount() {
        return visitDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tv_meet;
        private TextView tv_entry_time;
        private TextView tv_exit_time;
        private LinearLayout lin1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_meet = itemView.findViewById(R.id.tv_meet);
            tv_entry_time = itemView.findViewById(R.id.tv_entry_time);
            tv_exit_time = itemView.findViewById(R.id.tv_exit_time);
            lin1 = itemView.findViewById(R.id.lin1);

        }
    }
}

