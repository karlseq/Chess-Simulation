package pieces;

public class Pawn extends ChessPiece {
	public Pawn(Colors color) {
		super(color);
	}

	@Override
	public boolean isValidMove() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "p";
	}
}
