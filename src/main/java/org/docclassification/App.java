package org.docclassification;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.docclassification.models.Documents;

/**
 * Hello world!
 *
 */
public class App {
	static FileWriter fw;
	public static void main(String[] args) throws IOException {
		fw = new FileWriter(new File("outputdir/OutPutLast.txt"));
		fw.flush();
		RDC rdc = new RDC();
		/*
		 * 
		 */
		int[][] multiarray = rdc.readcsvfile("inputdir/sampledata.csv");
		
		double[] averagelength = rdc.averageLength(multiarray);
		int[] maxTerms = rdc.max_terms(multiarray);
		
		Set<Integer> classlabels = rdc.numberOfClasses(multiarray);
		int count = 0;
		for (int clas : classlabels) {
			
			fw.append("Results for Class "+ clas +"\n");
			fw.append("\n\n");
			
			Map<String, int[][]> map = rdc.splitArray(multiarray, clas);
			int[][] pos = map.get("positive");
			int[][] neg = map.get("negative");

			Documents.setNumOfNegativeDoc(neg.length);
			Documents.setNumOfPositiveDoc(pos.length);

			List<int[]> positiveClass = rdc.transpose(pos);
			List<int[]> negativeClass = rdc.transpose(neg);

			for (int i = 0; i < maxTerms.length; i++) {
				int[] positive = positiveClass.get(i);
				int[] negative = negativeClass.get(i);
				
				System.out.println("Maximum term : " + maxTerms[i]);
				double[] auc_arr = new double[maxTerms[i]];
				for (int j = 1; j <= maxTerms[i]; j++) {
					double freq_positive = 0.0;
					double freq_negative = 0.0;
					double tpr = 0.0;
					double fpr = 0.0;

					if (rdc.frequency(positive).get(j) != null) {
						freq_positive = rdc.frequency(positive).get(j);						
						tpr = freq_positive / Documents.getNumOfPositiveDoc();
					}
					if (rdc.frequency(negative).get(j) != null) {
						freq_negative = rdc.frequency(negative).get(j);
						fpr = freq_negative / Documents.getNumOfNegativeDoc();
					}
					double d = Math.abs(tpr - fpr);
					double[] arr = { tpr, fpr };
					
					double min = rdc.min(arr);
					if (min == 0.0) {
						min = 0.1;
					}
					double ntc = averagelength[count] * j;
					double result = (d / min) * ntc;
					
					auc_arr[j - 1] = Math.abs(result);
				}
				System.out.println();
				fw.append(aucFinder(auc_arr)+"\t");
			}
			fw.append("\n\n\n");
			count++;
		}
		fw.close();
	}

	public static double aucFinder(double[] arr) {
		double auc = 0.0;
		double res = 0.0;
		for (int k = 0; k < arr.length; k++) {
			if (k != arr.length - 1) {
				auc = (arr[k] + arr[k + 1]) / 2;
			} else {
				auc = (arr[k] + 0) / 2;
			}
			res += auc;
		}
		return res;
	}
}
