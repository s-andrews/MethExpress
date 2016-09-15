package uk.ac.babraham.methexpress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import uk.ac.babraham.methexpress.analysis.CorrelationCalculator;
import uk.ac.babraham.methexpress.analysis.DataPointPairer;
import uk.ac.babraham.methexpress.data.DataCollection;
import uk.ac.babraham.methexpress.data.DataPointPair;
import uk.ac.babraham.methexpress.output.ReportGenerator;
import uk.ac.babraham.methexpress.preferences.Preferences;
import uk.ac.babraham.methexpress.preferences.PreferencesParsingException;

public class MethExpressApplication {

	private Preferences prefs;

	public MethExpressApplication (String [] args) {
		
		try {
			prefs = new Preferences(args);
		} 
		catch (PreferencesParsingException e) {
			System.err.println("Failed to parse command line: "+e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		if (prefs.help()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("uk/ac/babraham/methexpress/preferences/pref_options.txt")));
			
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				
				br.close();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(1);
			}
			
			System.exit(0);
		}
		
		
		try {
			
			if (!prefs.quiet()) System.err.println("Reading data files");
			
			// First we read in the data
			DataCollection data = new DataCollection(prefs.file1(), prefs.file2());
			
			
			if (!prefs.quiet()) System.err.println("Dataset1 ("+data.dataSet1().file().getName()+") has "+data.dataSet1().dataPoints().length+" data points");
			if (!prefs.quiet()) System.err.println("Dataset2 ("+data.dataSet2().file().getName()+") has "+data.dataSet2().dataPoints().length+" data points");

			// Now we need to pair up the data points so that we have a set of points to test
			// This will also let us filter out any points which don't match the preferences
			// which have been set.
			
			if (!prefs.quiet()) System.err.println("Creating data point pairs");
			
			if (!prefs.matchOnPosition()) {
				if (!prefs.quiet()) System.err.println("Matching on position");
			}
			else {
				if (!prefs.quiet()) System.err.println("Matching by name");
			}
			
			DataPointPair [] dataPairs = DataPointPairer.pairDataPoints(data, prefs);
			
			if (!prefs.quiet()) System.err.println("Found "+dataPairs.length+" pairs");

			if (dataPairs.length == 0) {
				System.err.println("No valid pairs found to test - exiting");
				System.exit(1);
			}
			
			// Now we can run the correlation analysis across the set of points
			
			if (!prefs.quiet()) System.err.println("Found "+dataPairs.length+" pairs");
			CorrelationCalculator.correlateDataPointPairs(dataPairs,prefs);
			
			
			// Finally we can report on the results
			ReportGenerator.writeReport(dataPairs,prefs);
			
			
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		

	}
	
	
	public static void main (String [] args) {
		
		new MethExpressApplication(args);
	}
	
}
