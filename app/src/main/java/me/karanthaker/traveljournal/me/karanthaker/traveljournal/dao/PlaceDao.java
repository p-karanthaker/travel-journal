package me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Place;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM place ORDER BY id ASC")
    LiveData<List<Place>> getAllPlaces();

    @Query("SELECT * FROM place ORDER BY id ASC")
    List<Place> getAll();

    @Query("SELECT * FROM place WHERE id = :placeId")
    Place getPlaceById(int placeId);

    @Insert
    void insert(Place place);

    @Update
    void update(Place place);

    @Delete
    void delete(Place place);

    @Query("DELETE FROM place")
    void deleteAll();

}
