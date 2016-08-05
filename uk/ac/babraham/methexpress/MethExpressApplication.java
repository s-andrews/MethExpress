package uk.ac.babraham.methexpress;

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
		
		

	}
	
	
	public static void main (String [] args) {
		
		new MethExpressApplication(args);
	}
	
}
