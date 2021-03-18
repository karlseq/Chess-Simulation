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
	public boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2, Board chessBoard) {
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
	
	public boolean isInCheck(int[] king_coordinates, ChessPiece king, Board chessBoard) {
		
		/* How does this algorithm work?
		 * check each diagonal / horizontal/ vertical / and knight position going into King
		 * iterate through the entire line
		 * 		if the line is empty, perfect, no threat
		 * 		else if you find a piece there, 
		 * 			determine if isValidMove from that piece to king
		 *				if so, it's in check, if not, that part is good
		 * 
		 * 
		 * 
		 * make all of this a lambda?? or use interfaces
		 */
		
		//check left //good
		int i = king_coordinates[1] - 1;
		ChessPiece currentPiece = null;
		while(i >= 0 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[king_coordinates[0]][i];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {king_coordinates[0], i}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			i--;
		}
		
		//check right //good
		i = king_coordinates[1] + 1;
		currentPiece = null;
		while(i <= 7 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[king_coordinates[0]][i];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {king_coordinates[0], i}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			i++;
		}
		
		//check up //good
		i = king_coordinates[0] + 1;
		currentPiece = null;
		while(i <= 7 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[i][king_coordinates[1]];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {i, king_coordinates[1]}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			i++;
		}
		
		//check down
		i = king_coordinates[0] - 1;
		currentPiece = null;
		while(i >= 0 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[i][king_coordinates[1]];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {i, king_coordinates[1]}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			i--;
		}
		//check forward left diagonal //good
		int col = king_coordinates[1]-1, row = king_coordinates[0]+1;
		currentPiece = null;
		while((row <= 7 && col >=0) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row++; col--;
		}
		
		//check forward right diagonal //good
		col = king_coordinates[1]+1; row = king_coordinates[0]+1;
		currentPiece = null;
		while((col <= 7 && row <=7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row++; col++;
		}
		
		//check backward left diagonal //good
		col = king_coordinates[1]-1; row = king_coordinates[0]-1;
		currentPiece = null;
		while((row >= 0 && col >=0) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row--; col--;
		}
		
		//check backward right diagonal //good
		col = king_coordinates[1]+1; row = king_coordinates[0]-1;
		currentPiece = null;
		while((row >=0 && col <= 7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, king_coordinates, king, chessBoard)) {
				return true;
			}
			row--; col++;
		}
		
		
		//all you have to check is if the piece occupying that spot is a knight of opposite color
	
		//regular L forward //good
		row = king_coordinates[0] + 2;
		if(row <=7) {
			col = king_coordinates[1]-1;//left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}
			col = king_coordinates[1] + 1; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}
		}
		//regular L backward //good
		row = king_coordinates[0] - 2;
		if(row >= 0) {
			col = king_coordinates[1]-1;//left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}
			col = king_coordinates[1] + 1; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}

		}
		
		//sideways L forward //good
		row = king_coordinates[0] + 1;
		if(row <= 7) {
			col = king_coordinates[1] - 2;
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}

			col = king_coordinates[1] + 2; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}
		}
		
		//sideways L backward //good
		row = king_coordinates[0] - 1;
		if(row >= 0) {
			col = king_coordinates[1] - 2; // left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}

			col = king_coordinates[1] + 2; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && currentPiece.color != king.color) {
					return true;
				}
			}

		}
		
		
		return false;
	}
	
	public boolean checkForCheckMate(int[] king_coordinates, ChessPiece king, Board chessBoard) {
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
		if(row <=7) {
			row = king_coordinates[0]+1; col = king_coordinates[1]-1;
			for(int i = 0; i < 3; i++) {
				if((col >= 0 && col <=7) && chessBoard.getBoard()[row][col] == null) {
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
		
		chessBoard.getBoard()[king_coordinates[0]][king_coordinates[1]] = king;
		return true;
	}
	
	private boolean spotIsInCheck(int row, int col, int[] king_coordinates, King king, Board chessBoard) {
		chessBoard.getBoard()[row][col] = king;
		boolean check = true;
		
		if(!isInCheck(new int[]{row, col}, king, chessBoard)) {
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