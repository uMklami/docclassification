package org.docclassification;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

public class RDC {
	List<Integer> classes_list;
	
	public RDC(){
		classes_list = new ArrayList<Integer>();
	}
	
	public int[][] readcsvfile(String csvFile) {

		int[][] matrix = null;
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			int cols = 0;
			int rows = 0;
			List<String[]> list = new ArrayList<String[]>();
			
			while ((line = br.readLine()) != null) {
				String[] doc = line.split(",");
				list.add(doc);
				cols = doc.length;
			}
			matrix = new int[list.size()][cols];
			for(String[] doc : list ){
				for(int col = 0; col < doc.length; col++){
					matrix[rows][col] = Integer.parseInt(doc[col]);
				}
			rows++;	
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return matrix;
	  }

	
	public double max(double[] array){
		
		double max = array[0];
		for (int i = 1; i < array.length; i++) {
		    if (array[i] > max) {
		      max = array[i];
		    }
		}
		return max;
	}
	
	public double min(double[] array){
		
		double min = array[0];
		for (int i = 1; i < array.length; i++) {
		    if (array[i] < min) {
		    	min = array[i];
		    }
		}
		return min;
	}
	
	public Map<Integer, Integer> frequency(int[] array) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < array.length; i++) {
			if (!map.containsKey(array[i])) {
				map.put(array[i], 1);
			} else {
				map.put(array[i], map.get(array[i]) + 1);
			}
		}
		return map;
	}
	
	public Map<String, int[][]> splitArray(int[][] multiarray, int clas){
		
		Map<String, int[][]> result = new HashMap<String, int[][]>();
		int rows = multiarray.length;
		int cols = multiarray[0].length;

		
		int[] intArray = ArrayUtils.toPrimitive(classes_list.toArray(new Integer[classes_list.size()]));

		int freq = frequency(intArray).get(clas);
		
		int[][] positive = new int[freq][cols];
		int[][] negative = new int[(rows-freq)][cols];
		int pos_row = 0; int neg_row =0;
		
		for (int r = 0; r < rows; r++) {
			if (multiarray[r][cols - 1] == clas) {
				for (int c = 0; c < cols - 1; c++) {
						positive[pos_row][c] = multiarray[r][c];
					}
				pos_row++;
				}
			else{
				for (int c = 0; c < cols - 1; c++) {
					negative[neg_row][c] = multiarray[r][c];
				}
				neg_row++;
			}
		}
		result.put("positive", positive);
		result.put("negative", negative);
		
		return result;
	}
	
	public List<int[]> transpose(int[][] arr){
		List<int[]> res = new ArrayList<int[]>();
		int C = arr[0].length;
		int R = arr.length;
		for (int c = 0 ; c != C ; c++) {
	        int[] row = new int[R];
	        for (int r = 0 ; r < R ; r++) {
	            row[r] = arr[r][c];
	        }
	        res.add(row);
	    }
		return res;
	}
	
	public Set<Integer> numberOfClasses(int[][] arr){
		int C = (arr[0].length)-1;
		int R = arr.length;
	    for (int r = 0 ; r < R ; r++) {
	    	classes_list.add(arr[r][C]);
	    }
		return new HashSet<Integer>(classes_list);
	}
	
	public int[] max_terms(int[][] docs){
		
		int C = docs[0].length;
		int R = docs.length;
		int[] maxTerms = new int[C-1];
		for (int c = 0 ; c < C-1 ; c++) {
			int max = docs[0][c];
	        for (int r = 1 ; r < R ; r++) {
	        	if(max < docs[r][c]){
	        		max = docs[r][c];
	        	}
	        }
	        maxTerms[c] = max;
	    }
		return maxTerms;
	}
	
	public int addArray(int[] array){
		int sum = 0;
		for(int i =0; i <array.length; i++){
			sum = array[i];
		}
		return sum;
	}
	
	public double[] averageLength(int[][] docs){
		int C = docs[0].length;
		int R = docs.length;
		Set<Integer> classes = new HashSet<Integer>();

	    for (int r = 0 ; r < R ; r++) {
	    	classes.add(docs[r][C-1]);
	    }
		
		double[] avergeLength = new double[classes.size()];
		
		Double all_sum = 0.0;
		int count = 0;
		
		for(int cls : classes){
			int sum = 0; 
			for (int r = 0 ; r < R ; r++) {
				if(cls == docs[r][C-1]){
					for (int c = 0 ; c < C-1 ; c++) {
						sum += docs[r][c];
					}
				}
			}
			all_sum += sum;
			avergeLength[count] = sum;
			count++;
		}
		all_sum = all_sum/R;
		for(int i=0; i < avergeLength.length; i++){
			avergeLength[i] = all_sum/avergeLength[i];
		}
		
		return avergeLength;
	}
	
}