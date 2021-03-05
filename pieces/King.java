package pieces;

public class King extends ChessPiece {
	public King(Colors color) {
		super(color);
	}

	@Override
	public boolean isValidMove() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "K";
	}
}