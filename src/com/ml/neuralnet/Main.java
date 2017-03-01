package com.ml.neuralnet;

import java.util.ArrayList;

public class Main {
	public static String datasetPath;
	public static ArrayList<Instance> dataset;
	public static int trainPercent;
	public static float errorTolerance;
	public static int noOfHiddenLayers;
	public static void main(String args[]) {
		int layerSize[];
		
		//input
		datasetPath = args[0];
		
		
		//fetchingData and pre process
		dataset = Preprocessing.FetchAndPreprocessInput(datasetPath);
		NeuralNetwork neuralnet = new NeuralNetwork();
		trainPercent = Integer.parseInt(args[1]);
		errorTolerance =  Float.parseFloat(args[2]);
		noOfHiddenLayers = Integer.parseInt(args[3]);
		layerSize = new int[noOfHiddenLayers];
		for(int i=0; i < noOfHiddenLayers; i++){
			layerSize[i] =Integer.parseInt( args[4+i]);
		}
		
		neuralnet.initLayers(layerSize);
		
		//train
		
		neuralnet = BackPropagation.BuildNeuralNet(dataset);
		
		
		//test
		
		
		
		
	}

}
