package com.example.al_bawaba;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.al_bawaba.moduls.Ad;
import com.example.al_bawaba.moduls.AdAdapter;

import org.parceler.Parcels;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdAdapter.AdClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    AdAdapter adAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adAdapter = AdAdapter.get();
        adAdapter.setAdClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adAdapter.stopListening();
    }

    @Override
    public void onAdClick(Ad ad) {
        Intent intent = new Intent(MainActivity.this,AdActivity.class);
        intent.putExtra(AdActivity.AD_TAG, Parcels.wrap(ad));
        startActivity(intent);
    }
}