package com.trangdv.appcontact.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.transition.FragmentTransitionSupport;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trangdv.appcontact.R;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        ListContact();
    }

    private void ListContact() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new ListContactFragment())
                .commit();
    }

}
