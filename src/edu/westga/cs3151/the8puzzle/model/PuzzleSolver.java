/**
 * 
 */
package edu.westga.cs3151.the8puzzle.model;

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
		Stack<Node> moves = new Stack<Node>(); // use a queue for BFS
		Node rootNode = new Node(this.board.getEmptyTilePosition());
		moves.push(rootNode);
		
		while (!moves.empty()) {
			var neighbors = moves.pop().value.getNeighbors();
			for (var value : neighbors) {
				Node newNode = new Node(value);
				moves.push(newNode);
			}
		}
		
		return null;
	}
	
	
	private final class Node {
		private Position value;
		private stack
		
		private Node(Position item) {
			this.value = item;
			this.next = null;
			this.previous = null;
		}
	}

}
