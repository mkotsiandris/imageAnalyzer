package com.thesis.models;

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

	String filePath;
	private int height;
	private int width;

	public ImageModel(String thePath) {
		this.filePath = String.valueOf(thePath);
		this.getSize();
	}

	private void getSize(){
		try {
			File imageFile = new File(this.filePath);
			BufferedImage imageBuffer = ImageIO.read(imageFile);
			this.height = imageBuffer.getHeight();
			this.width = imageBuffer.getWidth();
		} catch (IOException e){
			e.printStackTrace();
		}
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

	/*
		@Category Getters and Setters
	 */

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
