package com.thesis.models;

import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;

public class ImageLibModel {

	String imageName;
	int imageMax;
	int iamgeMin;

	public ImageLibModel(String imageName){
		this.imageName = imageName;
	}

	public void displayImage() throws ImgIOException{
		Img<FloatType> img = new ImgOpener().openImg(this.imageName, new FloatType());
		ImageJFunctions.show(img);
	}

}
