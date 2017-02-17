package dataset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FetchDataFromCSV {
	public static ArrayList<Integer> classes = new ArrayList<Integer>();
	
	public void fetchData(String filePath) throws FileNotFoundException{
		String rowData;
		ArrayList<String> features = new ArrayList<String>();
		int rowCount = 0;
		Map<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
			try{
				while(( rowData = br.readLine() ) != null){
					String[] tempRow = rowData.split(",");
					if(rowCount ==0){
						features = fetchFeatures(tempRow);
						rowCount++;
					}
					else{
						map = saveDataSet(map, features, rowCount, tempRow);	
						classes.add(rowCount, Integer.parseInt(tempRow[tempRow.length-1]));
						System.out.println(map+ "hi");
						rowCount++;
						System.out.println(classes);
					}
				}
			}
			catch(IOException ie){
				ie.printStackTrace();
			}
	}

	private Map<String, ArrayList<Integer>> saveDataSet(Map<String, ArrayList<Integer>> map, ArrayList<String> features, int rowCount, String[] tempRow) {
		
		ArrayList<Integer> newArray;
		
		try{
		for(int i = 0; i<tempRow.length-2; i++){			
			if(!map.isEmpty() && map.containsKey(features.get(i))){
				newArray =  map.get(features.get(i));
			}
			else{
				newArray = new ArrayList<>();				
			}
			newArray.add(Integer.parseInt(tempRow[i]));
			map.put(features.get(i), newArray);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	private ArrayList<String> fetchFeatures(String[] tempRow) {
		ArrayList<String> featureSet = new ArrayList<String>();
		for(int i = 0; i<tempRow.length-1; i++){
			featureSet.add(tempRow[i]);
		}
		return featureSet;
	}
	public static void main(String[] args) throws FileNotFoundException{
		FetchDataFromCSV fd = new FetchDataFromCSV();
		//fd.fetchData("F:\\UTD\\Machine Learning\\assignments\\data_sets1\\training_set.csv");
		//check the file name
		fd.fetchData("/media/ruth/Ruth/Eclipse Neon Workspace/Machine Learning/data/data_sets1/training_set.csv");
		
	}
	
}
