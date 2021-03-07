package pieces;

import chess.Board;

public class Bishop  extends ChessPiece implements DiagonalMover {
	public Bishop(Colors color) {
		super(color);
	}

	@Override
	public String toString() {
		return super.toString() + "B";
	}

	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		return checkDiagonal(c1, cp1, c2,cp2, chessBoard);
	}
	
}