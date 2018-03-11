package me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Dao
public interface HolidayDao {

    @Query("SELECT * FROM holiday ORDER BY id ASC")
    LiveData<List<Holiday>> getAllHolidays();

    @Query("SELECT * FROM holiday ORDER BY id ASC")
    List<Holiday> getAll();

    @Query("SELECT * FROM holiday WHERE id = :holidayId")
    Holiday getHolidayById(int holidayId);

    @Insert
    void insert(Holiday holiday);

    @Update
    void update(Holiday holiday);

    @Delete
    void delete(Holiday holiday);

    @Query("DELETE FROM holiday")
    void deleteAll();

}
