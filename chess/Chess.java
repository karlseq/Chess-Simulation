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
				
				
					
			}
			
			System.out.println();

		}
				
	}

}
