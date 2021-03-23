package chess;

import pieces.ChessPiece;
import pieces.King;

/**
 * Holds the utility methods for the Chess class
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */
public class Utility {
	
	/**
	 * This method takes in the user input from command line and returns row and column to
	 * be used for the chess-board
	 * 
	 * @param string form of user input
	 * @return the row and column of the input in integer form
	 */
	public static int[] getRowCol(String block) {
		int row = 8 - Integer.parseInt(String.valueOf(block.charAt(1)));
		int col = block.charAt(0) - 97;
		return new int[] {row,col};
	}
	
	
	/**
	 * This method handles the case when the player makes a move that puts their king in check
	 * 
	 * @param source_coordinates the coordinates of the piece you want to move
	 * @param dest_coordinates the coordinates of the spot you want to move to
	 * @param source_piece the piece you want to move
	 * @param dest_piece the piece at the destination you want to move to
	 * @param king_coordinates the coordinates of the king
	 * @param king the king piece
	 * @param chessBoard the board object
	 * @param whiteMove who's turn is it, white or black?
	 * @return the boolean that tells you whose turn it is
	 */
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
	
	/**
	 * This method checks if the check is a check-mate
	 * 
	 * @param king_coordinates the coordinates of the king
	 * @param threat_coordinates the coordinates of the piece that's putting the king in check
	 * @param king the king piece
	 * @param chessBoard the board object
	 * @param game 
	 * @return the boolean game which tells you if game is over or not
	 */
	public static boolean handleLegalCheck(int[] king_coordinates, int[] threat_coordinates, King king, Board chessBoard, Boolean game) {
		if(king.checkForCheckMate(king_coordinates, king, chessBoard, threat_coordinates)) {
			chessBoard.drawBoard();
			System.out.println("Checkmate");
			System.out.println(king.color.toString().toLowerCase() + " wins");
			return game=false;
		}
		return game=true;
	}
	
}
