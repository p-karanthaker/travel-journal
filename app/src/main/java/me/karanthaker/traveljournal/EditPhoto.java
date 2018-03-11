package me.karanthaker.traveljournal;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

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
