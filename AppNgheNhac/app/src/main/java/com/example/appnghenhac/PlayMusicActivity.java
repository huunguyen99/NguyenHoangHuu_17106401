package com.example.appnghenhac;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnghenhac.model.BaiHat;
import com.example.appnghenhac.services.MyService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayMusicActivity extends AppCompatActivity {
    ImageButton btnBack, btnNext, btnPlay_Pause, btnRepeat, btnRandom;
    TextView txtTenBaiHat, txtTimeStart, txtTimeEnd;
    SeekBar seekBar;
    Intent intent;
    BaiHat baihat;
    String tenCS;
    ImageView imgView;
    Toolbar toolbar;
    int repeat = 0;
    int random = 0;
    private MyService myService;
    ServiceConnection connection;
    boolean isStart = false;
    boolean isBoud = false;
    boolean isPlay = true;
    private Handler handlerUpdateCurrentTime;
    private Runnable runnableUpdateCurrentTime ;
    List<BaiHat> lstBaiHat;
    int dieukien;
    int positionBaiHat = 0;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        ConnectView();
        initActionBar();
        intent = getIntent();
        tenCS = intent.getStringExtra("tencs");
        baihat = (BaiHat)intent.getSerializableExtra("baihat");
        lstBaiHat = MainActivity.lstBaiHat;
        dieukien = intent.getIntExtra("dieukien", 0);
        if(dieukien == 2)
        {
            List<BaiHat> lstTam = new ArrayList<BaiHat>();
            for(BaiHat bh : lstBaiHat)
            {
                if(bh.getMaCS() == baihat.getMaCS())
                    lstTam.add(bh);
            }
            lstBaiHat = lstTam;
        }
        for(int i = 0; i < lstBaiHat.size(); i++)
        {
            if(lstBaiHat.get(i).getMaBH() == baihat.getMaBH())
            {
                positionBaiHat = i;
                break;
            }
        }
        toolbar.setTitle(tenCS);
        khoiTaoService(baihat);
        ImageAnimation(baihat);
        xuLyPlay_Pause();
        xuLyRepeat();
        xuLyRandom();
        xuLyNext_Back();

    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    void khoiTaoService(BaiHat bh)
    {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder myBinder = (MyService.MyBinder) service;
                myService = myBinder.getService();
                isBoud = true;
                myService.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBoud = false;
            }
        };
        Intent intent = new Intent(PlayMusicActivity.this, MyService.class);
        intent.putExtra("baihat", (Serializable) bh);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        isStart = true;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(!isPlay)
                    myService.fastForward();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myService.setCurrentPosition(seekBar.getProgress());
                if (!isPlay) {
                    myService.fastStart();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setNewMusic(BaiHat baihat)
    {
        myService.stopService();
        myService.setNewMusic(baihat.getMusic());
        if (isBoud) {
            myService.fastStart();
        }
        long totalMillis = myService.getTotalTime();
        long totalMinutes = TimeUnit.MILLISECONDS.toMinutes(totalMillis);
        long totalSeconds = TimeUnit.MILLISECONDS.toSeconds(totalMillis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalMillis));
        seekBar.setMin(0);
        seekBar.setMax((int) totalMillis);
        txtTimeStart.setText("00:00");
        txtTimeEnd.setText((totalMinutes < 10 ? "0" + totalMinutes : totalMinutes + "") + ":" + (totalSeconds < 10 ? "0" + totalSeconds : totalSeconds + ""));
        myService.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    }


    void xuLyNext_Back()
    {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                BaiHat baiHat;
                myService.stopService();
                if(positionBaiHat == lstBaiHat.size() -  1)
                    baiHat = lstBaiHat.get(0);
                else
                    baiHat = lstBaiHat.get(positionBaiHat + 1);
                positionBaiHat = lstBaiHat.indexOf(baiHat);
                setData(baiHat);
                setNewMusic(baiHat);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                BaiHat baiHat;
                myService.stopService();
                if(positionBaiHat == 0)
                    baiHat = lstBaiHat.get(lstBaiHat.size() - 1);
                else
                    baiHat = lstBaiHat.get(positionBaiHat - 1);
                positionBaiHat = lstBaiHat.indexOf(baiHat);
                setData(baiHat);
                setNewMusic(baiHat);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                repeat = 0;
                onBackPressed();
                break;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                repeat = 0;
                onBackPressed();
                break;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void playMusic()
    {
        long totalMillis = myService.getTotalTime();
        seekBar.setProgress(0);
        seekBar.setMin(0);
        seekBar.setMax((int)totalMillis);
            handlerUpdateCurrentTime = new Handler();
            runnableUpdateCurrentTime = new Runnable() {
                @Override
                public void run() {
                    if (myService.getIsPlaying()) {
                        long currentMillis = myService.getCurrentTime();
                        long currentMinutes = TimeUnit.MILLISECONDS.toMinutes(currentMillis);
                        long currentSeconds = TimeUnit.MILLISECONDS.toSeconds(currentMillis)
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentMillis));
                        txtTimeStart.setText((currentMinutes < 10 ? "0" + currentMinutes : currentMinutes + "") + ":" + (currentSeconds < 10 ? "0" + currentSeconds : currentSeconds + ""));
                        seekBar.setProgress((int) currentMillis);
                    }

                    handlerUpdateCurrentTime.postDelayed(runnableUpdateCurrentTime, 100);
                }
            };
            runnableUpdateCurrentTime.run();
        long totalMinutes = TimeUnit.MILLISECONDS.toMinutes(totalMillis);
        long totalSeconds = TimeUnit.MILLISECONDS.toSeconds(totalMillis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalMillis));

        txtTimeEnd.setText((totalMinutes < 10 ? "0" + totalMinutes : totalMinutes + "") + ":" + (totalSeconds < 10 ? "0" + totalSeconds : totalSeconds + ""));
        if(isBoud)
            myService.fastStart();
    }

    void xuLyPlay_Pause()
    {
        btnPlay_Pause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                isPlay = !isPlay;
                if(!isPlay)
                {
                    btnPlay_Pause.setImageResource(R.drawable.ic_pause);
                    playMusic();
                }
                else
                {
                    btnPlay_Pause.setImageResource(R.drawable.ic_play);
                    myService.fastForward();
                }

            }
        });
    }

    void xuLyRandom()
    {
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random++;
                if(random % 2 == 1)
                {
                    btnRandom.setColorFilter(Color.rgb(0, 168, 142));

                }
                else
                {
                    btnRandom.setColorFilter(Color.rgb(210, 210, 210));
                }
            }
        });
    }

    void xuLyRepeat()
    {
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                repeat++;
                if(repeat % 3 == 1)
                {

                    btnRepeat.setColorFilter(Color.rgb(0, 168, 142));
                }
                else if(repeat % 3 == 2)
                {
                    myService.setLooping(true);

                    btnRepeat.setColorFilter(Color.rgb(0, 168, 142));
                    btnRepeat.setImageResource(R.drawable.ic_repeat_one);
                }
                else
                {

                    btnRepeat.setColorFilter(Color.rgb(210, 210, 210));
                    btnRepeat.setImageResource(R.drawable.ic_repeat);
                }
            }
        });
    }

    void setData(BaiHat bh)
    {
        imgView.setImageResource(bh.getImage());
        txtTenBaiHat.setText(bh.getTenBH());
        txtTimeEnd.setText(bh.getThoiLuong());
        txtTimeStart.setText("00:00");
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void ImageAnimation(BaiHat bh)
    {
        setData(bh);
        imgView.setClipToOutline(true);
        RotateAnimation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(10000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        imgView.startAnimation(anim);
    }

    void ConnectView()
    {
        imgView = (ImageView)findViewById(R.id.imgView);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnNext = (ImageButton)findViewById(R.id.btnNext);
        btnPlay_Pause = (ImageButton)findViewById(R.id.btnPlay_Pause);
        btnRepeat = (ImageButton)findViewById(R.id.btnRepeat);
        btnRandom = (ImageButton)findViewById(R.id.btnRandom);
        txtTenBaiHat = (TextView)findViewById(R.id.txtTenBaiHat);
        txtTimeEnd = (TextView)findViewById(R.id.txtTimeEnd);
        txtTimeStart = (TextView)findViewById(R.id.txtTimeStart);
        seekBar = (SeekBar)findViewById(R.id.progress_music);
    }
}