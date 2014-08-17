package com.thesis.helpers;

import com.thesis.models.ImageModel;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.ParticleAnalyzer;

/**
 * Created by cerebro on 8/17/14.
 */
public class ProcessHelper {
	ImagePlus imagePlus;

	public ProcessHelper(String filePath){
		this.imagePlus = IJ.openImage(filePath);
	}

	public Double getPorosity() throws NullPointerException{
		Double porosity = 0.0;
		Double area = 0.0;
		Double max = 0.0;
		Double min = 0.0;
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
			rt.reset();
		} catch(Exception e) {
			e.printStackTrace();
		}
			return porosity;
	}

	public void countParcicles(int width, int height){
		this.imagePlus.getProcessor().setAutoThreshold("Default");
		this.imagePlus.setRoi(0, 0, width, height - 100);
		int measurments = Measurements.AREA+
				Measurements.FERET+
				Measurements.PERIMETER+
				Measurements.CIRCULARITY;
		ResultsTable rt = new ResultsTable();
		ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurments,rt, 10, 99999);
		particleAnalyzer.analyze(this.imagePlus);
		rt.show("My analyzer");
	}
}
