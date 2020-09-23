package com.akx.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private String TAG="TAGER";

    private GoogleMap mMap;
    public static Location location;

    LatLng latLng1;
    LatLng latLng2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(latLng1==null){
                    latLng1=latLng;
                    String address=getAddress(MapsActivity.this,latLng);
                    MarkerOptions markerOptions=new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(address);
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
                else if(latLng2==null){
                    latLng2=latLng;
                    String address=getAddress(MapsActivity.this,latLng);
                    MarkerOptions markerOptions=new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(address);
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
                else if(latLng1!=null && latLng2!=null){
                    Toast.makeText(MapsActivity.this, ""+distance(latLng1.latitude,latLng1.longitude,latLng2.latitude,latLng2.longitude)+" Km", Toast.LENGTH_SHORT).show();
                    latLng1=null;
                    latLng2=null;
                }

            }
        });

    }

    private String getAddress(Context context, LatLng latLng){
        Geocoder geocoder=new Geocoder(context,Locale.getDefault());

        String line="";
        try {
            List<Address> addressList=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            Address address=addressList.get(0);
            line=address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}