package uk.ac.babraham.methexpress.data;

import java.io.File;

public class DataCollection {

	// This class holds the two data sets for the two input files
	

	private DataSet dataSet1;
	private DataSet dataSet2;
	private SampleSet sampleSet = new SampleSet();
	
	public DataCollection (File file1, File file2) {
		
		dataSet1 = new DataSet(file1,sampleSet);
		dataSet2 = new DataSet(file2,sampleSet);
		
		

	}
	
	
}
