package pieces;

public class Knight extends ChessPiece {
	public Knight(Colors color) {
		super(color);
	}

	@Override
	public boolean isValidMove() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "N";
	}
}