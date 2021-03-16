package pieces;

import chess.Board;

public class Knight extends ChessPiece {
	public Knight(Colors color) {
		super(color);
	}


	
	@Override
	public String toString() {
		return super.toString() + "N";
	}

	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, char promotionPiece, Board chessBoard) {
		/*what constitutes a valid move?
		 * two options:
		 * 	1) regular L (flipped / backwards)
		 * 	2) sideways L (flipped /backwards)
		 */
		
		if (!isInBoard(c1[0],c1[1],c2[0],c2[1])) return false; //makes sure the move is within the bounds of the board
		
		int run = Math.abs(c1[1] - c2[1]), rise = Math.abs(c1[0] - c2[0]);
		
		boolean sideways_L = run == 2 && rise == 1, regular_L = run == 1 && rise == 2;
		
		if(!sideways_L && !regular_L) return false;
		else if(cp2!=null && cp2.color.equals(cp1.color)) return false;
		
		return true;
	}
	
	//checks if any of the row, col indices are out of the bounds of the board
	private boolean isInBoard(int c1x, int c1y, int c2x, int c2y) {
		if ((c1x<0||c1x>7) || (c1y<0||c1y>7) || (c2x<0||c2x>7) || (c2y<0||c2y>7)) {
			return false;
		}
		return true;
	}
	
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, Board chessBoard) {
		chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
		chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
	}
}