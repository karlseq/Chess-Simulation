package pieces;

import chess.Board;

/**
 * Interface for pieces that can move diagonally
 * 
 * @author ibrahimkhajanchi
 * @author Karl Sequiera 
 */
public interface DiagonalMover {
	
	/**
	 * Checks that the target piece, cp2, is on a diagonal of the source piece, cp1
	 * 
	 * @param c1 source piece coordinates
	 * @param cp1 source piece itself
	 * @param c2 target piece coordinates
	 * @param cp2 target piece itself
	 * @param chessBoard chess-board object
	 * @return true, cp2 lies on an empty diagonal, or false, cp2 isn't on a diagonal or cp2 is blocked by another piece
	 */
	public default boolean checkDiagonal(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		//for it to be valid, it has to be on the bishop's diagonal
		//calculate difference of rows
		
		if (!isInBoard(c1[0],c1[1],c2[0],c2[1])) return false; //makes sure the move is within the bounds of the board
		
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
	
	/**
	 * Checks that both pieces are on the board
	 * 
	 * @param c1x x value of c1
	 * @param c1y y value of c1
	 * @param c2x x value of c2
	 * @param c2y y value of c2
	 * @return true or false: the pieces are on the board
	 */
	private boolean isInBoard(int c1x, int c1y, int c2x, int c2y) {
		if ((c1x<0||c1x>7) || (c1y<0||c1y>7) || (c2x<0||c2x>7) || (c2y<0||c2y>7)) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method makes sure that the diagonal between c1 and c2 is empty
	 * 
	 * @param c1y y value of c1
	 * @param c1x x value of c1
	 * @param c2y y value of c2
	 * @param c2x x value of c2
	 * @param cp2 chess-piece 2
	 * @param board the board object
	 * @return true or false: the diagonal is empty
	 */
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
