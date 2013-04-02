
public class Minesweeper {
	private char[][] board;
	private boolean[][] revealBoard;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Minesweeper ms = new Minesweeper(5,5);
		ms.printBoard();
		boolean quit = false;
		while (!quit) {
			int xCheck = -1;
			int yCheck = -1;
			while (xCheck < 0 || xCheck >= ms.getBoardWidth() || yCheck < 0 || yCheck >= ms.getBoardLength()) {
				System.out.println("Enter x coordinate to check");
				xCheck = IO.readInt();
				System.out.println("Enter y coordinate to check");
				yCheck = IO.readInt();
				if (xCheck < 0 || xCheck >= ms.getBoardWidth() || yCheck < 0 || yCheck >= ms.getBoardLength()) {
					System.out.println("Given coordinates are out of bounds, try again");
				}
			}
			if (ms.isRevealed(xCheck, yCheck)) {
				System.out.println("You have already checked this cell");
			}
			else {
				ms.revealCell(xCheck, yCheck);
				ms.printBoard();
			
				if (ms.isGameOver()) {
					System.out.println("GAME OVER");
					quit = true;
				}
				else {
					System.out.println("Quit?");
					quit = IO.readBoolean();
				}
			}
		}
	}
	
	/**
	 * Constructor using variable board size and sets board randomly
	 * @param x amount of rows for board
	 * @param y amount of columns for board
	 */
	public Minesweeper(int x, int y) {
		randBoard(x,y,20);
		revealBoard = new boolean[x][y];
		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				revealBoard[i][j] = false;
			}
		}
	}
	
	/**
	 * Reveals a cell
	 * @param x x coordinate of cell to reveal
	 * @param y y coordinate of cell to reveal
	 * @return false if cell has been revealed, true if cell is succesfully revealed
	 */
	private void revealCell(int x, int y) {
		revealBoard[x][y] = true;

		//Start cascading reveal mess
		if (board[x][y] == '0') {
			if (y > 0) {
				if (board[x][y-1] == '0' && !isRevealed(x,y-1)) revealCell(x,y-1);
				if (x > 0) {
					if (board[x-1][y-1] == '0' && !isRevealed(x-1,y-1)) revealCell(x-1,y-1);
				}
				if (x < board.length - 1) {
					if (board[x+1][y-1] == '0' && !isRevealed(x+1,y-1)) revealCell(x+1,y-1);
				}
			}

			if (x > 0) {
				if (board[x-1][y] == '0' && !isRevealed(x-1,y)) revealCell(x-1,y);
			}

			if (x < board.length - 1) {
				if (board[x+1][y] == '0' && !isRevealed(x+1,y)) revealCell(x+1,y);
			}

			if (y < board[0].length - 1) {
				if (board[x][y+1] == '0' && !isRevealed(x,y+1)) revealCell(x,y+1);
				if (x > 0) {
					if (board[x-1][y+1] == '0' && !isRevealed(x-1,y+1)) revealCell(x-1,y+1);
				}
				if (x < board.length - 1) {
					if (board[x+1][y+1] == '0' && !isRevealed(x+1,y+1)) revealCell(x+1,y+1);
				}
			}
		}
		//End cascading reveal mess
	}
	
	private boolean isRevealed(int x, int y) {
		return revealBoard[x][y];
	}
	
	/**
	 * Prints out the board, only showing revealed cells
	 */
	private void printBoard() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (revealBoard[x][y]) System.out.print(" " + board[x][y] + " ");
				else System.out.print("[ ]");
			}
			System.out.println();
		}
	}
	
	/**
	 * Generates a random board
	 * @param x number of rows
	 * @param y number of columns
	 * @param pr probability of bombs, given as number between 1 and 100
	 * @return the generated board
	 */
	public void randBoard(int x, int y, int pr) {
		board = new char[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				double r = Math.round(Math.random()*100);
				if (r <= pr) {
					board[i][j] = 'B';
				}
				else board[i][j] = 'U';
			}
		}

		fillWithNums();
	}
	
	/**
	 * Fills randomized board with bomb numbers in cells
	 */
	private void fillWithNums() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != 'B') board[i][j] = (char) ('0' + bombNum(i,j));
			}
		}
	}
	
	/**
	 * Checks if the game is over by checking if any bombs were revealed
	 * @return true if game over, false is not
	 */
	private boolean isGameOver() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 'B' && revealBoard[i][j]) return true;
			}
		}
		return false;
	}
	
	/**
	 * Counts the adjacent bombs to the given cell
	 * @param x x coordinate of the cell to check
	 * @param y y coordinate of the cell to check
	 * @return number of adjacent bombs
	 */
	private int bombNum(int x, int y) {
		int count = 0;
		//check the 3 spaces left of (x,y) for bombs
		if (y > 0) {
			if (board[x][y-1] == 'B') count++;
			if (x > 0) {
				if (board[x-1][y-1] == 'B') count++;
			}
			if (x < board.length - 1) {
				if (board[x+1][y-1] == 'B') count++;
			}
		}
		//check space above (x,y) for bomb
		if (x > 0) {
			if (board[x-1][y] == 'B') count++;
		}
		//check space below (x,y) for bomb
		if (x < board.length - 1) {
			if (board[x+1][y] == 'B') count++;
		}
		//check the 3 spaces to the right of (x,y) for bombs
		if (y < board[0].length - 1) {
			if (board[x][y+1] == 'B') count++;
			if (x > 0) {
				if (board[x-1][y+1] == 'B') count++;
			}
			if (x < board.length - 1) {
				if (board[x+1][y+1] == 'B') count++;
			}
		}
		return count;
	}
	
	public int getBoardWidth() {
		return board.length;
	}
	public int getBoardLength() {
		return board[0].length;
	}

}
