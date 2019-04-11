import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("I am in driver");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of queens between 8 - 13");
		int numQueens=sc.nextInt();
		while(numQueens < 8 || numQueens > 13) {
			System.out.println("Invalid Number of Queens Entered. Please enter between 8 - 13");
			numQueens=sc.nextInt();
		}
		
		System.out.println("Enter the number of Algo");
		System.out.println("Steepest");
		System.out.println("Steepest Sideways");
		System.out.println("Random Restart");
		System.out.println("Random Restart with sideways");
		int algo=sc.nextInt();
		
		SearchAlgorithm sa= new SearchAlgorithm(100,algo,numQueens);
		
	}

}
