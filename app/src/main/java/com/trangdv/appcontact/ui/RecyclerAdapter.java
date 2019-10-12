package com.trangdv.appcontact.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trangdv.appcontact.DatabaseHandler;
import com.trangdv.appcontact.R;
import com.trangdv.appcontact.listeners.OnDatabaseChangedListeners;
import com.trangdv.appcontact.model.Contacts;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements OnDatabaseChangedListeners {
    //private String[] contacts;
    private LayoutInflater mInflater;
    private int iD;
    Context context;
    LinearLayoutManager layoutManager;
    ItemListener listener;
    DatabaseHandler database;
    Contacts contacts;

    public RecyclerAdapter(Context context, LinearLayoutManager layoutManager, ItemListener itemListener) {
        super();
        this.context = context;
        this.layoutManager = layoutManager;
        listener = itemListener;
        database = new DatabaseHandler(context);
        database.setChangedListeners(this);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        contacts = getItem(position);
        holder.tvName.setText(contacts.getmName());
    }

    @Override
    public int getItemCount() {
        return database.getCount();
    }

    @Override
    public void onNewDatabaseEntryAdded() {

    }

    @Override
    public void onNewDatabaseEntryRemoved() {

    }

    @Override
    public void onNewDatabaseEntryRenamed() {
        notifyItemChanged(iD);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.dispatchToEdit(context, getLayoutPosition());
                    iD = getLayoutPosition();
                }
            });
            /*view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.dispatchToEdit(context, getLayoutPosition());
                    return false;
                }
            });*/
        }
    }

    public Contacts getItem(int position) {
        return database.getItemAt(position);
    }

    interface ItemListener{
        void dispatchToEdit(Context context ,int id);
    }

}
