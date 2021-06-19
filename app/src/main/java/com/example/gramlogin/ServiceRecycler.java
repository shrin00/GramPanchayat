package com.example.gramlogin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ServiceRecycler extends FirebaseRecyclerAdapter<ServiceModel, ServiceRecycler.myviewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public ServiceRecycler(@NonNull FirebaseRecyclerOptions<ServiceModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ServiceModel model) {
            final String postkey = getRef(position).getKey();

            holder.heading.setText(model.getServicename());
            holder.decs.setText(model.getFees());

            holder.heading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ServiceForm.class);
                    intent.putExtra("servicename", model.getServicename());
                    intent.putExtra("fees", model.getFees());
                    intent.putExtra("postkey", postkey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new ServiceRecycler.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView heading, decs;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading_id);
            decs = (TextView) itemView.findViewById(R.id.decription_id);
        }
    }
}
