package me.karanthaker.traveljournal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EditHoliday extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);

        final TextView activityTitle = (TextView) findViewById(R.id.holidayActivityTitle);
        activityTitle.setText(R.string.edit_holiday);

    }
}
