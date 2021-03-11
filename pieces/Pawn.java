package pieces;

import chess.Board;

public class Pawn extends ChessPiece {
	public Pawn(Colors color) {
		super(color);
	}


	
	@Override
	public String toString() {
		return super.toString() + "p";
	}

	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, Board chessBoard) {
		
	}
}
