package me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Entity
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "path")
    private String path;

    @ColumnInfo(name = "place_id")
    private int placeId;

    @ColumnInfo(name = "holiday_id")
    private int holidayId;

    public Photo(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getHolidayId() {
        return this.holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

}
