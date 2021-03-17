package chess;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import pieces.ChessPiece;
import pieces.Colors;
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
		
		int[] blackKing_coordinates = {0, 4};
		int[] whiteKing_coordinates = {7, 4};
		
		
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
			else if(move.contains("pass")) continue;  //TAKE OUT LATER
			/* regular move / promotion */
			else {
				
				int[] source_coordinates = Utility.getRowCol(move.substring(0, 2));
				int[] dest_coordinates = Utility.getRowCol(move.substring(3));
				
				
				ChessPiece source_piece = chessBoard.board[source_coordinates[0]][source_coordinates[1]];
				ChessPiece dest_piece = chessBoard.board[dest_coordinates[0]][dest_coordinates[1]];

				
				boolean moveIsValid = source_piece.isValidMove(source_coordinates, source_piece, dest_coordinates, dest_piece, chessBoard);
				if (moveIsValid) {
					source_piece.move(source_coordinates, source_piece, dest_coordinates, chessBoard);
					
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

					
					if(!whiteMove && whiteKing.isInCheck(whiteKing_coordinates, whiteKing, chessBoard)) {
						
						//reverse the move because it is invalid
						chessBoard.board[source_coordinates[0]][source_coordinates[1]] = source_piece;
						chessBoard.board[dest_coordinates[0]][dest_coordinates[1]] = dest_piece;
						
						//update piece if it's a king
						if(source_piece instanceof King) { 
							whiteKing = (King) chessBoard.board[source_coordinates[0]][source_coordinates[1]];
							whiteKing_coordinates[0] = source_coordinates[0]; whiteKing_coordinates[1] = source_coordinates[1];
						}
						System.out.println("Illegal move, try again");
						whiteMove = !whiteMove;

					}
					else if(whiteMove && blackKing.isInCheck(blackKing_coordinates, blackKing, chessBoard)) {
						//check if it's checkmate
						if(blackKing.checkForCheckMate(whiteKing_coordinates, whiteKing, chessBoard)) {
							System.out.println("Checkmate");
							System.out.println("White Win");
						}
						
						chessBoard.board[source_coordinates[0]][source_coordinates[1]] = source_piece;
						chessBoard.board[dest_coordinates[0]][dest_coordinates[1]] = dest_piece;
						
						//update piece if its a king
						if(source_piece instanceof King) { 
							blackKing = (King) chessBoard.board[source_coordinates[0]][source_coordinates[1]];
							blackKing_coordinates[0] = source_coordinates[0]; blackKing_coordinates[1] = source_coordinates[1];
						}
						System.out.println("Illegal move, try again");
						whiteMove = !whiteMove;
					}
					//check opp king
					else {
						boolean check = false;
						if(whiteMove && whiteKing.isInCheck(whiteKing_coordinates, whiteKing, chessBoard)) {
							check = true;
							if(whiteKing.checkForCheckMate(whiteKing_coordinates, whiteKing, chessBoard)) {
								chessBoard.drawBoard();
								System.out.println("Checkmate");
								System.out.println("Black wins");
								game = false;
								break;
							}
						}
						else if(!whiteMove && blackKing.isInCheck(blackKing_coordinates, blackKing, chessBoard)) {
							check = true;
							if(blackKing.checkForCheckMate(blackKing_coordinates, blackKing, chessBoard)) {
								chessBoard.drawBoard();
								System.out.println("Checkmate");
								System.out.println("White wins");
								game = false;
								break;
							}
						}
						if(check) System.out.println("check");
					}
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

