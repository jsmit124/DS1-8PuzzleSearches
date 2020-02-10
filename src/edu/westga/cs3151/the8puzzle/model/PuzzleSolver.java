/**
 * 
 */
package edu.westga.cs3151.the8puzzle.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Stores information for the PuzzleSolver class
 * 
 * @author jsmit124
 *
 * @version 1.0
 */
public class PuzzleSolver {
	private Board board;
	
	/**
	 * Creates a new instance of the PuzzleSolver object
	 * 
	 * @pre board != null
	 * @post this.board == board && this.rootPos == board.getEmptyTilePosition()
	 * 
	 * @param board
	 * 		The current state of the board to be solved
	 */
	public PuzzleSolver(Board board) {
		if (board == null) {
			throw new IllegalArgumentException("Param 'board' in PuzzleSolver constructor cannot be null.");
		}
		
		this.board = board;
	}
	
	/**
	 * Finds the optimal moves to get to the solution of the puzzle
	 * 
	 * @pre board != null
	 * @post none
	 * 
	 * @return
	 * 		the moves required to optimally solve the board
	 */
	public Queue<Move> findSolution() {
		Queue<Node> nextNodes = new LinkedList<Node>(); // new queue of available moves
		Node source = new Node(this.board, new LinkedList<Move>()); // create source node
		nextNodes.add(source); // add source to nextNodes
		
		while (!nextNodes.isEmpty()) {
			var nextSearchNode = nextNodes.remove(); // remove a node, nextSearchNode, from nextNodes
			
			if (nextSearchNode.nodeReached.isSorted()) { // if the node, nextSearchNode, is a destination node
				System.out.println("solution found");
				return nextSearchNode.path; // return the path stored in nextSearchNode
			}
			
			System.out.println("node checked, adding neighbors");
			
			var sourcePosition = nextSearchNode.nodeReached.getEmptyTilePosition();
			var neighbors = sourcePosition.getNeighbors();
			for (var neighbor : neighbors) { // for all neighbors, neighbor, of the node in nextSearchNode
				var currBoard = new Board(nextSearchNode.nodeReached);
				Queue<Move> currPath = new LinkedList<Move>(nextSearchNode.path);

				var newMove = new Move(neighbor, sourcePosition);
				
				currPath.add(newMove);
				currBoard.moveTile(newMove);
				
				var newNode = new Node(currBoard, currPath);
				nextNodes.add(newNode);
				System.out.println("neighbor added");
			}
			
			System.out.println("Checking next node");
		}
		
		return null; // no solution found
	}
	
	
	private final class Node {
		public Board nodeReached; // node that has been reached by the algorithm
		private Queue<Move> path; // path through which this node was reached
		
		private Node(Board nodeReached, Queue<Move> queueMoves) {
			this.path = queueMoves;
			this.nodeReached = nodeReached;
		}
	}

}
