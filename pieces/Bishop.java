package pieces;

import chess.Board;

/**
 * Represents a Bishop chess piece
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Bishop  extends ChessPiece implements DiagonalMover {
	
	/**
	 * Creates a Bishop chess piece of the given color
	 * 
	 * @param color the color of the Bishop piece (black or white)
	 */
	public Bishop(Colors color) {
		super(color);
	}

	@Override
	public String toString() {
		return super.toString() + "B";
	}

	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, char promotionPiece, Board chessBoard) {
		return checkDiagonal(c1, cp1, c2,cp2, chessBoard);
	}

	
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, char promotionPiece,Board chessBoard) {
		chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
		chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
	}
	
}