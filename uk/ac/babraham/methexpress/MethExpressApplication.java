package uk.ac.babraham.methexpress;

import java.io.IOException;

import uk.ac.babraham.methexpress.data.DataCollection;
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
			new DataCollection(prefs.file1(), prefs.file2());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		

	}
	
	
	public static void main (String [] args) {
		
		new MethExpressApplication(args);
	}
	
}
