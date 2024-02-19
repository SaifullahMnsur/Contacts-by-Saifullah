package com.saifullahmnsur.contactsbysaifullah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    private Contact contact; // declare contact
    private DBHelper db; // declare database helper declaration
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        db = new DBHelper(this); // get instance of database helper
        contact = new Contact(); // initialize contact

        EditText etName = findViewById(R.id.etName); // declare and initialize edit text view for name
        EditText etNumber = findViewById(R.id.etNumber); // declare and initialize edit text view for number
        RadioGroup radioGroup = findViewById(R.id.radioGroup);  // declare and initialize radio group view for sex
        radioGroup.setHapticFeedbackEnabled(true); // enable haptic feedback
        Button btSave = findViewById(R.id.btSave); // declare and initialize button view to save the contact

        // when any of the radio button is clicked
        radioGroup.setOnCheckedChangeListener((radioGroup1, buttonId) -> {
            RadioButton radioButton = findViewById(buttonId);
            contact.setSex(radioButton.getText().toString());
        });

        // when save button is clicked
        btSave.setOnClickListener(view -> {
            contact.setName(etName.getText().toString()); // get name
            contact.setNumber(etNumber.getText().toString()); // get number
            // when all necessary inputs are given
            if( contact.isSavable() ) {
                Boolean checkInsertData = db.insertContactData(contact); // add the data into database
                if (checkInsertData) {
                    // if data is stored, show toast message and return to main activity
                    Toast.makeText(AddContactActivity.this, contact.getName() + "\n" + contact.getNumber() + "\n" + contact.getSex() + "\nSaved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    // if data is not store, show toast message
                    Toast.makeText(AddContactActivity.this, "Could not save data", Toast.LENGTH_SHORT).show();
                }
            }
            if( contact.getName().isEmpty() ){
                etName.setError("Name required"); // if name is not given show error
            }
            if( contact.getNumber().isEmpty() ){
                etNumber.setError("Number required"); // if number is not given show error
            }
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // initialize vibrator
            // Check if the device supports vibration
            if (vibrator != null && vibrator.hasVibrator()) {
                // For API level 26 and above
                VibrationEffect vibe = VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE); // vibrate for 150ms
                vibrator.vibrate(vibe);
            }
        });
    }
}