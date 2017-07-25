package com.example.sameer.sharemylocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sameer on 6/8/2017.
 */

public class Dbloc extends SQLiteOpenHelper {


    public static final String table_locations="loc";
    public static final String lat="latitude";
    public static final String lon ="longitude";
    public static final String tags="tags";
    public static final String id="id";

    public static String create_locations="CREATE TABLE "+table_locations+" ("+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+tags+" TEXT,"
            + lat+" TEXT,"+lon+" TEXT)";

    public static final String name="name";
    public Dbloc(Context context) {
        super(context,name , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_locations);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + name);

        // Create tables again
        onCreate(db);

    }
}
