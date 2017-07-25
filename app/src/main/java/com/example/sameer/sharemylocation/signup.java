package com.example.sameer.sharemylocation;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity
{
    EditText mail,mophone,pswd,usrusr;
    TextView lin,sup;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        openHelper = new DatabaseHandler(this);
        db = openHelper.getReadableDatabase();

        sup = (TextView) findViewById(R.id.sup);
        lin = (TextView) findViewById(R.id.lin);
        usrusr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
        mail = (EditText) findViewById(R.id.mail);
        mophone = (EditText) findViewById(R.id.mobphone);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
        mophone.setTypeface(custom_font);
        sup.setTypeface(custom_font1);
        pswd.setTypeface(custom_font);
        lin.setTypeface(custom_font);
        usrusr.setTypeface(custom_font);
        mail.setTypeface(custom_font);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mail.getText().toString();
                String user=usrusr.getText().toString();
                String pass=pswd.getText().toString();
                String phone=mophone.getText().toString();
                ContentValues values = new ContentValues();
                values.put(DatabaseHandler.username,user);
                values.put(DatabaseHandler.COLUMN_USER_EMAIL,email);
                values.put(DatabaseHandler.COLUMN_USER_PASSWORD,pass);
                values.put(DatabaseHandler.phone,phone);
                long id = db.insert(DatabaseHandler.table_name,null,values);
                Toast.makeText(signup.this,"account created",Toast.LENGTH_SHORT).show();
                mail.setText("");
                usrusr.setText("");
                pswd.setText("");
                mophone.setText("");
            }
        });
        lin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(signup.this,login.class);
                startActivity(it);
                overridePendingTransition( R.anim.slide_up, R.anim.slide_down );
                finish();
            }
        });
    }
    @Override
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
