<h1> Building decision tree using both ID3 algorithm and Random Selection Method and Pruning </h1>

<b> Language used : </b> JAVA
</br></br>
<b>How to run the program from command line:</b></br>
javac main.java</br>
java main training_set.csv validation_set.csv test_set.csv 0.2 </br>
</br>


<b>Java project name:</b> Machine Learning</br></br>

<b>Source code:</b></br>
src/com/ml/decisiontree</br>

<b>Steps to run the code in eclipse:</b></br>
1)open Main.java in src/com/ml/decisiontree
2)Add run configurations as</br>

<b> Run configurations to be given in IDE: </b></br>
"E:\Eclipse Neon Workspace\Machine Learning\data\data_sets1\training_set.csv" "E:\Eclipse Neon Workspace\Machine Learning\data\data_sets1\validation_set.csv" "E:\Eclipse Neon Workspace\Machine Learning\data\data_sets1\test_set.csv" 0.1</br>

<b> Assumptions made: </b></br>
1. Assuming pruning trails to be upto 30 times.</br>
2. Test data contains only boolean values. </br>
3. First row of the data set will contain column names and each non blank line after that will contain a new data instance. </br>
4. Last column contins class labels. </br>

