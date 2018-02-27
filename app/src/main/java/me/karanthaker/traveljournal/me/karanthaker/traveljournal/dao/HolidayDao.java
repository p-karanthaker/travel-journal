package me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Holiday;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Dao
public interface HolidayDao {

    @Query("SELECT * FROM holiday ORDER BY id ASC")
    LiveData<List<Holiday>> getAllHolidays();

    @Query("SELECT * FROM holiday WHERE id IN (:holidayIds)")
    List<Holiday> loadAllByIds(int[] holidayIds);

    @Insert
    void insert(Holiday holiday);

    @Delete
    void delete(Holiday holiday);

    @Query("DELETE FROM holiday")
    void deleteAll();

}
