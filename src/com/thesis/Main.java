package com.thesis;

import com.thesis.com.thesis.models.Image;

public class Main {

    public static void main(String[] args) {
        Image theImage = new Image("/Users/cerebro/Projects/masterthesis/marios.jpg");
        System.out.println(theImage.getHeight());
        System.out.println(theImage.getWidth());
        theImage.readImageAndDisplayMetaData();

    }
}
