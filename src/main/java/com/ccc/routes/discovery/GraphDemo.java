package com.ccc.routes.discovery;

/**
 * A Java Console application which creates a simple directed graph and then invokes the DFS and BFS traversal of the graph.
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
 * @author gibrancastillo
 *
 */
public class GraphDemo {
	public static void main(String[] args) {
		//Create nodes as given in graph above
		Node nA = new Node('A');
		Node nB = new Node('B');
		Node nC = new Node('C');
		Node nD = new Node('D');
		Node nE = new Node('E');
		
		//Create the graph
		Graph g = new Graph();
		
		//Add nodes
		g.addNode(nA);
		g.addNode(nB);
		g.addNode(nC);
		g.addNode(nD);
		g.addNode(nE);
		
		//Set root node
		g.setRootNode(nA);
		/*
		 * Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
		 * 0 ---> A=[town: B distance: 5, town: D distance: 5, town: E distance: 7]
		 */
		//Create edges between nodes
		g.connectNode(nA, nB);
		g.connectNode(nA, nD);
		g.connectNode(nA, nE);
		
		//1 ---> B=[town: C distance: 4]
		g.connectNode(nB, nC);
		
		//2 ---> C=[town: D distance: 8, town: E distance: 2]
		g.connectNode(nC, nD);
		g.connectNode(nC, nE);
		
		//3 ---> D=[town: C distance: 8, town: E distance: 6]
		g.connectNode(nD, nC);
		g.connectNode(nD, nE);
		
		//4 ---> E=[town: B distance: 3]
		g.connectNode(nE, nB);
		
		//Perform the traversal of the graph
		System.out.println("Start DFS Traversal of a tree -------------->");
		g.dfs();
		System.out.println("\n<---------------- End DFS Traversal of a tree");
		
		
		System.out.println("\n\nStart BFS Traversal of a tree -------------->");
		g.bfs();
		System.out.println("\n<---------------- End BFS Traversal of a tree");
	}
}