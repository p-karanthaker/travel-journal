package me.karanthaker.traveljournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter.HolidayListAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter.PhotoListAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.HolidayViewModel;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.viewmodel.PhotoViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PhotoViewModel photoViewModel;
    private HolidayViewModel holidayViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        /** TESTING ADDING ITEMS TO VIEW */
        final PhotoListAdapter adapter = new PhotoListAdapter(this);
        final HolidayListAdapter adapter1 = new HolidayListAdapter(this);

        recyclerView.setAdapter(adapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        photoViewModel.getAllPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                adapter.setPhotos(photos);
            }
        });

        holidayViewModel = ViewModelProviders.of(this).get(HolidayViewModel.class);

        holidayViewModel.getAllHolidays().observe(this, new Observer<List<Holiday>>() {
            @Override
            public void onChanged(@Nullable List<Holiday> holidays) {
                adapter1.setHolidays(holidays);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //photoViewModel.insert(new Photo("/dummy/path/pic.png"));

                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    holidayViewModel.insert(new Holiday("MyHoliday", f.parse("2018-01-01"), f.parse("2018-01-02")));
                } catch (ParseException pe) {
                    System.out.println(pe.getLocalizedMessage());
                }
                Snackbar.make(view, "Added photo.", Snackbar.LENGTH_LONG).show();
            }
        });

        /** END TESTING */

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View view = findViewById(R.id.drawer_layout);

        switch (id) {
            case R.id.home:
                // TODO: Start main activity.
                Snackbar.make(view, "Main Activity", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.new_photo:
                // TODO: Start upload activity.
                Snackbar.make(view, "Upload Activity", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.collections:
                // TODO: Start collections activity.
                Snackbar.make(view, "Collections Activity", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.search:
                // TODO: Start search activity.
                Snackbar.make(view, "Search Activity", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                // TODO: Start settings activity.
                Snackbar.make(view, "Settings Activity", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
