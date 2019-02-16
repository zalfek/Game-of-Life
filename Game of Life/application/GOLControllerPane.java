package application;

import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.control.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;

import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import foundation.GOLBoard;
import foundation.GOLFileException;

public class GOLControllerPane extends VBox {

	private static final int BUTTONWIDTH = 60;
	private GOLBoard currentBoard = new GOLBoard();

	private Timeline timeline = null;

	public GOLControllerPane(GOLCanvas canvas) {
		super(10);

		// Create the button. 
		Button btnLoad = new Button("Load");
		btnLoad.setMinWidth(BUTTONWIDTH);

		Button btnPlay = new Button("Play");
		btnPlay.setMinWidth(BUTTONWIDTH);

		Button btnPause = new Button("Pause");
		btnPause.setMinWidth(BUTTONWIDTH);

		btnLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {

				// choose a file
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Load File");
				fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); 
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("GOL", "*.gol"),
						new FileChooser.ExtensionFilter("All Files", "*.*")
						);
				File file = fileChooser.showOpenDialog(btnLoad.getScene().getWindow());
				if (file == null) return;

				try {
					currentBoard = new GOLBoard(file.getName());
				} catch (GOLFileException GOLF) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Severe Error");
					alert.setHeaderText("GOLFileException has occurred");
					alert.showAndWait();
				}
				canvas.show(currentBoard);
			}
		});
		timeline = new Timeline(new KeyFrame(Duration.millis(100), ae -> canvas.show(proceed())));
		timeline.setCycleCount(Timeline.INDEFINITE);

		btnPlay.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				btnPlay.setDisable(true);
				btnPause.setDisable(false);
				timeline.play();
			}
		});

		btnPause.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				btnPause.setDisable(true);
				btnPlay.setDisable(false);
				timeline.pause();
			}
		});

		// set the handler of the ActionEvent to select a GOL file
		// and then reconstruct the 'currentBoard' with the 
		// filename selected with appropriate handling of 
		// GOLFileException (Alert)
		// If a new board has been created, it should be shown using
		// the show method of the canvas

		// Add them to the box. 
		getChildren().addAll(btnLoad);
		getChildren().addAll(btnPlay);
		getChildren().addAll(btnPause);
		canvas.show(currentBoard);
	}
	public GOLBoard proceed() {
		currentBoard = new GOLBoard(currentBoard);
		return currentBoard;

	}

}
