package com.example.appnghenhac.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.IconCompat;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.BaiHat;

import java.io.Serializable;

public class MyService extends Service {

    private MyPlayer myPlayer;
    private IBinder binder;
    BaiHat baiHat;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Đã gọi onCreate()");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        binder = new MyBinder();
        Log.d("ServiceDemo", "gọi onBind()");
        baiHat = (BaiHat)intent.getSerializableExtra("baihat");
        myPlayer = new MyPlayer(this, baiHat);


        return binder;

    }
    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    // Xây dựng các phương thức thực hiện nhiệm vụ,
    // ở đây mình demo phương thức tua bài hát
    public void fastForward(){

        myPlayer.fastForward(60000); // tua đến giây thứ 120
    }
    public void fastStart(){

        myPlayer.fastStart();
    }

    public int getTotalTime() {
        return myPlayer.getTotalTime();
    }
    public void stopService()
    {
        myPlayer.stop();
    }
    public void setNewMusic(int resource)
    {
        myPlayer.setMusic(resource);
    }

    public int getCurrentTime() {
        return myPlayer.getCurrentTime();
    }


    public boolean getIsPlaying() {
        return myPlayer.getIsPlaying();
    }

    public void setCurrentPosition(int miliseconds) {
        myPlayer.setCurrentPosition(miliseconds);
    }

    public MediaPlayer getMediaPlayer()
    {
        return myPlayer.getMediaPlayer();
    }
    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public MyService getService() {
            return MyService.this;
        }
    }
    public void setLooping(boolean looping)
    {
        myPlayer.setLooping(looping);
    }

}

// Xây dựng một đối tượng riêng để chơi nhạc
class MyPlayer {
    // đối tượng này giúp phát một bài nhạc
    private MediaPlayer mediaPlayer;
    private Context context;
    public MyPlayer(Context context, BaiHat bh) {
        // Nạp bài nhạc vào mediaPlayer
        mediaPlayer = MediaPlayer.create(
                context, bh.getMusic());
        // Đặt chế độ phát lặp lại liên tục
        this.context = context;
        mediaPlayer.setLooping(false);
//        mediaPlayer.pause();
    }

    public void setLooping(boolean looping)
    {
        mediaPlayer.setLooping(looping);
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    public void setMusic(int resouce)
    {
        mediaPlayer = MediaPlayer.create(this.context, resouce);
    }

    public void fastForward(int pos){
        //mediaPlayer.seekTo(pos);
        mediaPlayer.pause();

    }
    public void fastStart(){
        mediaPlayer.start();
    }

    // phát nhạc
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // dừng phát nhạc
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public int getTotalTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }

        return 0;
    }

    public int getCurrentTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }

        return 0;
    }

    public boolean getIsPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }

        return false;
    }

    public void setCurrentPosition(int milisecond) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(milisecond);
        }
    }
}