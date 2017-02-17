package com.ml.decisiontree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;



public class DecisionTree {
	
	public static Node root = null;
	public static int nodes=1;
	public static int leafnodes=0;
	public static int attrCount;
	public static int tree_depth=0;
	

	/*
	 * Function Description: Constructs decision tree using ID3 Algorithm
	 * Input: 
	 * training_data - Training data set
	 * Output: displays builtDecisionTree
	 * 
	 * */

	public static Node GenerateDecisionTree(ArrayList<Instance> dataset, String dataSetType){
			
		Node rootNode=null;
		int selectedAttr=0;
		
		Queue<Node> q = new LinkedList<Node>();		
		q.offer(rootNode);
		
		while(!q.isEmpty()){
			
			rootNode = q.poll();
			if(rootNode==null){
				rootNode = new Node();
				rootNode.rowData = dataset;
				rootNode.node = nodes++;	
				rootNode.classLabel = getClassLabel(rootNode.rowData);
				root = rootNode;			
			}
			selectedAttr = ID3Algorithm.calcInformationGain(rootNode.rowData,rootNode.deleted_attrs);
			rootNode.attribute = selectedAttr;
			rootNode.classLabel = getClassLabel(rootNode.rowData);
			rootNode.deleted_attrs.add(selectedAttr);
			if(selectedAttr!=-1){
				rootNode.left = ID3Algorithm.classifyData(rootNode,"0");
				rootNode.left.node = nodes++;
				rootNode.left.deleted_attrs.addAll(0, rootNode.deleted_attrs);
				if(ID3Algorithm.findEntropy(rootNode.left.rowData)!=0){
					q.offer(rootNode.left);
				} else {
					rootNode.left.isLeafNode = true;
					leafnodes++;
					rootNode.left.classLabel = getClassLabel(rootNode.left.rowData);
				}
				
				rootNode.right = ID3Algorithm.classifyData(rootNode, "1");
				rootNode.right.node = nodes++;
				rootNode.right.deleted_attrs.addAll(0, rootNode.deleted_attrs);
				if(ID3Algorithm.findEntropy(rootNode.right.rowData)!=0) {
					q.offer(rootNode.right);				
				} else {
					rootNode.right.isLeafNode = true;
					leafnodes++;
					rootNode.right.classLabel = getClassLabel(rootNode.right.rowData);
				}
			} else {
				rootNode.isLeafNode = true;
				leafnodes++;
				rootNode.node = nodes++;
				rootNode.classLabel = getClassLabel(rootNode.rowData);
			}
		}
		System.out.println("***************************************************");
		System.out.println("Number of "+dataSetType+" instances = "+ dataset.size());
		System.out.println("Number of "+dataSetType+" attributes = "+ Instance.attributeLabels.size());
		System.out.println("***************************************************");
		
		return root;
	}
	

	
	
	/*
	 * Function Name: getClassLabel()
	 * Description: Function to determine the class label for given data
	 * Input: 
	 * data - data set
	 * Output: Returns the class label for a given data set 
	 */	
	public static char getClassLabel(ArrayList<Instance> rowData){		
		
		int classLabel0 = 0;
		int classLabel1 = 0;
		
		Iterator<Instance> iter = rowData.iterator();
		
		while(iter.hasNext()){
			Instance inst= (Instance)iter.next();
			if(inst.classValue.equals("1"))
				classLabel1++;
			else
				classLabel0++;
		}		
		if(classLabel1 > classLabel0){
			return '1';
		}else{
			return '0';
		}
	}
	
	

	public void displayTree(Node root){
		tree_depth++;
		Node node1=root;
		if(getAttributeLabel(node1.attribute)=="-1"){
			System.out.print(" "+ node1.classLabel);
		}
		else{
			System.out.println();
			for(int i=0; i<tree_depth;i++){
				System.out.print(" | ");
			}
			System.out.print(getAttributeLabel(node1.attribute) + " = 0 :");
		}

		if(node1.left != null){
			displayTree(node1.left);
			if(getAttributeLabel(node1.attribute)=="-1"){
				System.out.print(" "+ node1.classLabel);
			}
			else{
				System.out.println();
				for(int i=0; i<tree_depth;i++){
					System.out.print(" | ");
				}
				System.out.print(getAttributeLabel(node1.attribute) + " = 1 :" );
			}
			displayTree(node1.right);
		}
		tree_depth--;
	}
	


		/*
		 * Function Name: getAttributeLabel()
		 * Description: Function to return a attribute label given a attribute number
		 * Input: 
		 * attributeNo - Integer value for the given attribute number
		 * Output: Returns the corresponding string representing the attribute 
		 */	
	public static String getAttributeLabel(int attributeNo){
		if(attributeNo!=-1){
			return Instance.attributeLabels.get(attributeNo);
		}
		else{
			return "-1";
		}
	}
		
}
