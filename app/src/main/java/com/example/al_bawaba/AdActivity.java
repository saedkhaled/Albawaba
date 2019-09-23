package com.example.al_bawaba;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.example.al_bawaba.moduls.Ad;
import com.example.al_bawaba.moduls.MyFragmentPagerAdapter;
import com.example.al_bawaba.moduls.PhotoAdapter;
import com.google.android.material.tabs.TabLayout;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdActivity extends AppCompatActivity implements PhotoAdapter.OnPhotoListener {
    public static final String AD_TAG = "AD_TAG";
    PhotoAdapter photoAdapter;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    Ad currentAd;

    @BindView(R.id.adRecyclerView)
    RecyclerView adRecyclerView;
    @BindView(R.id.imagesIndefinitePagerIndicator)
    IndefinitePagerIndicator imagesIndefinitePagerIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerViewLinearLayout)
    LinearLayout recyclerViewLinearLayout;
    @BindView(R.id.houseNameTextView)
    TextView houseNameTextView;
    @BindView(R.id.isEmptyTextView)
    TextView isEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);
        currentAd = Parcels.unwrap(getIntent().getParcelableExtra(AD_TAG));
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), currentAd);
        viewPager.setAdapter(myFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(2);
        adRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesIndefinitePagerIndicator.attachToRecyclerView(adRecyclerView);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        recyclerViewLinearLayout.setVisibility(View.GONE);
                        break;
                    case 1:
                        recyclerViewLinearLayout.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        recyclerViewLinearLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if (currentAd != null) {
            houseNameTextView.setText(currentAd.getName());
            if (currentAd.getFeatures().get(0)) {
                isEmptyTextView.setText(getResources().getString(R.string.available));
                isEmptyTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else {
                isEmptyTextView.setText(getResources().getString(R.string.rented));
                isEmptyTextView.setBackgroundColor(getResources().getColor(R.color.red));
            }
            photoAdapter = new PhotoAdapter(currentAd.getImagesUrls());
            adRecyclerView.setAdapter(photoAdapter);
            photoAdapter.setOnPhotoListener(this);
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(adRecyclerView);
        }
    }

    @Override
    public void onPhotoClicked(String photoUrl) {
        Bundle bundle = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        bundle.putString(PhotoFragment.TAG, photoUrl);
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "photo");
    }
}
