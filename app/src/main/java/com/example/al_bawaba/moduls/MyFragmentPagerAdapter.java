package com.example.al_bawaba.moduls;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.al_bawaba.AboutFragment;
import com.example.al_bawaba.InformationFragment;
import com.example.al_bawaba.MyMapFragment;

import org.parceler.Parcels;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public static final String TAG = MyFragmentPagerAdapter.class.getSimpleName();
    private static int NUM_ITEMS = 3;
    private Ad currentAd;

    public MyFragmentPagerAdapter(FragmentManager fm, Ad ad) {
        super(fm);
        currentAd = ad;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle =new Bundle();
        if (currentAd != null) {
            switch (position) {
                case 0:
                    bundle.putParcelable(MyMapFragment.TAG, Parcels.wrap(currentAd));
                    MyMapFragment mapFragment = new MyMapFragment();
                    mapFragment.setArguments(bundle);
                    return mapFragment;
                case 1:
                    bundle.putParcelable(AboutFragment.TAG, Parcels.wrap(currentAd));
                    AboutFragment aboutFragment = new AboutFragment();
                    aboutFragment.setArguments(bundle);
                    return aboutFragment;
                case 2:
                    bundle.putParcelable(InformationFragment.TAG,Parcels.wrap(currentAd));
                    InformationFragment informationFragment = new InformationFragment();
                    informationFragment.setArguments(bundle);
                    return informationFragment;
                default:
                    return null;
            }
        }
        return null;
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "الموقع";
            case 1:
                return "الشرح";
            case 2:
                return "المعلومات";
            default:
                return "";
        }
    }


}
