package me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PhotoDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.database.AppDatabase;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class PhotoRepository {

    private PhotoDao photoDao;
    private LiveData<List<Photo>> allPhotos;

    public PhotoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        photoDao = db.photoDao();
        allPhotos = photoDao.getAllPhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return allPhotos;
    }

    public void insert(Photo photo) {
        new insertAsyncTask(photoDao).execute(photo);
    }

    public void delete(Photo photo) {
        new deleteAsyncTask(photoDao).execute(photo);
    }

    private static class insertAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDao photoDao;

        insertAsyncTask(PhotoDao dao) {
            photoDao = dao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.insert(photos[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDao photoDao;

        deleteAsyncTask(PhotoDao dao) {
            photoDao = dao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.delete(photos[0]);
            return null;
        }
    }

}
