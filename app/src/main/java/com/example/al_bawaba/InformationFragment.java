package com.example.al_bawaba;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.widget.CompoundButtonCompat;
import androidx.fragment.app.Fragment;

import com.example.al_bawaba.moduls.Ad;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InformationFragment extends Fragment {

    public static final String TAG = InformationFragment.class.getSimpleName();
    Ad currentAd;
    Unbinder unbinder;

    @BindView(R.id.houseContactTextView)
    TextView houseContactTextView;
    @BindView(R.id.housePriceTextView)
    TextView housePriceTextView;
    @BindView(R.id.poolCheckBox)
    AppCompatCheckBox poolCheckBox;
    @BindView(R.id.electricCheckBox)
    AppCompatCheckBox electricCheckBox;
    @BindView(R.id.closeToTownCheckBox)
    AppCompatCheckBox closeToTownCheckBox;
    @BindView(R.id.tvCheckBox)
    AppCompatCheckBox tvCheckBox;
    @BindView(R.id.bedroomsCheckBox)
    AppCompatCheckBox bedroomsCheckBox;
    @BindView(R.id.goodViewCheckBox)
    AppCompatCheckBox goodViewCheckBox;
    @BindView(R.id.childrenPoolCheckBox)
    AppCompatCheckBox childrenPoolCheckBox;
    @BindView(R.id.waterCheckBox)
    AppCompatCheckBox waterCheckBox;
    @BindView(R.id.carParkCheckBox)
    AppCompatCheckBox carParkCheckBox;
    @BindView(R.id.kitchenCheckBox)
    AppCompatCheckBox kitchenCheckBox;


    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            currentAd = Parcels.unwrap(getArguments().getParcelable(TAG));
        }
        if (currentAd != null) {
            String price = currentAd.getPrice() + " SYP";
            String contactString = "للتواصل " + currentAd.getContact();
            houseContactTextView.setText(contactString);
            housePriceTextView.setText(price);
            changingCheckBoxState(poolCheckBox, currentAd.getFeatures().get(1));
            changingCheckBoxState(electricCheckBox, currentAd.getFeatures().get(2));
            changingCheckBoxState(closeToTownCheckBox, currentAd.getFeatures().get(3));
            changingCheckBoxState(tvCheckBox, currentAd.getFeatures().get(4));
            changingCheckBoxState(bedroomsCheckBox, currentAd.getFeatures().get(5));
            changingCheckBoxState(goodViewCheckBox, currentAd.getFeatures().get(6));
            changingCheckBoxState(childrenPoolCheckBox, currentAd.getFeatures().get(7));
            changingCheckBoxState(waterCheckBox, currentAd.getFeatures().get(8));
            changingCheckBoxState(carParkCheckBox, currentAd.getFeatures().get(9));
            changingCheckBoxState(kitchenCheckBox, currentAd.getFeatures().get(10));
        }

        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void changingCheckBoxState(CheckBox checkBox, Boolean state) {
        checkBox.setChecked(state);
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.red)};
        CompoundButtonCompat.setButtonTintList(checkBox, new ColorStateList(states, colors));
        if (state) {
            checkBox.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            checkBox.setTextColor(getResources().getColor(R.color.red));
        }

    }
}