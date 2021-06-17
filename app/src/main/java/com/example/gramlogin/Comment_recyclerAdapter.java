package com.example.gramlogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Comment_recyclerAdapter extends FirebaseRecyclerAdapter<Comment_model, Comment_recyclerAdapter.myviewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Comment_recyclerAdapter(@NonNull FirebaseRecyclerOptions<Comment_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Comment_model model) {
        String cudt = model.getTime()+""+model.getDate();

        holder.cuname.setText(model.getName());
        holder.cumessage.setText(model.getUsermsg());
        holder.cudt.setText(cudt);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_single_row, parent, false);
        return new Comment_recyclerAdapter.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView cuname, cumessage, cudt;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            cuname = (TextView) itemView.findViewById(R.id.cuname);
            cumessage = (TextView) itemView.findViewById(R.id.cumessage);
            cudt = (TextView) itemView.findViewById(R.id.cudt);
        }
    }
}



