package com.thesis;

import com.thesis.com.thesis.models.Image;

public class Main {

    public static void main(String[] args) {
        Image marios = new Image("/Users/cerebro/Pictures/foto/01-12-07_000ΑΚ.jpg");
        marios.readImageAndDisplayMetaData();
    }
}
