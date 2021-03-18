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


	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard, int num) {
		if (!isInBoard(c1[0],c1[1],c2[0],c2[1])) return false; //makes sure the move is within the bounds of the board
		if(checkDiagonal(c1, cp1, c2, cp2, chessBoard)) {
			return true;
		}
		else if(checkLine(c1, cp1, c2, cp2, chessBoard)){
			return true;
		}
		return false;
	}
	
	//checks if any of the row, col indices are out of the bounds of the board
	private boolean isInBoard(int c1x, int c1y, int c2x, int c2y) {
		if ((c1x<0||c1x>7) || (c1y<0||c1y>7) || (c2x<0||c2x>7) || (c2y<0||c2y>7)) {
			return false;
		}
		return true;
	}
	
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, Board chessBoard) {
		chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
		chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
	}



	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
		// TODO Auto-generated method stub
		return false;
	}
}