package com.trangdv.appcontact.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.trangdv.appcontact.DatabaseHandler;
import com.trangdv.appcontact.R;
import com.trangdv.appcontact.model.Contacts;

public class EditContactFragment extends Fragment {
    private TextView tvName;
    private TextView tvPhoneNumber;
    private ImageView imgEditName;
    private ImageView imgEditPhone;
    private EditText edtName;
    private EditText edtPhone;
    private ImageView imgDoneName;
    private ImageView imgDonePhone;
    private TextView tvDelete;
    private String name;
    private String phoneNumber;

    private int id;

    Context context;
    DatabaseHandler database;
    Contacts contacts;

    public EditContactFragment(Context context, int position) {
        this.context = context;
        id = position;
        database = new DatabaseHandler(context);
        contacts = database.getItemAt(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        tvName = view.findViewById(R.id.tv_name);
        tvPhoneNumber = view.findViewById(R.id.tv_phone_number);
        tvName.setText(contacts.getmName());
        tvPhoneNumber.setText(contacts.getmPhoneNumber());
        imgEditName = view.findViewById(R.id.img_edit_name);
        imgEditPhone = view.findViewById(R.id.img_edit_phone);
        edtName = view.findViewById(R.id.edt_name);
        edtPhone = view.findViewById(R.id.edt_phone_number);
        imgDoneName = view.findViewById(R.id.img_done_name);
        imgDonePhone = view.findViewById(R.id.img_done_phone);
        tvDelete = view.findViewById(R.id.tv_delete);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = tvName.getText().toString();
        phoneNumber = tvPhoneNumber.getText().toString();

        imgEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName.setVisibility(View.GONE);
                edtName.setVisibility(View.VISIBLE);
                edtName.setText(name);
                edtName.setSelection(name.length());
                imgEditName.setVisibility(View.GONE);
                imgDoneName.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtName, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        imgEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPhoneNumber.setVisibility(View.GONE);
                edtPhone.setVisibility(View.VISIBLE);
                edtPhone.setText(phoneNumber);
                edtPhone.setSelection(phoneNumber.length());
                imgEditPhone.setVisibility(View.GONE);
                imgDonePhone.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtName, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        imgDoneName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString();
                database.renameItem(database.getItemAt(id), name, phoneNumber);
                imgDoneName.setVisibility(View.GONE);
                imgEditName.setVisibility(View.VISIBLE);
                edtName.setVisibility(View.GONE);
                tvName.setVisibility(View.VISIBLE);
                tvName.setText(name);
            }
        });

        imgDonePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = edtPhone.getText().toString();
                database.renameItem(database.getItemAt(id), name, phoneNumber);
                imgDonePhone.setVisibility(View.GONE);
                imgEditPhone.setVisibility(View.VISIBLE);
                edtPhone.setVisibility(View.GONE);
                tvPhoneNumber.setVisibility(View.VISIBLE);
                tvPhoneNumber.setText(phoneNumber);
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.removeItemWithId(database.getItemAt(id).getmId());
                Toast.makeText(getContext(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).backToList();
            }
        });

    }

}

