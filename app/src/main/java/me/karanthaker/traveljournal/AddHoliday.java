package me.karanthaker.traveljournal;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter.HolidayListAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.HolidayViewModel;

public class AddHoliday extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private final Holiday holiday = new Holiday();

    private TextView holidayStart;
    private TextView holidayEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);


        final EditText holidayName = (EditText) findViewById(R.id.holidayName);
        holidayStart  = (TextView) findViewById(R.id.holidayStart);
        holidayEnd = (TextView) findViewById(R.id.holidayEnd);

        Button setHolidayStart = (Button) findViewById(R.id.setHolidayStart);
        Button setHolidayEnd = (Button) findViewById(R.id.setHolidayEnd);
        setHolidayStart.setOnClickListener(listener);
        setHolidayEnd.setOnClickListener(listener);

        Button confirm = (Button) findViewById(R.id.add_holiday);

        final HolidayViewModel holidayViewModel = ViewModelProviders.of(this).get(HolidayViewModel.class);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String name = holidayName.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(view, "Holiday name required!", Snackbar.LENGTH_LONG).show();
                } else if (!validDates(holiday.getStartDate(), holiday.getEndDate())) {
                    Snackbar.make(view, "Holiday end date cannot occur before start date!", Snackbar.LENGTH_LONG).show();
                } else {
                    holiday.setName(name);
                    holidayViewModel.insert(holiday);
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
                    }
                    else if (view.getId() == R.id.setHolidayEnd) {
                        updateLabel(holidayEnd);
                        holiday.setEndDate(calendar.getTime());
                    }
                }
            };

            new DatePickerDialog(AddHoliday.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    };
}
