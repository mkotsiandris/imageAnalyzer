package com.thesis;

import com.thesis.models.ImageModel;

public class Main {

	public static void main(String[] args) {
		ImageModel imageModel = new ImageModel("rsz_30nm.png");
		System.out.println(imageModel.calculatePorosityProcess().toString());
	}
}
