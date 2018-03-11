package me.karanthaker.traveljournal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.database.AppDatabase;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.PhotoRepository;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AddFloatingActionButton fab = findViewById(R.id.fab);
        final FloatingActionsMenu fmenu = findViewById(R.id.fam);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fmenu.isExpanded())
                    fmenu.collapse();
                else
                    fmenu.expand();
            }
        });

        FloatingActionButton fabAddHol = findViewById(R.id.fab_add_holiday);
        FloatingActionButton fabAddPlace = findViewById(R.id.fab_add_place);
        FloatingActionButton fabAddPhoto = findViewById(R.id.fab_add_photo);

        View.OnClickListener fabListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                switch (view.getId()) {
                    case R.id.fab_add_holiday:
                        intent = new Intent(MainActivity.this, AddHoliday.class);
                        break;
                    case R.id.fab_add_place:
                        intent = new Intent(MainActivity.this, AddPlace.class);
                        break;
                    case R.id.fab_add_photo:
                        takePicture();
                        break;
                }
                fmenu.collapse();
                MainActivity.this.startActivity(intent);
            }
        };

        fabAddHol.setOnClickListener(fabListener);
        fabAddPlace.setOnClickListener(fabListener);
        fabAddPhoto.setOnClickListener(fabListener);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View view = findViewById(R.id.drawer_layout);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        switch (id) {
            case R.id.my_holidays:
                if (!(currentFragment instanceof HolidayFragment))
                    switchFragment(new HolidayFragment());
                break;
            case R.id.my_places:
                if (!(currentFragment instanceof PlacesFragment))
                    switchFragment(new PlacesFragment());
                break;
            case R.id.my_photos:
                switchFragment(new GalleryFragment());
                break;
            case R.id.search:
                // TODO: Start search fragment
                Snackbar.make(view, "Search Activity", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                // TODO: Start settings fragment
                Snackbar.make(view, "Settings Activity", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePicture() {
        final int REQUEST_TAKE_PHOTO = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Add to database
            PhotoRepository repository = new PhotoRepository(getApplication());
            repository.insert(new Photo(mCurrentPhotoPath));
            // EditPhoto activity (associate with holiday or place, tag location)
            Intent intent = new Intent(MainActivity.this, EditPhoto.class);
            intent.putExtra("PHOTO_PATH", mCurrentPhotoPath);
            MainActivity.this.startActivity(intent);
        } else {
            try {
                Files.deleteIfExists(Paths.get(mCurrentPhotoPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
