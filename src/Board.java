import java.util.concurrent.ThreadLocalRandom;

public class Board {
	private int[] [] chessBoard;
	private int dimension;
	Board(int dimension){
		this.dimension=dimension;
		chessBoard= new int [dimension] [dimension];
		for(int i=0;i<dimension;i++) {
			int randomRow = ThreadLocalRandom.current().nextInt(0, dimension);
			int randomColumn = ThreadLocalRandom.current().nextInt(0, dimension);
			if(chessBoard[randomRow][randomColumn]==0) {
				chessBoard[randomRow][randomColumn]=1;
				continue;
			}else {
				i=i-1;
				continue;
			}	
		}
	}
	
	Board(){
		
	}

	public void printboard() {
		for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++) {
				System.out.print(this.chessBoard[i][j]+",");
			}
		System.out.println();
	}
}

	public int[][] getChessBoard() {
		return chessBoard;
	}

	public void setChessBoard(int[][] chessBoard) {
		this.chessBoard = chessBoard;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public void copyBoard(int[][] copyBoard) {
		for(int i=0;i<this.dimension;i++) {
			for(int j=0;j<this.dimension;j++) {
				this.chessBoard[i][j]=copyBoard[i][j];
			}
		}
	}
	
	
	
}
