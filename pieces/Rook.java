package pieces;

public class Rook extends ChessPiece {
	public Rook(Colors color) {
		super(color);
	}

	@Override
	public boolean isValidMove() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "R";
	}
}
