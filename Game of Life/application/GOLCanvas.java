package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.util.Duration;
import foundation.CellState;
import foundation.GOLBoard;

public class GOLCanvas extends Canvas {

	private static final int CELLSIZE = 5;
	private static final int GAP = 2;

	private GraphicsContext gc = null;

	public GOLCanvas() {
		super(GOLBoard.CELLSHORIZONTAL * (CELLSIZE + GAP) + GAP, 
				GOLBoard.CELLSVERTICAL * (CELLSIZE + GAP) + GAP);

		// Get the graphics context for the canvas & clear. 
		gc = getGraphicsContext2D();
		clear();
	}

	// shows the contents of 'board' on the canvas



	public void show(GOLBoard board) {
		clear();
		
		for (int i = 0; i < GOLBoard.CELLSVERTICAL; i++) {

			for (int j = 0; j < GOLBoard.CELLSHORIZONTAL; j++) {
				if(board.getCellState(j, i) == CellState.LIVE) {
					gc.setFill(Color.BLACK);
					gc.fillRect(GAP + (CELLSIZE + GAP)*j, GAP + (CELLSIZE + GAP)*i , CELLSIZE, CELLSIZE);
				}
				else if(board.getCellState(j, i) == CellState.DEAD) {
					gc.setStroke(Color.LIGHTSLATEGRAY);
					gc.strokeRect(GAP + (CELLSIZE + GAP)*j, GAP+(CELLSIZE + GAP)*i, CELLSIZE, CELLSIZE);
				}
			}
		}
	}


	public void clear() {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		gc.setStroke(Color.LIGHTSLATEGRAY);
		gc.strokeRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
	}

}
