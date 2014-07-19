package com.thesis;

import com.thesis.models.ImageModel;
import com.thesis.models.ImageLibModel;
import io.scif.img.ImgIOException;


public class Main {

	public static void main(String[] args) throws ImgIOException{
		ImageLibModel theImageLibModel = new ImageLibModel("marios.jpg");
		theImageLibModel.displayImage();
	}
}
