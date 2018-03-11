package me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.helpers.Converters;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Entity
public class Place {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "date")
    @TypeConverters({Converters.class})
    private Date date;

    @ColumnInfo(name = "gPlaceId")
    private String googlePlaceId;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "companions")
    private String companions;

    public Place() {

    }

    public Place(String name, Date date) {
        this.name = name;
        this.date = date;
    }

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

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public void setGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCompanions() {
        return companions;
    }

    public void setCompanions(String companions) {
        this.companions = companions;
    }
}
