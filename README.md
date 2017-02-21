<h1> Implementation of ID3 decision tree algorithm and Pruning </h1>

<b> Language used : </b> JAVA
</br></br>
<b>How to run the program from command line:</b></br>
javac main.java</br>
java main training_set.csv validation_set.csv test_set.csv 0.2 </br>
</br>
<b> Run configurations to be given in IDE: </b></br>
"E:\Eclipse Neon Workspace\Machine Learning\data\data_sets1\training_set.csv" (your training data set path) </br>
"E:\Eclipse Neon Workspace\Machine Learning\data\data_sets1\validation_set.csv" (your validation data set path) </br>
"E:\Eclipse Neon Workspace\Machine Learning\data\data_sets1\test_set.csv" (your test data set path) </br>
“0.2” (pruning factor) </br></br>
<b>Java project name:</b> MachineLearning</br></br>
<b> Assumptions made: </b></br>
1. Assuming pruning trails to be upto 30 times. </br>
2. Test data contains only boolean values. </br>
3. There will be no missing data. </br>
4. First row of the data set will contain column names and each non blank line after that will contain a new data instance. </br>
5. Last column contins class labels. </br>

