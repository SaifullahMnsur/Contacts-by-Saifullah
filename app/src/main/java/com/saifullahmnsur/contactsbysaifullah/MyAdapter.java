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
    private final Context context;
//    private final ArrayList<String> nameId, numberId, sexId;
    private final ArrayList<Contact> contacts;

//    public MyAdapter(Context context, ArrayList<String> nameId, ArrayList<String> numberId, ArrayList<String> sexId) {
//        this.context = context;
//        this.nameId = nameId;
//        this.numberId = numberId;
//        this.sexId = sexId;
//    }
    public MyAdapter(Context context, ArrayList<Contact>contacts){
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contactcard, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.nameId.setText(String.valueOf(nameId.get(position)));
//        holder.numberId.setText(String.valueOf(numberId.get(position)));
        Contact contact = contacts.get(position);

        holder.nameId.setText(contact.getName());
        holder.numberId.setText(contact.getNumber());

        holder.favCall.setOnClickListener(view -> initiateCall(contact.getNumber()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameId, numberId, sexId;
        FloatingActionButton favCall; // Add this line
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameId = itemView.findViewById(R.id.tvContactName);
            numberId = itemView.findViewById(R.id.tvContactNumber);
            favCall = itemView.findViewById(R.id.favCall); // Add this line
        }
    }

    private void initiateCall(String phoneNumber){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Check if the device supports vibration
            if (vibrator != null && vibrator.hasVibrator()) {
                // For API level 26 and above
                VibrationEffect vibe = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(vibe);
            }
            String phoneNumberUri = "tel: " + phoneNumber;
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumberUri));
            context.startActivity(dialIntent);
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
            // Handle the result in onRequestPermissionsResult callback
        }
    }

}
