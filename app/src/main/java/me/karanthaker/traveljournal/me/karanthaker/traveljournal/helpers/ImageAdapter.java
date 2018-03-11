package me.karanthaker.traveljournal.me.karanthaker.traveljournal.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import me.karanthaker.traveljournal.OpenHoliday;
import me.karanthaker.traveljournal.R;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;

/**
 * Created by Karan on 10/03/2018.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Photo> imageList;

    public ImageAdapter(Context context) {
        this.context = context;
        this.imageList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }

        imageView.setAdjustViewBounds(true);
        imageView.setImageURI(Uri.parse(imageList.get(i).getPath()));
        return imageView;
    }

    public void setImageList(List<Photo> imageList) {
        this.imageList = imageList;
    }
}
