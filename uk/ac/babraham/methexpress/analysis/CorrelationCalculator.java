package uk.ac.babraham.methexpress.analysis;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import uk.ac.babraham.methexpress.data.DataPointPair;
import uk.ac.babraham.methexpress.preferences.Preferences;

public class CorrelationCalculator {

	/**
	 * This class annotated a set of DataPoint pairs with their corresponding 
	 * R and p values from a Person correlation
	 */


	public static void correlateDataPointPairs (DataPointPair [] data, Preferences prefs) {

		if (data.length == 0) return;

		boolean logTransform1 = prefs.logTrasformData1();
		boolean logTransform2 = prefs.logTrasformData2();
		
		String [] sampleNames = data[0].point1().samples().names();

		double [] data1 = new double[sampleNames.length];
		double [] data2 = new double[sampleNames.length];
		boolean [] valid = new boolean[sampleNames.length];
		

		int validCount;

		for (int i=0;i<data.length;i++) {

			// We need to find which data points we can use for this correlation

			for (int j=0;j<sampleNames.length;j++) {
				data1[j] = data[i].point1().getValueForSample(sampleNames[j]);
				if (logTransform1) {
					data1[j] = Math.log(data1[j]);
				}
				data2[j] = data[i].point2().getValueForSample(sampleNames[j]);
				if (logTransform2) {
					data2[j] = Math.log(data2[j]);
				}

				if (Double.isNaN(data1[j]) || Double.isInfinite(data1[j]) || Double.isNaN(data2[j]) || Double.isInfinite(data2[j])) {
					valid[j] = false;
				}
				else {
					valid[j] = true;
				}
			}

			// See if we have enough data to do a correlation
			validCount = 0;
			for (int j=0;j<valid.length;j++) {
				if (valid[j]) ++validCount;
			}


			if (validCount >= prefs.minValidValues()) {
				// We can do the correlation

				double [][] corData;

				corData = new double [validCount][2];

				int index = 0;

				for (int j=0;j<valid.length;j++) {
					if (valid[j]) {
						
//						System.err.println("Found valid pair "+data1[j]+" and "+data2[j]);
						
						corData[index][0] = data1[j];
						corData[index][1] = data2[j];
						++index;
					}
				}


//				System.err.println("Cor data matrix is "+corData.length+" x "+corData[0].length);

				PearsonsCorrelation pc = new PearsonsCorrelation(corData);

				double rValue = pc.getCorrelationMatrix().getEntry(0, 1);
				double pValue = pc.getCorrelationPValues().getEntry(0, 1);
				if (Double.isNaN(rValue)) {
					// This can happen if there is no variance in one of the datasets
					// We'll treat this as if we never did the test since it could
					// never give us a sensible answer.
					continue;
				}

//				System.err.println("Correction is R="+rValue+" p="+pValue);
				
				data[i].setCorrelation(rValue, pValue);

			}


		}

		// Finally we apply multiple testing correction
		if (!prefs.skipMultipleTesting()) {
			BenjHochFDR.calculateQValues(data);
		}
		else {
			// We'll just sort the list by pvalue so that the output is 
			// ordered (the FRD calculation does this for us)
			Arrays.sort(data, new Comparator<DataPointPair>() {
				public int compare(DataPointPair p1, DataPointPair p2) {
					if (p1.wasTested() && ! p2.wasTested()) {
						return -1;  // Tested results always come first
					}
					return Double.compare(p1.pValue(), p2.pValue());
				}
			});

		}

	}



}
