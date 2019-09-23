package com.example.al_bawaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class PhotoFragment extends BlurDialogFragment {
    public static final String TAG = PhotoFragment.class.getSimpleName();
    String photoUrl;

    @BindView(R.id.photoFragmentImageView)
    PhotoView photoFragmentImageView;

    Unbinder unbinder;

    public PhotoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        photoUrl = getArguments().getString(TAG);
        StorageReference photoReference = FirebaseStorage.getInstance().getReference().child(photoUrl);
        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .centerCrop()
                        .placeholder(R.drawable.ic_cached_black_48dp)
                        .error(R.drawable.ic_error_outline_black_48dp))
                .load(photoReference)
                .into(photoFragmentImageView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}