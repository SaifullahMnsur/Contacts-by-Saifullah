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
    RecyclerView recyclerView;
    ArrayList<String> name, number, sex;
    ArrayList<Contact> contacts;
    DBHelper db;
    MyAdapter adapter;
    FloatingActionButton fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        name = new ArrayList<>();
        number = new ArrayList<>();
        sex = new ArrayList<>();
        contacts = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
//        adapter = new MyAdapter(this, name, number, sex);
        adapter = new MyAdapter(this, contacts);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
        fav = findViewById(R.id.favAddNew);
        fav.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddContactActivity.class)));
    }

    private void displayData() {
        Cursor cursor = db.getData();
        if( cursor.getCount() == 0 ){
            Toast.makeText(MainActivity.this, "No Contact", Toast.LENGTH_SHORT).show();
        } else {
            while ( cursor.moveToNext() ){
                contacts.add(new Contact(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }
    }
}