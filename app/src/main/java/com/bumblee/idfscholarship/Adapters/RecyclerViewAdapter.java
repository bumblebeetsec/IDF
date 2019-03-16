package com.bumblee.idfscholarship.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumblee.idfscholarship.R;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG= "RecyclerViewAdapter";
    private ArrayList<String> name=new ArrayList<>();
    private ArrayList<String> dev=new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> name,ArrayList<String> dev,Context context) {
        this.name = name;
        this.dev=dev;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.org_scholarship_listitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtName.setText(name.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            parentLayout=(RelativeLayout)itemView.findViewById(R.id.parent_layout);

        }
    }
}
