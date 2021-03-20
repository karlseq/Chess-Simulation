package pieces;

import chess.Board;

public abstract class ChessPiece {
	 
	public Colors color; // name of the piece as shown on the board (e.g. "wp" for white pawn)
	boolean isFirstMove = true; //only for piece, Rook, and Pawn
	boolean justMovedTwice = false; //only for Pawns - specifically for en passant
	
	public ChessPiece(Colors c) {
		this.color = c;
	}
	
	// the move() method will be implemented by each unique piece
	// ex: user inputs in command line "e3 e5"
	// srcRow and srcCol are the indexes corresponding to the piece occupying e3
	// destRow and destCol aret he indexes corresponding to where you wanna move e3 to, in this case e5
	// public abstract void move(int srcRow,int srcCol,int destRow,int destCol);
	
	public String toString() {
		if(color.equals(Colors.BLACK)) {
			return "b";
		}
		return "w";
	}
	
	//promotion piece only used in Pawn.java
	public abstract boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2,char promotionPiece,Board chessBoard);
	public abstract void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, char promotionPiece,Board chessBoard);
	
	//this method checks if the given piece can be killed or empty block can be occupied 
	public int[] canBeKilled(int[] piece_coordinates, ChessPiece piece, char promotionPiece, ChessPiece threatPiece, Board chessBoard) {
		
		/* How does this algorithm work?
		 * check each diagonal / horizontal/ vertical / and knight position going into piece
		 * iterate through the entire line
		 * 		if the line is empty, perfect, no threat
		 * 		else if you find a piece there, 
		 * 			determine if isValidMove from that piece to piece
		 *				if so, it's in check, if not, that part is good
		 * 
		 * 
		 * 
		 * make all of this a lambda?? or use interfaces
		 */
		
		boolean isThreat = threatPiece != null;
		
		//check left //good
		int i = piece_coordinates[1] - 1;
		ChessPiece currentPiece = null;
		while(i >= 0 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[piece_coordinates[0]][i];
			if(!isThreat) {
				if(currentPiece != null && currentPiece.isValidMove(new int[] {piece_coordinates[0], i}, currentPiece, piece_coordinates, piece, promotionPiece,chessBoard)) {
					return new int[] {piece_coordinates[0], i};
				}
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color!= threatPiece.color && currentPiece.isValidMove(new int[] {piece_coordinates[0], i}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {piece_coordinates[0], i};
				}
			}

			i--;
		}
		
		//check right //good
		i = piece_coordinates[1] + 1;
		currentPiece = null;
		while(i <= 7 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[piece_coordinates[0]][i];
			if(!isThreat) {
				if(currentPiece != null && currentPiece.isValidMove(new int[] {piece_coordinates[0], i}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {piece_coordinates[0], i};
				}	
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color != threatPiece.color && currentPiece.isValidMove(new int[] {piece_coordinates[0], i}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {piece_coordinates[0], i};
				}
			}
			i++;
		}
		
		//check up //good
		i = piece_coordinates[0] + 1;
		currentPiece = null;
		while(i <= 7 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[i][piece_coordinates[1]];
			if(!isThreat) {
				if(currentPiece != null && currentPiece.isValidMove(new int[] {i, piece_coordinates[1]}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {i, piece_coordinates[1]};
				}
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color != threatPiece.color && currentPiece.isValidMove(new int[] {i, piece_coordinates[1]}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {i, piece_coordinates[1]};
				}
			}
			i++;
		}
		
		//check down
		i = piece_coordinates[0] - 1;
		currentPiece = null;
		while(i >= 0 && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[i][piece_coordinates[1]];
			if(!isThreat) {
				if(currentPiece != null && currentPiece.isValidMove(new int[] {i, piece_coordinates[1]}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {i, piece_coordinates[1]};
				}				
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color != threatPiece.color && currentPiece.isValidMove(new int[] {i, piece_coordinates[1]}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {i, piece_coordinates[1]};
				}
			}

			i--;
		}
		
		//check forward left diagonal //good
		int col = piece_coordinates[1]-1, row = piece_coordinates[0]+1;
		currentPiece = null;
		while((row <= 7 && col >=0) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece != threatPiece && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
					promotionPiece,chessBoard)) {
				return new int[] {row, col};
			}
			row++; col--;
		}
		
		//check forward right diagonal //good
		col = piece_coordinates[1]+1; row = piece_coordinates[0]+1;
		currentPiece = null;
		while((col <= 7 && row <=7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece != threatPiece && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
					promotionPiece,chessBoard)) {
				return new int[] {row, col};
			}
			row++; col++;
		}
		
		//check backward left diagonal //good
		col = piece_coordinates[1]-1; row = piece_coordinates[0]-1;
		currentPiece = null;
		while((row >= 0 && col >=0) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece != threatPiece && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
					promotionPiece,chessBoard)) {
				return new int[] {row, col};
			}
			row--; col--;
		}
		
		//check backward right diagonal //good
		col = piece_coordinates[1]+1; row = piece_coordinates[0]-1;
		currentPiece = null;
		while((row >=0 && col <= 7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(currentPiece != null && currentPiece != threatPiece && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
					promotionPiece,chessBoard)) {
				return new int[] {row, col};
			}
			row--; col++;
		}
		
		
		//all you have to check is if the piece occupying that spot is a knight of opposite color
	
		//regular L forward //good
		row = piece_coordinates[0] + 2;
		if(row <=7) {
			col = piece_coordinates[1]-1;//left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}
			col = piece_coordinates[1] + 1; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}
		}
		//regular L backward //good
		row = piece_coordinates[0] - 2;
		if(row >= 0) {
			col = piece_coordinates[1]-1;//left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}
			col = piece_coordinates[1] + 1; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}

		}
		
		//sideways L forward //good
		row = piece_coordinates[0] + 1;
		if(row <= 7) {
			col = piece_coordinates[1] - 2;
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}

			col = piece_coordinates[1] + 2; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}
		}
		
		//sideways L backward //good
		row = piece_coordinates[0] - 1;
		if(row >= 0) {
			col = piece_coordinates[1] - 2; // left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}

			col = piece_coordinates[1] + 2; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(currentPiece instanceof Knight && (piece == null || currentPiece.color != piece.color)) {
					return new int[] {row, col};
				}
			}

		}
		
		
		return null;
	}
}
