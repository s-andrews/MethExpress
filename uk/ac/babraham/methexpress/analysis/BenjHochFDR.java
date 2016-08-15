/**
 * Copyright Copyright 2010-15 Simon Andrews
 *
 *    This file is part of SeqMonk.
 *
 *    SeqMonk is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    SeqMonk is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with SeqMonk; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package uk.ac.babraham.methexpress.analysis;

import java.util.Arrays;
import java.util.Comparator;

import uk.ac.babraham.methexpress.data.DataPointPair;

/**
 * The Class BenjHochFDR provides false discovery rate correction
 * to a set of calculated p-values.
 */
public class BenjHochFDR {

	/**
	 * Calculate q values.  The t test values which are provided
	 * as input are modified in place.
	 * 
	 * @param pValues the t test values
	 */
	public static void calculateQValues (DataPointPair [] pValues) {
		Arrays.sort(pValues, new Comparator<DataPointPair>() {
			public int compare(DataPointPair p1, DataPointPair p2) {
				return Double.compare(p1.pValue(), p2.pValue());
			}
		});
		
		// Find the number of comparisons actually made
		int validComparisons = 0;
		for (int i=0;i<pValues.length;i++) {
			if (pValues[i].wasTested()) {
				++validComparisons;
			}
		}
				
		for (int i=0;i<pValues.length;i++) {
			pValues[i].setFDR(pValues[i].pValue() * ((double)(validComparisons)/(i+1)));
			
			if (i>0 && pValues[i].fdr() < pValues[i-1].fdr()) {
				pValues[i].setFDR(pValues[i-1].fdr());
			}			
		}
		
	}
	
}
