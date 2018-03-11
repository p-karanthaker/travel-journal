package me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by KTHAKER on 26/02/2018.
 */

@Entity
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "path")
    private String path;

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

}
