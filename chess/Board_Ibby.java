package chess;

import java.util.Arrays;

public class Board_Ibby {
	
	private static String[][] getBlankBoard(){
		
		String[][] board = new String[9][9];
		for(String[] row : board) {
			Arrays.fill((Object[]) row, "   ");
		}
		
		for(int i = 0; i < 8; i++) {
			//evens
			if(i % 2 != 0) {
				for(int j = 0; j<8; j+=2) {
					board[i][j]="## ";
				}
			}
			//odds
			else {
				for(int j = 1; j<8; j+=2) {
					board[i][j]="## ";
				}
			}
			board[i][8] = Integer.toString(8-i);
			
			int letter = 'a';
			board[8][i]=  " " + Character.toString((char)(letter + i)) + " ";
			
		}

		return board;
		
	}
	
	public static void getBoard() {
		//String[][] board = getBlankBoard();
	}
	
	public static void printBoard(String[][] board) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		String[][] board = getBlankBoard();
		printBoard(board);
	}

}
