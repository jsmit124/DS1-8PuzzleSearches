/**
 * 
 */
package edu.westga.cs3151.the8puzzle.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

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
		Queue<Node> nextNodes = new LinkedList<Node>(); 
		Node source = new Node(this.board, new LinkedList<Move>()); 
		nextNodes.add(source); 
		var visited = new HashSet<String>();
		visited.add(this.board.getTileString());
		
		while (!nextNodes.isEmpty()) {
			var nextSearchNode = nextNodes.remove(); 
			visited.add(nextSearchNode.nodeReached.getTileString());
			
			if (nextSearchNode.nodeReached.isSorted()) { 
				return nextSearchNode.path; 
			}
			
			var sourcePosition = nextSearchNode.nodeReached.getEmptyTilePosition();
			var neighbors = sourcePosition.getNeighbors();
			for (var neighbor : neighbors) { 
				var currBoard = new Board(nextSearchNode.nodeReached);
				var newMove = new Move(neighbor, sourcePosition);
				currBoard.moveTile(newMove);

				if (!visited.contains(currBoard.getTileString())) {
					Queue<Move> currPath = new LinkedList<Move>(nextSearchNode.path);					
					currPath.add(newMove);
					var newNode = new Node(currBoard, currPath);
					nextNodes.add(newNode);				
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Only returns the sequence of moves to solve the next piece of the puzzle
	 * 
	 * @return
	 * 	The queue of moves which will result in the next piece of the puzzle being solved
	 */
	public Queue<Move> getHelp() {
		var solution = this.findSolution();
		var helpMoves = new LinkedList<Move>();
		var currNumSorted = this.board.getNumberSortedTiles();
		var boardClone = new Board(this.board);
		
		for (var move : solution) {
			boardClone.moveTile(move);
			helpMoves.add(move);
			if (boardClone.getNumberSortedTiles() > currNumSorted) {
				break;
			}
		}
		
		return helpMoves;
	}
	
	private final class Node {
		private Board nodeReached;
		private Queue<Move> path;
		
		private Node(Board nodeReached, Queue<Move> queueMoves) {
			this.path = queueMoves;
			this.nodeReached = nodeReached;
		}
	}

}
