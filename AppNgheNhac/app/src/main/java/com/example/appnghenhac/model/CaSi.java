package com.example.appnghenhac.model;

import java.io.Serializable;

public class CaSi implements Serializable {
    private int maCS;
    private String hoTen;
    private int image;

    public CaSi(int maCS, String hoTen, int image) {
        this.maCS = maCS;
        this.hoTen = hoTen;
        this.image = image;
    }

    public CaSi() {
    }

    public int getMaCS() {
        return maCS;
    }

    public void setMaCS(int maCS) {
        this.maCS = maCS;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

