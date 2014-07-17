package com.thesis;

import com.thesis.models.ImageModel;


public class Main {

	public static void main(String[] args) {
		ImageModel theImage = new ImageModel("/Users/cerebro/Projects/masterthesis/marios.jpg");
		System.out.println(theImage.getHeight());
		System.out.println(theImage.getWidth());
		theImage.readImageAndDisplayMetaData();

	}
}
