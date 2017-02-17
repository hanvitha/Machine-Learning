package dataset;

public class Node {
	String name;
	Node parent;
	Node left;
	Node right;
	boolean pure;
	boolean[] classes = {false,false};
	public Node(String name) {
		this.name = name;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.pure = false;
	}

}
