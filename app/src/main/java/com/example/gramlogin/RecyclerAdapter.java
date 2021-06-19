package com.example.gramlogin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RecyclerAdapter extends FirebaseRecyclerAdapter<NewsData, RecyclerAdapter.myviewholder> {
    //adapter for news
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private FirebaseUser user;

    Context context;
    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<NewsData> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull NewsData model) {
        final String uid = user.getUid();
        final String postkey = getRef(position).getKey();

        holder.heading.setText(model.getHeading());
        holder.about.setText(model.getAbout());
        holder.heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Newsdetails.class);
                intent.putExtra("heading", model.getHeading());
                intent.putExtra("detail", model.getDetail());
                intent.putExtra("postkey", postkey);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView heading, about, details;
        RecyclerView commentRecview;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading_id);
            about = (TextView) itemView.findViewById(R.id.decription_id);
        }
    }
}
