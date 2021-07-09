package com.example.gramlogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TransactionAdapter extends FirebaseRecyclerAdapter<TransactionData, TransactionAdapter.myholder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TransactionAdapter(@NonNull FirebaseRecyclerOptions<TransactionData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myholder holder, int position, @NonNull TransactionData model) {
            String dt = model.getDate()+" "+model.getTime();

            holder.trans_id.setText(getRef(position).getKey());
            holder.amount.setText(model.getAmount());
            holder.dati.setText(dt);
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_transaction_row, parent, false);
        return new TransactionAdapter.myholder(view);
    }

    class myholder extends RecyclerView.ViewHolder{

        TextView trans_id, dati, amount;
        public myholder(@NonNull View itemView) {
            super(itemView);
            trans_id = (TextView) itemView.findViewById(R.id.transaction_id);
            dati = (TextView) itemView.findViewById(R.id.datetime_id);
            amount = (TextView) itemView.findViewById(R.id.amount_id);
        }
    }
}
