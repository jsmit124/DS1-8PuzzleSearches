package edu.westga.cs3151.the8puzzle.model;

/**
 * The Class Move
 *
 * @author CS3151
 */
public class Move {
	
	private Position source;
	private Position destination;
	
	/**
	 * Instantiates a new move.
	 *
	 * @pre none
	 * @post getSource().equals(src) && getDestination().equals(dest)
	 * @param src the source position
	 * @param dest the destination position
	 */
	public Move(Position src, Position dest) {
		if (src == null) {
			throw new IllegalArgumentException("source position cannot be null");
		}
		if (dest == null) {
			throw new IllegalArgumentException("destination position cannot be null");
		}
		this.source = src;
		this.destination = dest;
	}
	
	/**
	 * Gets the source.
	 *
	 * @pre none
	 * @post none
	 * @return the source
	 */
	public Position getSource() {
		return this.source;
	}
	
	/**
	 * Gets the destination.
	 *
	 * @pre none
	 * @post none
	 * @return the destination
	 */
	public Position getDestination() {
		return this.destination;
	}
}
