package me.karanthaker.traveljournal.me.karanthaker.traveljournal.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

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
}
