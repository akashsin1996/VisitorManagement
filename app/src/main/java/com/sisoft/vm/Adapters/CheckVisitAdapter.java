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
import com.sisoft.vm.Visitor;

import java.util.ArrayList;

public class CheckVisitAdapter extends RecyclerView.Adapter<CheckVisitAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<Visitor> visitors;
    public CheckVisitAdapter(ArrayList<Visitor> visitors, Context context){
        this.context = context;
        this.visitors=visitors;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_dashboard,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Visitor visitor = visitors.get(i);
        viewHolder.tv_firstname.setText(visitor.firstname);
        viewHolder.tv_city.setText(visitor.city+"");
        viewHolder.tv_mob.setText(visitor.mob+"");

        viewHolder.lin.setOnClickListener(view->{

            Intent intent =new Intent(context, VisitorStatusActivity.class);
            intent.putExtra("visitors",visitors);
            context.startActivity(intent);

        });


    }
    @Override
    public int getItemCount() {
        return visitors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_firstname;
        private TextView tv_city;
        private TextView tv_mob;
        private LinearLayout lin;
        private TextView tv_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_firstname = itemView.findViewById(R.id.tv_firstname);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_mob = itemView.findViewById(R.id.tv_mob);
            lin = itemView.findViewById(R.id.lin1);
           // tv_icon = itemView.findViewById(R.id.tv_icon);
        }
    }
}

