
package com.thesis.helpers;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import java.util.HashMap;

/**
 * Created by cerebro on 8/17/14.
 */
public class ProcessHelper {
	ImagePlus imagePlus;
	ImageConverter imageConverter;
	ImageProcessor imageProcessor;

	public ProcessHelper(String filePath){
		this.imagePlus = IJ.openImage(filePath);
		this.imageConverter = new ImageConverter(this.imagePlus);
		this.imageProcessor = this.imagePlus.getProcessor();
		imageConverter.convertToGray8();
	}

	public HashMap<String, Double> getPorosity() throws NullPointerException{
		Double porosity;
		Double area;
		Double max;
		Double min;
		HashMap<String, Double> resultsMap = new HashMap<>();

		try{
			this.imagePlus.getProcessor().setAutoThreshold("Default");
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
			Analyzer analyzer = new Analyzer(this.imagePlus, measurements, rt);
			analyzer.measure();
			porosity = rt.getValue("%Area", rt.getCounter() - 1);
			area = rt.getValue("Area", rt.getCounter() - 1);
			min = rt.getValue("Min", rt.getCounter() - 1);
			max = rt.getValue("Max", rt.getCounter() - 1);

			resultsMap.put("porosity", porosity);
			resultsMap.put("area", area);
			resultsMap.put("min", min);
			resultsMap.put("max", max);

			rt.reset();
		} catch(Exception e) {
			e.printStackTrace();
		}
			return resultsMap;
	}

	public void countParcicles(int width, int height, String thesholdType){
		try {
//			adaptiveThr_ adaptiveThr = new adaptiveThr_();
//			adaptiveThr.run(this.imageProcessor);
			this.imagePlus.getProcessor().setAutoThreshold(thesholdType);
			this.imagePlus.show();
//			this.imagePlus.setRoi(0, 0, width, height - 500);
			int measurments = Measurements.AREA+
					Measurements.FERET+
					Measurements.PERIMETER+
					Measurements.CIRCULARITY;
			ResultsTable rt = new ResultsTable();
			ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurments,rt, 10, 99999);
			particleAnalyzer.analyze(this.imagePlus);
			rt.show("My analyzer");
		} catch (Exception e) {
			System.out.println("Exception on countParticles");
			e.printStackTrace();
		}
	}
}
