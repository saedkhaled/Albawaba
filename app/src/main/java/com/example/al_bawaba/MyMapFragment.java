package com.example.al_bawaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.al_bawaba.moduls.Ad;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = MyMapFragment.class.getSimpleName();
    Ad currentAd;
    double v = 0,v1 = 0;

    @BindView(R.id.houseDetailedAddressTextView)
    TextView houseDetailedAddressTextView;
    Unbinder unbinder;
    private GoogleMap mMap;

    public MyMapFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_map, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            currentAd = Parcels.unwrap(getArguments().getParcelable(TAG));
        }
        if (currentAd != null) {
            houseDetailedAddressTextView.setText(currentAd.getDetailedAddress());
            String string = currentAd.getAddress();
            String[] parts = string.split(",");
            v = Double.parseDouble(parts[0]);
            v1 = Double.parseDouble(parts[1]);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        if (currentAd != null ) {
            LatLng place = new LatLng(v, v1);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.addMarker(new MarkerOptions().position(place).title(currentAd.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        }
    }
}