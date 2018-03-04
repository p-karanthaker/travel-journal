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
public class Holiday {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "start_date")
    @TypeConverters({Converters.class})
    private Date startDate;

    @ColumnInfo(name = "end_date")
    @TypeConverters({Converters.class})
    private Date endDate;

    @ColumnInfo(name = "notes")
    private String notes;

    public Holiday() {

    }

    public Holiday(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
