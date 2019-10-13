package com.trangdv.appcontact.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.trangdv.appcontact.DatabaseHandler;
import com.trangdv.appcontact.R;

import java.util.ArrayList;
import java.util.List;

public class ListContactFragment extends Fragment implements RecyclerAdapter.ItemListener {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    MaterialSearchBar searchBar;
    DatabaseHandler database;
    List<String> contactsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_contact, container, false);
        recyclerView = view.findViewById(R.id.rv_account);
        fabAdd = view.findViewById(R.id.fab_add);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setHint("Search Contact");
        searchBar.setPlaceHolder("Search Contact");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new DatabaseHandler(getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), layoutManager, this);
        recyclerView.setAdapter(adapter);

        ClickAddContact();
        setSearch();

    }

    private void setSearch() {
        getSuggestionList();
        searchBar.setLastSuggestions(contactsList);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for (String search:suggest) {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                    searchBar.setLastSuggestions(suggest);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getSuggestionList() {
        for (int i = 0; i < database.getCount(); i++) {
            contactsList.add(database.getItemAt(i).getmName());
        }
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
