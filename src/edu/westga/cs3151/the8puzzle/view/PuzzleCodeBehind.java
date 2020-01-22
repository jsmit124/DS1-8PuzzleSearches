package edu.westga.cs3151.the8puzzle.view;

import edu.westga.cs3151.the8puzzle.model.Position;
import edu.westga.cs3151.the8puzzle.viewmodel.PuzzleViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * The Class PuzzleCodeBehind
 * 
 * @author 3151
 */
public class PuzzleCodeBehind {

	@FXML
	private AnchorPane tilePane;
	
	@FXML
    private Label headerLabel;

    @FXML
    private Label solvedLabel;

	private Button[][] tileButton;

	@FXML
	private Button undoButton;

	@FXML
	private Button helpButton;
	
	@FXML
	private Button solveButton;

	private PuzzleViewModel viewModel;
	private final BooleanProperty solvedBoardProperty;

	/**
	 * Instantiates a new student info code behind.
	 * 
	 * @precondition none
	 * @precondition none
	 */
	public PuzzleCodeBehind() {
		this.viewModel = new PuzzleViewModel();
		this.solvedBoardProperty = new SimpleBooleanProperty();
	}

	@FXML
	private void initialize() {
		this.setupTiles();
		this.setupSolvedBoardEvent();
	}

	private void setupTiles() {
		this.tileButton = new Button[3][3];
		for (Position pos : Position.values()) {
			int row = pos.getRow();
			int col = pos.getCol();
			this.tileButton[row][col] = new Button("");
			this.tileButton[row][col].setLayoutX(col * 82 + 2);
			this.tileButton[row][col].setLayoutY(row * 82 + 2);
			this.tileButton[row][col].setMinWidth(80);
			this.tileButton[row][col].setMinHeight(80);
			this.tileButton[row][col].setStyle("-fx-font-size:36px;");
			this.tileButton[row][col].visibleProperty()
					.bind(Bindings.notEqual("0", this.tileButton[row][col].textProperty()));
			this.tileButton[row][col].textProperty().bind(this.viewModel.tileNumberProperty(row, col));
			this.tilePane.getChildren().add(this.tileButton[row][col]);
			this.addTileListener(row, col);
		}
	}

	private void addTileListener(int row, int col) {
		this.tileButton[row][col].setOnAction(event -> {
			this.viewModel.moveTile(new Position(row, col));
		});
	}

	private void setupSolvedBoardEvent() {
		this.solvedBoardProperty.bindBidirectional(this.viewModel.solvedBoardProperty());
		this.solvedBoardProperty.addListener((observable, oldValue, newValue) -> {
			this.checkForFinishedPuzzle();
		});
	}

	@FXML
	void handleUndo(ActionEvent event) {
		this.viewModel.undo();
	}

	@FXML
	void handleHelp(ActionEvent event) {
		this.viewModel.help();
	}

	@FXML
	void handleNewPuzzle(ActionEvent event) {
		this.viewModel.newPuzzle();
		this.headerLabel.setVisible(true);
		this.solvedLabel.setVisible(false);
		this.setButtonDisable(false);
		this.solvedBoardProperty.set(false);
	}

	@FXML
	void handleSolvePuzzle(ActionEvent event) {
		this.viewModel.solve();
	}

	private void checkForFinishedPuzzle() {
		if (this.solvedBoardProperty.get()) {
			this.headerLabel.setVisible(false);
			this.solvedLabel.setVisible(true);
			this.setButtonDisable(true);
		}
	}
	
	private void setButtonDisable(boolean disabled) {
		this.helpButton.setDisable(disabled);
		this.undoButton.setDisable(disabled);
		this.solveButton.setDisable(disabled);
		for (Node tile : this.tilePane.getChildren()) {
			tile.setDisable(disabled);
		}
	}
}
