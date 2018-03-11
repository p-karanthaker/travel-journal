package me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.HolidayDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.database.AppDatabase;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;

/**
 * Created by Karan on 27/02/2018.
 */

public class HolidayRepository {

    private HolidayDao holidayDao;
    private LiveData<List<Holiday>> allHolidays;

    public HolidayRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        holidayDao = db.holidayDao();
        allHolidays = holidayDao.getAllHolidays();
    }

    public LiveData<List<Holiday>> getAllHolidays() {
        return allHolidays;
    }

    public List<Holiday> getAll() {
        return holidayDao.getAll();
    }

    public Holiday getHolidayById(int holidayId) {
        Holiday holiday = null;
        try {
            holiday = new getAsyncTask(holidayDao).execute(holidayId).get();
        } catch (Exception e) {

        }
        return holiday;
    }

    public void update(Holiday holiday) {
        new updateAsyncTask(holidayDao).execute(holiday);
    }

    public void insert(Holiday holiday) {
        new insertAsyncTask(holidayDao).execute(holiday);
    }

    public void delete(Holiday holiday) {
        new deleteAsyncTask(holidayDao).execute(holiday);
    }

    private static class getAsyncTask extends AsyncTask<Integer, Void, Holiday> {

        private HolidayDao holidayDao;

        getAsyncTask(HolidayDao dao) {
            holidayDao = dao;
        }

        @Override
        protected Holiday doInBackground(Integer... ids) {
            return holidayDao.getHolidayById(ids[0]);
        }
    }

    private static class updateAsyncTask extends AsyncTask<Holiday, Void, Void> {

        private HolidayDao holidayDao;

        updateAsyncTask(HolidayDao dao) {
            holidayDao = dao;
        }

        @Override
        protected Void doInBackground(Holiday... holidays) {
            holidayDao.update(holidays[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Holiday, Void, Void> {

        private HolidayDao holidayDao;

        insertAsyncTask(HolidayDao dao) {
            holidayDao = dao;
        }

        @Override
        protected Void doInBackground(Holiday... holidays) {
            holidayDao.insert(holidays[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Holiday, Void, Void> {

        private HolidayDao holidayDao;

        deleteAsyncTask(HolidayDao dao) {
            holidayDao = dao;
        }

        @Override
        protected Void doInBackground(Holiday... holidays) {
            holidayDao.delete(holidays[0]);
            return null;
        }
    }

}
