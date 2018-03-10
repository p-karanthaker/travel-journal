package me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PlaceDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.database.AppDatabase;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;

/**
 * Created by Karan on 27/02/2018.
 */

public class PlaceRepository {

    private PlaceDao placeDao;
    private LiveData<List<Place>> allPlaces;

    public PlaceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        placeDao = db.placeDao();
        allPlaces = placeDao.getAllPlaces();
    }

    public LiveData<List<Place>> getAllPlaces() {
        return allPlaces;
    }

    public Place getPlaceById(int placeId) {
        Place place = null;
        try {
            place = new getAsyncTask(placeDao).execute(placeId).get();
        } catch (Exception e) {

        }
        return place;
    }

    public void update(Place place) {
        new updateAsyncTask(placeDao).execute(place);
    }

    public void insert(Place place) {
        new insertAsyncTask(placeDao).execute(place);
    }

    public void delete(Place place) {
        new deleteAsyncTask(placeDao).execute(place);
    }

    private static class getAsyncTask extends AsyncTask<Integer, Void, Place> {

        private PlaceDao placeDao;

        getAsyncTask(PlaceDao dao) {
            placeDao = dao;
        }

        @Override
        protected Place doInBackground(Integer... ids) {
            return placeDao.getPlaceById(ids[0]);
        }
    }

    private static class updateAsyncTask extends AsyncTask<Place, Void, Void> {

        private PlaceDao placeDao;

        updateAsyncTask(PlaceDao dao) {
            placeDao = dao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.update(places[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Place, Void, Void> {

        private PlaceDao placeDao;

        insertAsyncTask(PlaceDao dao) {
            placeDao = dao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.insert(places[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Place, Void, Void> {

        private PlaceDao placeDao;

        deleteAsyncTask(PlaceDao dao) {
            placeDao = dao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.delete(places[0]);
            return null;
        }
    }

}
