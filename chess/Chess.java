package chess;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import pieces.ChessPiece;

public class Chess {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Board chessBoard = new Board();
		chessBoard.makeBoard();
		
		Boolean game = true, whiteMove = true, draw = false;
		
		
		//read input from user
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
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
			
			if (draw) break; //end the game
			
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
			//what is pass for?
			else if(move.contains("pass")) continue;
			/* regular move / promotion */
			else {
				
				int[] source_coordinates = Utility.getRowCol(move.substring(0, 2));
				int[] dest_coordinates = Utility.getRowCol(move.substring(3));
				
				if(move.length() == 7) {
					//handle promotion
					//ex: g7 g8 N
					//char promotion_piece = move.charAt(6);
				}
				
				ChessPiece source_piece = chessBoard.board[source_coordinates[0]][source_coordinates[1]];
				ChessPiece dest_piece = chessBoard.board[dest_coordinates[0]][dest_coordinates[1]];

				
				boolean result = source_piece.isValidMove(source_coordinates, source_piece, dest_coordinates, dest_piece, chessBoard);
				if (result) {
					source_piece.move(source_coordinates, source_piece, dest_coordinates, chessBoard);
				}
				else {
					System.out.println("Illegal move, try again");
					whiteMove = !whiteMove;
				}
					
			}
			
			System.out.println();

		}
				
	}

}
