package com.ml.neuralnet;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
	public static int layerCount;
	public static int noOfNeurons;
	public static int[] layerNeuronCount;
	public static ArrayList<Layer> layers;
	public static ArrayList<ArrayList<Float>> Weights;
	public static float errorTolerance;
	public static float learningRate;

	public static void initLayers(int[] layersSizes, Instance instance) {
		int neuronCount = 0;
		layerCount = layersSizes.length + 2;
		layers = new ArrayList<Layer>();
		Layer templayer = new Layer();
		Layer temp;
		int isNotInputLayer = 0; 
		for (int i = 0; i < layerCount; i++) {
			if (i == 0)
				neuronCount = instance.numericValues.length - 1;
			else if (i == layerCount - 1){
				neuronCount = 1;
				isNotInputLayer = 1; 
			}			
			else{
				neuronCount = layersSizes[i - 1];
				isNotInputLayer = 1;
			}
			temp = templayer.initLayer(i, neuronCount, noOfNeurons, isNotInputLayer);
			layers.add(temp);
			noOfNeurons = noOfNeurons + temp.neuronCount;
		}

	}

	public void BuildNeuralNet(ArrayList<Instance> dataset, int[] layersSizes, int trainPercent, float lr, float error) {
		int flag1 = 0;
		int flag2 = 0;
		initLayers(layersSizes, dataset.get(0));
		errorTolerance = error;
		learningRate = lr;
		generateRandomWeights();
		System.out.println("size: " + dataset.size() * trainPercent / 100);
		do {
			for (int i = 0; i < dataset.size() * trainPercent/100; i++) {
				// for(int i=0; i<50;i++){
				Instance.error = trainNeuralNet(dataset.get(i));
				System.out.println("Error generated newly is : " + Instance.error);
				if (flag1 == 1 && Instance.error <= errorTolerance) {
					flag2 = 1;
					System.out.println("error cdn met!!");
					break;
				}
				// System.out.println("Error = " + Instance.error);
			}
			flag1 = 1;
			System.out.println("done with an iteration!");
		} while (flag2 != 1);

	}

	private float trainNeuralNet(Instance instance) {
		float error = 0;
		BackPropagation bp = new BackPropagation();
		setInputLayer(instance);
		setOutputLayer(instance);

		error = bp.forwardPass();
		// if(error > errorTolerance){
		bp.backPropagation();
		// }
		return error;

	}

	private void setOutputLayer(Instance instance) {
		layers.get(layerCount - 1).neurons.get(0).classValue = instance.numericValues[instance.numericValues.length
				- 1];
		// System.out.println("class value
		// "+layers.get(layerCount-1).neurons.get(0) );
	}

	private void generateRandomWeights() {
		for (int i = 0; i < layers.size() - 1; i++) {
			for (Neuron currentNeuron : layers.get(i).neurons) {
				for (Neuron nextLayerNeuron : layers.get(i + 1).neurons) {
					currentNeuron.weights.put(nextLayerNeuron.id, getRandomWeight());
					// System.out.println(nextLayerNeuron.id + " next layer
					// id");
				}
				// System.out.println(currentNeuron.weights + "is the weight of
				// neuron " + currentNeuron.id);

			}
		}

	}

	public static float getRandomWeight() {	
		Random rand = new Random();
		return 1;
	}

	private void setInputLayer(Instance instance) {
		for (int i = 0; i < layers.get(0).neurons.size(); i++) {
			layers.get(0).neurons.get(i).value = instance.numericValues[i];
		}
	}

	public void testNeuralNet(ArrayList<Instance> dataset, int trainPercent) {
		float error;
		BackPropagation bp = new BackPropagation();
		
		for (int i = (int) (dataset.size() * trainPercent/100); i < dataset.size(); i++) {
			error = 0;
			setInputLayer(dataset.get(i));
			setOutputLayer(dataset.get(i));	

			error = bp.forwardPass();
			System.out.print("\ndata:\n  ");
			for(int j=0;j<dataset.get(i).numericValues.length;j++){
				System.out.print("  "+dataset.get(i).values[j]);
			}
			System.out.print("\npreprocessed data:\n  ");
			for(int j=0;j<dataset.get(i).numericValues.length;j++){
				System.out.print("  "+ dataset.get(i).numericValues[j]);
			}
			System.out.println("\nTesting error of row "+i+" is : " + error);
		}
	}



}
