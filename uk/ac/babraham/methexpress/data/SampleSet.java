package uk.ac.babraham.methexpress.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class SampleSet {
	
	// This keeps track of the different sample names seen in the data and checks that
	// they are consistent between the files.
	
	private HashSet<String> sampleNames = new HashSet<String>();
	private HashMap<String, Integer> indexPositions = new HashMap<String, Integer>();
	
	
	public SampleSet  () {
		
	}
	
	public void checkNames (String [] names) {
		
		if (sampleNames.size() == 0) {
			// This is a new set so add these as the reference names
			for (int i=0;i<names.length;i++) {
				if (sampleNames.contains(names[i])) {
					throw new IllegalArgumentException("Duplicate name found in sample set ("+names[i]+")");
				}
				indexPositions.put(names[i], sampleNames.size());
				sampleNames.add(names[i]);
			}
		}
		
		else {
			// We're simply validating that the names provided are in the sample set
			for (int i=0;i<names.length;i++) {
				if (! sampleNames.contains(names[i])) {
					throw new IllegalArgumentException("Mismatched name ("+names[i]+") in second sample set");
				}
			}
		}
		
		
	}
	
	public String [] names () {
		
		String [] names = sampleNames.toArray(new String[0]);
		Arrays.sort(names);
		return names;
	}
	
	
	public int size () {
		return sampleNames.size();
	}
	
	public int getIndexForName (String name) {
		
		if (indexPositions.containsKey(name)) {
			return indexPositions.get(name);
		}
		return -1;
	}
	

}
