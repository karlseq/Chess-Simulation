package pieces;

public abstract class ChessPiece {
	String name; // name of the piece as shown on the board (e.g. "wp" for white pawn)
	
	public ChessPiece(String name) {
		this.name = name;
	}
	
	// the move() method will be implemented by each unique piece
	// ex: user inputs in command line "e3 e5"
	// srcRow and srcCol are the indexes corresponding to the piece occupying e3
	// destRow and destCol aret he indexes corresponding to where you wanna move e3 to, in this case e5
	// public abstract void move(int srcRow,int srcCol,int destRow,int destCol);
	
	public String toString() {
		return this.name;
	}
}
