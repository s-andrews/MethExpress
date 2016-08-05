package uk.ac.babraham.methexpress.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataSet {


	// This stores the data contained in a single input file.
	
	private File file;
	private DataPoint [] dataPoints;
	
	public DataSet (File file, SampleSet samples) throws IOException {

		this.file = file;
		
		parseFile(file,samples);
		
		
	}
	
	
	private void parseFile (File file, SampleSet samples) throws IOException {
		
		ArrayList<DataPoint> points = new ArrayList<DataPoint>();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String [] header = br.readLine().split("\t");
		
		// Fields are:
		/*
		 * 0 Probe name
		 * 1 Chr
		 * 2 Start
		 * 3 End
		 * 4 Strand
		 * 5 No Value
		 * 6 Feature
		 * 7 ID
		 * 8 Desc
		 * 9 Type
		 * 10 Orientation
		 * 11 Distance
		 * 12+ Data fields
		 */
		
		String [] sampleNames = new String[header.length-12];
		for (int i=0;i<sampleNames.length;i++) {
			sampleNames[i] = header[i+12];			
		}
		samples.checkNames(sampleNames);
		
		String [] sections;
		String line;
		
		while ((line = br.readLine()) != null) {
			
			if (line.length() == 0) continue;
			
			sections = line.split("\t");
			
			if (sections.length < 13) {
				br.close();
				throw new IOException("Not enough data on line "+line);
			}
			
			String name = sections[0];
			String chr = sections[1];
			int start = Integer.parseInt(sections[2]);
			int end = Integer.parseInt(sections[3]);
			
			DataPoint point = new DataPoint(samples, name, chr, start, end);
			
			for (int i=0;i<sampleNames.length;i++) {
				point.setValueForSample(Double.parseDouble(sections[i+12]), sampleNames[i]);
			}
			
			points.add(point);
		}
		
		br.close();
	
		dataPoints = points.toArray(new DataPoint[0]);
		
	}
	
	
	public DataPoint [] dataPoints () {
		return dataPoints;
	}
	
	
	
}
