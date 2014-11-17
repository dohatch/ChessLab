package module;

public class ChessBoard {
	private Piece[][] board = new Piece[8][8];
	
	public void setPiece(Piece piece, Integer location1, Integer location2) {
		board[location1][location2] = piece;
	}
	
	public Piece getPiece(Integer location1, Integer location2) {
		return board[location1][location2];
	}
	
	public void printBoard() {
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++) {
				if(board[j][i] == null) {
					System.out.print("[ ]");
				}
				else {
					System.out.print("[" + board[j][i].getSymbol() + "]");
				}
			}
		System.out.println();
		}
	}
	
	public boolean checkBoard(int[] location) {
		return board[location[0]][location[1]] != null;
	}
	
	public void init() {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				board[i][j] = null;
	}
}
