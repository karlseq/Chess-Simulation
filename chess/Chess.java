package chess;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import pieces.ChessPiece;
import pieces.Colors;
import pieces.King;

/**
 * Main application
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

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
		
		int[] blackKing_coordinates = {0, 4};
		int[] whiteKing_coordinates = {7,4};
		
		boolean shouldDisplay = true; //board should NOT be displayed if the user inputed an illegal move
		while(game) {
			if(draw) game = false;
			else {
				if (shouldDisplay) {
					chessBoard.drawBoard();
				}
			}
			
			if(whiteMove) {
				System.out.print("White's Move: ");
				whiteMove = false;
			}
			else {
				System.out.print("Black's Move: ");
				whiteMove = true;
			}
			
			String move = reader.readLine();
			move = move.trim(); //removes leading and trailing spaces
			
			if (draw) break; //end the game
			
			/* Possible Inputs
			 * 1) resign (opposite player wins)
			 * 2) move contains "draw?". Allow next player to type input (whatever it may be), and game ends
			 * 3) regular move with or without promotion
			 */
			
			if(move.equals("resign")){ 	/* RESIGN */

				if(whiteMove) {
					System.out.print("White wins");
				}
				else {
					System.out.print("Black wins");
				}
				game = false;
			}
			
			else if(move.contains("draw?")) draw = true; /* DRAW */
			else if(move.contains("pass")) continue;
			else { 	/* REGULAR MOVE / REGULAR MOVE WITH PROMOTION */

				int[] source_coordinates = Utility.getRowCol(move.substring(0, 2));
				int[] dest_coordinates = Utility.getRowCol(move.substring(3));
				
				char promotionPiece = 'X'; //x is just arbitrary
				if(move.length() == 7) {
					//handle promotion
					//ex: g7 g8 N
					promotionPiece = move.charAt(6);
				}
				
				ChessPiece source_piece = chessBoard.board[source_coordinates[0]][source_coordinates[1]];
				ChessPiece dest_piece = chessBoard.board[dest_coordinates[0]][dest_coordinates[1]];
				
				boolean moveIsValid;
				boolean ogIFM = true; //arbitrary- might be overwritten
				boolean ogJMT = false; //arbitrary- might be overwritten
				if (source_piece==null) moveIsValid = false; //can't move a null piece (empty square)
				else {
					//saving state of src piece's fields
					ogIFM = source_piece.isFirstMove; //src piece's isFirstMove state before moving
					ogJMT = source_piece.justMovedTwice; //src piece's justMovedTwice state before moving
					/*if (source_piece instanceof King) {
						boolean ogICM = (King)source_piece.
					}*/
					moveIsValid = source_piece.isValidMove(source_coordinates, source_piece, dest_coordinates, dest_piece, promotionPiece,chessBoard);
				}
				if (moveIsValid) {
					source_piece.move(source_coordinates, source_piece, dest_coordinates, promotionPiece,chessBoard);
					
					//if the source piece was a king, you have to update whiteKing or blackKing references
					if(source_piece instanceof King) {
						
						if(source_piece.color == Colors.WHITE) {
							whiteKing = (King) chessBoard.board[dest_coordinates[0]][dest_coordinates[1]];
							whiteKing_coordinates[0] = dest_coordinates[0]; whiteKing_coordinates[1] = dest_coordinates[1];
						}
						else {
							blackKing = (King) chessBoard.board[dest_coordinates[0]][dest_coordinates[1]];
							blackKing_coordinates[0] = dest_coordinates[0]; blackKing_coordinates[1] = dest_coordinates[1];
						}
					}

					//if it's white's turn and white's move puts the whiteKing in check it is invalid
					if(!whiteMove && whiteKing.isInCheck(whiteKing_coordinates, whiteKing, chessBoard) != null) {
						whiteMove = Utility.handleIllegalCheck(source_coordinates, dest_coordinates, source_piece, dest_piece, whiteKing_coordinates, whiteKing, chessBoard, whiteMove);
						shouldDisplay = false;
						//reversing state of src piece's fields
						source_piece.isFirstMove = ogIFM;
						source_piece.justMovedTwice = ogJMT;
					}
					//if it's black's turn and black's move puts the blackKing in check it is invalid
					else if(whiteMove && blackKing.isInCheck(blackKing_coordinates, blackKing, chessBoard) != null) {
						whiteMove = Utility.handleIllegalCheck(source_coordinates, dest_coordinates, source_piece, dest_piece, blackKing_coordinates, blackKing, chessBoard, whiteMove);
						shouldDisplay = false;
						//reversing state of src piece's fields
						source_piece.isFirstMove = ogIFM;
						source_piece.justMovedTwice = ogJMT;
					}
					//check if opposite king is put in check
					else {
						boolean check = false;
						int[] threat_coordinates;
						if(whiteMove) {
							threat_coordinates = whiteKing.isInCheck(whiteKing_coordinates, whiteKing, chessBoard);
							if(threat_coordinates != null) {
								check = true;
								game = Utility.handleLegalCheck(whiteKing_coordinates, threat_coordinates, whiteKing, chessBoard, game);	

							}
						}
						else if(!whiteMove) {
							threat_coordinates = blackKing.isInCheck(blackKing_coordinates, blackKing, chessBoard);
							if(threat_coordinates != null) {
								check = true;
								game = Utility.handleLegalCheck(blackKing_coordinates, threat_coordinates, blackKing, chessBoard, game);

							}
						}
						if(game && check) System.out.println("Check");
						shouldDisplay = true;
					}
				}
				else {
					System.out.println("Illegal move, try again");
					whiteMove = !whiteMove;
					shouldDisplay = false;
				}
					
			}
			
			System.out.println();

		}

	}

}

