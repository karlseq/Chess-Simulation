package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pieces.ChessPiece;
import pieces.King;

public class Chess {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Board chessBoard = new Board();
		chessBoard.makeBoard();
				
		Boolean game = true, whiteMove = true, draw = false;
		
		
		//read input from user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		King blackKing = (King) chessBoard.board[0][4];
		King whiteKing = (King) chessBoard.board[7][4];
		
		while(game) {
			
			if(draw) game = false; else chessBoard.drawBoard();
			
			if(whiteMove) {
				System.out.print("White's Move: ");
				whiteMove = false;
			}
			else {
				System.out.print("Black's Move: ");
				whiteMove = true;
			}
			
			String move = reader.readLine();
			
			/* Possible Inputs
			 * 1) resign (opposite player wins)
			 * 2) move contains "draw?". Allow next player to type input (whatever it may be), and game ends
			 * 3) move with a character at the end (castling move). Have to check if valid
			 * 4) regular move
			 */
			
			/* RESIGN */
			if(move.equals("resign")){
				if(whiteMove) {
					System.out.print("White wins");
				}
				else {
					System.out.print("Black wins");
				}
				game = false;
			}
			
			/* DRAW */
			else if(move.contains("draw?")) draw = true;
			else if(move.contains("pass")) continue;
			/* regular move / promotion */
			else {
				
				int[] source_coordinates = Utility.getRowCol(move.substring(0, 2));
				int[] dest_coordinates = Utility.getRowCol(move.substring(3));
				
				//this is promotion
				if(move.length() == 7) {
					//handle promotion
					//ex: g7 g8 N
					//char promotion_piece = move.charAt(6);
				}
				
				ChessPiece source_piece = chessBoard.board[source_coordinates[0]][source_coordinates[1]];
				ChessPiece dest_piece = chessBoard.board[dest_coordinates[0]][dest_coordinates[1]];

				
				boolean isValid = source_piece.isValidMove(source_coordinates, source_piece, dest_coordinates, dest_piece, chessBoard);
				//if it's valid, check if moving it puts the player's king in check
				if(isValid) {
					if(whiteMove) {
					}
				}
					
			}
			
			System.out.println();

		}
				
	}

}
