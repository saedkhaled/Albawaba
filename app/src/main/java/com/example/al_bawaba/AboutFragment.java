package com.example.al_bawaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.al_bawaba.moduls.Ad;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AboutFragment extends Fragment {
    public static final String TAG = AboutFragment.class.getSimpleName();
    Ad currentAd;
    Unbinder unbinder;

    @BindView(R.id.houseContentTextView)
    TextView houseContentTextView;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            currentAd = Parcels.unwrap(getArguments().getParcelable(TAG));
        }
        if (currentAd != null) {
            String houseContentString = currentAd.getContent() + " " + currentAd.getContact();
            houseContentTextView.setText(houseContentString);
        }
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
