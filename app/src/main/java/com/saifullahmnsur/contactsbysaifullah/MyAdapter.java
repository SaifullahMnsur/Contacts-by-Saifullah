package com.saifullahmnsur.contactsbysaifullah;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final Context context; // declare context
    private final ArrayList<Contact> contacts; // declare Contacts Array List

    public MyAdapter(Context context, ArrayList<Contact>contacts){
        this.context = context; // initialize context
        this.contacts = contacts; // initialize contacts array list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a view using contact card layout to show in recyclerview
        View view = LayoutInflater.from(context).inflate(R.layout.contactcard, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // set the values of ith position
        Contact contact = contacts.get(position);

        holder.tvName.setText(contact.getName()); // set name
        holder.tvNumber.setText(contact.getNumber()); // set number

        // initialize call on clicking call button
        holder.fabCall.setOnClickListener(view -> initiateCall(contact.getNumber()));
    }

    @Override
    public int getItemCount() {
        return contacts.size(); // number of individual contact
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber; // declare contact name and number view
        FloatingActionButton fabCall; // declare call button view
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvContactName); // initialize contact name view
            tvNumber = itemView.findViewById(R.id.tvContactNumber); // initialize contact number view
            fabCall = itemView.findViewById(R.id.fabCall); // initialize call button view
        }
    }

    private void initiateCall(String phoneNumber){
        // Check if the device granted permission for phone call
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE); // declare and initialize vibrator
            // Check if the device supports vibration
            if (vibrator != null && vibrator.hasVibrator()) {
                // For API level 26 and above
                VibrationEffect vibe = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(vibe); // vibrate for 50ms
            }
            String phoneNumberUri = "tel: " + phoneNumber; // set string of Telephone URI
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumberUri)); // declare and initialize a dial intent to call the number
            context.startActivity(dialIntent); // start dial intent and the call
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

}
