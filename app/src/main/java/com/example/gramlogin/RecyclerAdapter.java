package com.example.gramlogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder>{

    ArrayList<NewsData> dataHolder;

    public RecyclerAdapter(ArrayList<NewsData> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.heading.setText(dataHolder.get(position).getHeading());
        holder.desc.setText(dataHolder.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView heading, desc;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading_id);
            desc = (TextView) itemView.findViewById(R.id.decription_id);
        }
    }
}
