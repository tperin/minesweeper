
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
			System.out.println("Enter x coordinate to check");
			int xCheck = IO.readInt();
			System.out.println("Enter y coordinate to check");
			int yCheck = IO.readInt();
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
	
	public Minesweeper(int x, int y) {
		randBoard(x,y,20);
		revealBoard = new boolean[x][y];
		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				revealBoard[i][j] = false;
			}
		}
	}
	
	private boolean revealCell(int x, int y) {
		if (revealBoard[x][y]) return false;
		revealBoard[x][y] = true;
		return true;
	}
	
	private void printBoard() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (revealBoard[x][y]) System.out.print(board[x][y]);
				else System.out.print("[]");
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
				//chance of bomb will be 1/n
				double p = pr;
				double r = Math.round(Math.random()*100);
				//if (DEBUG) System.out.println(r);
				if (r <= p) {
					board[i][j] = 'B';
				}
				else board[i][j] = 'U';
			}
		}

		fillWithNums();
	}
	
	private void fillWithNums() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != 'B') board[i][j] = (char) ('0' + bombNum(i,j));
			}
		}
	}
	
	private boolean isGameOver() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 'B' && revealBoard[i][j]) return true;
			}
		}
		return false;
	}
	
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

}
