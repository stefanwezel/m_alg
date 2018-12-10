package edmondsKarp;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {

	private int[][] adjacencyMatrix; // contains the capacities; if a capacity is 0, there is no edge
	private int[][] flowMatrix;

	private int numberOfVertices;
	private int sourceVertexId;     // the source of this graph
	private int targetVertexId;     // the sink of this graph

	/**
	 * Creates a new Graph
	 * @param adjacencyMatrix
	 */
	public Graph(int[][] adjacencyMatrix) {
		this.numberOfVertices = adjacencyMatrix.length;
		this.adjacencyMatrix  = adjacencyMatrix;
		flowMatrix            = new int[numberOfVertices][numberOfVertices];
		sourceVertexId        = 0;
		targetVertexId        = numberOfVertices-1;
	}

	/**
	 * Sets the flow from <code>source</code> to <code>target</code>.
	 * @param source
	 * @param target
	 * @param flow
	 */
	public void setFlow(int source, int target, int flow) {
		flowMatrix[source][target] = flow;
	}

	/**
	 * Returns the flow from <code>source</code> to <code>target</code>.
	 * @param source
	 * @param target
	 * @return
	 */
	public int getFlow(int source, int target) {
		return flowMatrix[source][target];
	}

	/**
	 * Returns the capacity of edge (<code>source</code>, <code>target</code>).
	 * @param source
	 * @param target
	 * @return
	 */
	public int getCapacity(int source, int target) {
		return adjacencyMatrix[source][target];
	}

	/**
	 * Returns the residual matrix (that is the matrix representing the residual graph).
	 * @return
	 */
	public int[][] getResidualMatrix() {
		int size = numberOfVertices;
		int[][] residualMatrix = new int[size][size];

		for (int i=0; i<numberOfVertices; i++) {
			for (int j=0; j<numberOfVertices; j++) {
				residualMatrix[i][j] = getResidualValue(i, j);
			}
		}
		return residualMatrix;
	}



	/**
	 * Calculates the value of edge (<code>source</code>, <code>target</code>) in the residual graph.
	 * @param source
	 * @param target
	 * @return
	 */
	private int getResidualValue(int source, int target) {
		// use capacity and flow to calculate residual value
		return getCapacity(source, target) - getFlow(source, target);
	}


	/**
	 * Calculates the shortest path using breadth-first search in the residual graph.
	 * @param residualMatrix
	 * @return the shortest path from source to target
	 */
	public List<Integer> getShortestSTPath(int[][] residualMatrix) {
		// use a linked list to represent pathsc
		// init new list as current shortest path
		List<Integer> shortestPath = new LinkedList<Integer>();
		int start = sourceVertexId;
		int end = targetVertexId;
		// new path
		Queue<Integer> queue = new LinkedList<Integer>();
		boolean[] isVisited = new boolean[numberOfVertices];
		isVisited[start] = true;
		int currNode = -1;
		int[] visited = new int[numberOfVertices];

		// add start node to queue and then subsequentially go through nodes, adding them to the queue and backtrack
		queue.add(start);
		while(!queue.isEmpty()) {
			currNode = queue.poll();
			if(currNode == end){
				int backTrackPath = end;
				while(backTrackPath != start) {
					shortestPath.add(backTrackPath);
					backTrackPath = visited[backTrackPath];
				}
				shortestPath.add(start);

				return shortestPath;
			}
			// go through graph and calc residual matrix for each node at step
			//if new not visited, add to queue
			for (int i = 0; i < numberOfVertices; i++) {
				int result = residualMatrix[currNode][i];
				if(result > 0 && !isVisited[i]) {
					queue.add(i);
					visited[i] = currNode;
					isVisited[i] = true;
				}
			}	
		}
		return shortestPath;
	}


	/**
	 * Calculate the MaxFlow by executing the algorithm of Edmonds & Karp.
	 */
	public void executeEdmondsKarp() {
		// initiate residual matrix
		int[][] residualMatrix = getResidualMatrix();
		// initiate a linked list, representing the shortest path
		List<Integer> shortestPath = getShortestSTPath(residualMatrix);
		// define a threshold for bottleneck
		int threshold = 100;
		// initiate values on start
		int start = -1;
		int next = -1;
		int capacity = -1;
		
		while(!shortestPath.isEmpty()){
			threshold = 100;
			start = -1;
			for (int i = 0; i < shortestPath.size(); i++) {
				if(start == -1) {
					next = start;
					start = shortestPath.get(i);
				}
				else {
					next = start;
					start = shortestPath.get(i);
					capacity = residualMatrix[start][next];	

					 if (capacity < threshold) threshold = capacity;
				}
			} 
			start = -1;
			next = -1;
			
			for (int j = 0; j < shortestPath.size(); j++) {
				if(start == -1) {
					next = start;
					start = shortestPath.get(j);
				}
				else {
					next = start;
					start = shortestPath.get(j);
					int flow = getFlow(start, next);
					setFlow(start, next, threshold + flow);
				}
			}
			residualMatrix = getResidualMatrix();
			shortestPath = getShortestSTPath(residualMatrix);
		}

	}







	/**
	 * Prints the capacities of this graph.
	 */
	public void printCapacities() {
		System.out.println("Capacities:");
		for (int i=0; i<numberOfVertices; i++) {
			for (int j=0; j<numberOfVertices; j++) {
				System.out.print(getCapacity(i, j) + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Prints the flows of this graph.
	 */
	public void printFlows() {
		System.out.println("Current flows:");
		for (int i=0; i<numberOfVertices; i++) {
			for (int j=0; j<numberOfVertices; j++) {
				System.out.print(getFlow(i, j) + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Prints the current residual matrix.
	 */
	public void printResidualMatrix() {
		System.out.println("Current residual matrix:");

		int[][] residualMatrix = getResidualMatrix();
		for (int i=0; i<numberOfVertices; i++) {
			for (int j=0; j<numberOfVertices; j++) {
				System.out.print(residualMatrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Prints a given path.
	 * @param path
	 */
	public void printShortestPath() {
		int[][]       residualMatrix = getResidualMatrix();
		List<Integer> shortestPath   = getShortestSTPath(residualMatrix);
		if (shortestPath.isEmpty()) {
			System.out.println("No s-t-path in residual graph.");
		}
		else {
			System.out.println("Shortest s-t-path in residual graph:");
			for (int i=0; i<shortestPath.size(); i++) {
				System.out.print(shortestPath.get(i) + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}


	private static void example(int[][] capacities) {
		Graph g = new Graph(capacities);
		g.printCapacities();
		g.printFlows();
		g.printShortestPath();
		g.executeEdmondsKarp();
		g.printFlows();
		//g.printResidualMatrix();
		g.printShortestPath();
	}


	public static void main(String[] args) {

		// First graph for testing (example from Wikipedia: Algorithmus von Ford und Fulkerson)
		int[][] capacities1 = {
				{0,4,2,0},
				{0,0,3,1},
				{0,0,0,6},
				{0,0,0,0}
		};
		example(capacities1);

		// Second graph for testing (example from Wikipedia: Maximum flow problem)
		int[][] capacities2 = {
				{0,3,3,0,0,0},
				{0,0,2,3,0,0},
				{0,0,0,0,2,0},
				{0,0,0,0,4,2},
				{0,0,0,0,0,3},
				{0,0,0,0,0,0},
		};
		example(capacities2);

		// Third graph for testing (example from assignment 4)
		int[][] capacities3 = {
				{0,9,3,2,0,0,0,0},
				{0,0,0,0,0,9,0,0},
				{0,0,0,4,0,0,0,0},
				{0,0,0,0,6,0,0,0},
				{0,0,0,0,0,0,4,0},
				{0,0,0,3,0,0,4,1},
				{0,0,0,0,0,0,0,9},
				{0,0,0,0,0,0,0,0},
		};
		example(capacities3);
	}

}
