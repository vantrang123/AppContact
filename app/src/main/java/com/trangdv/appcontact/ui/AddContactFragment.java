package com.trangdv.appcontact.ui;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.trangdv.appcontact.DatabaseHandler;
import com.trangdv.appcontact.R;

import java.io.File;

public class AddContactFragment extends Fragment {
    private EditText edtName;
    private EditText edtPhoneNumber;
    private TextView tvAdd;

    String name;
    String phoneNumber;

    DatabaseHandler database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        edtName = view.findViewById(R.id.edt_name);
        edtPhoneNumber = view.findViewById(R.id.edt_phone_number);
        tvAdd = view.findViewById(R.id.tv_add);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Contact");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new DatabaseHandler(getContext());
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtName.getText().toString().equals("") && !edtPhoneNumber.getText().toString().equals("")) {
                    getInfo();
                    database.addContact(name, phoneNumber);
                    Toast.makeText(getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).backToList();
                }
                else {
                    Toast.makeText(getContext(), "You must enter enough information!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getInfo() {
        name = edtName.getText().toString();
        phoneNumber = edtPhoneNumber.getText().toString();
    }

}
