package pieces;

import chess.Board;

/**
 * Represents a generic chess piece
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public abstract class ChessPiece {
	 
	/**
	 * The color of the chess piece
	 */
	public Colors color; 
	
	/**
	 * Determines if it's the piece's first move (only for King, Rook, and Pawn)
	 */
	boolean isFirstMove = true;
	
	/**
	 * Determines if the Pawn just moved two spaces as its first move (only for pawns, specifically for en passant)
	 */
	boolean justMovedTwice = false;
	
	/**
	 * Creates a generic chess piece of the given color
	 * 
	 * @param c the color of the chess piece to be created
	 */
	public ChessPiece(Colors c) {
		this.color = c;
	}
	
	// the move() method will be implemented by each unique piece
	// ex: user inputs in command line "e3 e5"
	// srcRow and srcCol are the indexes corresponding to the piece occupying e3
	// destRow and destCol aret he indexes corresponding to where you wanna move e3 to, in this case e5
	// public abstract void move(int srcRow,int srcCol,int destRow,int destCol);
	
	/**
	 * Name of the piece (color + name -> eg. "wp" = white pawn)
	 */
	public String toString() {
		if(color.equals(Colors.BLACK)) {
			return "b";
		}
		return "w";
	}
	
	
	/**
	 * Determines if the move the user wants to make is legal or not
	 * 
	 * @param c1 coordinates of the source piece
	 * @param cp1 the source piece
	 * @param c2 coordinates of the destination
	 * @param cp2 the piece on the dest coordinates (if any)
	 * @param promotionPiece what piece the Pawn should be promoted to (only for Pawns)
	 * @param chessBoard actual chess board
	 * @return returns true if the user inputed a legal move
	 */
	public abstract boolean isValidMove(int[] c1, ChessPiece cp1, int[] c2, ChessPiece cp2,char promotionPiece,Board chessBoard);
	
	
	/**
	 * Moves the source piece to the destination square
	 * 
	 * @param src_coordinates coordinates of the source piece
	 * @param cp1 the source piece
	 * @param dest_coordinates coordinates of the destination square
	 * @param promotionPiece what piece the Pawn should be promoted to (only for Pawns)
	 * @param chessBoard actual chess board
	 */
	public abstract void move(int[] src_coordinates, ChessPiece cp1, int[] dest_coordinates, char promotionPiece,Board chessBoard);
	
	/**
	 * Checks if the given piece can be killed or empty block can be occupied
	 * 
	 * @param piece_coordinates coordinates of the given piece
	 * @param piece piece that is being checked
	 * @param promotionPiece what piece the Pawn should be promoted to (not useful for this method)
	 * @param threatPiece piece that can kill the given piece (initially null)
	 * @param chessBoard actual chess board
	 * @return returns the coordinates of a threat piece that can kill the given piece, null if given piece can't be killed
	 */
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
				/*if (currentPiece != null) {
					if (currentPiece.isValidMove(new int[] {piece_coordinates[0], i}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
						return new int[] {piece_coordinates[0], i};
					}
				}*/
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
			if(!isThreat) {
				if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color != threatPiece.color && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
			}
			row++; col--;
		}
		
		//check forward right diagonal //good
		col = piece_coordinates[1]+1; row = piece_coordinates[0]+1;
		currentPiece = null;
		while((col <= 7 && row <=7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(!isThreat) {
				if(currentPiece != null &&  currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color != threatPiece.color && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
			}
			row++; col++;
		}
		
		//check backward left diagonal //good
		col = piece_coordinates[1]-1; row = piece_coordinates[0]-1;
		currentPiece = null;
		while((row >= 0 && col >=0) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(!isThreat) {
				if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color != threatPiece.color && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
			}
			row--; col--;
		}
		
		//check backward right diagonal //good
		col = piece_coordinates[1]+1; row = piece_coordinates[0]-1;
		currentPiece = null;
		while((row >=0 && col <= 7) && currentPiece == null) {
			currentPiece = chessBoard.getBoard()[row][col];
			if(!isThreat) {
				if(currentPiece != null && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
			}
			else {
				if(currentPiece != null && currentPiece != threatPiece && currentPiece.color != threatPiece.color && currentPiece.isValidMove(new int[] {row, col}, currentPiece, piece_coordinates, piece, 
						promotionPiece,chessBoard)) {
					return new int[] {row, col};
				}
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
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}

			}
			col = piece_coordinates[1] + 1; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}
			}
		}
		//regular L backward //good
		row = piece_coordinates[0] - 2;
		if(row >= 0) {
			col = piece_coordinates[1]-1;//left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}
			}
			col = piece_coordinates[1] + 1; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}
			}

		}
		
		//sideways L forward //good
		row = piece_coordinates[0] + 1;
		if(row <= 7) {
			col = piece_coordinates[1] - 2;
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}
			}

			col = piece_coordinates[1] + 2; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}
			}
		}
		
		//sideways L backward //good
		row = piece_coordinates[0] - 1;
		if(row >= 0) {
			col = piece_coordinates[1] - 2; // left
			if(col >= 0) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}
			}

			col = piece_coordinates[1] + 2; //right
			if(col <= 7) {
				currentPiece = chessBoard.getBoard()[row][col];
				if(!isThreat) {
					if(currentPiece instanceof Knight && currentPiece.color != piece.color) {
						return new int[] {row, col};
					}	
				}
				else {
					if(currentPiece instanceof Knight && currentPiece!=threatPiece && currentPiece.color != threatPiece.color) {
						return new int[] {row, col};
					}
				}
			}

		}
		
		
		return null;
	}
}
