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
		// TODO Auto-generated method stub
		return false;
	}
}