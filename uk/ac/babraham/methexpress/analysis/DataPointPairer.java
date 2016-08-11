package uk.ac.babraham.methexpress.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import uk.ac.babraham.methexpress.data.DataCollection;
import uk.ac.babraham.methexpress.data.DataPoint;
import uk.ac.babraham.methexpress.data.DataPointPair;
import uk.ac.babraham.methexpress.preferences.Preferences;

public class DataPointPairer {

	public static DataPointPair [] pairDataPoints (DataCollection data, Preferences prefs) {
		
		// See on what basis we're pairing these points
		if (prefs.matchOnNames()) {
			return pairOnNames(data, prefs);
		}
		else if (prefs.matchOnPosition()) {
			return pairOnPosition(data, prefs);
		}
		else {
			throw new IllegalArgumentException("Don't know how to pair when it's not by name or position");
		}
	}
	
	
	private static DataPointPair [] pairOnNames (DataCollection data, Preferences prefs) {
		
		ArrayList<DataPointPair> dataPairs = new ArrayList<DataPointPair>();
		
		// We'll put the points from the first data store into a hash indexed by name
		// so we can do a quick lookup for those in the second set.  There may be duplicate
		// names so we'll have to keep an array in each case.
		
		DataPoint [] dataPoints = data.dataSet1().dataPoints();
		
		HashMap<String, DataPoint []> dataPoints1Map = new HashMap<String, DataPoint[]>();
		
		for (int i=0;i<dataPoints.length;i++) {
			
			// Check that this point matches the values we need
			if (dataPoints[i].getNaNCount() > prefs.maxNaValuesData1()) continue;
			if (dataPoints[i].maxValue() < prefs.minValueData1()) continue;
			if (dataPoints[i].maxDifference() < prefs.minDiffData1()) continue;
			
			if (dataPoints1Map.containsKey(dataPoints[i].name())) {
				DataPoint [] localPoints = dataPoints1Map.get(dataPoints[i].name());
				DataPoint [] newPoints = new DataPoint[localPoints.length+1];
				for (int j=0;j<localPoints.length;j++) {
					newPoints[j] = localPoints[j];
				}
				newPoints[localPoints.length] = dataPoints[i];
				dataPoints1Map.put(dataPoints[i].name(), newPoints);
			}
			else {
				dataPoints1Map.put(dataPoints[i].name(), new DataPoint[] {dataPoints[i]});
			}
		}
		
		// Now we can go through the points in the second data set and match them up.
		dataPoints = data.dataSet2().dataPoints();
		
		for (int i=0;i<dataPoints.length;i++) {
			
			if (!dataPoints1Map.containsKey(dataPoints[i].name())) continue;

			if (dataPoints[i].getNaNCount() > prefs.maxNaValuesData2()) continue;
			if (dataPoints[i].maxValue() < prefs.minValueData2()) continue;
			if (dataPoints[i].maxDifference() < prefs.minDiffData2()) continue;
			
			DataPoint [] data1Points = dataPoints1Map.get(dataPoints[i].name());
			
			for (int j=0;j<data1Points.length;j++) {
				dataPairs.add(new DataPointPair(data1Points[j], dataPoints[i]));
			}
		}
		
		return dataPairs.toArray(new DataPointPair[0]);
		
	}
	

	private static DataPointPair [] pairOnPosition (DataCollection data, Preferences prefs) {
		
		// We'll work our way through the data sets one chromosome at a time since we can never
		// pair between chromosomes
		
		String [] chromosomes = data.dataSet1().chromosomes();
		
		for (int c=0;c<chromosomes.length;c++) {
			
			// Fetch and filter the data points for dataset1
			DataPoint [] data1 = data.dataSet1().getDataPointsForChromosome(chromosomes[c]);
			
			ArrayList<DataPoint> validPoints = new ArrayList<DataPoint>();
			
			for (int i=0;i<data1.length;i++) {
				if (data1[i].getNaNCount() > prefs.maxNaValuesData1()) continue;
				if (data1[i].maxValue() < prefs.minValueData1()) continue;
				if (data1[i].maxDifference() < prefs.minDiffData1()) continue;
				
				validPoints.add(data1[i]);
			}
			
			data1 = validPoints.toArray(new DataPoint[0]);
			validPoints = null;
			
			Arrays.sort(data1,new Comparator<DataPoint>() {	
				public int compare(DataPoint d1, DataPoint d2) {
					return d1.start() - d2.start();
				}
			});
			
			// Fetch and filter the data points for dataset2
			DataPoint [] data2 = data.dataSet2().getDataPointsForChromosome(chromosomes[c]);
			
			validPoints = new ArrayList<DataPoint>();
			
			for (int i=0;i<data2.length;i++) {
				if (data2[i].getNaNCount() > prefs.maxNaValuesData2()) continue;
				if (data2[i].maxValue() < prefs.minValueData2()) continue;
				if (data2[i].maxDifference() < prefs.minDiffData2()) continue;
				
				validPoints.add(data2[i]);
			}
			
			data2 = validPoints.toArray(new DataPoint[0]);
			validPoints = null;
			
			Arrays.sort(data2,new Comparator<DataPoint>() {	
				public int compare(DataPoint d1, DataPoint d2) {
					return d1.start() - d2.start();
				}
			});
			
			// Now we can go through the points in data1 and data2 matching up those
			// whose distance is close enough according to the matching criteria
			
			
		}
		
		
		
		
		return null;
	}

	
}
