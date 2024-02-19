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

import java.util.Objects;

public class AddContactActivity extends AppCompatActivity {

    String name;
    String number;
    String sex = "";
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        db = DBHelper.getInstance(this);

        EditText etName = findViewById(R.id.etName);
        EditText etNumber = findViewById(R.id.etNumber);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setHapticFeedbackEnabled(true);
        Button btSave = findViewById(R.id.btSave);


        radioGroup.setOnCheckedChangeListener((radioGroup1, buttonId) -> {
            RadioButton radioButton = findViewById(buttonId);
            sex = radioButton.getText().toString();
        });

        btSave.setOnClickListener(view -> {
            name = etName.getText().toString();
            number = etNumber.getText().toString();
            if( !name.isEmpty() && !number.isEmpty() && !sex.isEmpty()) {
                Boolean checkInsertData = db.insertContactData(name, number, sex);
                if (checkInsertData) {
                    Toast.makeText(AddContactActivity.this, name + "\n" + number + "\n" + sex + "\nSaved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(AddContactActivity.this, "Could not save data", Toast.LENGTH_SHORT).show();
                }
            }
            if( name.isEmpty() ){
                etName.setError("Name required");
            }
            if( number.isEmpty() ){
                etNumber.setError("Number required");
            }
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Check if the device supports vibration
            if (vibrator != null && vibrator.hasVibrator()) {
                // For API level 26 and above
                VibrationEffect vibe = VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(vibe);
            }
        });
    }
}