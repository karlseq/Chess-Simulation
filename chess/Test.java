package chess;

import pieces.ChessPiece;
import pieces.Colors;
import pieces.Knight;
import pieces.Queen;

public class Test {

	public static void main(String[] args) {
		
		int[][] board = {
				{1,2,3},
				{4,5,6}
		};
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		
		int[][] temp = board;
		board[0][0] = 9;
		board = temp;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		
	}

}
