package erickkim.dtu.dk.affaldsprojekt;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        float zoomLevel = 13.0f; //This goes up to 21
        LatLng zoomPosition = new LatLng(55.230006, 11.753839);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomPosition, zoomLevel));

    }

    private void getAllHubNames() {
        FirebaseDatabase.getInstance().getReference().child("hubs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String hubName = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hubName = (String) snapshot.getKey();
                    addHubToMap(hubName);
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
                String hubStatus = "";
                int errors = 0;
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
                    if (snapshot.getKey().equals("towers")) {
                        // TODO: Make towers indepedent of what is actually in the hub to allow for more tower types.
                        if (!snapshot.child("resttower").getValue().toString().equals("ok")) {
                            errors++;
                            hubStatus += "RestT Fail. ";
                        }
                        if (!snapshot.child("plastictower").getValue().toString().equals("ok")) {
                            errors++;
                            hubStatus += "PlasticT Fail. ";
                        }
                        if (!snapshot.child("metaltower").getValue().toString().equals("ok")) {
                            hubStatus += "MetalT Fail. ";
                            errors++;
                        }
                        if (!snapshot.child("biotower").getValue().toString().equals("ok")) {
                            hubStatus += "BioT Fail. ";
                            errors++;
                        }
                        if (hubStatus.isEmpty())
                            hubStatus += "Running. ";
                    }
                }

                // Create the marker and set the color depending on hub status. Is slightly hardcoded.
                LatLng hub = new LatLng(coordinates[0], coordinates[1]);
                if (hubName.equals("")) {
                    mMap.addMarker(new MarkerOptions().position(hub).title("DebugFailedToCatchName"));

                    // Create the marker but with different colours depending on how many errors are present. Hardcoded limit to 4.
                } else {
                    switch (errors) {
                        case 0:
                            mMap.addMarker(new MarkerOptions().position(hub).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(hubName).snippet("Status: " + hubStatus));
                            break;
                        case 1:
                        case 2:
                        case 3:
                            mMap.addMarker(new MarkerOptions().position(hub).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title(hubName).snippet("Status: " + hubStatus));
                            break;
                        case 4:
                            mMap.addMarker(new MarkerOptions().position(hub).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(hubName).snippet("Status: " + hubStatus));
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
