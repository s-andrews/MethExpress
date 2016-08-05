package uk.ac.babraham.methexpress.data;

public class DataPoint {

	// This object stores the data for one line in a data file
	
	private String name;
	private String chr;
	private int start;
	private int end;
	
	private double [] values;
	
	private SampleSet samples;
	
	
	public DataPoint (SampleSet samples, String name, String chr, int start, int end) {
		this.samples = samples;
		values = new double[samples.size()];
		this.name = name;
		this.chr = chr;
		this.start = start;
		this.end = end;
	}
	
	public void setValueForSample (double value, String sample) {
		values[samples.getIndexForName(sample)] = value; 
	}
	
	public String name () {
		return name;
	}
	
	public String chr () {
		return chr;
	}
	
	public int start () {
		return start;
	}
	
	public int end () {
		return end;
	}
	
	public double [] values () {
		return values;
	}
	
	public double getValueForSample (String name) {
		return values[samples.getIndexForName(name)];
	}
	
	
	
}
