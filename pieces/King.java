package pieces;

import chess.Board;

public class King extends ChessPiece implements ForwardMover, DiagonalMover{
	
	boolean isCastleMove = false; //tells if the move is a castling move
	
	public King(Colors color) {
		super(color);
	}

	
	@Override
	public String toString() {
		return super.toString() + "K";
	}

	@Override
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, char promotionPiece, Board chessBoard) {
		if (!checkLine(c1,cp1,c2,cp2,chessBoard) && !checkDiagonal(c1,cp1,c2,cp2,chessBoard)) {
			return false;
		}
		if (isMoreThanOne(c1,c2)) { //doesn't automatically make it invalid because of castling
			if (c1[0]==c2[0]) { //castling must be on the same row
				if (cp1.isFirstMove) { //for castling, it must be the king's first move
					int[] corner = whichCorner(c2);
					ChessPiece rook = chessBoard.getBoard()[corner[0]][corner[1]]; //getting the *potential* rook
					if (rook!=null) {
						int letter = rook.toString().length()-1;
						/* 1) Makes sure corner piece is a rook
						 * 2) Makes sure king and rook are of same color
						 * 3) Makes sure it's the rook's first move
						 */
						if (rook.toString().charAt(letter)=='R' && rook.color.equals(cp1.color) && rook.isFirstMove) {
							isCastleMove = true;
							cp1.isFirstMove = false;
							rook.isFirstMove = false;
						}
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	
	//returns true if the dest is more than one square away from the src
	private boolean isMoreThanOne(int[] c1, int[] c2) {
		if ((Math.abs(c1[0]-c2[0])>1) || (Math.abs(c1[1]-c2[1])>1)) {
			return true;
		}
		return false;
	}
	
	//determines which corner the rook for castling would be at
	private int[] whichCorner(int[] c2) {
		int[] corner = new int[2];
		if (c2[0]<3 && c2[1]<3) { //top left corner
			corner[0] = 0;
			corner[1] = 0;
		}
		else if (c2[0]<3 && c2[1]>3) { //top right corner
			corner[0] = 0;
			corner[1] = 7;
		}
		else if (c2[0]>3 && c2[1]<3) { //bottom left corner
			corner[0] = 7;
			corner[1] = 0;
		}
		else { //bottom right corner
			corner[0] = 7;
			corner[1] = 7;
		}
		return corner;
	}
	
	//moves the King to its appropriate spot
	//2 types: traditional and castling
	@Override
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, Board chessBoard) {
		if (isCastleMove) { //castle move
			chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
			chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
			int[] corner = whichCorner(dest_coordinates); //will help determine where the rook should go
			ChessPiece rook = chessBoard.getBoard()[corner[0]][corner[1]];
			if (corner[1]<3) { //rook was on left side of board -> goes to right of king
				chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]+1] = rook;
				chessBoard.getBoard()[corner[0]][corner[1]] = null;
			}
			else { //rook was on right side of board -> goes to left of king
				chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]-1] = rook;
				chessBoard.getBoard()[corner[0]][corner[1]] = null;
			}
		}
		else { //standard move
			chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
			chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
		}
	}
}