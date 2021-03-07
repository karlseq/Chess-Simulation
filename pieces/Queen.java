package pieces;

import chess.Board;

public class Queen extends ChessPiece implements DiagonalMover, ForwardMover{
	public Queen(Colors color) {
		super(color);
	}


	
	@Override
	public String toString() {
		return super.toString() + "Q";

	}


	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		return checkDiagonal(c1, cp2, c2, cp2, chessBoard) ? true : checkLine(c1, cp2, c2, cp2, chessBoard);
	}
}