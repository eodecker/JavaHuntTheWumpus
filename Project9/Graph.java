/** 
* 04/30/2018
* Eli Decker
* Graph.java
*/

import java.util.ArrayList;
import java.util.PriorityQueue;


public class Graph {
	private ArrayList<Vertex> graph;



	public Graph() {
		this.graph = new ArrayList<Vertex>();
	}

	//returns the number of vertices in the graph
	public int vertexCount() {
		return this.graph.size();
	}

	// adds given vertex to the list
	public void addVertex( Vertex ver) {
		this.graph.add( ver );
	}

	// returns the list of vertices
	public ArrayList<Vertex> getList () {
		return graph;
	}

	// returns the index of the given Vertex
	public int getIndex( Vertex a) {
		return graph.indexOf( a );
	}

	public Vertex getVertex( int index ) {
		return graph.get(index);
	}

	//adds v1 and v2 to the graph (if necessary) and adds an edge connecting v1 to v2 via direction dir and 
	//a second edge connecting v2 to v1 in the opposite direction, creating a bi-directional link.
	public void addEdge(Vertex src, Direction dir, Vertex des) {
		if (!graph.contains(src)) graph.add(src);
		if (!graph.contains(des)) graph.add(des);
		
		src.connect(des, dir);
		des.connect(src, Vertex.opposite(dir)); 
	}



	//implements a single-source shortest-path algorithm for the Graph, Dijkstra's algorithm. 
	//This method finds the cost of the shortest path between v0 and every other connected vertex in the graph, 
	//counting every edge as having unit cost.
	public void shortestPath(Vertex v0) {
		// Given: a graph G and starting vertex v0 in G
	
		// Initialize all vertices in G to be unmarked and have infinite cost
		for (Vertex v : graph) {
			v.setMarked(false);
			v.setCost(Integer.MAX_VALUE);
		}
		// Create a priority queue, q, that orders vertices by lowest cost
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>( );
		// Set the cost of v0 to 0 and add it to q
		v0.setCost(0);
		q.add(v0);

		while (!q.isEmpty() ) {
			Vertex v = q.poll();
			v.setMarked(true);
			for (Vertex w : v.getNeighbors() ) {
				if (!w.isMarked() && v.getCost() + 1 < w.getCost() ){
					w.setCost( v.getCost() + 1);
					q.remove(w);
					q.offer(w);
				}
			}
		}

	}

	// test function
	public static void main( String[] argv ) {
		Graph g = new Graph();
		// Vertex v1 = new Vertex(1);
		// Vertex v2 = new Vertex(2);
		// Vertex v3 = new Vertex(3);
		// Vertex v4 = new Vertex(4);
		// Vertex v5 = new Vertex(5);
		// Vertex v6 = new Vertex(6);
		// Vertex v7 = new Vertex(7);
		// Vertex v8 = new Vertex(8);
		// Vertex v9 = new Vertex(9);
		// g.addEdge( v1, Direction.EAST, v2);
		// g.addEdge( v2, Direction.SOUTH, v3);
		// g.addEdge( v3, Direction.WEST, v4);
		// g.addEdge( v4, Direction.NORTH, v1);
		// g.addEdge( v1, Direction.WEST, v5);
		// g.addEdge( v1, Direction.NORTH, v6);

		// g.addEdge( v6, Direction.NORTH, v7);
		// g.addEdge( v7, Direction.NORTH, v8);
		// g.addEdge( v8, Direction.NORTH, v9);
		// g.shortestPath(v1);
		// System.out.println("TEST: " + v9);
	}
}