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
	
	public double maxValue () {
		double maxValue = Double.NEGATIVE_INFINITY;
		for (int i=0;i<values.length;i++) {
			if (Double.isNaN(values[i])) continue;
			if (values[i]> maxValue) maxValue = values[i];
		}
		
		return maxValue;
	}
	
	public double minValue () {
		double minValue = Double.POSITIVE_INFINITY;
		for (int i=0;i<values.length;i++) {
			if (Double.isNaN(values[i])) continue;
			if (values[i]< minValue) minValue = values[i];
		}
		
		return minValue;
	}

	public double maxDifference () {
		double maxValue = maxValue();
		if (Double.isInfinite(maxValue)) {
			return 0;
		}
		double minValue = minValue();
		
		return (maxValue-minValue);

	}
	
	
	public int getNaNCount () {
		int count = 0;
		for (int i=0;i<values.length;i++) {
			if (Double.isNaN(values[i])) count++;
		}
		return count;
	}
	
}
