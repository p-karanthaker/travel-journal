package me.karanthaker.traveljournal;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Locale;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.helpers.ImageAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.PlaceViewModel;

public class OpenPlace extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);

        final int placeId = getIntent().getIntExtra("PLACE_ID", -1);
        final PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        final Place place = placeViewModel.getPlaceById(placeId);

        TextView date = findViewById(R.id.itemDate);
        final SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
        date.setText("Visited: " + f.format(place.getDate()));

        final TextView location = findViewById(R.id.location);
        if (place.getGooglePlaceId() != null) {
            GeoDataClient geoDataClient = Places.getGeoDataClient(this, null);
            geoDataClient.getPlaceById(place.getGooglePlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                    PlaceBufferResponse places = task.getResult();
                    com.google.android.gms.location.places.Place myPlace = places.get(0);
                    location.setText("Location: " + myPlace.getName());
                    places.release();
                }
            });
        }

        TextView companions = findViewById(R.id.travelCompanions);
        if (place.getCompanions() != null)
            companions.setText("Travel Companions: " + place.getCompanions());

        TextView notes = findViewById(R.id.itemNotes);
        if (place.getNotes() != null)
            notes.setText("Notes: " + place.getNotes());

        getSupportActionBar().setTitle(String.format("#%d %s", place.getId(), place.getName()));

        GridView gridView = findViewById(R.id.gridView);
        final ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(OpenPlace.this, "" + i, Toast.LENGTH_SHORT).show();

                final ImageView expandedImageView = findViewById(R.id.expanded_image);
                expandedImageView.setImageResource((int) imageAdapter.getItem(i));
                expandedImageView.setVisibility(View.VISIBLE);

                expandedImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expandedImageView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
