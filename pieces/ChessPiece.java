package pieces;

import chess.Board;

public abstract class ChessPiece {
	 
	public Colors color; // name of the piece as shown on the board (e.g. "wp" for white pawn)
	boolean isFirstMove = true; //only for King, Rook, and Pawn
	boolean justMovedTwice = false; //only for Pawns - specifically for en passant
	
	public ChessPiece(Colors c) {
		this.color = c;
	}
	
	// the move() method will be implemented by each unique piece
	// ex: user inputs in command line "e3 e5"
	// srcRow and srcCol are the indexes corresponding to the piece occupying e3
	// destRow and destCol aret he indexes corresponding to where you wanna move e3 to, in this case e5
	// public abstract void move(int srcRow,int srcCol,int destRow,int destCol);
	
	public String toString() {
		if(color.equals(Colors.BLACK)) {
			return "b";
		}
		return "w";
	}
	
	//promotion piece only used in Pawn.java
	public abstract boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2,char promotionPiece,Board chessBoard);
	public abstract void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, char promotionPiece,Board chessBoard);
}
