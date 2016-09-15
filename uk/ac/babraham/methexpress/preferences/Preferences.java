package uk.ac.babraham.methexpress.preferences;

import java.io.File;

public class Preferences {

	/**
	 * These are the two files to parse
	 */
	
	private File file1;
	private File file2;
	private File outFile;
	
	
	/**
	 * How many valid values do we need for each comparison we're making
	 */
	private int minValidValues = 3;
	
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
	 * We can set an FDR cutoff
	 */
	private double maxFDR = 0.1d;
	
	
	/**
	 * We can opt not to do multiple testing correction
	 */
	private boolean skipMultipleTesting = false;
	
	
	/**
	 * We can say whether we want the output to be quiet or not
	 */
	private boolean quiet = false;
	
	
	public Preferences (String [] args) throws PreferencesParsingException {
		
		String [] remainingArgs = parsePreference(args);
		
		if (remainingArgs.length < 3) {
			throw new PreferencesParsingException("Not enough file names supplied - we need 2 data files and an output file");
		}
		
		if (remainingArgs.length > 3) {
			throw new PreferencesParsingException("Too many file names supplied - we only need 2 data files and an output file");
		}
		
		
		file1 = new File(remainingArgs[0]);
		file2 = new File(remainingArgs[1]);
		outFile = new File(remainingArgs[2]);
		
		if (! file1.exists() && file1.canRead()) {
			throw new PreferencesParsingException("Couldn't find a data file at "+file1.getAbsolutePath()+" which I could read");
		}
		
		if (! file2.exists() && file2.canRead()) {
			throw new PreferencesParsingException("Couldn't find a data file at "+file2.getAbsolutePath()+" which I could read");
		}

		
	}
	
	
	private String [] parsePreference (String [] prefs) throws PreferencesParsingException {
		
		int lastIndex;
		
		for (lastIndex=0;lastIndex<prefs.length;lastIndex++) {
			
			if (prefs[lastIndex] == "--minValid") {
				++lastIndex;
				int minValidValue = Integer.parseInt(prefs[lastIndex]);
				
				if (minValidValue < 3) {
					throw new PreferencesParsingException("Min valid values can't be less than 3");
				}
				
				minValidValues = minValidValue;
			}

			
			else if (prefs[lastIndex] == "--minValue1") {
				++lastIndex;
				double minValue1 = Double.parseDouble(prefs[lastIndex]);
								
				minValueData1 = minValue1;
			}

			else if (prefs[lastIndex] == "--minValue2") {
				++lastIndex;
				double minValue2 = Double.parseDouble(prefs[lastIndex]);
								
				minValueData2 = minValue2;
			}

			else if (prefs[lastIndex] == "--minDiff1") {
				++lastIndex;
				double minDiff1 = Math.abs(Double.parseDouble(prefs[lastIndex]));

				minDiffData1 = minDiff1;
			}
			
			else if (prefs[lastIndex] == "--minDiff2") {
				++lastIndex;
				double minDiff2 = Math.abs(Double.parseDouble(prefs[lastIndex]));

				minDiffData2 = minDiff2;
			}

			else if (prefs[lastIndex] == "--logData1") {
				logTransformData1 = true;
			}
			
			else if (prefs[lastIndex] == "--logData2") {
				logTransformData2 = true;
			}

			else if (prefs[lastIndex] == "--matchNames") {
				matchOnNames = true;
			}

			else if (prefs[lastIndex] == "--matchPositions") {
				matchOnNames = false;
			}
			
			else if (prefs[lastIndex] == "--matchDist") {
				++lastIndex;
				int matchDist = Math.abs(Integer.parseInt(prefs[lastIndex]));
								
				maxMatchingDist = matchDist;
			}

			else if (prefs[lastIndex] == "--pValueFilter") {
				++lastIndex;
				double p = Double.parseDouble(prefs[lastIndex]);
				
				if (p < 0 || p > 1) {
					throw new PreferencesParsingException("P value filter must be between 0 and 1");
				}
				
								
				maxFDR = p;
			}
			
			else if (prefs[lastIndex] == "--skipMTC") {
				skipMultipleTesting = true;
			}

			else if (prefs[lastIndex] == "--quiet") {
				quiet = true;
			}

			else if (prefs[lastIndex].startsWith("-")) {
				// We assume they tried to specify an option which we don't understand.  Who
				// starts a file name with a minus?
				
				throw new PreferencesParsingException("Didn't understand option "+prefs[lastIndex]);
			}
			
			else {
				break;
			}
			
		}
		
		// Now we construct a string array from whatever we have left.
		
		String [] returnValues = new String[prefs.length-(lastIndex+1)];
		
		for (int i=lastIndex;i<prefs.length;i++) {
			returnValues[i-lastIndex] = prefs[i];
		}
		
		return returnValues;
		
	}
	
	
	public int minValidValues () {
		return minValidValues;
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
	
	public double maxFDR () {
		return maxFDR;
	}
	
	public boolean skipMultipleTesting () {
		return skipMultipleTesting;
	}
	
	public File file1 () {
		return file1;
	}

	public File file2 () {
		return file2;
	}
	
	public File outFile () {
		return outFile;
	}
	
	public boolean quiet () {
		return quiet;
	}

	
}
