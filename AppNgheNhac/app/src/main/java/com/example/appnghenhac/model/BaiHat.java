package com.example.appnghenhac.model;

import android.media.MediaPlayer;

import java.io.Serializable;
import java.sql.Time;

public class BaiHat implements Serializable {
    private int maBH, maCS;
    private String tenBH;
    private String thoiLuong;
    private int music;
    private int image;

    public BaiHat(int maBH, int maCS, String tenBH, String thoiLuong, int music, int image) {
        this.maBH = maBH;
        this.maCS = maCS;
        this.tenBH = tenBH;
        this.thoiLuong = thoiLuong;
        this.music = music;
        this.image = image;
    }

    public BaiHat() {
    }

    public String getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(String thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getMaBH() {
        return maBH;
    }

    public void setMaBH(int maBH) {
        this.maBH = maBH;
    }

    public int getMaCS() {
        return maCS;
    }

    public void setMaCS(int maCS) {
        this.maCS = maCS;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }

}
