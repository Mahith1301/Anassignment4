package com.example.fourth_notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class notelistFragment extends Fragment implements recyclerInterface {

    ArrayList<notes> note = new ArrayList<>();
    Notes_RecyclerAdapter adapter;
    recyclerInterface recyclerinterface;
    RecyclerView recyclerView;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notelist, container, false);



        adapter = new Notes_RecyclerAdapter(getActivity(),note, this);

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

        //On clicking the cardLayout a new activity is created to display the details of the view
        Intent details = new Intent(getContext(), editnotesactivity.class);
        details.putExtra("title", note.getTitle());
        details.putExtra("description",note.getContent());

        String p = Integer.toString(position);
        details.putExtra("position",p);
        //startActivity(details);

        //launching third activity
        getResult.launch(details);

    }

    ActivityResultLauncher<Intent> getResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //if result code is 4 then the result comes from update button which is in third activity
            if(result.getResultCode()==4)
            {
                //getting the intent and updated details of the book
                Intent intent = result.getData();
                String p = intent.getStringExtra("position");
                int position = Integer.parseInt(p);
                String ntitle = intent.getStringExtra("title");
                String ndescription = intent.getStringExtra("description");

                //adding new details of that book in same position
                note.set(position,new notes(ntitle,ndescription));
                Log.i("UPDATE","uPDATED");
                Toast.makeText(getActivity().getApplicationContext(), "record updated", Toast.LENGTH_SHORT).show();
                //notifying the adapter for some changes done
                adapter.notifyItemChanged(position);
            }
            //if result code is 5 then the result comes from delete button which is in third activity
            else if (result.getResultCode()==5) {
                //getting the intent and deleting the book details from the arraylist
                Intent intent = result.getData();
                String p = intent.getStringExtra("position");
                int position = Integer.parseInt(p);
                //removing the book
                note.remove(position);
                //notifying the adapter for deleting the book
                adapter.notifyItemRemoved(position);
            }
        }
    });

}