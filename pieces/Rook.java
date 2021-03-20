package pieces;

import chess.Board;

public class Rook extends ChessPiece implements ForwardMover {
	
	public Rook(Colors color) {
		super(color);
	}

	
	@Override
	public String toString() {
		return super.toString() + "R";
	}


	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, char promotionPiece, Board chessBoard) {
		return checkLine(c1, cp1, c2, cp2, chessBoard);
	}
	
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, char promotionPiece,Board chessBoard) {
		chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
		chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
	}
}
