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
	
	public boolean isInCheck(int[] king_coordinates, ChessPiece king, Board chessBoard) {
		/* How does this algorithm work?
		 * check each diagonal / horizontal/ vertical / and knight position going into King
		 * iterate through the entire line
		 * 		if the line is empty, perfect, no threat
		 * 		else if you find a piece there, 
		 * 			determine if isValidMove from that piece to king
		 *				if so, it's in check, if not, that part is good
		 * 
		 * 
		 * 
		 * make all of this a lambda?? or use interfaces
		 */
		
		//check left
		int i = king_coordinates[1];
		ChessPiece currentPiece = null;
		while(i >= 0 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[king_coordinates[0]][i];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {king_coordinates[0], i}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			i--;
		}
		
		//check right
		i = king_coordinates[1];
		currentPiece = null;
		while(i <= 7 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[king_coordinates[0]][i];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {king_coordinates[0], i}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			i++;
		}
		
		//check forward left diagonal
		int col = king_coordinates[1], row = king_coordinates[0];
		currentPiece = null;
		while((row <= 7 && col >=0) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row++; col--;
		}
		
		//check forward right diagonal
		col = king_coordinates[1]; row = king_coordinates[0];
		currentPiece = null;
		while((col <= 7 && row <=7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row++; col++;
		}
		
		//check backward left diagonal
		col = king_coordinates[1]; row = king_coordinates[0];
		currentPiece = null;
		while((row >= 0 && col >=0) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row--; col--;
		}
		
		//check backward left diagonal
		col = king_coordinates[1]; row = king_coordinates[0];
		currentPiece = null;
		while((row >=0 && col <= 7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row--; col++;
		}
		
		//now you have to check: sideways L on forward left, forward right, backward left, backward right
							//	 regular L on forward left,  forward right, backward left, backward right
	
		
		return true;
	}
	 
	public void bfs(int[] king_coordinates, Board chessBoard) {
		
	}
	

}