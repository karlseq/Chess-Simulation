package chess;

import pieces.ChessPiece;
import pieces.King;

public class Utility {
	
	
	//returns row and col of the respective piece
	public static int[] getRowCol(String block) {
		int row = 8 - Integer.parseInt(String.valueOf(block.charAt(1)));
		int col = block.charAt(0) - 97;
		return new int[] {row,col};
	}
	
	//handles the case when a user puts his own king in check
	public static boolean handleIllegalCheck(int[] source_coordinates, int[] dest_coordinates, ChessPiece source_piece, ChessPiece dest_piece, int[] king_coordinates, King king, Board chessBoard, boolean whiteMove) {
		chessBoard.board[source_coordinates[0]][source_coordinates[1]] = source_piece;
		chessBoard.board[dest_coordinates[0]][dest_coordinates[1]] = dest_piece;
		
		//update piece if it's a king
		if(source_piece instanceof King) { 
			king = (King) chessBoard.board[source_coordinates[0]][source_coordinates[1]];
			king_coordinates[0] = source_coordinates[0]; king_coordinates[1] = source_coordinates[1];
		}
		System.out.println("Illegal move, try again");
		return whiteMove = !whiteMove;
	}
	
	//handles when there's a check and you need to check for checkMate
	public static boolean handleLegalCheck(int[] king_coordinates, King king, Board chessBoard, Boolean check, Boolean game) {
		check = true;
		if(king.checkForCheckMate(king_coordinates, king, chessBoard)) {
			chessBoard.drawBoard();
			System.out.println("Checkmate");
			System.out.println("Black wins");
			return game=false;
		}
		return game=true;
	}
	
	
}
