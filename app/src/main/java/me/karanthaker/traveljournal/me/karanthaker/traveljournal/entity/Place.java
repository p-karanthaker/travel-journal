package me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.helpers.Converters;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Entity(foreignKeys = @ForeignKey
                                (
                                    entity = Photo.class,
                                    parentColumns = "id",
                                    childColumns = "photo_id",
                                    onDelete = CASCADE)
                                )
public class Place {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "date")
    @TypeConverters({Converters.class})
    private Date date;

    @ColumnInfo(name = "latitude")
    private long latitude;

    @ColumnInfo(name = "longitude")
    private long longitude;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "photo_id")
    private int photo_id;

    @ColumnInfo(name = "companions")
    private String companions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getCompanions() {
        return companions;
    }

    public void setCompanions(String companions) {
        this.companions = companions;
    }
}
