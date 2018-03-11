package me.karanthaker.traveljournal;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.karanthaker.traveljournal.me.karanthaker.traveljournal.entity.Photo;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.helpers.ImageAdapter;
import me.karanthaker.traveljournal.me.karanthaker.traveljournal.repository.PhotoRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {


    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridView = rootView.findViewById(R.id.gridView);
        final ImageAdapter imageAdapter = new ImageAdapter(rootView.getContext());
        PhotoRepository photoRepository = new PhotoRepository(getActivity().getApplication());
        final List<Photo> photos = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                photos.addAll(photoRepository.getPhotoList());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                imageAdapter.setImageList(photos);
                gridView.setAdapter(imageAdapter);
            }
        }.execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ImageView expandedImageView = rootView.findViewById(R.id.expanded_image);
                Photo p = (Photo) imageAdapter.getItem(i);

                new AsyncTask<Void, Void, Void>() {
                    Bitmap bitmap;
                    @Override
                    protected Void doInBackground(Void... voids) {
                        bitmap = BitmapFactory.decodeFile(p.getPath());
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        expandedImageView.setImageBitmap(bitmap);
                        expandedImageView.setVisibility(View.VISIBLE);
                    }
                }.execute();

                expandedImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expandedImageView.setVisibility(View.GONE);
                    }
                });
            }
        });
        return rootView;
    }

}
