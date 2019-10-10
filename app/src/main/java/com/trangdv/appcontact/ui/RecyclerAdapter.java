package com.trangdv.appcontact.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trangdv.appcontact.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] contacts;
    private LayoutInflater mInflater;
    private ItemListener listener;

    public RecyclerAdapter(String[] contacts, ItemListener itemListener) {
        this.contacts = contacts;
        listener = itemListener;
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
        holder.tvName.setText(contacts[position]);

    }

    @Override
    public int getItemCount() {
        return contacts.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.dispatchToEdit(tvName.getText().toString());
                }
            });
        }

    }

    interface ItemListener{
        void dispatchToEdit(String name);
    }

}
