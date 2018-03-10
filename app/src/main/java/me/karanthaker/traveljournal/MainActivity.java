package me.karanthaker.traveljournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
                        //intent = new Intent(MainActivity.this, AddPhoto.class);
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

        switch (id) {
            case R.id.my_holidays:
                switchFragment(new HolidayFragment());
                break;
            case R.id.my_places:
                //switchFragment(new PlacesFragment());
                break;
            case R.id.my_photos:
                //switchFragment(new PhotosFragment());
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
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
