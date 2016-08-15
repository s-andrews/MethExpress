package uk.ac.babraham.methexpress.data;

public class DataPointPair {

	/**
	 * This class stores a matched pair of data points which are going to be
	 * the basis for a correlation test.
	 */
	
	private DataPoint point1;
	private DataPoint point2;
	
	private double rValue = 0;
	private double pValue = 1;
	private double fdr = 1;
	
	
	public DataPointPair (DataPoint point1, DataPoint point2) {
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public DataPoint point1 () {
		return point1;
	}
	
	public DataPoint point2 () {
		return point2;
	}
	
	public void setCorrection (double rValue, double pValue) {
		this.rValue = rValue;
		this.pValue = pValue;
	}
	
	public double rValue () {
		return rValue;
	}
	
	public double pValue () {
		return pValue;
	}
	
	public void setFDR (double fdr) {
		this.fdr = fdr;
	}
	
	public double fdr () {
		return fdr;
	}
	
	
	
}
