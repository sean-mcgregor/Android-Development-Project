package com.androiddev;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    boolean walkStarted;
    static List<Address> addresses;
    static List<double[]> walkData = new List<double[]>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<double[]> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(double[] doubles) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends double[]> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends double[]> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public double[] get(int index) {
            return new double[0];
        }

        @Override
        public double[] set(int index, double[] element) {
            return new double[0];
        }

        @Override
        public void add(int index, double[] element) {

        }

        @Override
        public double[] remove(int index) {
            return new double[0];
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<double[]> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<double[]> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<double[]> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

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

                walkStarted = true;

                getLocation();
                System.out.println(walkData.size());
            }
        });

        endWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Walk finished!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


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
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            double[] latLong = {latitude, longitude};
                            Collections.addAll(walkData, latLong);
                            System.out.println("latLong is " + latLong.toString());
                            System.out.println(walkData.size());

                        } catch (IOException e){

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