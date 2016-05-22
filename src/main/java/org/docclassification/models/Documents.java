package org.docclassification.models;

public class Documents {

	private static int numOfPositiveDoc;
	private static int numOfNegativeDoc;
	
	public static int getNumOfPositiveDoc() {
		return numOfPositiveDoc;
	}
	public static void setNumOfPositiveDoc(int numOfPositiveDoc) {
		Documents.numOfPositiveDoc = numOfPositiveDoc;
	}
	public static int getNumOfNegativeDoc() {
		return numOfNegativeDoc;
	}
	public static void setNumOfNegativeDoc(int numOfNegativeDoc) {
		Documents.numOfNegativeDoc = numOfNegativeDoc;
	}
	
	
}
