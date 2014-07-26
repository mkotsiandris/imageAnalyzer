package com.thesis.models;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageModel {

	final int GRAY8 = 1;
	final int GRAY16 = 2;
	final int GRAY32 = 3;
	final int COLOR_256 = 4;
	final int COLOR_RGB = 5;

	public String filePath;
	public String type;
	private int height;
	private int width;
	private ImagePlus imp;
	public Double porosity;
	public Double area;
	public Double min;
	public Double max;


	public ImageModel(String thePath) {
		this.filePath = String.valueOf(thePath);
		this.initializeImageBasics();
	}

	private void initializeImageBasics(){
		try{
			File imageFile = new File(this.filePath);
			if(imageFile.exists() && !imageFile.isDirectory()) {
				this.imp = IJ.openImage(this.filePath);
				this.getImageType();
				this.height = this.imp.getHeight();
				this.width = this.imp.getWidth();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public Double getPorosity(){
		imp.getProcessor().setAutoThreshold("Default");
		int measurements =
				Measurements.AREA +
				Measurements.MEAN +
				Measurements.MIN_MAX +
				Measurements.STD_DEV +
				Measurements.MODE +
				Measurements.MEDIAN +
				Measurements.AREA_FRACTION +
				Measurements.LIMIT;

		ResultsTable rt = new ResultsTable();
		Analyzer analyzer = new Analyzer(imp, measurements, rt);
		analyzer.measure();

		this.porosity = rt.getValue("%Area", rt.getCounter() - 1);
		this.area = rt.getValue("Area", rt.getCounter() - 1);
		this.min = rt.getValue("Min", rt.getCounter() - 1);
		this.max = rt.getValue("Max", rt.getCounter() - 1);
		rt.reset();

		return this.porosity;
	}

	/*
		@Category Getters and Setters
	 */


	public void countParcicles(){
		this.imp.getProcessor().setAutoThreshold("Default");
	}

	public void readImageAndDisplayMetaData() {
		try {
			File file = new File(this.filePath);
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

			if (readers.hasNext()) {
				ImageReader reader = readers.next();
				reader.setInput(imageInputStream, true);
				IIOMetadata metadata = reader.getImageMetadata(0);

				String[] names = metadata.getMetadataFormatNames();
				int length = names.length;
				for (int i = 0; i < length; i++) {
					System.out.println( "Format name: " + names[ i ] );
					displayMetadata(metadata.getAsTree(names[i]));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	void displayMetadata(Node root) {
		displayMetadata(root, 0);
	}

	void indent(int level) {
		for (int i = 0; i < level; i++)
			System.out.print("    ");
	}

	void displayMetadata(Node node, int level) {
		// print open tag of element
		indent(level);
		System.out.print("<" + node.getNodeName());
		NamedNodeMap map = node.getAttributes();
		if (map != null) {

			// print attribute values
			int length = map.getLength();
			for (int i = 0; i < length; i++) {
				Node attr = map.item(i);
				System.out.print(" " + attr.getNodeName() +
						"=\"" + attr.getNodeValue() + "\"");
			}
		}

		Node child = node.getFirstChild();
		if (child == null) {
			// no children, so close element and return
			System.out.println("/>");
			return;
		}

		// children, so close current tag
		System.out.println(">");
		while (child != null) {
			// print children recursively
			displayMetadata(child, level + 1);
			child = child.getNextSibling();
		}

		// print close tag of element
		indent(level);
		System.out.println("</" + node.getNodeName() + ">");
	}

	public void getImageType(){
		int type = this.imp.getType();
		switch (type) {
			case 1 :
				this.type = "GRAY8";
			case 2:
				this.type = "GRAY16";
			case 3:
				this.type = "GRAY32";
			case 4:
				this.type = "COLOR_256";
			case 5:
				this.type = "COLOR_RGB";
		}

	}
}
