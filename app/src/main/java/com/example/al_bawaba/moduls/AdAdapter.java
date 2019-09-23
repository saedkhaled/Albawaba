package com.example.al_bawaba.moduls;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.al_bawaba.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdAdapter extends FirestoreRecyclerAdapter<Ad, AdAdapter.AdHolder> {

    private static final String TAG = AdAdapter.class.getSimpleName();
    AdClickListener adClickListener;
    public AdClickListener getAdClickListener() {
        if (adClickListener == null) {
            adClickListener = new AdClickListener() {
                @Override
                public void onAdClick(Ad ad) {
                    Log.e(TAG, "you need to call setAdClickListener() to set the click listener of AdAdapter ");
                }
            };
        }
        return adClickListener;
    }

    public void setAdClickListener(AdClickListener adClickListener) {
        this.adClickListener = adClickListener;
    }

    public static AdAdapter get() {
        Query query = FirebaseFirestore.getInstance()
                .collection("houses");
        FirestoreRecyclerOptions<Ad> options = new FirestoreRecyclerOptions.Builder<Ad>()
                .setQuery(query, Ad.class)
                .build();
        return new AdAdapter(options);
    }


    public AdAdapter(@NonNull FirestoreRecyclerOptions<Ad> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdHolder holder, int position, @NonNull Ad ad) {
        holder.bind(ad);
    }

    @NonNull
    @Override
    public AdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad, parent, false);
        return new AdHolder(itemView);
    }

    class AdHolder extends RecyclerView.ViewHolder {
        View itemView;

        @BindView(R.id.houseNameTextView)
        TextView houseNameTextView;
        @BindView(R.id.houseImageImageView)
        ImageView houseImageImageView;
        @BindView(R.id.housePriceTextView)
        TextView housePriceTextView;
        @BindView(R.id.houseAreaTextView)
        TextView houseAreaTextView;
        @BindView(R.id.isEmptyTextView)
        TextView isEmptyTextView;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;


        AdHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void bind(final Ad ad) {
            if (ad != null) {
                if (ad.getMainImageUrl() != null) {
                    StorageReference mainPhotoReference = FirebaseStorage.getInstance().getReference().child(ad.getMainImageUrl());

                String price = ad.getPrice() + " SYP";
                if (ad.features.get(0)) {
                    isEmptyTextView.setText(itemView.getContext().getResources().getString(R.string.available));
                    isEmptyTextView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                } else {
                    isEmptyTextView.setText(itemView.getContext().getResources().getString(R.string.rented));
                    isEmptyTextView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.red));
                }
                ratingBar.setRating(4.0f);
                houseNameTextView.setText(ad.getName());
                Glide.with(itemView.getContext())
                        .applyDefaultRequestOptions(new RequestOptions()
                                .transforms(new CenterCrop(), new RoundedCorners(16))
                                .placeholder(R.drawable.ic_cached_black_48dp)
                                .error(R.drawable.ic_error_outline_black_48dp)
                                .override(100, 100))
                        .load(mainPhotoReference)
                        .into(houseImageImageView);
                housePriceTextView.setText(price);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAdClickListener().onAdClick(ad);
                    }
                });
            }
            }
        }
    }

    public interface AdClickListener {
        void onAdClick(Ad ad);
    }

}