
public class Minesweeper {
	private char[][] board;
	private boolean[][] revealBoard;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Minesweeper(int x, int y) {
		board = randBoard(x,y,20);
		revealBoard = new boolean[x][y];
		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				revealBoard[i][j] = false;
			}
		}
	}
	
	private void printBoard() {
		
	}
	
	/**
	 * Generates a random board
	 * @param x number of rows
	 * @param y number of columns
	 * @param pr probability of bombs, given as number between 1 and 100
	 * @return the generated board
	 */
	public static char[][] randBoard(int x, int y, int pr) {
		char[][] board = new char[x][y];
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
		
		return board;
	}
	
	public int bombNum(int x, int y) {
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
