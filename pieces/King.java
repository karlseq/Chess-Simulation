package pieces;

import chess.Board;

public class King extends ChessPiece {
	public King(Colors color) {
		super(color);
	}

	
	@Override
	public String toString() {
		return super.toString() + "K";
	}

	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		// TODO Auto-generated method stub
		return false;
	}
}