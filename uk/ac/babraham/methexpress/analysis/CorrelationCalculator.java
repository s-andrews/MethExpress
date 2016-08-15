package uk.ac.babraham.methexpress.analysis;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import uk.ac.babraham.methexpress.data.DataPointPair;
import uk.ac.babraham.methexpress.data.SampleSet;

public class CorrelationCalculator {

	/**
	 * This class annotated a set of DataPoint pairs with their corresponding 
	 * R and p values from a Person correlation
	 */


	public static void correlateDataPointPairs (DataPointPair [] data) {

		if (data.length == 0) return;

		String [] sampleNames = data[0].point1().samples().names();

		double [] data1 = new double[sampleNames.length];
		double [] data2 = new double[sampleNames.length];
		boolean [] valid = new boolean[sampleNames.length];

		int validCount;

		for (int i=0;i<data.length;i++) {

			// We need to find which data points we can use for this correlation

			for (int j=0;j<sampleNames.length;j++) {
				data1[j] = data[i].point1().getValueForSample(sampleNames[j]);
				data2[j] = data[i].point2().getValueForSample(sampleNames[j]);

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


			if (validCount >= 3) {
				// We can do the correlation

				double [][] corData;

				corData = new double [validCount][2];

				int index = 0;

				for (int j=0;j<validCount;j++) {
					if (valid[j]) {
						corData[index][0] = data1[j];
						corData[index][1] = data2[j];
						++index;
					}
				}


				System.err.println("Cor data matrix is "+corData.length+" x "+corData[0].length);

				PearsonsCorrelation pc = new PearsonsCorrelation(corData);

				double rValue = pc.getCorrelationMatrix().getEntry(0, 1);
				double pValue = pc.getCorrelationPValues().getEntry(0, 1);

				System.err.println("Correction is R="+rValue+" p="+pValue);


			}

			else {
				// We need to put in default values

			}





		}


		// TODO: Calculate the correlation
	}



}
