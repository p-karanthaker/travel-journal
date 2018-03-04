package me.karanthaker.traveljournal;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.HolidayDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.HolidayRepository;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.HolidayViewModel;

public class EditHoliday extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private Holiday editHoliday;

    private TextView holidayStartDate;
    private TextView holidayEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);

        Intent intent = getIntent();
        final int holidayId = intent.getIntExtra("HOLIDAY_ID", -1);

        final TextView activityTitle = (TextView) findViewById(R.id.holidayActivityTitle);
        activityTitle.setText(R.string.edit_holiday);

        final HolidayViewModel holidayViewModel = ViewModelProviders.of(this).get(HolidayViewModel.class);
        editHoliday = holidayViewModel.getHolidayById(holidayId);

        final EditText holidayName = (EditText) findViewById(R.id.holidayName);
        holidayStartDate = (TextView) findViewById(R.id.holidayStart);
        holidayEndDate = (TextView) findViewById(R.id.holidayEnd);

        holidayName.setText(editHoliday.getName());

        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-YYYY", Locale.UK);
        holidayStartDate.setText(f.format(editHoliday.getStartDate()));
        holidayEndDate.setText(f.format(editHoliday.getEndDate()));

        Button setHolidayStart = (Button) findViewById(R.id.setHolidayStart);
        Button setHolidayEnd = (Button) findViewById(R.id.setHolidayEnd);
        setHolidayStart.setOnClickListener(listener);
        setHolidayEnd.setOnClickListener(listener);

        Button confirmEdit = (Button) findViewById(R.id.add_holiday);
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = holidayName.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(view, "Holiday name required!", Snackbar.LENGTH_LONG).show();
                } else if (!validDates(editHoliday.getStartDate(), editHoliday.getEndDate())) {
                    Snackbar.make(view, "Holiday end date cannot occur before start date!", Snackbar.LENGTH_LONG).show();
                } else {
                    editHoliday.setName(name);
                    holidayViewModel.update(editHoliday);
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
                        updateLabel(holidayStartDate);
                        editHoliday.setStartDate(calendar.getTime());
                    }
                    else if (view.getId() == R.id.setHolidayEnd) {
                        updateLabel(holidayEndDate);
                        editHoliday.setEndDate(calendar.getTime());
                    }
                }
            };

            new DatePickerDialog(EditHoliday.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    };
}
