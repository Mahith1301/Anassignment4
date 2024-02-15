package com.example.fourth_notes;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class mainFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        EditText title = view.findViewById(R.id.textName);
        EditText description= view.findViewById(R.id.textdescription);

        Button update=view.findViewById(R.id.updateButton);
        Button load=view.findViewById(R.id.loadButton);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put(contentprovider.id,title.getText().toString());
                values.put(contentprovider.name,description.getText().toString());

                getActivity().getContentResolver().insert(contentprovider.CONTENT_URI, values);

                // displaying a toast message
                Toast.makeText(view.getContext(), "New Record Inserted", Toast.LENGTH_LONG).show();

                title.setText("");
                description.setText("");

            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new notelistFragment());

            }
        });

    return view;
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    }
