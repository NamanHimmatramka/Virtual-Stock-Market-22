package com.example.vsm22.models;

import android.media.Image;

public class News {
    private String Headline;
    private int RoundNo;
    private String newsImage;
    public News() {

    }

    public News(String headline, int roundNo) {
        Headline = headline;
        RoundNo = roundNo;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }


    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public int getRoundNo() {
        return RoundNo;
    }

    public void setRoundNo(int roundNo) {
        RoundNo = roundNo;
    }
}
