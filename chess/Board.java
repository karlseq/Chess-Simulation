package chess;

//import java.util.ArrayList;
import pieces.*;

public class Board {

	ChessPiece[][] board = new ChessPiece[8][8]; //board, not including borders (file or rank labels)
	
	public void makeBoard() {
		//adds black pieces to board
		board[0][0] = new Rook("bR");
		board[0][1] = new Knight("bN");
		board[0][2] = new Bishop("bB");
		board[0][3] = new Queen("bQ");
		board[0][4] = new King("bK");
		board[0][5] = new Bishop("bB");
		board[0][6] = new Knight("bN");
		board[0][7] = new Rook("bR");
		for (int col=0; col<8; col++) {
			board[1][col] = new Pawn("bp");
		}
		
		//adds white pieces to board
		board[7][0] = new Rook("wR");
		board[7][1] = new Knight("wN");
		board[7][2] = new Bishop("wB");
		board[7][3] = new Queen("wQ");
		board[7][4] = new King("wK");
		board[7][5] = new Bishop("wB");
		board[7][6] = new Knight("wN");
		board[7][7] = new Rook("wR");
		for (int col=0; col<8; col++) {
			board[6][col] = new Pawn("wp");
		}
	}
	
	public void drawBoard() {
		int rankNum = 8; //allows to print the rank labels (1-8)
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				ChessPiece piece = board[i][j];
				if (piece != null) { //there is a piece on that square
					System.out.print(piece.toString() + " ");
				}
				else { //it's an empty square - could be black or white
					if (i%2==0) { //even row = white,black,white,black pattern
						if (j%2==0) { //white square
							System.out.print("   ");
						}
						else { //black square
							System.out.print("## ");
						}
					}
					else { //odd row = black,white,black,white pattern
						if (j%2==0) { //black square
							System.out.print("## ");
						}
						else { //white square
							System.out.print("   ");
						}
					}
				}
			}
			System.out.print(rankNum);
			rankNum--;
			System.out.println();
		}
		System.out.print(" a");
		for (char c='b'; c<105; c++) {
			System.out.print("  " + c);
		}
		System.out.println();
		System.out.println();
 	}
	

}
