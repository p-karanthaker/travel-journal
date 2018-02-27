package me.karanthaker.traveljournal.me.karanthaker.traveljournal.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import me.karanthaker.traveljournal.R;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.HolidayDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PhotoDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao.PlaceDao;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;

/**
 * Created by KTHAKER on 26/02/2018.
 */

// User and Book are classes annotated with @Entity.
@Database(version = 1, entities = {Holiday.class, Place.class, Photo.class})
public abstract class AppDatabase extends RoomDatabase {

    // HolidayDao is a class annotated with @Dao.
    abstract public HolidayDao holidayDao();

    // PlaceDao is a class annotated with @Dao.
    abstract public PlaceDao placeDao();

    // PhotoDao is a class annotated with @Dao.
    abstract public PhotoDao photoDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, context.getString(R.string.database)).addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };
}
