package com.example.gramlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;


public class UserApplicationAdapter extends RecyclerView.Adapter<UserApplicationAdapter.myviewholder>{

    ArrayList<UserApplicationModel> applications = new ArrayList<>();
    Context context;
    public UserApplicationAdapter(ArrayList<UserApplicationModel> applications, Context context) {
        this.applications = applications;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        String servicename = applications.get(position).getServicename();
        String dat = applications.get(position).getDat();
        holder.servicename.setText(servicename);
        holder.datime.setText(dat);
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView servicename, datime;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            servicename = (TextView) itemView.findViewById(R.id.heading_id);
            datime = (TextView) itemView.findViewById(R.id.decription_id);
        }
    }

}
