import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SearchAlgorithm {

	private int maxIter;
	private int successCount;
	private int failCount;
	private int totalSteps;
	private int currentSteps;
	private int totaliterations;
	
	public SearchAlgorithm(int maxIter,int algo,int dimension) {
		this.successCount=0;
		this.totalSteps=0;
		this.failCount=0;
		this.totaliterations=0;
		this.maxIter=maxIter;
		Board board;
		if(algo==1) {
		for(int i=0;i<maxIter;i++) {
			System.out.println("############## Iteration number" + i +"#################################");
			board=new Board(dimension);
			board.printboard();
			System.out.println("Current Heuristic value of the board: " + this.calculateHeuristic(board));
			Board b=this.hillClimbingAlgo(board);	
			b.printboard();
		}}
		if(algo==2) {
			for(int i=0;i<maxIter;i++) {
				System.out.println("############## Iteration number" + i +"#################################");
				board=new Board(dimension);
				board.printboard();
				System.out.println("Current Heuristic value of the board: " + this.calculateHeuristic(board));
				Board b=this.hillClimbingAlgo(board);	
				b.printboard();
		}
		}
		if(algo==3 || algo==4) {
			while (true){
			// Generates a new board everytime as random restart	
			System.out.println("Random Resrart Number " + this.totaliterations);
			board=new Board(dimension);
			board.printboard();
			Board b;
			this.totaliterations++;
			System.out.println("Current Heuristic value of the board: " + this.calculateHeuristic(board));
			boolean solutionfound=false;
			if(algo==3) {
				solutionfound=this.hillClimbingRandomSideAlgo(board,false);	
			}else {
				solutionfound=this.hillClimbingRandomSideAlgo(board,true);	
			}
			if(solutionfound) {
				break;
			}
		}
			}
		this.printStats(algo);
		
	}

	private void printStats(int algo) {
		
		if(algo==1 || algo==2) {
		System.out.println("--------------------------------------------");
		System.out.println("Total Success = " + this.successCount);
		System.out.println("Total Steps= " + this.totalSteps);
		System.out.println("Fail count= " + this.failCount);
		}else {
			System.out.println("Total number of restarts= " + this.totaliterations);
		}
		
	}

	private Board hillClimbingAlgo(Board board) {
		boolean valid=true;
		int currentHValue=1000;
		int afterHValue=1000;
		Board tempBoard=board;
		currentHValue=this.calculateHeuristic(board);
		while(valid) {
			tempBoard=getlowercostboard(tempBoard);
			afterHValue=this.calculateHeuristic(tempBoard);
			if(currentHValue==afterHValue) {
				valid=false;
				break;
			}
			this.currentSteps++;
			currentHValue=afterHValue;
		}
		 if(afterHValue == 0) {
				this.successCount++;
				tempBoard.printboard();
				System.out.println("======= Solution Found In " + this.currentSteps+" =============");
				this.totalSteps=this.totalSteps+this.currentSteps;
				this.currentSteps=0;
			}else {
				System.out.println("========== No Solution Found ======================");
				this.totalSteps=this.totalSteps+this.currentSteps;
				this.currentSteps=0;
				this.failCount++;
			}	
		 return tempBoard;
	}
	
	
	private Board hillClimbingAlgoSideWays(Board board) {
		boolean valid=true;
		int currentHValue=1000;
		int afterHValue=1000;
		int sideways=0;
		Board tempBoard=board;
		currentHValue=this.calculateHeuristic(board);
		while(valid) {
			currentHValue=this.calculateHeuristic(tempBoard);
			tempBoard=getlowercostboard(tempBoard);
			afterHValue=this.calculateHeuristic(tempBoard);
			if(currentHValue==afterHValue) {
				if(afterHValue !=0) {
					tempBoard=this.moveSideWays(tempBoard);
					if(tempBoard==null) {
						break;
					}
					sideways++;
					if(sideways==100) {
						break;
					}
				this.currentSteps++;
				currentHValue=afterHValue;
				}else {
					break;
				}
			}
			this.currentSteps++;
			currentHValue=afterHValue;
		}
		 if(afterHValue == 0) {
				this.successCount++;
				tempBoard.printboard();
				System.out.println("======= Solution Found In " + this.currentSteps+" =============");
				this.totalSteps=this.totalSteps+this.currentSteps;
				this.currentSteps=0;
			}else {
				System.out.println("========== No Solution Found ======================");
				this.totalSteps=this.totalSteps+this.currentSteps;
				this.currentSteps=0;
				this.failCount++;
			}	
		 return tempBoard;
	}
	
	private boolean hillClimbingRandomSideAlgo(Board board, boolean isSideWays) {
		boolean valid=true;
		boolean result=false;
		int sideways=0;
		int currentHValue=1000;
		int afterHValue=1000;
		Board tempBoard=board;
		currentHValue=this.calculateHeuristic(board);
		while(valid) {
			tempBoard=getlowercostboard(tempBoard);
			afterHValue=this.calculateHeuristic(tempBoard);
			
			if(currentHValue==afterHValue) {
				if(isSideWays) {
					if(afterHValue !=0) {
						tempBoard=this.moveSideWays(tempBoard);
						if(tempBoard==null) {
							break;
						}
						sideways++;
						if(sideways==100) {
							break;
						}
						this.currentSteps++;
						currentHValue=afterHValue;
					}else {
						break;
					}
				}else {
					break;
				}
			}
			this.currentSteps++;
			currentHValue=afterHValue;
		}
		 if(afterHValue == 0) {
				this.successCount++;
				tempBoard.printboard();
				System.out.println("======= Solution Found In " + this.currentSteps+" =============");
				this.totalSteps=this.totalSteps+this.currentSteps;
				this.currentSteps=0;
				result=true;
			}else {
				System.out.println("========== No Solution Found ======================");
				this.totalSteps=this.totalSteps+this.currentSteps;
				this.currentSteps=0;
				this.failCount++;
				result=false;
			}	
		 return result;
	}
	
	private Board moveSideWays(Board board) {
		int currentHVal=calculateHeuristic(board);
		List<Board> sideMoves = new ArrayList<Board>(); 
		
		for(int col1=0;col1<board.getDimension();col1++) {
			for(int row1=0;row1<board.getDimension();row1++) {
				if(board.getChessBoard()[row1][col1]==1) {
					int col2=col1;
					for(int row2=0;row2<board.getDimension();row2++) {
							if(board.getChessBoard()[row2][col2] !=1) {
								Board tempBoard= new Board(board.getDimension());
								tempBoard.copyBoard(board.getChessBoard());
								tempBoard.getChessBoard()[row1][col1]=0;
								tempBoard.getChessBoard()[row2][col2]=1;
								int tempBoardHVal=this.calculateHeuristic(tempBoard);
								if(tempBoardHVal==currentHVal) {
									currentHVal=tempBoardHVal;
									sideMoves.add(tempBoard);
								}
							}
						}}}}
		if(sideMoves.size()==0) {
			return null;
		}else {
			if(sideMoves.size()==1) {
				return sideMoves.get(0);
			}else {
			return sideMoves.get(ThreadLocalRandom.current().nextInt(0, sideMoves.size()-1));
			}
		}	
	}

	private Board getlowercostboard(Board board) {
		int currentHVal=calculateHeuristic(board);
		Board lowestCollisionBoard=board;
		
		for(int row1=0;row1<board.getDimension();row1++) {
			for(int col1=0;col1<board.getDimension();col1++) {
				if(board.getChessBoard()[row1][col1]==1) {
					for(int row2=0;row2<board.getDimension();row2++) {
						for(int col2=0;col2<board.getDimension();col2++) {
							if(board.getChessBoard()[row2][col2] !=1) {
								Board tempBoard= new Board(board.getDimension());
								tempBoard.copyBoard(board.getChessBoard());
								tempBoard.getChessBoard()[row1][col1]=0;
								tempBoard.getChessBoard()[row2][col2]=1;
								int tempBoardHVal=this.calculateHeuristic(tempBoard);
								if(tempBoardHVal<currentHVal) {
									currentHVal=tempBoardHVal;
									lowestCollisionBoard=tempBoard;
								}
							//	tempBoard.printboard();
							//	System.out.println("----------------------");
							//	board.printboard();
							}
						}}}}}
		
		return lowestCollisionBoard;
	}

	private int calculateHeuristic(Board b) {
		// TODO Auto-generated method stub
		int hhVal=0;
		int hdval=0;
		
		for(int i=0;i<b.getDimension();i++) {
			for(int j=0;j<b.getDimension();j++) {
				if(b.getChessBoard()[i][j]==1) {
					hhVal=hhVal-2;
					for(int k=0;k<b.getDimension();k++) {
						if(b.getChessBoard()[i][k]==1) {
							hhVal++;
						}
						if(b.getChessBoard()[k][j]==1) {
							hhVal++;
						}
					}
					int m=i+1;
					int n=j+1;
					while(m<b.getDimension() && n<b.getDimension()) {
						if(b.getChessBoard()[m][n]==1) {
							hdval++;
						}
						m++;
						n++;
					}
					
					m=i+1;
					n=j-1;
					while(m<b.getDimension() && n>0) {
						if(b.getChessBoard()[m][n]==1) {
							hdval++;
						}
						m++;
						n--;
					}
					
					m=i-1;
					n=j-1;
					while(m>0 && n>0) {
						if(b.getChessBoard()[m][n]==1) {
							hdval++;
						}
						m--;
						n--;
					}
					
					m=i-1;
					n=j+1;
					while(n<b.getDimension() && m>0) {
						if(b.getChessBoard()[m][n]==1) {
							hdval++;
						}
						m--;
						n++;
					}
					
				}
			}
		}
		if(hhVal + hdval==0) {
	//		System.out.println("Hval is 0");
	//		b.printboard();
		}
		
		return hhVal+hdval;
	}

	public int getTotaliterations() {
		return totaliterations;
	}

	public void setTotaliterations(int totaliterations) {
		this.totaliterations = totaliterations;
	}
}
