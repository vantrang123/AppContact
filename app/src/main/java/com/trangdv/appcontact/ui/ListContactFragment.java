package com.trangdv.appcontact.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trangdv.appcontact.R;

public class ListContactFragment extends Fragment implements RecyclerAdapter.ItemListener {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_contact, container, false);
        recyclerView = view.findViewById(R.id.rv_account);
        fabAdd = view.findViewById(R.id.fab_add);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), layoutManager, this);
        recyclerView.setAdapter(adapter);

        ClickAddContact();

    }

    @Override
    public void dispatchToEdit(Context context, int id) {
        ((MainActivity) getActivity()).replace(new EditContactFragment(context, id));

    }


    private void ClickAddContact() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchToAdd();
            }
        });
    }

    private void dispatchToAdd() {
        ((MainActivity) getActivity()).replace(new AddContactFragment());
    }
}
