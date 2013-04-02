
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

}
