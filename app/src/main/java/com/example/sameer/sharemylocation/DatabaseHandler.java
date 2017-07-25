package com.example.sameer.sharemylocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sameer on 6/5/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
public static final String DB_name="ShareMyLocation.db";
    public static final String table_name="pass";
   public static  final int DB_version=1;
    public static final String username="user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String phone="phone";
    public static String CREATE_USER_TABLE = "CREATE TABLE " + table_name + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," +username+" TEXT,"+phone+" TEXT" + ")";

 // drop table sql query
    public String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + DB_name;

    public DatabaseHandler(Context context) {
        super(context, DB_name, null, DB_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.e("tables","created");
            db.execSQL(CREATE_USER_TABLE);

        }catch (Exception e){Log.e("err",e+"");}

    }
@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DB_name);

        // Create tables again
        onCreate(db);

    }

}
