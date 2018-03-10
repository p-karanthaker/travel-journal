package me.karanthaker.traveljournal.me.karanthaker.traveljournal.database;

import android.os.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.HolidayDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PhotoDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PlaceDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final PhotoDao photoDao;
    private final HolidayDao holidayDao;
    private final PlaceDao placeDao;

    public PopulateDbAsync(AppDatabase db) {
        photoDao = db.photoDao();
        holidayDao = db.holidayDao();
        placeDao = db.placeDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Clear db tables
        photoDao.deleteAll();
        holidayDao.deleteAll();
        placeDao.deleteAll();

        // Insert dummy data
        photoDao.insert(new Photo("/dummy/path/pic.png"));
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        try {
            holidayDao.insert(new Holiday("MyHoliday", f.parse("2018-01-01"), f.parse("2018-01-02")));
            placeDao.insert(new Place("New Place", f.parse("2018-05-05")));
        } catch (ParseException parseException) {
            System.out.printf(parseException.getLocalizedMessage());
        }


        return null;
    }
}
