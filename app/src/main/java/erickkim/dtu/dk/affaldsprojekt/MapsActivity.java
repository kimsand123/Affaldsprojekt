package erickkim.dtu.dk.affaldsprojekt;

import android.os.AsyncTask;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> allHubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


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

        /*
        class asyncLoadHubLocations extends AsyncTask {

            double coordinates[] = {0, 0};


            @Override
            protected Object doInBackground(Object[] objects) {
                coordinates = TEST_Database.getInstance().getCoordinates("hub1");
                // TODO: Add way to find all hubs?
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                LatLng hub = new LatLng(coordinates[0], coordinates[1]);
                mMap.addMarker(new MarkerOptions().position(hub).title("Testmarker in Læsø"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(hub));
            }

        }
        */
        getAllHubNames();

    }

    private void getAllHubNames() {
        FirebaseDatabase.getInstance().getReference().child("hubs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String hubName = "";
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hubName = (String) snapshot.getKey();
                    addHubToMap(hubName);
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addHubToMap(String hubName) {
        FirebaseDatabase.getInstance().getReference().child("hubs").child(hubName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double coordinates[] = {0.0, 0.0};
                String hubName = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.getKey().equals("hubname")) {
                        hubName = ((String) snapshot.getValue());
                    }
                    if (snapshot.getKey().equals("latitude")) {
                        coordinates[0] = (double) snapshot.getValue();
                    }
                    if (snapshot.getKey().equals("longitude")) {
                        coordinates[1] = (double) snapshot.getValue();
                    }
                }

                LatLng hub = new LatLng(coordinates[0], coordinates[1]);
                if (hubName.equals("")) {
                    mMap.addMarker(new MarkerOptions().position(hub).title("DebugFailedToCatchName"));
                } else {
                    mMap.addMarker(new MarkerOptions().position(hub).title(hubName));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
