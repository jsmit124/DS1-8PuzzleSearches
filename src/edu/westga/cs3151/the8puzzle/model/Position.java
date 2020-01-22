package edu.westga.cs3151.the8puzzle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * The Class Position
 * 
 * @author CS3151
 */
public class Position {

	public static final int MAX_ROWS = 3;
	public static final int MAX_COLS = 3;
	public static final int MAX_POSITIONS = Position.MAX_ROWS * Position.MAX_COLS;

	private static Collection<Position> positions = new ArrayList<Position>(Arrays.asList(new Position(0, 0),
			new Position(0, 1), new Position(0, 2), new Position(1, 0), new Position(1, 1), new Position(1, 2),
			new Position(2, 0), new Position(2, 1), new Position(2, 2)));

	private int row;
	private int col;

	/**
	 * Instantiates a new position for a tile of an 8-puzzle
	 * 
	 * @pre row >= 0 and row < MAX_ROWS and col >= 0 and col < MAX_COLS
	 * @post getRow() == row and getCol() == col
	 * @param row the row
	 * @param col the column
	 */
	public Position(int row, int col) {
		if (row < 0 || row >= MAX_ROWS) {
			throw new IllegalArgumentException("invalid row index of 8-puzzle");
		}
		if (col < 0 || col >= MAX_COLS) {
			throw new IllegalArgumentException("invalid col index of 8-puzzle");
		}
		this.row = row;
		this.col = col;
	}

	/**
	 * Gets the row
	 * 
	 * @pre none
	 * @post none
	 * @return the row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Gets the column
	 * 
	 * @pre none
	 * @post none
	 * @return the column
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * Gets the neighboring positions of the specified position
	 * 
	 * @pre none
	 * @post none
	 * @return the collection of neighboring positions
	 */
	public Collection<Position> getNeighbors() {
		Collection<Position> neighbors = new ArrayList<Position>();
		if (this.col > 0) {
			neighbors.add(new Position(this.row, this.col - 1));
		}
		if (this.col < 2) {
			neighbors.add(new Position(this.row, this.col + 1));
		}
		if (this.row > 0) {
			neighbors.add(new Position(this.row - 1, this.col));
		}
		if (this.row < 2) {
			neighbors.add(new Position(this.row + 1, this.col));
		}
		return neighbors;
	}

	/**
	 * Gets the collection of all possible positions
	 * 
	 * @pre none
	 * @post none
	 * @return the collection of positions
	 */
	public static Collection<Position> values() {
		return Position.positions;
	}

	/**
	 * Determines if this position equals the specified position
	 * 
	 * @return true if this position and the specified position have the same row
	 *         and column value
	 */
	@Override
	public boolean equals(Object pos) {
		if (pos == null) {
			throw new IllegalArgumentException("Param 'pos' in Position::equals method cannot be null");
		}
		
		if (((Position) pos).getRow() == this.getRow() && ((Position) pos).getCol() == this.getCol()) {
			return true;
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return 3 * this.getRow() + this.getCol();
	}
}
