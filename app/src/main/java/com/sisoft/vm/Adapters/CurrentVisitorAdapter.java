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

import com.sisoft.vm.Activities.CompletedVisitorActivity;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;
import com.sisoft.vm.Visitor;

import java.util.ArrayList;

public class CurrentVisitorAdapter extends RecyclerView.Adapter<CurrentVisitorAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<VisitDetails> visitDetails;

    public CurrentVisitorAdapter(ArrayList<VisitDetails> visitDetails, Context context){
        this.context = context;
        this.visitDetails=visitDetails;


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_current,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        VisitDetails visitDetail = visitDetails.get(i);
        viewHolder.tv_name.setText(visitDetail.visitor_name+"");
        viewHolder.tv_meet_to.setText(visitDetail.meet_to+"");
        viewHolder.tv_purpose.setText(visitDetail.purpose+"");


       /* viewHolder.lin1.setOnClickListener(view->{

            Intent intent =new Intent(context, CompletedVisitorActivity.class);
            intent.putExtra("visitDetails",visitDetails);
            context.startActivity(intent);

        });*/

    }
    @Override
    public int getItemCount() {
        return visitDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_meet_to;
        private TextView tv_purpose;
        private LinearLayout lin1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_meet_to = itemView.findViewById(R.id.tv_meet_to);
            tv_purpose = itemView.findViewById(R.id.tv_purpose);
            lin1 = itemView.findViewById(R.id.lin1);

        }
    }
}

