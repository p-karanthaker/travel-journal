package me.karanthaker.traveljournal;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.HolidayViewModel;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.PlaceViewModel;

public class AddPlace extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private Place place = new Place();
    private List<String> travelCompanions = new ArrayList<>();
    private TextView travelCompanionsText;
    private TextView placeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);


        final boolean newItem = getIntent().getBooleanExtra("NEW_ITEM", true);
        final int placeId = getIntent().getIntExtra("PLACE_ID", -1);

        final PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);


        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        if (placeId > -1) {
            final TextView activityTitle = findViewById(R.id.holidayActivityTitle);
            activityTitle.setText(R.string.edit_place);
            place = placeViewModel.getPlaceById(placeId);
        }

        final EditText placeName = findViewById(R.id.placeName);
        final EditText additionalNotes = findViewById(R.id.placeNotes);
        placeDate = findViewById(R.id.placeDate);

        travelCompanionsText = findViewById(R.id.travelCompanions);
        if (place.getCompanions() != null) {
            this.travelCompanions.addAll(Arrays.asList(place.getCompanions().split(",")));
            travelCompanionsText.setText(place.getCompanions());
        }

        Button setHolidayStart = findViewById(R.id.setHolidayStart);
        setHolidayStart.setOnClickListener(listener);
        Button addTravelCompanion = findViewById(R.id.addTravelCompanion);
        addTravelCompanion.setOnClickListener(companionListener);

        if (!newItem) {
            placeName.setText(place.getName());
            additionalNotes.setText(place.getNotes());
            SimpleDateFormat f = new SimpleDateFormat("dd-MMM-YYYY", Locale.UK);
            placeDate.setText(f.format(place.getDate()));
            GeoDataClient geoDataClient = Places.getGeoDataClient(this, null);
            if (place.getGooglePlaceId() != null) {
                geoDataClient.getPlaceById(place.getGooglePlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                        PlaceBufferResponse places = task.getResult();
                        com.google.android.gms.location.places.Place myPlace = places.get(0);
                        autocompleteFragment.setText(myPlace.getName());
                        places.release();
                    }
                });
            }
        }

        Button confirm = findViewById(R.id.add_place);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String name = placeName.getText().toString();
                final String notes = additionalNotes.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(view, "Place name required!", Snackbar.LENGTH_LONG).show();
                } else {
                    place.setName(name);
                    if (!notes.isEmpty())
                        place.setNotes(notes);
                    if (!travelCompanions.isEmpty())
                        place.setCompanions(String.join(",", travelCompanions));
                    if (newItem)
                        placeViewModel.insert(place);
                    else
                        placeViewModel.update(place);
                    finish();
                }
            }
        });


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(com.google.android.gms.location.places.Place gPlace) {
                Log.i("INFO", "Place: " + gPlace.getName());
                place.setGooglePlaceId(gPlace.getId());
            }

            @Override
            public void onError(Status status) {
                Log.i("ERROR", "An error occurred: " + status);
            }
        });

    }

    private void updateLabel(TextView textView) {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
        textView.setText(f.format(calendar.getTime()));
    }

    View.OnClickListener companionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText newCompanion = findViewById(R.id.newCompanion);
            if (!newCompanion.getText().toString().isEmpty()) {
                travelCompanions.add(newCompanion.getText().toString());
                travelCompanionsText.setText(String.join(",", travelCompanions));
            }
        }
    };


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    updateLabel(placeDate);
                    place.setDate(calendar.getTime());
                }
            };

            new DatePickerDialog(AddPlace.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    };
}
