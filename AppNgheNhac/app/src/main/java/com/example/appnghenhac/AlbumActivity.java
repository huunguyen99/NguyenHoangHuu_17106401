package com.example.appnghenhac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.appnghenhac.adapter.BaiHatAdapter;
import com.example.appnghenhac.interface_truyendulieu.ISendBaiHat;
import com.example.appnghenhac.model.BaiHat;
import com.example.appnghenhac.model.CaSi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity implements ISendBaiHat {

    RecyclerView rcvDSBaiHat;
    Toolbar toolbar;
    BaiHatAdapter adapter;
    List<BaiHat> lstBaiHat = new ArrayList<BaiHat>();
    CaSi casi;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ConnectView();

        initActionBar();
        intent = getIntent();
        casi = (CaSi)intent.getSerializableExtra("casi");
        toolbar.setTitle(casi.getHoTen());
        for(BaiHat bh : MainActivity.lstBaiHat)
        {
            if(bh.getMaCS() == casi.getMaCS())
                lstBaiHat.add(bh);
        }
        adapter = new BaiHatAdapter(this, lstBaiHat);
        rcvDSBaiHat.setHasFixedSize(true);
        rcvDSBaiHat.setLayoutManager(new GridLayoutManager(this, 1));
        rcvDSBaiHat.setAdapter(adapter);
    }
    void ConnectView()
    {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        rcvDSBaiHat = (RecyclerView)findViewById(R.id.rcvDSBaiHat);
    }
    private void initActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void sendData(BaiHat bh, String tenCS) {
        Intent intent = new Intent(AlbumActivity.this, PlayMusicActivity.class);
        intent.putExtra("baihat", (Serializable)bh);
        intent.putExtra("tencs", tenCS);
        intent.putExtra("dieukien", 2);
        startActivity(intent);
    }
}