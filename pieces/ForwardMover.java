package pieces;

import chess.Board;

/**
 * Interface for pieces that can move forward or sideways
 * 
 * @author ibrahimkhajanchi
 * @author Karl Sequiera
 */
public interface ForwardMover {
	
	/**
	 * This method checks that the target piece is on the same line as the source piece
	 * 
	 * @param c1 coordinates of the source piece
	 * @param cp1 source piece itself
	 * @param c2 coordinates of the target piece
	 * @param cp2 target piece itself
	 * @param chessBoard board object
	 * @return true if cp2 is on the same line as cp1, false if it isn't, or if the line is not empty
	 */
	public default boolean checkLine(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		/* What constitutes a valid move?
		 * cp2 is on the same line as cp1 whether that be on the same X or Y
		 * every block between cp1 & cp2 must be empty
		 * 
		 * so: same y and different x's
		 * or: same x and different y's
		 */
	
		if (!isInBoard(c1[0],c1[1],c2[0],c2[1])) return false; //makes sure the move is within the bounds of the board
		
		boolean same_y = c1[0] == c2[0] && c1[1] != c2[1];
		boolean same_x = c1[1] == c2[1] && c1[0] != c2[0];
		

		if(!same_y && !same_x) {
			return false;
		}
 		else if(cp2 != null && cp2.color == cp1.color) {
 			return false;
 		}
		
		return LineIsEmpty(c1[0], c1[1], c2[0], c2[1], cp2, chessBoard);
	}
	
	/**
	 * Checks that both pieces are on the board
	 * 
	 * @param c1x x value of chess piece 1
	 * @param c1y y value of chess piece 1
	 * @param c2x x value of chess piece 2
	 * @param c2y y value of chess piece 2
	 * @return true or false: both pieces are on the board
	 */
	private boolean isInBoard(int c1x, int c1y, int c2x, int c2y) {
		if ((c1x<0||c1x>7) || (c1y<0||c1y>7) || (c2x<0||c2x>7) || (c2y<0||c2y>7)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks that the line between cp2 and cp1 is empty
	 * 
	 * @param c1y y value of cp1
	 * @param c1x x value of cp1
	 * @param c2y y value of cp2
	 * @param c2x x value of cp2
	 * @param cp2 chess piece 2 object
	 * @param board chess board object
	 * @return true line is empty, false, line is not empty
	 */
	private boolean LineIsEmpty(int c1y, int c1x, int c2y, int c2x, ChessPiece cp2, Board board) {
		if(c1x == c2x && c1y == c2y) {
			return true;
		}
		else if(board.getBoard()[c2y][c2x] == null || board.getBoard()[c2y][c2x] == cp2) {
			if(c1x == c2x) { //on same vertical
				if(c2y < c1y) c2y++;
				else c2y--;
			}
			else { //on same horizontal
				if(c2x < c1x) c2x++;
				else c2x --;
			}
			
			return LineIsEmpty(c1y, c1x, c2y, c2x, cp2, board);
		}
		return false;
	}
}
