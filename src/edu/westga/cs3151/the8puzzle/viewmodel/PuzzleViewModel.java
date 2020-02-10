package edu.westga.cs3151.the8puzzle.viewmodel;

import java.util.Queue;
import java.util.Stack;

import edu.westga.cs3151.the8puzzle.model.Board;
import edu.westga.cs3151.the8puzzle.model.Move;
import edu.westga.cs3151.the8puzzle.model.Position;
import edu.westga.cs3151.the8puzzle.model.PuzzleSolver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

/**
 * The Class PuzzleViewModel
 * 
 * @author CS3151
 */
public class PuzzleViewModel {

	private final StringProperty[][] tileNumberProperty;
	private final BooleanProperty solvedBoardProperty;

	private Board board;
	private Stack<Position> movesMade;

	/**
	 * Instantiates a new student info view model.
	 * 
	 * @pre none
	 * @post a new view model representing a random 8-puzzle
	 */
	public PuzzleViewModel() {
		this.board = new Board();
		this.board.shuffle();
		this.tileNumberProperty = new StringProperty[Position.MAX_ROWS][Position.MAX_COLS];
		for (Position pos : Position.values()) {
			this.tileNumberProperty[pos.getRow()][pos.getCol()] = new SimpleStringProperty();
		}
		this.setTilesForView();
		this.solvedBoardProperty = new SimpleBooleanProperty(false);
		
		this.movesMade = new Stack<Position>();
	}

	/**
	 * Gets the tile number property.
	 *
	 * @pre none
	 * @post none
	 * @param row the row number of the tile
	 * @param col the column number of the tile
	 * @return the tile number property
	 */
	public StringProperty tileNumberProperty(int row, int col) {
		return this.tileNumberProperty[row][col];
	}

	/**
	 * Gets the solved board property.
	 *
	 * @pre none
	 * @post none
	 * @return the solved board property
	 */
	public BooleanProperty solvedBoardProperty() {
		return this.solvedBoardProperty;
	}

	/**
	 * Moves the tile selected by the user
	 * 
	 * @pre pos != null
	 * @post the tile at the specified position is moved if possible
	 * @param pos the position of the tile to be moved
	 */
	public void moveTile(Position pos) {
		Position destinationPos = this.board.getEmptyTilePosition();
		if (this.board.moveTile(pos, destinationPos)) {
			this.setTilesForView();
			this.movesMade.push(destinationPos);
			if (this.board.isSorted()) {
				this.solvedBoardProperty.set(true);
			}
		}
	}

	/**
	 * Undoes the most recent move
	 * 
	 * @pre none
	 * @post the most recent move of a puzzle board tile is undone
	 */
	public void undo() {
		if (this.movesMade.empty()) {
			return;
		}
		
		Position destinationPos = this.board.getEmptyTilePosition();
		this.board.moveTile(this.movesMade.pop(), destinationPos);
		this.setTilesForView();
	}

	/**
	 * Moves the next tile that is not in sequence to its correct position. First,
	 * the moves to place the next tile of the puzzle that is not in the correct
	 * order are determined without modifying the board. Then the moves are passed
	 * to the method call to traceMoves in a queue. Method traceMoves causes the
	 * moves in the queue to be executed on the board and to be displayed
	 * one-by-one.
	 * 
	 * @pre none
	 * @post the next tile that is moved to its correct position
	 */
	public void help() {
		//TODO
		System.out.println("Replace me by instructions to set the next tile at the correct position.");
	}

	/**
	 * Solves this 8-puzzle. First, the moves to solve the puzzle are determined
	 * without modifying the board. Then the moves are passed to the method call to
	 * traceMoves in a queue. Method traceMoves causes the moves in the queue to be
	 * executed on the board and to be displayed one-by-one.
	 * 
	 * @pre none
	 * @post all tiles of this board are in the correct position
	 */
	public void solve() {
		var solver = new PuzzleSolver(this.board);
		var moves = solver.findSolution();
		this.traceMoves(moves);
	}

	/**
	 * Shuffles the tiles to generate a new 8-puzzle
	 * 
	 * @pre none
	 * @post the application is reset for a new random puzzle
	 */
	public void newPuzzle() {
		this.board.shuffle();
		this.movesMade.clear();
		this.setTilesForView();
	}

	private void traceMoves(Queue<Move> moves) {
		if (moves != null && moves.size() > 0) {
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), evt -> {
				Move nextMove = moves.remove();
				this.board.moveTile(nextMove);
				this.movesMade.push(nextMove.getDestination());
				this.setTilesForView();
			}));
			timeline.setOnFinished(evt -> {
				if (this.board.isSorted()) {
					this.solvedBoardProperty.set(true);
				}
			});
			timeline.setCycleCount(moves.size());
			timeline.play();
		}
	}

	private void setTilesForView() {
		for (Position pos : Position.values()) {
			String tileNumber = Integer.toString(this.board.getTile(pos));
			this.tileNumberProperty[pos.getRow()][pos.getCol()].set(tileNumber);
		}
	}
}
