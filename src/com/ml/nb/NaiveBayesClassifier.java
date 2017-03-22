package com.ml.nb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NaiveBayesClassifier {
	public static ArrayList<Class> classes;
	public static ArrayList<String> Vocabulary;
	public static int totalNoOfDocs;
	public static int classCount;

	public static ArrayList<Class> ProcessData(String trainingDataPath) {
		initClasses(trainingDataPath);
		return classes;
	}

	private static void initClasses(String trainingDataPath) {

		File[] classDirs = new File(trainingDataPath).listFiles(File::isDirectory);
		if(classDirs.length>5)
			classCount = 5;
		else
			classCount = classDirs.length;
		for (int i = 0; i < classCount; i++) {
			if (classes == null)
				classes = new ArrayList<Class>();
			System.out.println(classDirs[i]);
			Class c = initClass(classDirs[i]);
			totalNoOfDocs = totalNoOfDocs + c.docSize;
			classes.add(c);
			System.out.println(c.docSize);
			System.out.println(c.vocabCountList);		}
		for (int i = 0; i < classCount; i++) {
			classes.get(i).prior = (double) classes.get(i).docSize / totalNoOfDocs;
			classes.get(i).calculateCPs();
		}
		System.out.println(Vocabulary);
	}

	private static Class initClass(File classFile) {
		String[] classPath = classFile.toString().split("\\\\");
		File[] docs = classFile.listFiles();
		Class newClass = new Class(classPath[classPath.length - 1]);
		newClass.docSize = docs.length;
		totalNoOfDocs = totalNoOfDocs + newClass.docSize;
		if (Vocabulary != null)
			newClass.vocabCountList = new ArrayList<Integer>(Collections.nCopies(Vocabulary.size(), 0));
		else
			newClass.vocabCountList = new ArrayList<Integer>();

		for (int i = 0; i < docs.length; i++) {
			newClass.processDocAndUpdateclass(docs[i].toString());
		}

		return newClass;
	}

	public static double TestClassifier(String testDataPath) {
		int correctCount = 0;
		int totalCount = 0;
		File[] classDirs = new File(testDataPath).listFiles(File::isDirectory);
		for (int i = 0; i < classCount; i++) {
			String[] classPath = classDirs[i].toString().split("\\\\");
			File[] docs = classDirs[i].listFiles();
			for (int j = 0; j < docs.length; j++) {
				if (classify(docs[i].toString()).equals(classPath[classPath.length - 1]))
					correctCount++;
				totalCount++;
			}
		}
		return (double) correctCount / totalCount;
	}

	private static String classify(String path) {
		double maxLikelihood = -100000000;
		int maxLikeliHoodClassIndex = 0;
		for (int classIndex = 0; classIndex < classCount; classIndex++) {
			Class c = classes.get(classIndex);
			double score = Math.log(c.prior);
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(path));

				String str = br.readLine();
				while (str != null && !str.contains("Lines:")) {
					str = br.readLine();
				}
				str = br.readLine();
				while (str != null) {

					str = str.replaceAll("[^a-zA-Z]+|^\\s", " ");
					str = str.trim().replaceAll(" +", " ").toLowerCase();

					if (str.length() >= 1) {
						String[] vocab = str.split(" ");
						int index = 0;
						double p = 0;
						for (int i = 0; i < vocab.length; i++) {
							if (Class.commonWords.contains(vocab[i])) {
								continue;
							}
							if (!Vocabulary.contains(vocab[i]))
								p = 1;
							else {
								index = Vocabulary.indexOf(vocab[i]);
								if (index < c.vocabCountList.size())
									p = c.condprob.get(index);
							}
							score = score + Math.log(p);
						}
					}
					str = br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (maxLikelihood < score) {
				maxLikelihood = score;
				maxLikeliHoodClassIndex = classIndex;
			}	
		}
		return classes.get(maxLikeliHoodClassIndex).name;

	}
}
