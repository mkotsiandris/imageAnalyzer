package com.thesis;

import com.thesis.models.ImageModel;
import org.apache.commons.cli.*;

public class Main {

	public static void main(String[] args) {
		Options options = new Options();
		Option fileNameProperty  = OptionBuilder.withArgName("property=value")
				.hasArgs(2)
				.withValueSeparator()
				.withDescription( "enter the filepath" )
				.create( "fileName" );
		Option functionProperty  = OptionBuilder.withArgName("property=value")
				.hasArgs(2)
				.withValueSeparator()
				.withDescription( "enter the function you want to run" )
				.create( "function" );
		Option thresholdProperty  = OptionBuilder.withArgName("property=value")
				.hasArgs(2)
				.withValueSeparator()
				.withDescription( "enter the threshold you want to apply" )
				.create( "threshold" );
		options.addOption(fileNameProperty);
		options.addOption(functionProperty);
		options.addOption(thresholdProperty);
		// create the parser
		CommandLineParser parser = new GnuParser();

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);
			if(line.hasOption("fileName")) {
				// initialise the member variable
				String fileName = line.getOptionValue( "fileName" );
				ImageModel imageModel = new ImageModel(fileName);
					if (line.hasOption("function")){
						String function = line.getOptionValue("function");
							if (function.equals("p")){
								System.out.println(imageModel.calculatePorosityProcess().toString());
							} else if (function.equals("c")) {
								String threshold;
								if (line.hasOption("threshold")){
									threshold = line.getOptionValue("threshold");
								} else {
									threshold = "Default";
								}
								imageModel.countParticlesProcess(threshold);
							}
						}
					}
			} catch (ParseException exp) {
			exp.printStackTrace();
			// oops, something went wrong
//			HelpFormatter formatter = new HelpFormatter();
//			formatter.printHelp("help", options );
			System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
		} finally {
		}
	}
}
