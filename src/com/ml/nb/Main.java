package com.ml.nb;

import java.util.ArrayList;

public class Main {
	public static ArrayList<Class> trainingData;
	public static ArrayList<Class> testingData;
	
	public static void main(String[] args) {
		
		// Process data
		String trainingDataPath = args[0];
		String testDataPath = args[1];
		long startTime = System.currentTimeMillis();
		trainingData = NaiveBayesClassifier.ProcessData(trainingDataPath);
		
		int accuracy = (int) (100* NaiveBayesClassifier.TestClassifier(testDataPath));
		System.out.println("Accuracy " + accuracy);
		long endTime   = System.currentTimeMillis();
		System.out.println("Time taken totally = " +(endTime - startTime)/1000+ " seconds");
		
	}
}
