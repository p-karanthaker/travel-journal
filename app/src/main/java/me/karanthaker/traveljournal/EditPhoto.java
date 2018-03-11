package me.karanthaker.traveljournal;

import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.HolidayRepository;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.PlaceRepository;

public class EditPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        String imagePath = getIntent().getStringExtra("PHOTO_PATH");

        final ImageView imageView = findViewById(R.id.photo_to_edit);
        imageView.setImageURI(Uri.parse(imagePath));
    }
}
