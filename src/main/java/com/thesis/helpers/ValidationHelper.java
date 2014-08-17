package com.thesis.helpers;


import java.io.File;

public class ValidationHelper {

	public static boolean isFilePathValid(String filePath) {
		try {
			File imageFile = new File(filePath);
			if (imageFile.exists() && !imageFile.isDirectory()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
