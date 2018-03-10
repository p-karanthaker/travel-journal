package me.karanthaker.traveljournal;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

public class EditPlace extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private Place editPlace;

    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        Intent intent = getIntent();
        final int placeId = intent.getIntExtra("PLACE_ID", -1);

        final TextView activityTitle = (TextView) findViewById(R.id.holidayActivityTitle);
        activityTitle.setText(R.string.edit_place);

        final PlaceViewModel placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        editPlace = placeViewModel.getPlaceById(placeId);

        final EditText placeName = (EditText) findViewById(R.id.placeName);
        date = (TextView) findViewById(R.id.placeDate);

        placeName.setText(editPlace.getName());

        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-YYYY", Locale.UK);
        date.setText(f.format(editPlace.getDate()));

        Button setHolidayStart = (Button) findViewById(R.id.setHolidayStart);
        setHolidayStart.setOnClickListener(listener);

        Button confirmEdit = (Button) findViewById(R.id.add_place);
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = placeName.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(view, "Place name required!", Snackbar.LENGTH_LONG).show();
                } else {
                    editPlace.setName(name);
                    placeViewModel.update(editPlace);
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

                    updateLabel(EditPlace.this.date);
                    editPlace.setDate(calendar.getTime());
                }
            };

            new DatePickerDialog(EditPlace.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    };
}
