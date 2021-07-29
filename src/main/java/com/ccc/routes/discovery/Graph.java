package com.ccc.routes.discovery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Graphs and the trees are somewhat similar by their structure. In fact, tree is derived from 
 * the graph data structure. However there are two important differences between trees and graphs.
 *  
 * 1. Unlike trees, in graphs, a node can have many parents.
 * 2. The link between the nodes may have values or weights.
 * 
 * Graphs are good in modeling real world problems like representing cities which are connected by roads and finding the 
 * paths between cities, modeling air traffic controller system, etc. These kinds of problems are hard to represent using 
 * simple tree structures. Every graph has two components, Nodes and Edges.
 * 
 * Test Input Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
 *                          5            4
 *                   A ----------> B --------> C
 *                   |\            ^          /^
 *                 5 | \    7      |         / |
 *                   |  \--------> E <------/  |
 *                   |             ^           |
 *                   |  ____6_____/            |
 *                   V /                       |
 *                   D <------------------------
 *                                         8
 * 
 * Edges represent the connection between nodes. There are two ways to represent edges.
 * 
 * ====================
 * | Adjacency Matrix |
 * ====================
 * It is a two dimensional array with Boolean flags. As an example, we can represent the edges for the above graph using 
 * the following adjacency matrix.
 * -------------------------
 * |   | A | B | C | D | E |
 * | A | 0 | 1 | 0 | 1 | 1 |
 * | B | 0 | 0 | C | 0 | 0 |
 * | C | 0 | 0 | 0 | 1 | 1 |
 * | D | 0 | 0 | 1 | 0 | 1 |
 * | E | 0 | 1 | 0 | 0 | 0 |
 * -------------------------
 * 
 * In the given graph, A is connected with B, D, and E nodes, so adjacency matrix will have 1s in the 'A' row for the 'B', 
 * 'D' and 'E' columns.  
 * 
 * The advantages of representing the edges using adjacency matrix are: 
 * 1. Simplicity in implementation as you need a 2-dimensional array 
 * 2. Creating edges/removing edges is also easy as you need to update the Booleans 
 * 
 * The drawbacks of using the adjacency matrix are:  
 * 1. Increased memory as you need to declare N*N matrix where N is the total number of nodes.
 * 2. Redundancy of information, i.e. to represent an edge between A to B and B to A, it requires to set two Boolean flag 
 * in an adjacency matrix. 
 * 
 * In JAVA, we can represent the adjacency matrix as a 2 dimensional array of integers/Booleans.
 * 
 * ==================
 * | Adjacency List |
 * ==================  
 * It is an array of linked list nodes. In other words, it is like a list whose elements are a linked list. For the given 
 * graph example, the edges will be represented by the below adjacency list:
 * 
 * 0 ---> A=[town: B distance: 5, town: D distance: 5, town: E distance: 7]
 * 1 ---> B=[town: C distance: 4]
 * 2 ---> C=[town: D distance: 8, town: E distance: 2]
 * 3 ---> D=[town: C distance: 8, town: E distance: 6]
 * 4 ---> E=[town: B distance: 3]
 * 
 * 
 * 
 * @author gibrancastillo
 *
 */
public class Graph {
	int size;
	
	//Node
	public Node rootNode;
	
	//Edges will be represented as adjancency matrix
	public int[][] adjMatrix;
	
	//Adjancency List
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	public void setRootNode(Node n) {
		this.rootNode = n;
	}
	
	public Node getRootNode() {
		return this.rootNode;
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	/**
	 * This method will be called to make connect two nodes (create an edge).
	 * @param start
	 * @param end
	 */
	public void connectNode(Node start, Node end) {
		if(adjMatrix == null) {
			size = nodes.size();
			adjMatrix = new int[size][size];
		}
		
		int startIndex = nodes.indexOf(start);
		int endIndex = nodes.indexOf(end);
		adjMatrix[startIndex][endIndex] = 1;
		adjMatrix[endIndex][startIndex] = 1;
	}
	
	private Node getUnvisitedChildNode(Node n) {
		int index = nodes.indexOf(n);
		int j = 0;
		
		while(j < size) {
			/*
			 * If child node (vertice) has an edge with parent node and (vertice) is unvisited (mark as false)
			 * then return the child node (vertice) to be mark as visited, print out and enqueue.
			 */
			if(adjMatrix[index][j] == 1 && ((Node) nodes.get(j)).visited == false) {
				return (Node) nodes.get(j);
			}
			
			j++;
		}
		
		return null;
	}
	
	/**
	 * Breadth First Search (BFS)  
	 * The aim of BFS algorithm is to traverse the graph as close as possible to the root node.  Queue is used in the implementation of the breadth first search.
	 * Let's see how BFS traversal works with respect to the graph listed above
	 * 
	 * If we do the breadth first traversal of the above graph and print the visited node as the output, it will print the following output. 'A B C D E F'.
	 * The BFS visits the nodes level by level, so it will start with level 0 which is the root node, and then it moves to the next levels which are B, C and D, then the last levels which are E and F.  
	 * 
	 * Algorithmic Steps   
	 * Step 1: Push the root node in the Queue.
	 * Step 2: Loop until the queue is empty.
	 * Step 3: Remove the node from the Queue.
	 * Step 4: If the removed node has unvisited child nodes, mark them as visited and insert the unvisited children in the queue.
	 * 
	 * Based upon the above steps, the following Java code shows the implementation of the BFS algorithm: 
	 */
	public void bfs() {
		//BFS uses Queue data structure
		Queue<Node> q = new LinkedList<Node>();
		q.add(this.rootNode);  //Enqueue root node (vertice) - add to head of this queue.
		this.rootNode.visited = true;  //Mark as visited.
		printNode(this.rootNode);  //Print Node label as output.
		
		//Is the queue empty?
		while(!q.isEmpty()) {
			Node n = (Node) q.remove(); //Dequeue first element from the queue - retrieves and removes the head of this queue.
			Node child = null;
			
			/*
			 * To get unvisited child node(s) at this level, you have to do it inside this while loop
			 * This mechanism and the Queue data structure enable the bfs algorithm.
			 * 
			 * Find unvisited vertices (nodes) connected with it and mark them as visited and enqueue the visited vertices (nodes).
			 */
			while((child = getUnvisitedChildNode(n)) != null) {
				child.visited = true;  //Mark as visited.
				printNode(child);  //Print Node label as output.
				q.add(child);  //Enqueue visited node (vertice) - add to head of this queue.
			}
		}
		
		//Clear visited property of nodes
		clearNodes();
	}
	
	/**
	 * Depth First Search (DFS) 
	 * The aim of DFS algorithm is to traverse the graph in such a way that it tries to go far from the root node. Stack is used in the implementation of the depth first search.
	 * Let's see how DFS works with respect to the graph listed above   
	 * 
	 * In DFS, nodes are visited by going through the depth of the tree from the starting node. If we do the depth first traversal of the above graph and print the visited node, it will be
	 * A B E F C D.  DFS visits the root node and then its children nodes until it reaches the end node, i.e. E and F nodes, then moves up to the parent nodes. 
	 * 
	 * Algorithmic Steps   
	 * Step 1: Push the root node in the Stack.
	 * Step 2: Loop until stack is empty.
	 * Step 3: Peek the node of the stack.
	 * Step 4: If the node has unvisited child nodes, get the unvisited child node, mark it as traversed and push it on stack.
	 * Step 5: If the node does not have any unvisited child nodes, pop the node from the stack.
	 * 
	 * Based upon the above steps, the following Java code shows the implementation of the DFS algorithm:  
	 */
	public void dfs() {
		//DFS uses Stack data structure
		Stack<Node> s = new Stack<Node>();
		s.push(this.rootNode);  //Pushes a node onto the top of this Stack.
		this.rootNode.visited = true;
		printNode(this.rootNode);
		
		while(!s.isEmpty()) {
			Node n = (Node) s.peek();  //Looks at the Node at the top of this stack without removing it from the stack.
			Node child = getUnvisitedChildNode(n); //To get the next unvisited child node 
			
			if(child != null) {
				child.visited = true;
				printNode(child);
				s.push(child);
			} else {
				s.pop();  //Removes the Node at the top of this stack and returns that Node as the value of this function
			}
		}
		
		//Clear visited property of nodes
		clearNodes();
	}
	
	/**
	 * Utility method for clearing visited property of node
	 */
	private void clearNodes() {
		int i = 0;
		
		while(i < size) {
			Node n = (Node) nodes.get(i);
			n.visited = false;
			i++;
		}
	}
	
	/**
	 * Utility method for printing the node's label
	 * 
	 * @param n
	 */
	private void printNode(Node n) {
		System.out.print(n.label + " ");
	}
}