package pieces;

import chess.Board;

public interface ForwardMover {
	
	
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
		
		if(!same_y && !same_x) return false;
		
 		else if(cp2 != null && cp2.color.equals(cp1.color)) return false;
		
		return LineIsEmpty(c1[0], c1[1], c2[0], c2[1], cp2, chessBoard);
	}
	
	private boolean isInBoard(int c1x, int c1y, int c2x, int c2y) {
		if ((c1x<0||c1x>7) || (c1y<0||c1y>7) || (c2x<0||c2x>7) || (c2y<0||c2y>7)) {
			return false;
		}
		return true;
	}
	
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
