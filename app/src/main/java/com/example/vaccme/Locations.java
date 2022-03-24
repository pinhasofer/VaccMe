package com.example.vaccme;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationListenerCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.vaccme.databinding.ActivityLocationsBinding;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.Help;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//most of this is auto generated so ill comment where I code
public class Locations extends FragmentActivity implements OnMapReadyCallback {


    private static final int PERMISSION_FINE_LOCATION = 99; //permission number for Result method
    private static final int CAMERA_ZOOM_INT = 15;
    private GoogleMap mMap;
    private ActivityLocationsBinding binding;

    //my vars
    private FusedLocationProviderClient fusedLocationClient;
    LatLng usersLoc;
    Location usersLocation;
    String[] permissionsArray = new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};  //array for permissions

    private Marker locationMarker;
    FloatingActionButton fab;   //back button
    private DocumentSnapshot document;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialise vars
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //auto generated
        binding = ActivityLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        //check permissions
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            ActivityCompat.requestPermissions(Locations.this, permissionsArray, 23);
//
//        }
//        //we need to get the users location
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // save the location
//                            usersLocation = location;
//                            usersLoc = new LatLng(location.getLatitude(),location.getLongitude());
//
//                            //String cityName = getAddressString(usersLoc);
//                            String cityName = getCityString(usersLoc);
//                            addMarkerAndMove(usersLoc, locationMarker,  "מיקומינו: "+cityName);
//                            //List<GeoPoint> gpLst;
//                            //test list
//                            inRadiusOfUser(usersLoc);
//
//
//                        }else{
//                            PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
//                            Toast.makeText(getApplicationContext(), "הוחזר NULL", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_FINE_LOCATION:
            case 23:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){


                }
        }
    }

    /// test


    ///////



    //auto generated

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //check permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(Locations.this, permissionsArray, 23);

        }
        //we need to get the users location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // save the location
                            usersLocation = location;
                            usersLoc = new LatLng(location.getLatitude(),location.getLongitude());

                            //String cityName = getAddressString(usersLoc);
                            String cityName = getCityString(usersLoc);
                            addMarkerAndMove(usersLoc, locationMarker,  "מיקומינו: "+cityName);
                            //List<GeoPoint> gpLst;
                            //test list
                            inRadiusOfUser(usersLoc);


                        }else{
                            PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
                            Toast.makeText(getApplicationContext(), "הוחזר NULL", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    //permissions stackoverflow https://stackoverflow.com/questions/40142331/how-to-request-location-permission-at-runtime

    //function for pressing the floating back button
    public void returnToDash(View view){
        onBackPressed();
//        startActivity(new Intent(getApplicationContext(), Dashboard.class));
//        finish();
    }


    //geo functions
    public String getAddressString(LatLng latlng){
        //test
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getAddressLine(0);

        return cityName;
    }

    public String getCityString(LatLng latlng){
        //test
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(usersLoc.latitude,usersLoc.longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert addresses != null;
        return addresses.get(0).getLocality();
    }

    public LatLng convertGeoPointToLatLng(GeoPoint gp){
        if(gp == null)
            return null;

        return new LatLng(gp.getLatitude(),gp.getLongitude());
    }

    public void inRadiusOfUser(LatLng usersLoc){
        final int KM = 20;
        final GeoLocation center = new GeoLocation(usersLoc.latitude, usersLoc.longitude);
        final double radiusInM = KM * 1000;

        // Each item in 'bounds' represents a startAt/endAt pair. We have to issue
        // a separate query for each pair. There can be up to 9 pairs of bounds
        // depending on overlap, but in most cases there are 4.
        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        for (GeoQueryBounds b : bounds) {
            Query q = Helper.db.collection(Helper.SITES_COL)
                    .orderBy("geoHash")
                    .startAt(b.startHash)
                    .endAt(b.endHash);

            tasks.add(q.get());
        }

        // Collect all the query results together into a single list
        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> t) {
                        List<DocumentSnapshot> matchingDocs = new ArrayList<>();

                        for (Task<QuerySnapshot> task : tasks) {
                            QuerySnapshot snap = task.getResult();
                            for (DocumentSnapshot doc : snap.getDocuments()) {
                                GeoPoint gp = doc.getGeoPoint("LatLng");
                                Log.d(Helper.TAG, "DocumentSnapshot: gp"  + gp);


                                // We have to filter out a few false positives due to GeoHash
                                // accuracy, but most will match
                                assert gp != null;
                                GeoLocation docLocation = new GeoLocation(gp.getLatitude(), gp.getLongitude());
                                Log.d(Helper.TAG, "docLocation: gp"  + docLocation);
                                double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                                if (distanceInM <= radiusInM) {
                                    matchingDocs.add(doc);
                                }
                            }
                        }

                        // use the array
                        for(DocumentSnapshot doc: matchingDocs){
                            GeoPoint gp = (GeoPoint) doc.get("LatLng");
                            LatLng ltlng = convertGeoPointToLatLng(gp);
                            addMarker(ltlng, getAddressString(ltlng));

                        }

                    }
                });


    }

    public void addMarker(LatLng usersLoc, String msg) {
        //for adding marker that we dont need to use
        mMap.addMarker(new MarkerOptions().position(usersLoc).title(msg));
    }

    public void addMarkerAndMove(LatLng usersLoc, String msg) {
        //for adding marker that we dont need to use
        mMap.addMarker(new MarkerOptions().position(usersLoc).title(msg));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usersLoc,CAMERA_ZOOM_INT));
    }

    private void addMarkerAndMove(LatLng usersLoc, Marker marker, String msg) {
        //for adding marker that we need to use
        marker = mMap.addMarker(new MarkerOptions().position(usersLoc).title(msg));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usersLoc,CAMERA_ZOOM_INT));
    }

}
