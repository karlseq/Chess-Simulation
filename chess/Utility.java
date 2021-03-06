package chess;

import pieces.ChessPiece;

public class Utility {
	
	
	public static ChessPiece getChessPiece(String block, Board board) {
		int row = 8 - Integer.parseInt(String.valueOf(block.charAt(1)));
		int col = block.charAt(0) - 97;
		return board.board[row][col];
	}
}
