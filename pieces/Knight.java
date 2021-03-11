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
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		/*what constitutes a valid move?
		 * two options:
		 * 	1) regular L (flipped / backwards)
		 * 	2) sideways L (flipped /backwards)
		 */
		
		int run = Math.abs(c1[1] - c2[1]), rise = Math.abs(c1[0] - c2[0]);
		
		boolean sideways_L = (run == 2 && rise == 1), regular_L = (run == 1 && rise == 2);
		
		if(!sideways_L && !regular_L) return false;
		else if(cp2!=null && cp2.color.equals(cp1.color)) return false;
		
		return true;
	}
}