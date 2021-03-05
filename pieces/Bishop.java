package pieces;

public class Bishop extends ChessPiece {
	public Bishop(Colors color) {
		super(color);
	}

	@Override
	public boolean isValidMove() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "B";
	}
}