package com.androiddev;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime currentTime;
    FusedLocationProviderClient fusedLocationProviderClient;
    static boolean walkStarted;
    static List<Address> addresses;
    static Logs startPosition;
    static Logs endPosition;
    static Logs[] logList = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Walk Tracking Application");

        FloatingActionButton startWalk = findViewById(R.id.startWalk);
        FloatingActionButton endWalk = findViewById(R.id.endWalk);
        System.out.println("Hello World");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        startWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Walk started!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                getLocation();
                //startPosition = logList.get(0);
                walkStarted = true;
            }
        });

        endWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Walk finished!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                getLocation();
                endPosition = logList[logList.length - 1];
                walkStarted = false;

            }
        });
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {

                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String addressLineString = addresses.get(0).getAddressLine(0);
                            currentTime = LocalDateTime.now();
                            Logs temporaryLog = new Logs(currentTime, location.getLatitude(), location.getLongitude(), addressLineString);
                            Logs[] tempArr = Arrays.copyOf(logList, logList.length + 1);
                            System.out.println(Arrays.toString(tempArr) + " is tempArr");
                            System.out.println(Arrays.toString(logList) + " is logList");

                            tempArr[tempArr.length - 1] = temporaryLog;
                            logList = Arrays.copyOf(tempArr, tempArr.length);

                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    public void HaversineDistance(double lat1, double long1, double lat2, double long2) {
        // TODO Auto-generated method stub
        final double R = 6371.0088; // Radius of the earth
        double latDistance = toRad(lat2-lat1);
        double longDistance = toRad(long2-long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;

        System.out.println("The distance between two lat and long is::" + distance);

    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}