package com.ml.decisiontree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Pruning {
	
	/*
	 * Function Name: pruneTree()
	 * Description: Function to prune the tree constructed by either of the algorithm
	 * Input: 
	 * Root - Root node of the tree
	 * testing_data - Testing data set
	 * Validation_data_1 - Validation data set
	 * no_of_nodes_to_prune - No of nodes selected for pruning
	 * countNode - No of nodes in the tree
	 * Output: NONE 
	 */
	public static void pruneTree(Node root,ArrayList<Instance> testing_data,ArrayList<Instance> validation_data_1,float prune_factor,int node_Count){
		ArrayList<Integer> nodesToPrune = new ArrayList<Integer>();
		//Pruning random nodes to find best accuracy
		Random rand = new Random();
		//assuming pruning trials to be upto 30 times. to avoid infinite loop
		int randCount = 30;
		int max = node_Count;
		int min = 1;
		int count = 0;
		int nodesToBePruned = (int) (prune_factor * node_Count);
		while(count < nodesToBePruned && randCount>0){
			int randNo = rand.nextInt((max - min) + 1) + min;
			if(!nodesToPrune.contains(randNo)){
				Node temp = getNode(root,randNo);
				if(temp.left!= null && temp.right!= null){
					if(temp.left!=null)
						temp.left = null;
					if(temp.right!=null)
						temp.right=null;
					temp.classLabel = DecisionTree.getClassLabel(temp.rowData);
					temp.isLeafNode = true;
					nodesToPrune.add(randNo);
					count++;
				}
			}
			randCount--;
		} 
		System.out.println("****************************************************");
		System.out.println("Post-pruning Accuracy:");
		System.out.println("Accuracy after pruning on validation data = "+findAccuracy(root,validation_data_1));
		System.out.println("Accuracy after pruning on testing data = "+findAccuracy(root,testing_data));
	}	

	/*
	 * Description: Given a Root of a tree and a node id returns a node with given node id
	 * Input: 
	 * Root - Root node of the tree
	 * Node Id - Node Id to be searched
	 * Output: Returns Node with given Node Id
	 */
	public static Node getNode(Node root,int nodeId){
		Node node1 = null;
		Queue<Node> q = new LinkedList<Node>();
		q.offer(root);
		
		while(!q.isEmpty()) {
			node1 = q.poll();
			if(node1.node==nodeId){
				break;
			} else{
				if(node1.left!= null)
					q.offer(node1.left);
				if(node1.right!=null)
					q.offer(node1.right);
			}
		}
		return node1;
	}
	
	
	/*
	 * To calculate the accuracy of a given decision tree using test data
	 * Input: root ,test_data
	 * Output: Returns accuracy of a given decision tree model
	 */	
	public static double findAccuracy(Node root, ArrayList<Instance> test_data){
		Node temp = null;
		double accuracy = 0.0;		
		Iterator<Instance> iter = test_data.iterator();
		int count = 0;
		
		while(iter.hasNext()){
			Instance record = iter.next();
			temp = root;
			while(!temp.isLeafNode){
				if(record.attributes[temp.attribute].equals("0"))
					temp = temp.left;
				else
					temp = temp.right;
			}		
			if(String.valueOf(temp.classLabel).equals(record.classValue)){
				count++;
			} 
		}
		System.out.println("Test data size = "+ test_data.size());
		accuracy = (double)(count*100)/test_data.size();		
		return accuracy;
	}	
	
}
