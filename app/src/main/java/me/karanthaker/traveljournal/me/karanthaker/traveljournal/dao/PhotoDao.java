package me.karanthaker.traveljournal.me.karanthaker.traveljournal.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Dao
public interface PhotoDao {

    @Query("SELECT id, path FROM photo")
    List<Photo> getAll();

    @Query("SELECT id, path FROM photo WHERE id IN (:photoIds)")
    List<Photo> loadAllByIds(int[] photoIds);

    @Insert
    void insertAll(Photo... photos);

    @Delete
    void delete(Photo photo);

}
