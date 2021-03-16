package pieces;

import chess.Board;

public class Pawn extends ChessPiece {
	
	/* Tells which direction the pawn is moving "forward" through the board:
	 * 	If it's a black pawn, it moves down the board
	 *	If it's a white pawn, it moves up the board
	 */
	boolean downOrientation = false;
	boolean justMovedTwice = false; //tells if the pawn had just moved two spaces as its first move
	
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
						if (cp1.isFirstMove) {
							cp1.isFirstMove = false;
						}
						else {
							return false;
						}
					}
					else if (Math.abs(c1[0]-c2[0])>2) return false;
				}
				else {
					return false;
				}
			}
			else { //white pawn
				if (c2[0]<c1[0]) { //good-destination is "above" the source
					if (Math.abs(c1[0]-c2[0])==2) { //can only move two spaces on the first move
						if (cp1.isFirstMove) {
							cp1.isFirstMove = false;
						}
						else {
							return false;
						}
					}
					else if (Math.abs(c1[0]-c2[0])>2) return false;
				}
				else {
					return false;
				}
			}
		}
		
		else { //must be a diagonal move (either for killing or en passant)
			if (downOrientation) {
				if ((c2[0]-c1[0]==1) || (Math.abs(c2[1]-c1[1])==1)) { //proper diagonal move for a black pawn
					if (cp2!=null) { //must be a piece of the opposite color (in this case a white piece)
						if (cp2.color.equals(Colors.BLACK)) {
							return false;
						}
					}
					else { //not immediately invalid because it could be an en passant move
						
					}
				}
				else {
					return false;
				}
			}
			else {
				
			}
		}
		
		return true;
	}
	
	private boolean isInBoard(int c1x, int c1y, int c2x, int c2y) {
		if ((c1x<0||c1x>7) || (c1y<0||c1y>7) || (c2x<0||c2x>7) || (c2y<0||c2y>7)) {
			return false;
		}
		return true;
	}
	
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, Board chessBoard) {
		
	}
}
