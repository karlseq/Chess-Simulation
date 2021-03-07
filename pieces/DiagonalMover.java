package pieces;

import chess.Board;

public interface DiagonalMover {
	
	public default boolean checkDiagonal(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		//for it to be valid, it has to be on the bishop's diagonal
		//calculate difference of rows
		
		//METHOD THAT CHECKS DIAGONAL
		int run = Math.abs(c2[1] - c1[1]), rise = run;
		
		if(rise != Math.abs(c2[0] - c1[0])) { //check that cp2 is in board[rise][run]
			return false;
		}
		else if(cp2!=null && cp1.color.equals(cp2.color)) { //check that cp2 is not the same color as cp1
			return false;
		}
		
		return DiagonalIsEmpty(c1[0], c1[1], c2[0], c2[1], cp2, chessBoard);
	}
	
	private boolean DiagonalIsEmpty(int c1y, int c1x, int c2y, int c2x, ChessPiece cp2, Board board) {
		if(c1x == c2x && c1y == c2y) {
			return true;
		}
		else if(board.getBoard()[c2y][c2x] == null || board.getBoard()[c2y][c2x] == cp2) {
				if(c2x > c1x) c2x--; //cp2 is on right hand side:
				else  c2x++; //cp2 is on left hand side
				
				if(c2y > c1y) { //cp2 is "in front" of cp1
					c2y--;
				}
				else { //cp2 is "behind" cp1
					c2y++;
				}
				return DiagonalIsEmpty(c1y, c1x, c2y, c2x, cp2, board);
		}
		
		return false;
	}
}
