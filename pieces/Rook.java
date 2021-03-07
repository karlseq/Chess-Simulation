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
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		return checkLine(c1, cp2, c2, cp2, chessBoard);
	}
}
