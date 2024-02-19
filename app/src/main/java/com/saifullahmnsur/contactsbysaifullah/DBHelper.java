package com.saifullahmnsur.contactsbysaifullah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    // DBHelper constructor
    public DBHelper(Context context) {
        super(context, "ContactsData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create a table on create
        String command = "CREATE TABLE IF NOT EXISTS ContactDetails(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "number TEXT," +
                "sex TEXT)";
        try {
            db.execSQL(command);
        } catch (Exception e) {
            // On exception found
            Log.e("DBHelper", "onCreate: Exception found", e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists ContactDetails");
    }
    public Boolean insertContactData(Contact contact){
        try {
            SQLiteDatabase db = this.getWritableDatabase(); // get the writable database

            ContentValues contentValues = new ContentValues(); // declare and initialize a contact value to store before inserting into database
            contentValues.put("name", contact.getName()); // add name
            contentValues.put("number", contact.getNumber()); // add number
            contentValues.put("sex", contact.getSex()); // add sex

            long result = db.insert("ContactDetails", null, contentValues); // insert the data into database

            if (result != -1) {
                // Insert successful
                Log.i("DBHelper", "insertContactData: Successful! Inserted row with name: " + contact.getName() + ", number: " + contact.getNumber() + ", sex: " + contact.getSex());
                return true;
            } else {
                // Insert failed
                Log.e("DBHelper", "insertContactData: Failed to insert data! SQL statement: " + db.compileStatement("INSERT INTO ContactDetails(name, number, sex) VALUES (?, ?, ?)").toString());
                return false;
            }
        } catch (Exception e){
            // Exception found
            Log.e("DBHelper", "insertContactData: Failed! ", e);
            return false;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase(); // get readable database
        return db.rawQuery("SELECT * FROM ContactDetails", null); // get and return all the records from database
    }
}
