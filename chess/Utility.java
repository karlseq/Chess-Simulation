package chess;

import pieces.King;

public class Utility {
	
	
	//returns row and col of the respective piece
	public static int[] getRowCol(String block) {
		int row = 8 - Integer.parseInt(String.valueOf(block.charAt(1)));
		int col = block.charAt(0) - 97;
		return new int[] {row,col};
	}
	
}
