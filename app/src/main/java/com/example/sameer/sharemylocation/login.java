package com.example.sameer.sharemylocation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    EditText pswd, usrusr;
    TextView sup, lin,fpass,skip;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new DatabaseHandler(this);
        db = dbhelper.getReadableDatabase();

        fpass=(TextView)findViewById(R.id.fpass);
        skip=(TextView)findViewById(R.id.skip);
        lin = (TextView) findViewById(R.id.lin);
        usrusr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
        sup = (TextView) findViewById(R.id.sup);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        lin.setTypeface(custom_font1);
        sup.setTypeface(custom_font);
        fpass.setTypeface(custom_font);
        skip.setTypeface(custom_font);
        usrusr.setTypeface(custom_font);
        pswd.setTypeface(custom_font);
        lin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String user = usrusr.getText().toString();
                String pass = pswd.getText().toString();
                Log.e("tag",user+"  "+pass);
                cursor = db.rawQuery("SELECT *FROM " + DatabaseHandler.table_name + " WHERE " + DatabaseHandler.COLUMN_USER_EMAIL + "=? AND " + DatabaseHandler.COLUMN_USER_PASSWORD + "=?", new String[]{user, pass});
                if (cursor != null) {
                    if (cursor.getCount() > 0) {

                        cursor.moveToFirst();
                        //Retrieving User FullName and Email after successfull login and passing to LoginSucessActivity
                        String _fuser = cursor.getString(cursor.getColumnIndex(DatabaseHandler.username));
                        Log.e("hit",_fuser);
                        Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this,sharing.class);


                        startActivity(intent);


                        finish();
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Username or Password is wrong.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this,sharing.class);
                startActivity(i);
                finish();
            }
        });
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(login.this, signup.class);
                startActivity(it);
                overridePendingTransition( R.anim.slide_up, R.anim.slide_down );
                finish();
            }
        });
    } @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



}