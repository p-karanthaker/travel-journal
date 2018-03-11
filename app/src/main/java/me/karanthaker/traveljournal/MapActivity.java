package me.karanthaker.traveljournal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PlaceDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.database.AppDatabase;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private List<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                places = AppDatabase.getDatabase(contexts[0]).placeDao().getAll();
                return null;
            }
        }.execute(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        GeoDataClient geoDataClient = Places.getGeoDataClient(this, null);

        for (Place place : places) {
            if (place.getGooglePlaceId() != null) {
                geoDataClient.getPlaceById(place.getGooglePlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                        PlaceBufferResponse places = task.getResult();
                        com.google.android.gms.location.places.Place myPlace = places.get(0);

                        // Add marker
                        LatLng coords = myPlace.getLatLng();
                        final Marker marker = googleMap.addMarker(new MarkerOptions().position(coords).title(place.getName().toString()));
                        marker.setTag(place.getId());
                        places.release();
                    }
                });
            }
        }

        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int placeId = (int) marker.getTag();

        Intent intent = new Intent(this, OpenPlace.class);
        intent.putExtra("PLACE_ID", placeId);
        startActivity(intent);
        return false;
    }
}
