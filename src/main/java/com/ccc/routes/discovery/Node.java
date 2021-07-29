package com.ccc.routes.discovery;

/**
 * Nodes are implemented by class, structures or as Link-List nodes.  In this case I represent node as follows: 
 * 
 * @author gibrancastillo
 *
 */
public class Node {
	public char label;
	public boolean visited = false;
	
	public Node(char l) {
		this.label = l;
	}
}