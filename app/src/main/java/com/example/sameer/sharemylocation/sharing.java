package com.example.sameer.sharemylocation;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.sameer.sharemylocation.R.id.listView;

/**
 * Created by sameer on 6/6/2017.
 */

public class sharing extends AppCompatActivity implements View.OnClickListener{
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    private ProgressBar spinner;
    public static Double currentLattitude, currentLongitude;
    public static int done = 0;
    private BroadcastReceiver broadcastReceiver;
    public int i = 0, j = 0, k = 0;
    public LatLng[] latlng = new LatLng[1000];
    public String[] addressname = new String[1000];
    public String[] tags = new String[1000];
    public static TextView add, addLoc;
    public static TextView lat1, lon1;
    ListView lv;
    Context context;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private GoogleApiClient googleApiClient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing);
        dbhelper = new Dbloc(sharing.this);
        db = dbhelper.getReadableDatabase();

        spinner = (ProgressBar)findViewById(R.id.progressBar2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner.setVisibility(View.GONE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

                add = (TextView) findViewById(R.id.add);
        lat1 = (TextView) findViewById(R.id.lat);
        lon1 = (TextView) findViewById(R.id.lon);
        addLoc = (TextView) findViewById(R.id.addloc);
        addLoc.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addloc) {/*
            LatLng a = new LatLng(GPS_Service.latLng.latitude, GPS_Service.latLng.longitude);
            latlng[k] = a;
            k++;

*/
            TextView blah=(TextView)findViewById(R.id.usertag);
            //tags[j]=j+1+".  "+blah.getText().toString();
            if(blah.getText().toString().equals("")){
                Toast.makeText(this, "TAG not mentioned", Toast.LENGTH_SHORT).show();
            }
            else {
                String dlat, dlon, dtag;
                dlat = "" + currentLattitude;
                dlon = "" + currentLongitude;
                dtag = blah.getText().toString();
                ContentValues values = new ContentValues();
                values.put(Dbloc.tags, dtag);
                values.put(Dbloc.lat, dlat);
                values.put(Dbloc.lon, dlon);

                long id = db.insert(Dbloc.table_locations, null, values);
                Toast.makeText(this, "location added", Toast.LENGTH_SHORT).show();
                j++;

                add.setText("");
                lat1.setText("");
                lon1.setText("");

                blah.setText("");
            }

        }
    }



    private void getMyLocationAddress() {

        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
        List<android.location.Address> addresses = null;
        try {

            //Place your latitude and longitude

            try {
                Log.d("loc",""+currentLattitude+"  "+currentLongitude);
                addresses = geocoder.getFromLocation(currentLattitude,currentLongitude,1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses != null) {
                spinner.setVisibility(View.GONE);
                android.location.Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                String result = sb.toString();
                add.setText(result);
            }

            else {
                spinner.setVisibility(View.GONE);
                add.setText("No location found..!");
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }

    }

    private void called() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.list);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        ArrayList<String> aa = new ArrayList<String>();
        /*for (int i = 0; i < j; i++) {
            aa.add(tags[i]);
        }*/
        String ta="";
        int i=0;
        final Cursor cursor = db.rawQuery("select * from " + Dbloc.table_locations, null);
        if (cursor.moveToFirst()) {
            do {
                ta=cursor.getString(1);
               // ta=i+".  "+ta;
                aa.add(ta);

            } while (cursor.moveToNext());
        }


        ArrayAdapter<String> adpat = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aa);

        listView.setAdapter(adpat);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position,
                                    final long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(sharing.this);
                builder.setTitle("Share your location");
                builder.setMessage("Are You Sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String quer="SELECT latitude,longitude from " + Dbloc.table_locations
                                + " WHERE tags='"+((TextView)view).getText().toString()+"'" ;
                        Cursor c=db.rawQuery(quer,null);
                        if (c != null) {
                            if (c.getCount() > 0) {
                                c.moveToFirst();
                                String send_lat = c.getString(c.getColumnIndex(Dbloc.lat));
                                String send_lon=c.getString(c.getColumnIndex(Dbloc.lon));
                                String uri = "http://maps.google.com/maps?saddr=" +send_lat+","+send_lon;

                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String ShareSub = "Here is my location";
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                            }
                        }

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
        });

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int dialogWidth = (int) (displayMetrics.widthPixels * 0.85);
        int dialogHeight = (int) (displayMetrics.heightPixels * 0.85);
        dialog.getWindow().setLayout(dialogWidth, dialogHeight);
        dialog.show();


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myLoc:
                done = 0;
                Log.e("presed", "pressed");
                spinner.setVisibility(View.VISIBLE);
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);

                Log.e("hee","h3");

                break;
            case R.id.locations:
                called();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                    stopService(i);

                    lat1.setText(currentLattitude+"");
                        lon1.setText(currentLongitude+"");

                    getMyLocationAddress();


                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }
}
