package foundation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.Alert;

public class GOLBoard {

	public static int CELLSHORIZONTAL = 100;
	public static int CELLSVERTICAL   = 100;
	public static char REPRESENTATIONLIVECELL = '+';
	public static char REPRESENTATIONDEADCELL = '.';


	private CellState[][] board = new CellState[CELLSHORIZONTAL][CELLSVERTICAL];

	public GOLBoard() {
		for (int i = 0; i < board.length; ++i)
			for (int j = 0; j < board[i].length; ++j)
				board[i][j] = CellState.DEAD;
	}


	// loads the initial configuration from a GOL file and centers it on
	// the board
	// GOLFileException is thrown if
	// - the file cannot be opened or read
	// - the file contains illegal characters (different from
	//   REPRESENTATIONLIVECELL, REPRESENTATIONDEADCELL)
	// - the width of text lines varies
	// - the text lines have a length of 0 or > CELLSHORIZONTAL
	// - there are more lines than CELLSVERTICAL


	public GOLBoard(String filename) { 
		this();  
		String LineSize = "";
		String line = "";
		BufferedReader in = null;
		BufferedReader on = null;
		BufferedReader ch = null;
		int LineWidth = 0;
		int LineHeight = 0;
		int PositionHor = 0;
		int PositionVert = 0;
		int LinePos = 0;


		try {
			// open the file
			in = new BufferedReader(new FileReader(filename));

			//			 read loop
			while ((LineSize = in.readLine()) != null) { 
				LineHeight++;
				if (LineSize.length() == 0 || LineSize.length() > CELLSHORIZONTAL || LineHeight > CELLSVERTICAL) {
					throw new GOLFileException("GOLFileException has occurred");
				}
				for(LineWidth=0; LineWidth < LineSize.length(); LineWidth++);{
				}
			}
			ch = new BufferedReader(new FileReader(filename));
			int Check = ch.readLine().length();
			PositionHor = (CELLSHORIZONTAL - LineWidth)/2;
			PositionVert = (CELLSVERTICAL - LineHeight)/2;

			on = new BufferedReader(new FileReader(filename));

			while ((line = on.readLine()) != null) { 
				LinePos++;
				if(Check != line.length()) {
					throw new GOLFileException("GOLFileException has occurred");
				}

				for(int ColPos = 0; ColPos < line.length(); ColPos++) {
					if(line.charAt(ColPos) == REPRESENTATIONLIVECELL) {
						board[PositionHor + ColPos][PositionVert + LinePos] = CellState.LIVE;
					}
					else if (line.charAt(ColPos) == REPRESENTATIONDEADCELL) {

					} 
					else {
						throw new GOLFileException("GOLFileException has occurred");
					}

				}
			}	

		}catch (FileNotFoundException fnfe) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Severe Error");
			alert.setHeaderText("GOLFileException has occurred");
			alert.showAndWait();
		} catch (IOException ioe) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Severe Error");
			alert.setHeaderText("GOLFileException has occurred");
			alert.showAndWait();
		} catch (java.lang.NullPointerException NPE) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Severe Error");
			alert.setHeaderText("GOLFileException has occurred");
			alert.showAndWait();
		}
	}

	public GOLBoard(GOLBoard previous) {

		for(int j=0; j < CELLSVERTICAL; j++) {
			for(int i=0; i < CELLSHORIZONTAL; i++) {
				int LiveNeighbours = 0;
				if(i-1 >= 0 && previous.getCellState(i-1, j) == CellState.LIVE) {
					++LiveNeighbours;
				}
				if(i-1 >=0 && j-1 >=0 && previous.getCellState(i-1, j-1) == CellState.LIVE) {
					++LiveNeighbours;
				}
				if(j-1 >=0 && previous.getCellState(i, j-1) == CellState.LIVE) {
					++LiveNeighbours;
				}
				if( i+1 < 100 && j-1 >=0 && previous.getCellState(i+1, j-1) == CellState.LIVE) {
					++LiveNeighbours;
				}
				if(i+1 < 100 && previous.getCellState(i+1, j) == CellState.LIVE ) {
					++LiveNeighbours;
				}
				if(i+1 < 100 && j+1 < 100 && previous.getCellState(i+1, j+1) == CellState.LIVE) {
					++LiveNeighbours;
				}
				if(j+1 < 100 && previous.getCellState(i, j+1) == CellState.LIVE) {
					++LiveNeighbours;
				}
				if(i-1 >=0 && j+1 < 100 && previous.getCellState(i-1, j+1) == CellState.LIVE) {
					++LiveNeighbours;
				}
				if(LiveNeighbours > 3) {
					setCellState(i,j, CellState.DEAD);
				}
				else if(LiveNeighbours < 2) {
					setCellState(i,j, CellState.DEAD);
				}
				else if(LiveNeighbours == 3) {
					setCellState(i,j, CellState.LIVE);
				}
				else if(previous.getCellState(i, j) == CellState.LIVE && LiveNeighbours == 2) {
					setCellState(i,j, CellState.LIVE);
				}
				else if(previous.getCellState(i, j) == CellState.DEAD && LiveNeighbours == 2) {
					setCellState(i,j, CellState.DEAD);
				}
			}
		}
	}

	public CellState getCellState(int col, int row) {

		return board[col][row];
	}

	public void setCellState(int col, int row, CellState value) {

		board[col][row] = value;
	}

}
