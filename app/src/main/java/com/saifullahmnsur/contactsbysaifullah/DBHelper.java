package com.saifullahmnsur.contactsbysaifullah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper;
    public static DBHelper getInstance(Context context){
        if( dbHelper == null ){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }
    public DBHelper(Context context) {
        super(context, "ContactsData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = "CREATE TABLE ContactDetails(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "number TEXT," +
                "sex TEXT)";
        try {
            db.execSQL(command);
        } catch (Exception e) {
            Log.e("DBHelper", "onCreate: Exception found", e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists ContactDetails");
    }
    public Boolean insertContactData(String name, String number, String sex){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("number", number);
            contentValues.put("sex", sex);

            long result = db.insert("ContactDetails", null, contentValues);

            if (result != -1) {
                // Insert successful
                Log.i("DBHelper", "insertContactData: Successful! Inserted row with name: " + name + ", number: " + number + ", sex: " + sex);
                return true;
            } else {
                // Insert failed
                Log.e("DBHelper", "insertContactData: Failed to insert data! SQL statement: " + db.compileStatement("INSERT INTO ContactDetails(name, number, sex) VALUES (?, ?, ?)").toString());
                return false;
            }
        } catch (Exception e){
            Log.e("DBHelper", "insertContactData: Failed! ", e);
            return false;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from ContactDetails", null);
    }
}
