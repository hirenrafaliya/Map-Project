package com.akx.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kotlin.collections.MapAccessorsKt;

public class HomeActivity extends AppCompatActivity {

    private String TAG="TAGER";

    Button btnGet,btnMap;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnGet=findViewById(R.id.btnGet);
        btnMap=findViewById(R.id.btnMap);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MapsActivity.class));
            }
        });

        List<Location> locations=new ArrayList<>();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d(TAG, "onLocationChanged: "+location.getLatitude());
            }
        };

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        });
    }

    private void getAddress(Context context,Location location){
        Geocoder geocoder=new Geocoder(context,Locale.getDefault());

        try {
            List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            Address address=addressList.get(0);
            String adminArea=address.getAdminArea();
            String locality=address.getLocality();
            String country=address.getCountryName();
            String line=address.getAddressLine(0);
            String line2=address.getPostalCode();

            Log.d(TAG, "adminArea:"+adminArea);
            Log.d(TAG, "locality:"+locality);
            Log.d(TAG, "country:"+country);
            Log.d(TAG, "line:"+line);
            Log.d(TAG, "pin:"+line2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}