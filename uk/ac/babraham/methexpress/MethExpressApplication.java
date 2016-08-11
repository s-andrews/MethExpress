package uk.ac.babraham.methexpress;

import java.io.IOException;

import uk.ac.babraham.methexpress.analysis.DataPointPairer;
import uk.ac.babraham.methexpress.data.DataCollection;
import uk.ac.babraham.methexpress.data.DataPointPair;
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
		
		try {
			
			// First we read in the data
			DataCollection data = new DataCollection(prefs.file1(), prefs.file2());
			
			// Now we need to pair up the data points so that we have a set of points to test
			// This will also let us filter out any points which don't match the preferences
			// which have been set.
			
			DataPointPair [] dataPairs = DataPointPairer.pairDataPoints(data, prefs);
			
			// Now we can run the correlation analysis across the set of points
			
			
			
			
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		

	}
	
	
	public static void main (String [] args) {
		
		new MethExpressApplication(args);
	}
	
}
