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
import java.util.Date;
import java.util.Locale;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.HolidayViewModel;

public class AddHoliday extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private Holiday holiday = new Holiday();

    private TextView holidayStart;
    private TextView holidayEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);

        final boolean newItem = getIntent().getBooleanExtra("NEW_ITEM", true);
        final int holidayId = getIntent().getIntExtra("HOLIDAY_ID", -1);

        final HolidayViewModel holidayViewModel = ViewModelProviders.of(this).get(HolidayViewModel.class);

        if (holidayId > -1) {
            final TextView activityTitle = (TextView) findViewById(R.id.holidayActivityTitle);
            activityTitle.setText(R.string.edit_holiday);
            holiday = holidayViewModel.getHolidayById(holidayId);
        }


        final EditText holidayName = findViewById(R.id.holidayName);
        final EditText additionalNotes = findViewById(R.id.holidayNotes);
        holidayStart = findViewById(R.id.holidayStart);
        holidayEnd = findViewById(R.id.holidayEnd);

        Button setHolidayStart = findViewById(R.id.setHolidayStart);
        Button setHolidayEnd = findViewById(R.id.setHolidayEnd);
        setHolidayStart.setOnClickListener(listener);
        setHolidayEnd.setOnClickListener(listener);


        if (!newItem) {
            holidayName.setText(holiday.getName());
            additionalNotes.setText(holiday.getNotes());
            SimpleDateFormat f = new SimpleDateFormat("dd-MMM-YYYY", Locale.UK);
            holidayStart.setText(f.format(holiday.getStartDate()));
            holidayEnd.setText(f.format(holiday.getEndDate()));
        }

        Button confirm = findViewById(R.id.add_holiday);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String name = holidayName.getText().toString();
                final String notes = additionalNotes.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(view, "Holiday name required!", Snackbar.LENGTH_LONG).show();
                } else if (!validDates(holiday.getStartDate(), holiday.getEndDate())) {
                    Snackbar.make(view, "Holiday end date cannot occur before start date!", Snackbar.LENGTH_LONG).show();
                } else {
                    holiday.setName(name);
                    if (!notes.isEmpty())
                        holiday.setNotes(notes);
                    if (newItem)
                        holidayViewModel.insert(holiday);
                    else
                        holidayViewModel.update(holiday);
                    finish();
                }
            }
        });

    }

    private boolean validDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return false;
        } else if (startDate.compareTo(endDate) > 0) {
            return false;
        }
        return true;
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

                    if (view.getId() == R.id.setHolidayStart) {
                        updateLabel(holidayStart);
                        holiday.setStartDate(calendar.getTime());
                    } else if (view.getId() == R.id.setHolidayEnd) {
                        updateLabel(holidayEnd);
                        holiday.setEndDate(calendar.getTime());
                    }
                }
            };

            new DatePickerDialog(AddHoliday.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    };
}
