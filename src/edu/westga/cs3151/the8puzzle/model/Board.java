package edu.westga.cs3151.the8puzzle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * The Class Board
 * 
 * Represents a solvable 8-puzzle board.
 * 
 * @author CS3151
 */
public class Board {

	/**
	 * The entry of 0 represents the empty tile. The other entries (1, 2, 3, ..., 8)
	 * represent the tile value.
	 */
	private int[][] tiles;

	/**
	 * Instantiates an 8-puzzle board
	 * 
	 * @pre none
	 * @post the 8-puzzle board with tiles arranged in sequence
	 */
	public Board() {
		this.tiles = new int[Position.MAX_ROWS][Position.MAX_COLS];
		for (Position pos : Position.values()) {
			this.tiles[pos.getRow()][pos.getCol()] = (Position.MAX_ROWS * pos.getRow() + pos.getCol() + 1)
					% Position.MAX_POSITIONS;
		}
	}

	/**
	 * Instantiates a 8-puzzle board where tiles are placed as in the specified
	 * board
	 * 
	 * @pre board != null
	 * @post getTile(pos).equals(board.getTile(pos)) for all possible positions pos
	 * @param board the board to be copied
	 */
	public Board(Board board) {
		if (board == null) {
			throw new IllegalArgumentException("board cannot be null");
		}
		this.tiles = new int[Position.MAX_ROWS][Position.MAX_COLS];
		for (Position pos : Position.values()) {
			this.setTile(pos, board.getTile(pos));
		}
	}

	/**
	 * Shuffles the tiles randomly. First arranges the tiles randomly, then counts
	 * the number of inversions. If the number of inversions is odd, two consecutive
	 * numbers > 0 are swapped to obtain a solvable puzzle.
	 * 
	 * @pre none
	 * @post solvable 8-puzzle board with randomly arranged tiles
	 */
	public void shuffle() {
		ArrayList<Integer> tileNumbers = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
		Collections.shuffle(tileNumbers);
		if (this.getInversionCount(tileNumbers) % 2 == 1) {
			if (tileNumbers.get(0) > 0 && tileNumbers.get(1) > 0) {
				Integer number = tileNumbers.get(0);
				tileNumbers.set(0, tileNumbers.get(1));
				tileNumbers.set(1, number);
			} else {
				Integer number = tileNumbers.get(2);
				tileNumbers.set(2, tileNumbers.get(3));
				tileNumbers.set(3, number);
			}
		}
		int index = 0;
		for (Position pos : Position.values()) {
			this.tiles[pos.getRow()][pos.getCol()] = tileNumbers.get(index);
			index++;
		}
	}

	/**
	 * Gets the tile number at the specified position
	 * 
	 * @pre none
	 * @post none
	 * @param pos the position
	 * @return the tile number of the specified position
	 */
	public int getTile(Position pos) {
		return this.tiles[pos.getRow()][pos.getCol()];
	}

	/**
	 * Sets the tile number at the specified position
	 * 
	 * @pre none
	 * @post none
	 * @param pos  the position
	 * @param tile the tile number
	 */
	public void setTile(Position pos, int tile) {
		this.tiles[pos.getRow()][pos.getCol()] = tile;
	}

	/**
	 * Gets the number of tiles that are arranged in sequence starting at position
	 * (0, 0)
	 * 
	 * @pre none
	 * @post none
	 * @return the number of tiles arranged in sequence starting at (0, 0)
	 */
	public int getNumberSortedTiles() {
		int numSorted = 0;
		for (Position pos : Position.values()) {
			if ((numSorted + 1) != this.getTile(pos)) {
				return numSorted;
			}
			numSorted++;
		}
		return numSorted;
	}

	/**
	 * Gets the position that does not contain a tile
	 * 
	 * @pre none
	 * @post none
	 * @return the position without a tile
	 */
	public Position getEmptyTilePosition() {
		for (Position pos : Position.values()) {
			if (this.getTile(pos) == 0) {
				return pos;
			}
		}
		return null;
	}

	/**
	 * Determines whether all tiles are sorted
	 * 
	 * @pre none
	 * @post none
	 * @return true if the tiles are sorted
	 */
	public boolean isSorted() {
		return this.getNumberSortedTiles() >= Position.MAX_POSITIONS - 1;
	}

	/**
	 * Moves the tile at position src to position dest if there is a tile at the
	 * position src and the tile can be moved to the position dest according to the
	 * 8-puzzle rules for moving a tile.
	 * 
	 * @pre src != null and dest != null
	 * @post the board where tile at position src has been moved to dest if src can
	 *       be moved to dest according to the 8-puzzle rules
	 * 
	 * @param src  the position of the tile to be moved
	 * @param dest the destination position to move the tile
	 * 
	 * @return true if the positions specify a valid move, false otherwise
	 */
	public boolean moveTile(Position src, Position dest) {
		if (src == null) {
			throw new IllegalArgumentException("source position cannot be null");
		}
		if (dest == null) {
			throw new IllegalArgumentException("destination position cannot be null");
		}
		
		var destinationNeighbors = dest.getNeighbors();
		for (var position : destinationNeighbors) {
			if (position.equals(src)) {
				this.setTile(dest, this.getTile(src));
				this.setTile(src, 0);
				
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Moves the tile as specified by the position move
	 * 
	 * @pre move != null && move.getDestination() is the empty position
	 * @post the board where move has been made
	 * 
	 * @param move the move with the valid source and destination position
	 * 
	 * @return true if the specified move represents a valid move in this board
	 */
	public boolean moveTile(Move move) {
		if (move == null) {
			throw new IllegalArgumentException("move cannot be null");
		}

		return this.moveTile(move.getSource(), move.getDestination());
	}

	/**
	 * Counts the number inversions of values greater 0 in the array list
	 * 
	 * @pre none
	 * @post none
	 * @param values whose number inversions is counted
	 * @return number inversions
	 */
	private int getInversionCount(ArrayList<Integer> values) {
		int count = 0;
		for (int i = 0; i < values.size(); i++) {
			for (int j = i + 1; j < values.size(); j++) {
				if (values.get(i) > 0 && values.get(j) > 0 && values.get(i) > values.get(j)) {
					count++;
				}
			}
		}
		return count;
	}
}
