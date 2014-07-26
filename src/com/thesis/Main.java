package com.thesis;

import com.thesis.models.ImageModel;

public class Main {

	public static void main(String[] args) {
		ImageModel imageModel = new ImageModel("marios.jpg");
		imageModel.readImageAndDisplayMetaData();
		System.out.println("The porosity of this image is"+ imageModel.getPorosity());
	}
}
