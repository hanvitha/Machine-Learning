package dataset;

public class DecisionTree {

	Node root = null;
	public static int height = 0;
	
	public void insert(Node parentNode, boolean right, String newNodeName){
		Node newNode = new Node(newNodeName);
		insertNode(parentNode,right, newNode);
	}
	public void insertNode(Node parentNode, boolean right, Node newNode ){
		if(root == null){
			root = newNode; 
			height ++;
		}
		else if(right){
			newNode.parent = parentNode;
			parentNode.right = newNode;
		}
		else{
			newNode.parent = parentNode;
			parentNode.left = newNode;
		}
	}
	
	public void displayDecisionTree(){
		inOrderDisplay(root);
		}
	private void inOrderDisplay(Node root) {
		if(root == null){
			return;
		}
		else if(root.pure){
			System.out.println(root.name + " = 0 : " + (root.classes[0]));
			System.out.println(root.name + " = 1 : " + (root.classes[1]));
		}
		else{
			System.out.println(root.name + " = 0 : " );
			System.out.print("|		");
			inOrderDisplay(root.left);
			System.out.print("|		");
			inOrderDisplay(root.right);
		}
	}
	public void buildTree(String filePath) {
		
		
	}
}
