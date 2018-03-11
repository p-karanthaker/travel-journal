package me.karanthaker.traveljournal;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.helpers.ImageAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.HolidayViewModel;

public class OpenHoliday extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);

        final int holidayId = getIntent().getIntExtra("HOLIDAY_ID", -1);
        final HolidayViewModel holidayViewModel = ViewModelProviders.of(this).get(HolidayViewModel.class);
        final Holiday holiday = holidayViewModel.getHolidayById(holidayId);

        TextView date = findViewById(R.id.itemDate);
        final SimpleDateFormat f = new SimpleDateFormat("dd MMM YYYY", Locale.UK);
        String startDate = f.format(holiday.getStartDate());
        String endDate = f.format(holiday.getEndDate());
        date.setText(String.format("%s - %s", startDate, endDate));

        TextView notes = findViewById(R.id.itemNotes);
        if (holiday.getNotes() != null)
            notes.setText(holiday.getNotes());

        getSupportActionBar().setTitle(String.format("#%d %s", holiday.getId(), holiday.getName()));

        GridView gridView = findViewById(R.id.gridView);
        final ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(OpenHoliday.this, "" + i, Toast.LENGTH_SHORT).show();

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
