package com.ml.decisiontree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class RandomFunction {

	public static int avgDepth = 0;
	public static Node randomRoot = null;
	public static Node root = null;
	public static int nodes=1;
	public static int leafnodes=0;
	public static int attrCount;
	public static int tree_depth=0;
	
	
	public static Node GenerateDecisionTreeRandom(ArrayList<Instance> dataset){
			
		Node rootNode=null;
		int selectedAttr=0;
		int min = 1;
		int max = 19;
		Queue<Node> q = new LinkedList<Node>();		
		q.offer(rootNode);
		
		while(!q.isEmpty()){
			
			rootNode = q.poll();
			if(rootNode==null){
				rootNode = new Node();
				rootNode.rowData = dataset;
				rootNode.node = nodes++;	
				rootNode.classLabel = DecisionTree.getClassLabel(rootNode.rowData);
				root = rootNode;			
			}
			selectedAttr = selectAttrUsingRandom(rootNode.rowData,rootNode.deleted_attrs, min, max);
			rootNode.attribute = selectedAttr;
			rootNode.classLabel = DecisionTree.getClassLabel(rootNode.rowData);
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
					rootNode.left.classLabel = DecisionTree.getClassLabel(rootNode.left.rowData);
				}
				
				rootNode.right = ID3Algorithm.classifyData(rootNode, "1");
				rootNode.right.node = nodes++;
				rootNode.right.deleted_attrs.addAll(0, rootNode.deleted_attrs);
				if(ID3Algorithm.findEntropy(rootNode.right.rowData)!=0) {
					q.offer(rootNode.right);				
				} else {
					rootNode.right.isLeafNode = true;
					leafnodes++;
					rootNode.right.classLabel = DecisionTree.getClassLabel(rootNode.right.rowData);
				}
			} else {
				rootNode.isLeafNode = true;
				leafnodes++;
				rootNode.node = nodes++;
				rootNode.classLabel = DecisionTree.getClassLabel(rootNode.rowData);
			}
		}
		return root;
	}
	
	private static int selectAttrUsingRandom(ArrayList<Instance> rowData, ArrayList<Integer> deleted_attrs, int min, int max) {
		Random randFn = new Random();
		int rand = randFn.nextInt(max-min);
		System.out.println("deleted attrs "+deleted_attrs.toString());
		while(deleted_attrs.contains(rand)){
			if(deleted_attrs.size()==18)
				return -1;
			rand = randFn.nextInt(max-min);
			System.out.println("deleted attrs "+deleted_attrs.toString());
			System.out.println(rand);
		}
		return rand;
	}

	public static Node classifyData(Node root, String attrValue) {

		Node node = new Node();
		Iterator<Instance> iter = root.rowData.iterator();

		while (iter.hasNext()) {
			Instance inst = (Instance) iter.next();
			if (inst.attributes[root.attribute].equals(attrValue)) {
				node.rowData.add(inst);
			}
		}
		return node;
	}

	public static double AverageDepth(Node root) {
		int sumOfNodes = GetSumOfNodes(root, 0);
		return (double)sumOfNodes/(nodes-1);
	}

	private static int GetSumOfNodes(Node root, int i) {
		if(root == null){
			return 0;
		}
		else if(root.left == null && root.right == null){
			return i;//depth
		}
		else{
			return GetSumOfNodes(root.left, i+1)+GetSumOfNodes(root.right, i+1);
		}
	}
}