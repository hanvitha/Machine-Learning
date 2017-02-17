package com.ml.decisiontree;

import java.util.ArrayList;
import java.util.Iterator;

public class ID3Algorithm {
	/*
	 * Function Name: calcInformationGain()
	 * Description: Function to calculate the information gain for given dataset
	 * Input: 
	 * data - Given data for the node
	 * attributes_fixed - Arraylist that contains the attributes that are not allowed to be selecte
	 * since they are already chosen by the ancestors
	 * Output: Return the attribute index with maximum information gain for the given data set 
	 */	
	public static int calcInformationGain(ArrayList<Instance> data,ArrayList<Integer> attributes_fixed)
	{
		double classEntropy = findEntropy(data);
		
		double num_pos=0;
		double num_neg=0;
		double num_pos_0=0;
		double num_neg_0=0;
		double num_pos_1=0;
		double num_neg_1=0;
		double gain=0,maxGain=0;
		double constant = Math.log10(2);
		int maxGainIndex=-1;
		int data_size = data.size();
		
		if(classEntropy==0)
			return -1;
		
		for(int j=0;j<Instance.attributeCount;j++)
		{
			for(int i=0;i<data.size();i++)
			{
				Instance inst= data.get(i);
				if(inst.attributes[j].matches("1"))
				{
					num_pos++;
					if(inst.classValue.matches("1"))
						num_pos_1++;
					else
						num_neg_1++;
				}else{
					num_neg++;
					if(inst.classValue.matches("1"))
						num_pos_0++;
					else
						num_neg_0++;
				}
			}
			
			double prob_pos_0 = (double)num_pos_0/num_neg;
			double prob_pos_1 = (double)num_pos_1/num_pos;
			double prob_neg_0 = (double)num_neg_0/num_neg;
			double prob_neg_1 = (double)num_neg_1/num_pos;
			double term1 = 0, term2 = 0;
			if(prob_pos_1==0)
				term1 = 0;
			else
				term1 = Math.log10(prob_pos_1)/constant;
			
			if(prob_neg_1==0)
				term2 = 0;
			else 
				term2 = Math.log10(prob_neg_1)/constant;
			
			double entropy_1 = -((prob_pos_1*term1) + (prob_neg_1*term2));
			
			if(prob_pos_0==0)
				term1 = 0;
			else
				term1 = Math.log10(prob_pos_0)/constant;
			
			if(prob_neg_0==0)
				term2 = 0;
			else
				term2 = Math.log10(prob_neg_0)/constant;
			
			double entropy_0 = -((prob_pos_0*term1) + (prob_neg_0*term2));
			
			gain = classEntropy - (((double)num_pos/(double)data_size)*entropy_1) - (((double)num_neg/(double)data_size)*entropy_0);
				if(j==0 && !Double.isNaN(gain) && !attributes_fixed.contains(j))
				{
					maxGain = gain;
					maxGainIndex = j;
				}
				else
				{
					if(gain>maxGain && !Double.isNaN(gain) && !attributes_fixed.contains(j))
					{
						maxGain=gain;
						maxGainIndex=j;
					}
				}
				gain=0;
				num_pos_0=0;
				num_pos_1=0;
				num_neg_0=0;
				num_neg_1=0;
				num_pos=0;
				num_neg=0;		
		}
		return maxGainIndex;
	}
	

	/*
	 * Function Name: classifyData()
	 * Description: Function is to create data based on the attributes selected
	 * Input: 
	 * Root node,
	 * attribute_value -0 or 1 to classify data
	 * Output: returns a child node of the given root node with classified data for given attrValue
	 */	
	public static Node classifyData(Node root,String attrValue) {
		
		Node node = new Node(); 
		Iterator<Instance> iter = root.rowData.iterator();
		
		while(iter.hasNext()){
			Instance inst = (Instance)iter.next();
			if(inst.attributes[root.attribute].equals(attrValue)){
				node.rowData.add(inst);
			}
		}
		
		return node;
	}

	
	/*
	 * Function Name: Entropy()
	 * Description: To calculate entropy of a class
	 * Input: data of the class
	 * output: the class Entropy 
	 */	
	
	public static double findEntropy(ArrayList<Instance> data){
	
		double entropy = 0.0;
		int size = data.size();
		int count1 = 0;
		int count0=0;
		double constant = Math.log10(2);
		double term1 = 0, term2 = 0;
		
		Iterator<Instance> iter = data.iterator();
		if(size==0)
			return 0;
		else{
			while(iter.hasNext()){
				String value = iter.next().classValue;
			    if(value.equals("1")){
			    	count1++;
			    } else{
			    	count0++;
			    }
	        }
			double class_1_probability = (double)count1/size;
			double class_0_probability = (double)count0/size;
			if(class_1_probability==class_0_probability)
				entropy = 1;
			else if(class_1_probability==1 || class_0_probability==1)
				entropy = 0;
			else {
				if(class_1_probability==0)
					term1 = 0;
				else 
					term1 = Math.log10(class_1_probability)/constant;
				if(class_0_probability==0)
					term2 = 0;
				else
					term2 = Math.log10(class_0_probability)/constant;
						
				entropy = - ((class_1_probability*term1) + (class_0_probability*term2));
			}
		}
		return entropy;
	}
	
	
}
