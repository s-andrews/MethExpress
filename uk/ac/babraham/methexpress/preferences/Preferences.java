package uk.ac.babraham.methexpress.preferences;

import java.io.File;

public class Preferences {

	/**
	 * These are the two files to parse
	 */
	
	private File file1;
	private File file2;
	
	
	/**
	 * How many NA values can there be in a dataset to still consider it as valid
	 */
	private int maxNAValuesData1 = 1;
	private int maxNAValuesData2 = 1;
	
	/**
	 * We can set a min absolute value for datasets 1 and 2
	 */
	private double minValueData1 = Double.NEGATIVE_INFINITY;
	private double minValueData2 = Double.NEGATIVE_INFINITY;
	
	
	/**
	 * We can set a minimum difference for any sample pair
	 * 
	 */
	private double minDiffData1 = 0;
	private double minDiffData2 = 0;
	
	
	/**
	 * We can opt to log transform either dataset
	 */
	private boolean logTransformData1 = false;
	private boolean logTransformData2 = false;
	
	/**
	 * Matching can be on the basis of names or or distance
	 */
	private boolean matchOnNames = false;
	
	/**
	 * For distance matching we can specify a cutoff for the
	 * maximum allowed distance
	 */
	private int maxMatchingDist = 100000;
	
	/**
	 * We can say whether we want the output to be quiet or not
	 */
	private boolean quiet = false;
	
	
	public Preferences (String [] args) throws PreferencesParsingException {
		
		file1 = new File(args[0]);
		file2 = new File(args[1]);
		
	}
	
	
	public int maxNaValuesData1 () {
		return maxNAValuesData1;
	}
	public int maxNaValuesData2 () {
		return maxNAValuesData2;
	}
	
	public double minValueData1 () {
		return minValueData1;
	}
	public double minValueData2 () {
		return minValueData2;
	}
	
	public double minDiffData1 () {
		return minDiffData1;
	}
	public double minDiffData2 () {
		return minDiffData2;
	}
	
	public boolean logTrasformData1 () {
		return logTransformData1;
	}
	public boolean logTrasformData2 () {
		return logTransformData2;
	}
	
	public boolean matchOnNames () {
		return matchOnNames;
	}
	public boolean matchOnPosition () {
		return !matchOnNames;
	}
	
	public int maxMatchingDist () {
		return maxMatchingDist;
	}
	
	public File file1 () {
		return file1;
	}

	public File file2 () {
		return file2;
	}
	
	public boolean quiet () {
		return quiet;
	}

	
}
