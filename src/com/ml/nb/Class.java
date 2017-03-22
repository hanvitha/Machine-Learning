package com.ml.nb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Class {
	public String name;
	public int docSize;
	public double prior;
	public ArrayList<Integer> vocabCountList;// from all docs
	public int vocabCount;
	public ArrayList<Double> condprob;
	
	public static ArrayList<String> commonWords = new ArrayList<>(Arrays.asList("i", "me", "my", "myself", "we", "our", "ours",
			"ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her",
			"hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which",
			"who", "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being",
			"have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or",
			"because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into",
			"through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on",
			"off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how",
			"all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only",
			"own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now", "d",
			"ll", "m", "o", "re", "ve", "y", "ain", "aren", "couldn", "didn", "doesn", "hadn", "hasn", "haven", "isn",
			"ma", "mightn", "mustn", "needn", "shan", "shouldn", "wasn", "weren", "won", "wouldn"));

	Class(String name) {
		this.name = name;
	}

	public ArrayList<Integer> processDocAndUpdateclass(String file) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			String str = br.readLine();
			while (str!=null &&!str.contains("Lines:")) {
				str = br.readLine();
			}
			str = br.readLine();
			while (str != null) {

				str = str.replaceAll("[^a-zA-Z]+|^\\s", " ");
				str = str.trim().replaceAll(" +", " ").toLowerCase();

				if (str.length() >= 1) {
					String[] vocab = str.split(" ");
					for (int i = 0; i < vocab.length; i++) {

						if (NaiveBayesClassifier.Vocabulary == null)
							NaiveBayesClassifier.Vocabulary = new ArrayList<String>();
						if (this.vocabCountList == null)
							this.vocabCountList = new ArrayList<Integer>();

						if (commonWords.contains(vocab[i])) {
							continue;
						} else {
							int index = NaiveBayesClassifier.Vocabulary.size();
							if (!NaiveBayesClassifier.Vocabulary.contains(vocab[i]))
								NaiveBayesClassifier.Vocabulary.add(vocab[i]);
							else
								index = NaiveBayesClassifier.Vocabulary.indexOf(vocab[i]);

							if (this.vocabCountList.size() > index) {
								this.vocabCountList.set(index, this.vocabCountList.get(index) + 1);
							} else {
								this.vocabCountList.add(1);
							}
							vocabCount++;
						}
					}
				}
				str = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void calculateCPs() {
		this.condprob = new ArrayList<Double>();
		ArrayList<Integer> tempList = this.vocabCountList;
		for(int i=0; i<tempList.size() ; i++){
			double cp = (double)(tempList.get(i)+1)/(this.vocabCount + NaiveBayesClassifier.Vocabulary.size());
			this.condprob.add(cp);
		}
	}
}
