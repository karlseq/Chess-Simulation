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
					if (rightDest(corner,c2)==false) return false;
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
	
	//makes sure the dest coordinates are exactly right for castling
	private boolean rightDest(int[] corner, int[] c2) {
		if (corner[0]==0 && corner[1]==0) { //top left corner
			if (c2[0]!=0 || c2[1]!=2) return false;
		}
		else if (corner[0]==0 && corner[1]==7) { //top right corner
			if (c2[0]!=0 || c2[1]!=6) return false;
		}
		else if (corner[0]==7 && corner[1]==0) { //bottom left corner
			if (c2[0]!=7 || c2[1]!=2) return false;
		}
		else { //bottom right corner
			if (c2[0]!=7 || c2[1]!=6) return false;
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
	
	public int[] isInCheck(int[] king_coordinates, King king, Board chessBoard) {
		return super.canBeKilled(king_coordinates, king, 'A', null, chessBoard);
	}
	
	
	//just see what type the threatPiece is and determine the checks using that!!!
	public boolean checkForCheckMate(int[] king_coordinates, King king, Board chessBoard, int[] threat_coordinates) {
		ChessPiece threatPiece = chessBoard.getBoard()[threat_coordinates[0]][threat_coordinates[1]];
		
		//check if king can kill threat without falling into check again
		if(king.isValidMove(king_coordinates, king, threat_coordinates, threatPiece, 'A', chessBoard)) {
			//make king spot null
			chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = null;
			chessBoard.getBoard()[threat_coordinates[0]][threat_coordinates[1]] = king;
			
			boolean isValid = isInCheck(threat_coordinates, king, chessBoard) == null;

			//reverse move regardless of outcome
			chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
			chessBoard.getBoard()[threat_coordinates[0]][threat_coordinates[1]] = threatPiece;
			
			if(isValid) return false;
		}
		else if(canBeKilled(threat_coordinates, threatPiece, 'A', null, chessBoard) != null) return false;
		
		//check if king can flee
		int row = king_coordinates[0], col = king_coordinates[1];
		chessBoard.getBoard()[row][col] = null;
		
		//go to backwards row
		row = row - 1; col = col - 1;
		if(row >= 0) {
			for(int i = 0; i < 3; i++) {
				if((col >= 0 && col <=7) && chessBoard.getBoard()[row][col] == null) {
					if(!spotIsInCheck(row, col, king_coordinates, (King) king, chessBoard)) {
						return false;
					}
				}
				col++;
			}
		}
		
		//go to forward row
		row = king_coordinates[0]+1; col = king_coordinates[1]-1;
		if(row <=7) {
			for(int i = 0; i < 3; i++) {
				if((col >= 0 && col <=7) && chessBoard.getBoard()[row][col] == null) { //figure this out
					if(!spotIsInCheck(row, col, king_coordinates, (King) king, chessBoard)) {
						return false;
					}
				}
				col++;
			}
		}
		
		//left
		row = king_coordinates[0]; col = king_coordinates[1]-1;
		if(col >= 0 && (chessBoard.getBoard()[row][col] == null)) {
			if(!spotIsInCheck(row, col, king_coordinates, (King) king, chessBoard)) {
				return false;
			}
		}
		
		//right
		col = king_coordinates[1]+1;
		if(col <= 7 && (chessBoard.getBoard()[row][col] == null)) {
			if(!spotIsInCheck(row, col, king_coordinates, (King) king, chessBoard)) {
				return false;
			}
		}
		
		/* check if it can be saved*/
		
		//threat coordinate is above the king
		if(threat_coordinates[0] < king_coordinates[0] && threat_coordinates[1] == king_coordinates[1]) {
			for(int i = threat_coordinates[0]; i < king_coordinates[0]; i++) {
				//check if there is a savior in that spot
				int[] savior = canBeKilled(new int[] {i, threat_coordinates[1]}, chessBoard.getBoard()[i][threat_coordinates[1]],'A', chessBoard.getBoard()[threat_coordinates[0]][threat_coordinates[1]],chessBoard);
				if(savior != null) {
					chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
					return false;
				}
			}
		}
		
		//threat coordinate below the king
		if(threat_coordinates[0] > king_coordinates[0] && threat_coordinates[1] == king_coordinates[1]) {
			for(int i = threat_coordinates[0]; i > king_coordinates[0]; i--) {
				//check if there is a savior in that spot
				int[] savior = canBeKilled(new int[] {i, threat_coordinates[1]}, chessBoard.getBoard()[i][threat_coordinates[1]],'A', chessBoard.getBoard()[threat_coordinates[0]][threat_coordinates[1]],chessBoard);
				if(savior != null) {
					chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
					return false;
				}
			}
		}
		
		//threat coordinate horizontally on left
		if(threat_coordinates[0] == king_coordinates[0] && threat_coordinates[1] < king_coordinates[1]) {
			for(int i = threat_coordinates[1]; i < king_coordinates[1]; i++) {
				//check if there is a savior in that spot
				int[] savior = canBeKilled(new int[] {threat_coordinates[0], i}, chessBoard.getBoard()[threat_coordinates[0]][i],'A', chessBoard.getBoard()[threat_coordinates[0]][threat_coordinates[1]],chessBoard);
				if(savior != null) {
					chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
					return false;
				}
			}
		}
		
		//threat coordinate horizontally on right
		if(threat_coordinates[0] == king_coordinates[0] && threat_coordinates[1] > king_coordinates[1]) {
			for(int i = threat_coordinates[1]; i > king_coordinates[1]; i--) {
				//check if there is a savior in that spot
				int[] savior = canBeKilled(new int[] {threat_coordinates[0], i}, chessBoard.getBoard()[threat_coordinates[0]][i],'A', chessBoard.getBoard()[threat_coordinates[0]][threat_coordinates[1]],chessBoard);
				if(savior != null) {
					chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
					return false;
				}
			}
		}
		
		//handle diagonals
		if(checkDiagonal(threat_coordinates, threatPiece, king_coordinates, king, chessBoard)) {
			if(threat_coordinates[0] > king_coordinates[0]) { //king is behind
				if(threat_coordinates[1] > king_coordinates[1]) { //left
					for(int i=threat_coordinates[0], j=threat_coordinates[1]; i> king_coordinates[0] && j>king_coordinates[1];i--, j--) {
						int[] savior = canBeKilled(new int[] {i, j}, chessBoard.getBoard()[i][j],'A', threatPiece,chessBoard);
						if(savior != null) {
							chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
							return false;
						}
					}
				}
				else { //right
					for(int i=threat_coordinates[0], j=threat_coordinates[1]; i> king_coordinates[0] && j<king_coordinates[1];i--, j++) {
						int[] savior = canBeKilled(new int[] {i, j}, chessBoard.getBoard()[i][j],'A', threatPiece,chessBoard);
						if(savior != null) {
							chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
							return false;
						}
					}
				}
			}
			else { //ahead
				if(threat_coordinates[1] > king_coordinates[1]) { //right
					for(int i=threat_coordinates[0], j=threat_coordinates[1]; i< king_coordinates[0] && j>king_coordinates[1];i++, j--) {
						int[] savior = canBeKilled(new int[] {i, j}, chessBoard.getBoard()[i][j],'A', threatPiece,chessBoard);
						if(savior != null) {
							chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
							return false;
						}
					}
				}
				else { //left
					for(int i=threat_coordinates[0], j=threat_coordinates[1]; i< king_coordinates[0] && j<king_coordinates[1];i++, j++) {
						int[] savior = canBeKilled(new int[] {i, j}, chessBoard.getBoard()[i][j],'A', threatPiece,chessBoard);
						if(savior != null) {
							chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
							return false;
						}
					}
				}
			}
			
		}
			
		/* castling move */
		//check if castling possible
			//if so return false
		if (king.isFirstMove) {
			if (king.color.equals(Colors.BLACK)) {
				//checks if the black king can even do a castling move with the left rook
				if (king.isValidMove(king_coordinates, (ChessPiece) king, new int[] {0,2}, chessBoard.getBoard()[0][2],'A', chessBoard)) {
					chessBoard.getBoard()[0][2] = king; //put king in the new spot, not sure if isInCheck needs the king to be on the square
					if (king.isInCheck(new int[] {0, 2}, king,chessBoard)==null) { //means black king can escape check by castling to left
						chessBoard.getBoard()[0][2] = null;
						chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
						return false;
					}
				}
				//checks if the black king can even do a castling move with the right rook
				else if (king.isValidMove(king_coordinates, (ChessPiece) king, new int[] {0,6}, chessBoard.getBoard()[0][6],'A', chessBoard)) {
					chessBoard.getBoard()[0][6] = king;
					if (king.isInCheck(new int[] {0, 6},king,chessBoard)==null) { //means black king can escape check by castling to right
						chessBoard.getBoard()[0][6] = null;
						chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
						return false;
					}
				}
			}
			else {
				//checks if the white king can even do a castling move with the left rook
				if (king.isValidMove(king_coordinates, (ChessPiece) king, new int[] {7,2}, chessBoard.getBoard()[7][2],'A', chessBoard)) {
					chessBoard.getBoard()[7][2] = king;
					if (king.isInCheck(new int[] {7, 2},king,chessBoard)==null) { //means white king can escape check by castling to left
						chessBoard.getBoard()[7][2] = null;
						chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
						return false;
					}
				}
				//checks if the white king can even do a castling move with the right rook
				else if (king.isValidMove(king_coordinates, (ChessPiece) king, new int[] {7,6}, chessBoard.getBoard()[7][2],'A', chessBoard)) {
					chessBoard.getBoard()[7][6] = king;
					if (king.isInCheck(new int[] {7, 6},king,chessBoard)==null) { //means white king can escape check by castling to right
						chessBoard.getBoard()[7][6] = null;
						chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
						return false;
					}
				}
			}
		}
		
		chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
		return true;
	}
	
	private boolean spotIsInCheck(int row, int col, int[] king_coordinates, King king, Board chessBoard) {
		chessBoard.getBoard()[row][col] = king;
		boolean check = true;
		
		if(isInCheck(new int[]{row, col}, king, chessBoard) == null) {
			chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
			check = false;
		}
		chessBoard.getBoard()[row][col] = null;
		return check;
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
	public void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, char promotionPiece,Board chessBoard) {
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
			
			isCastleMove = false;
		}
		else { //standard move			
			chessBoard.getBoard()[dest_coordinates[0]][dest_coordinates[1]] = cp1;
			chessBoard.getBoard()[src_coordinates[0]][src_coordinates[1]] = null;
		}
	}
}