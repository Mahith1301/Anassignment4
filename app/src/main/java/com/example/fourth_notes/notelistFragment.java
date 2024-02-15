package com.example.fourth_notes;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class notelistFragment extends Fragment implements recyclerInterface {


    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notelist, container, false);
        RecyclerView recyclerView;
        recyclerInterface recyclerinterface;
        ArrayList<notes> note = new ArrayList<>();
        Notes_RecyclerAdapter adapter = new Notes_RecyclerAdapter(getActivity(),note, this);

       recyclerView=(RecyclerView) view.findViewById(R.id.noteslist);

        Cursor cursor = getActivity().getContentResolver().query(contentprovider.CONTENT_URI,
                null, null, null, null);
        // iteration of the cursor
        // to print whole table
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String title, content;
                while (!cursor.isAfterLast()) {
                    title = cursor.getString(cursor.getColumnIndex(contentprovider.id));
                    content = cursor.getString(cursor.getColumnIndex(contentprovider.name));
                    note.add(new notes(title, content));
                    cursor.moveToNext();
                }
                //setting adapter with recyclerView
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }


        return view;
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(notes note, int position) {

    }
}