package com.example.al_bawaba.moduls;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.al_bawaba.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private static final String TAG = PhotoAdapter.class.getSimpleName();
    private ArrayList<String> photosUrls;
    private OnPhotoListener onPhotoListener;

    private OnPhotoListener getOnPhotoListener() {
        if (onPhotoListener == null) {
            return new OnPhotoListener() {
                @Override
                public void onPhotoClicked(String photoUrl) {
                    Log.e(TAG, "Error: you must set a listener before start using it!!");
                }
            };
        }
        return onPhotoListener;
    }

    public void setOnPhotoListener(OnPhotoListener onPhotoListener) {
        this.onPhotoListener = onPhotoListener;
    }

    public PhotoAdapter(ArrayList<String> photosUrls) {
        this.photosUrls = photosUrls;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        String photoUrl = photosUrls.get(position);
        StorageReference photoReference = FirebaseStorage.getInstance().getReference().child(photoUrl);
        holder.bind(photoReference, photoUrl);
    }

    @Override
    public int getItemCount() {
        return photosUrls.size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {
        View itemView;

        @BindView(R.id.photoImageView)
        ImageView photoImageView;

        PhotoHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);

        }

        void bind(final StorageReference photoReference, final String photoUrl) {
            Glide.with(itemView.getContext())
                    .applyDefaultRequestOptions(new RequestOptions()
                            .override(Target.SIZE_ORIGINAL, 700)
                            .centerCrop()
                            .placeholder(R.drawable.ic_cached_black_48dp)
                            .error(R.drawable.ic_error_outline_black_48dp))
                    .load(photoReference)
                    .into(photoImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnPhotoListener().onPhotoClicked(photoUrl);
                }
            });
        }
    }

    public interface OnPhotoListener {
        void onPhotoClicked(String photoUrl);
    }
}