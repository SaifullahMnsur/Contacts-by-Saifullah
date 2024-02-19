package com.saifullahmnsur.contactsbysaifullah;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> contacts; // Array of Contact
    private DBHelper db; // Database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DBHelper(this); // get database helper instance

        contacts = new ArrayList<>(); // contacts array initialization


        // Recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerView); // find the recycler view

        // Adapter for Recyclerview
        MyAdapter adapter = new MyAdapter(this, contacts); // initialize adapter for recycler view

        recyclerView.setAdapter(adapter); // set adapter into recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // set a linear layout manager to the recycler view

        // floating action button
        FloatingActionButton fab = findViewById(R.id.fabAddNew); // find the floating action button
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddContactActivity.class))); // go to Add Contact Activity when the button is clicked
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayData(); // show all the contacts
    }

    private void displayData() {
        Cursor cursor = db.getData(); // get all the data stored in the database
        if( cursor.getCount() == 0 ){
            // Toast message if no contact is saved
            Toast.makeText(MainActivity.this, "No Contact", Toast.LENGTH_SHORT).show();
        } else {
            // store all the data into the Contact Array List
            while ( cursor.moveToNext() ){
                Contact contact = new Contact();
                contact.setID(cursor.getInt(0)); // set id
                contact.setName(cursor.getString(1)); // set name
                contact.setNumber(cursor.getString(2)); // set number
                contact.setSex(cursor.getString(3)); // set Sex
                contacts.add(contact); // add into the Contact Array List
            }
        }
    }
}