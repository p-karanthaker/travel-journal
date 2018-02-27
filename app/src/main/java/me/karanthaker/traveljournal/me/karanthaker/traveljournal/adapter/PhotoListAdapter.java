package me.karanthaker.traveljournal.me.karanthaker.traveljournal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.karanthaker.traveljournal.R;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;

/**
 * Created by KTHAKER on 27/02/2018.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final TextView photoItemView;

        private PhotoViewHolder(View itemView) {
            super(itemView);
            photoItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Photo> photos; // Cached copy of words

    public PhotoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        if (photos != null) {
            Photo current = photos.get(position);
            holder.photoItemView.setText(current.getPath());
        } else {
            // Covers the case of data not being ready yet.
            holder.photoItemView.setText("No Photo");
        }
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // photos has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (photos != null)
            return photos.size();
        else return 0;
    }

}
