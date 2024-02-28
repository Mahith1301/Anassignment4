package com.example.fourth_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editnotesactivity extends AppCompatActivity {

    EditText title,description;
    Button delete,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnotesactivity);



        Intent details=getIntent();

        title = findViewById(R.id.textName);
        description= findViewById(R.id.textdescription);

        update=findViewById(R.id.updateButton);
        delete=findViewById(R.id.deleteButton);

        title.setText(details.getStringExtra("title"));
        description.setText(details.getStringExtra("description"));


        String titlename =details.getStringExtra("title");
        String p = details.getStringExtra("position");

        Intent setResult = new Intent();


        //setting onClickListener for update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the values from the editText and storing it as string
                String eTitle = title.getText().toString();

                String edescription = description.getText().toString();

                //checking whether all fields are filled or not
                //if not then toast message will pop up
                if (eTitle.isEmpty() || edescription.isEmpty()) {
                    android.widget.Toast.makeText(getApplicationContext(), "Please Enter All Fields", Toast.LENGTH_LONG).show();
                } else
                {
                    //setting the selection to title column
                    String select = "id =?";
                    //search for book name and update its details
                    String args[] = {titlename};
                    // class to add values in the database
                    ContentValues values = new ContentValues();

                    // fetching text from user
                    values.put(contentprovider.id,eTitle);

                    values.put(contentprovider.name,edescription);

                    // updating a row in database through content URI
                    getContentResolver().update(contentprovider.CONTENT_URI, values,select,args);
                    // displaying a toast message
                    android.widget.Toast.makeText(getApplicationContext(), "Book details Updated", Toast.LENGTH_LONG).show();

                    //sending the details of book as result
                    setResult.putExtra("position",p);
                    setResult.putExtra("title",eTitle);

                    setResult.putExtra("description",edescription);

                    //when successful completion of third activity it sends result to second activity
                    setResult(4,setResult);
                    //finishing the third activity
                    finish();


                }
            }
        });

        //setting onClickListener for delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting the selection to title column
                String select = "id =?";
                //search for book name and deletes its details
                String args[] = {titlename};
                // deleting a row in database through content URI
                getContentResolver().delete(contentprovider.CONTENT_URI,select,args);
                // displaying a toast message
                android.widget.Toast.makeText(getApplicationContext(), "Book details Deleted", Toast.LENGTH_LONG).show();
                setResult.putExtra("position",p);
                //when successful completion of third activity it sends result to second activity
                setResult(5,setResult);
                //finishing the third activity
//                title.setText("");
//                description.setText("");
                finish();
            }
        });



    }
}