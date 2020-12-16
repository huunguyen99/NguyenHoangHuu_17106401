package com.example.appnghenhac;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.appnghenhac.interface_truyendulieu.ISendBaiHat;
import com.example.appnghenhac.interface_truyendulieu.ISendCaSi;
import com.example.appnghenhac.model.BaiHat;
import com.example.appnghenhac.model.CaSi;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ISendCaSi, ISendBaiHat {

    private AppBarConfiguration mAppBarConfiguration;
    public static List<BaiHat> lstBaiHat = new ArrayList<BaiHat>();
    public static List<CaSi> lstCaSi = new ArrayList<CaSi>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lstBaiHat.clear();
        lstCaSi.clear();
            CaSi cs1 = new CaSi(1, "Hà Anh Tuấn", R.drawable.haanhtuan);
            CaSi cs2 = new CaSi(2, "Đan Trường", R.drawable.dantruong);
            lstCaSi.add(cs1);
            lstCaSi.add(cs2);
            BaiHat bh1 = new BaiHat(1, 1, "Khúc hát chim trời", time(R.raw.khuchatchimtroi_haanhtuan), R.raw.khuchatchimtroi_haanhtuan, R.drawable.haanhtuan);
            BaiHat bh2 = new BaiHat(2, 1, "Hạ cuối", time(R.raw.hacuoi_haanhtuan), R.raw.hacuoi_haanhtuan, R.drawable.haanhtuan);
            BaiHat bh3 = new BaiHat(3, 1, "Giấc mơ chỉ là giấc mơ", time(R.raw.giacmochilagiacmo_haanhtuan), R.raw.giacmochilagiacmo_haanhtuan, R.drawable.haanhtuan);
            BaiHat bh4 = new BaiHat(4, 2, "Kiếp ve sầu", time(R.raw.kiepvesau_dantruong), R.raw.kiepvesau_dantruong, R.drawable.dantruong);
            BaiHat bh5 = new BaiHat(5, 2, "Mãi mãi một tình yêu", time(R.raw.maimaimottinhyeu_dantruong), R.raw.maimaimottinhyeu_dantruong, R.drawable.dantruong);
            lstBaiHat.add(bh1);
            lstBaiHat.add(bh2);
            lstBaiHat.add(bh3);
            lstBaiHat.add(bh4);
            lstBaiHat.add(bh5);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_casi)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public String time(int baihat){
        String t;
        MediaPlayer media = new MediaPlayer();
        media = MediaPlayer.create(this, baihat);
        SimpleDateFormat tg = new SimpleDateFormat("mm:ss");
        t = tg.format(media.getDuration());
        return t;
    }

    @Override
    public void sendData(CaSi caSi) {
        Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
        intent.putExtra("casi", (Serializable)caSi);
        startActivity(intent);
    }

    @Override
    public void sendData(BaiHat bh, String tenCS) {
        Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
        intent.putExtra("baihat", (Serializable)bh);
        intent.putExtra("tencs", tenCS);
        intent.putExtra("dieukien", 1);
        startActivity(intent);
    }
}