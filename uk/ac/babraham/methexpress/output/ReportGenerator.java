package uk.ac.babraham.methexpress.output;

import java.io.IOException;
import java.io.PrintWriter;

import uk.ac.babraham.methexpress.data.DataPointPair;
import uk.ac.babraham.methexpress.preferences.Preferences;

public class ReportGenerator {
	
	
	public static void writeReport (DataPointPair [] data, Preferences prefs) throws IOException {
		
		PrintWriter pr = new PrintWriter(prefs.outFile());
		
		/*
		 * The fields in the report will be:
		 * 
		 * 1) Probe1 name
		 * 2) Probe1 chr
		 * 3) Probe1 start
		 * 4) Probe1 end
		 * 5) Probe2 name
		 * 6) Probe2 chr
		 * 7) Probe2 start
		 * 8) Probe2 end
	     * 9) R value
	     * 10) P value
	     * 11) FDR
	     * 12) Probe1 data (colon delimited)
	     * 13) Probe2 data (colon delimited)
		 * 
		 */
		
		String [] sampleNames = data[0].point1().samples().names();
		
		// Write out the header first
		
		StringBuffer header = new StringBuffer();
		
		String [] headerNames = new String [] {
				 "Probe1 name",
				 "Probe1 chr",
				 "Probe1 start",
				 "Probe1 end",
				 "Probe2 name",
				 "Probe2 chr",
				 "Probe2 start",
				 "Probe2 end",
			     "R value",
			     "P value",
			     "FDR",
			     "Probe1 data",
			     "Probe2 data"
		};
		
		for (int s=0;s<headerNames.length;s++) {
			if (s != 0) header.append("\t");
			header.append(headerNames[s]);
		}
		
		pr.println(header.toString());
				
		// Write out all of the data
		for (int d=0;d<data.length;d++) {
			
			// See if we can skip this line
			if (!data[d].wasTested()) {
				continue; // This point wasn't tested
			}
			if (data[d].fdr() > prefs.maxFDR()) {
				continue; // This point didn't pass the significance threshold
			}
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(data[d].point1().name());
			sb.append("\t");
			sb.append(data[d].point1().chr());
			sb.append("\t");
			sb.append(data[d].point1().start());
			sb.append("\t");
			sb.append(data[d].point1().end());
			sb.append("\t");

			sb.append(data[d].point2().name());
			sb.append("\t");
			sb.append(data[d].point2().chr());
			sb.append("\t");
			sb.append(data[d].point2().start());
			sb.append("\t");
			sb.append(data[d].point2().end());
			sb.append("\t");
			
			sb.append(data[d].rValue());
			sb.append("\t");
			sb.append(data[d].pValue());
			sb.append("\t");
			sb.append(data[d].fdr());
			sb.append("\t");
			
			
			for (int s=0;s<sampleNames.length;s++) {
				if (s != 0) {
					sb.append(":");
					sb.append(data[d].point1().getValueForSample(sampleNames[s]));
				}
			}
			
			sb.append("\t");
			
			for (int s=0;s<sampleNames.length;s++) {
				if (s != 0) {
					sb.append(":");
					sb.append(data[d].point2().getValueForSample(sampleNames[s]));
				}
			}
			
			pr.println(sb.toString());			
		}
		
		
		
		pr.close();
	}

}
