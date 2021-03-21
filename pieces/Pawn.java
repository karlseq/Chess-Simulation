package pieces;

import chess.Board;

/**
 * Represents a Pawn chess piece
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Pawn extends ChessPiece {
	
	/**
	 * Tells which direction the pawn is moving "forward" through the board:
	 * 	If it's a black pawn, it moves down the board
	 *  If it's a white pawn, it moves up the board
	 */
	boolean downOrientation = false;
	
	/**
	 * tells if the Pawn had just moved two spaces as its first move
	 */
	boolean justMovedTwice = false;
	
	/**
	 * tells if the move is an en passant move
	 */
	boolean isEPMove = false; 
	
	/**
	 * Creates a Pawn chess piece of the given color
	 * 
	 * @param color the color of the Pawn piece (black or white)
	 */
	public Pawn(Colors color) {
		super(color);
	}
	
	@Override
	public String toString() {
		return super.toString() + "p";
	}

	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, char promotionPiece, Board chessBoard) {
		if (!isInBoard(c1[0],c1[1],c2[0],c2[1])) return false; //makes sure the move is within the bounds of the board
		if (this.color.equals(Colors.BLACK)) downOrientation = true; //pawn should move down the board because it's a black piece
		
		if (c1[1]==c2[1]) { //vertical move
			if (downOrientation) { //black pawn
				if (c2[0]>c1[0]) { //good-destination is "below" the source
					if (Math.abs(c1[0]-c2[0])==2) { //can only move two spaces on the first move
						ChessPiece p1 = chessBoard.getBoard()[c1[0]+1][c1[1]]; //making sure the square right after source is empty
						ChessPiece p2 = chessBoard.getBoard()[c1[0]+2][c1[1]]; //making sure the square two spaces after source is empty
						if (cp1.isFirstMove && p1==null && p2==null) {
							cp1.justMovedTwice = true;
						}
						else {
							return false;
						}
					}
					else if (Math.abs(c1[0]-c2[0])>2) return false;
					else { //this would happen if the pawn is only moving one space forward
						ChessPiece p1 = chessBoard.getBoard()[c1[0]+1][c1[1]]; //making sure the square right after source is empty
						if (p1!=null) return false;
					}
				}
				else {
					return false;
				}
			}
			else { //white pawn
				if (c2[0]<c1[0]) { //good-destination is "above" the source
					if (Math.abs(c1[0]-c2[0])==2) { //can only move two spaces on the first move
						ChessPiece p1 = chessBoard.getBoard()[c1[0]-1][c1[1]]; //making sure the square right after source is empty
						ChessPiece p2 = chessBoard.getBoard()[c1[0]-2][c1[1]]; //making sure the square two spaces after source is empty
						if (cp1.isFirstMove && p1==null && p2==null) {
							cp1.justMovedTwice = true;
						}
						else {
							return false;
						}
					}
					else if (Math.abs(c1[0]-c2[0])>2) return false;
					else { //this would happen if the pawn is only moving one space forward
						ChessPiece p1 = chessBoard.getBoard()[c1[0]-1][c1[1]]; //making sure the square right after source is empty
						if (p1!=null) return false;
					}
				}
				else {
					return false;
				}
			}
		}
		
		else { //must be a diagonal move (either for killing or en passant)
			if (downOrientation) { //black pawn
				if ((c2[0]-c1[0]==1) && (Math.abs(c2[1]-c1[1])==1)) { //proper diagonal move for a black pawn
					if (cp2!=null) { //must be a piece of the opposite color (in this case a white piece)
						if (cp2.color.equals(Colors.BLACK)) {
							return false;
						}
					}
					else { //not immediately invalid because it could be an en passant move
						if (c2[1]-c1[1]==-1) { //check for a white pawn on the left side of the black pawn
							ChessPiece wPawn = chessBoard.getBoard()[c1[0]][c1[1]-1];
							if (wPawn!=null && wPawn.color.equals(Colors.WHITE) && wPawn.toString().charAt(1)=='p') {
								if (wPawn.justMovedTwice) {
									isEPMove = true;
								}
								else {
									return false;
								}
							}
							else {
								return false;
							}
						}
						else { //check for a white pawn on the right side of the black pawn
							ChessPiece wPawn = chessBoard.getBoard()[c1[0]][c1[1]+1];
							if (wPawn!=null && wPawn.color.equals(Colors.WHITE) && wPawn.toString().charAt(1)=='p') {
								if (wPawn.justMovedTwice) {
									isEPMove = true;
								}
								else {
									return false;
								}
							}
							else {
								return false;
							}
						}
					}
				}
				else {
					return false;
				}
			}
			else { //white pawn
				if ((c2[0]-c1[0]==-1) && (Math.abs(c2[1]-c1[1])==1)) { //proper diagonal move for a white pawn
					if (cp2!=null) { //must be a piece of the opposite color (in this case a black piece)
						if (cp2.color.equals(Colors.WHITE)) {
							return false;
						}
					}
					else { //not immediately invalid because it could be an en passant move
						if (c2[1]-c1[1]==-1) { //check for a black pawn on the left side of the white pawn
							ChessPiece bPawn = chessBoard.getBoard()[c1[0]][c1[1]-1];
							if (bPawn!=null && bPawn.color.equals(Colors.BLACK) && bPawn.toString().charAt(1)=='p') {
								if (bPawn.justMovedTwice) {
									isEPMove = true;
								}
								else {
									return false;
								}
							}
							else {
								return false;
							}
						}
						else { //check for a black pawn on the right side of the white pawn
							ChessPiece bPawn = chessBoard.getBoard()[c1[0]][c1[1]+1];
							if (bPawn!=null && bPawn.color.equals(Colors.BLACK) && bPawn.toString().charAt(1)=='p') {
								if (bPawn.justMovedTwice) {
									this.isEPMove = true;
								}
								else {
									return false;
								}
							}
							else {
								return false;
							}
						}
					}
				}
				else {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Determines if both the src and dest coordinates are within the bounds of the chess board
	 * 
	 * @param c1x row index of the src piece
	 * @param c1y column index of the src piece
	 * @param c2x row index of the destination
	 * @param c2y column index of the destination
	 * @return returns true if both src and dest coordinates are within the bounds of the chess board
	 */
	private boolean isInBoard(int c1x, int c1y, int c2x, int c2y) {
		if ((c1x<0||c1x>7) || (c1y<0||c1y>7) || (c2x<0||c2x>7) || (c2y<0||c2y>7)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Sets each pawn of the given color justMovedTwice's field to false (needed for en passant)
	 * 
	 * @param color which color piece to look for while traversing through the chess board
	 * @param chessBoard actual chess board
	 */
	private void setJMT(Colors color, Board chessBoard) {
		for (int i=0; i<7; i++) {
			for (int j=0; j<7; j++) {
				ChessPiece piece = chessBoard.getBoard()[i][j];
				if (piece!=null && piece.color.equals(color) && piece.toString().charAt(1)=='p') {
					piece.justMovedTwice = false;
				}
			}
		}
	}
	
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, char promotionPiece, Board chessBoard) {
		chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
		chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
		cp1.isFirstMove = false;
		if (this.isEPMove) { //have to kill the pawn of the opposite color
			System.out.println("isEPMove was triggered");
			if (dest_coordinates[1]-src_coordinates[1]==-1) { //kill pawn that was to the left of the source piece
				chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]-1] = null;
			}
			else { //kill pawn that was to the right of the source piece
				chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]+1] = null;
			}
		}
		if (downOrientation) { //possible promotion for black pawn
			if (dest_coordinates[0]==7) { //black pawn reached end of board
				if (promotionPiece=='Q' || promotionPiece=='X') { //it'd be 'X' if no promotion piece is indicated -> assume Queen
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Queen(Colors.BLACK);
				}
				else if (promotionPiece=='N') {
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Knight(Colors.BLACK);
				}
				else if (promotionPiece=='B') {
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Bishop(Colors.BLACK);
				}
				else if (promotionPiece=='R') {
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Rook(Colors.BLACK);
				}
			}
		}
		else { //possible promotion for white pawn
			if (dest_coordinates[0]==0) { //white pawn reached end of board
				if (promotionPiece=='Q' || promotionPiece=='X') { //it'd be 'X' if no promotion piece is indicated -> assume Queen
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Queen(Colors.WHITE);
				}
				else if (promotionPiece=='N') {
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Knight(Colors.WHITE);
				}
				else if (promotionPiece=='B') {
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Bishop(Colors.WHITE);
				}
				else if (promotionPiece=='R') {
					chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = new Rook(Colors.WHITE);
				}
			}
		}
		
		//this block sets up for setJMT method
		Colors color = cp1.color;
		if (color.equals(Colors.BLACK)) {
			color = Colors.WHITE;
		}
		else {
			color = Colors.BLACK;
		}
		setJMT(color,chessBoard);
	}

}
