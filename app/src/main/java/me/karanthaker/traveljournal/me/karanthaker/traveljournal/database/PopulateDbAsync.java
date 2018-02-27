package me.karanthaker.traveljournal.me.karanthaker.traveljournal.database;

import android.os.AsyncTask;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PhotoDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final PhotoDao photoDao;

    public PopulateDbAsync(AppDatabase db) {
        photoDao = db.photoDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        photoDao.deleteAll();
        photoDao.insert(new Photo("/dummy/path/pic.png"));
        return null;
    }
}
