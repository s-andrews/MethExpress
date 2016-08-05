package uk.ac.babraham.methexpress.data;

import java.io.File;
import java.util.ArrayList;

public class DataSet {


	// This stores the data contained in a single input file.
	
	private File file;
	private DataPoint [] dataPoints;
	
	public DataSet (File file, SampleSet samples) {

		this.file = file;
		
		parseFile(file,samples);
		
		
	}
	
	
	private void parseFile (File file, SampleSet samples) {
		
		ArrayList<DataPoint> points = new ArrayList<DataPoint>();
		
		
	}
	
	
	
	
}
