package me.karanthaker.traveljournal;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.PlaceViewModel;

public class AddPlace extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private final Place place = new Place();

    private TextView placeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);


        final EditText placeName = (EditText) findViewById(R.id.placeName);
        placeDate = (TextView) findViewById(R.id.placeDate);

        Button setHolidayStart = (Button) findViewById(R.id.setHolidayStart);
        setHolidayStart.setOnClickListener(listener);

        Button confirm = (Button) findViewById(R.id.add_place);

        final PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String name = placeName.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(view, "Place name required!", Snackbar.LENGTH_LONG).show();
                } else {
                    place.setName(name);
                    placeViewModel.insert(place);
                    finish();
                }
            }
        });

    }

    private void updateLabel(TextView textView) {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
        textView.setText(f.format(calendar.getTime()));
    }


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
