package dataset;

public class Main {

	public static void main(String[] args) {
		String trainingSetPath = "/media/ruth/Ruth/Eclipse Neon Workspace/Machine Learning/data/data_sets1/training_set.csv";
		String validationSetPath = "/media/ruth/Ruth/Eclipse Neon Workspace/Machine Learning/data/data_sets1/validation_set.csv";
		String testSetPath = "/media/ruth/Ruth/Eclipse Neon Workspace/Machine Learning/data/data_sets1/test_set.csv";
		
		DecisionTree dt = new DecisionTree();
		dt.buildTree(trainingSetPath);
//		dt.ValidateTree(validationSetPath);
//		dt.TestTheTree(testSetPath);
		

	}

}
