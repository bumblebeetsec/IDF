package com.bumblee.idfscholarship.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumblee.idfscholarship.R;
import com.bumblee.idfscholarship.ScholarshipDetailsActivity;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG= "RecyclerViewAdapter";
    private ArrayList<String> name=new ArrayList<>();
    private ArrayList<String> dev=new ArrayList<>();
    private ArrayList<Integer> sid = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> name,ArrayList<String> dev, ArrayList<Integer> sid,Context context) {
        this.name = name;
        this.dev=dev;
        this.sid = sid;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.org_scholarship_listitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtName.setText(name.get(position));
        holder.descTextView.setText(dev.get(position));
        holder.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ScholarshipDetailsActivity.class);
                intent.putExtra("sid", sid.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView descTextView;
        LinearLayout parentLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            descTextView = (TextView) itemView.findViewById(R.id.descTextView);
            parentLinearLayout = (LinearLayout) itemView.findViewById(R.id.parentLL);


        }
    }
}
