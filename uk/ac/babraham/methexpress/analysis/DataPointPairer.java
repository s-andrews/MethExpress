package uk.ac.babraham.methexpress.analysis;

import java.util.ArrayList;
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
		return null;
	}

	
}
