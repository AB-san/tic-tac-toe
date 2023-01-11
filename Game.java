import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		

		int t = 0;
		do{
			System.out.println("1. Computer Vs Player.");
			System.out.println("2. Player Vs Player.");
			System.out.println("3. Exit");
			t = scanner.nextInt();
			switch(t){
				case 1 :
					scanner.nextLine();
					cvp(scanner);
					break;
				case 2 : 
					scanner.nextLine();
					pvp(scanner);
					break;
				case 3 :
					System.out.println("Game Exit");
					break;
				default :
					System.out.println("Please Select Valid Game Mode.");
			}
		}while(t!=3);
		scanner.close();
	}

	//for player vs player
	private static void pvp(Scanner scanner){
		char[][] board = {{' ', ' ', ' '},
				 	      {' ', ' ', ' '}, 
				 	      {' ', ' ', ' '}};
		
		printBoard(board);
		while(true){
			//player 1 turn 
			playerTurn(board, scanner);
			if(hasContestantWon(board,'X')){
				System.out.println("Player 1 won.");
				break;
			}
			printBoard(board);
			if (isGameFinished(board)){
				break;
			}
			//player 2 turn
			player2Turn(board, scanner);
			if(hasContestantWon(board,'O')){
				System.out.println("Player 2 won.");
				break;
			}
			printBoard(board);
			if (isGameFinished(board)){
				break;
			}
		}
	}

	//for comp vs player
	private static void cvp(Scanner scanner){
		char[][] board = {{' ', ' ', ' '},
				 	      {' ', ' ', ' '}, 
				 	      {' ', ' ', ' '}};
		
		printBoard(board);
		while (true) {
			//player turn
			playerTurn(board, scanner);
			if (isGameFinished(board)){
				break;
			}
			printBoard(board);
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}

			//comp turn
			computerTurn(board);
			if (isGameFinished(board)){
				break;
			}
			printBoard(board);
		}
	}


	private static boolean isGameFinished(char[][] board) {
		//case 1 : if player wins 
		if (hasContestantWon(board, 'X')) {	
			printBoard(board);
			System.out.println("Player wins!");
			return true;
		}
		//case 2 : if comp wins
		if (hasContestantWon(board, 'O')) {	
			printBoard(board);
			System.out.println("Computer wins!");
			return true;
		}
		//case 3 : if board has no empty spaces
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == ' ') {
					return false;
				}
			}
		}
		printBoard(board);
		System.out.println("The game ended in a tie!");
		return true;
	}


	private static boolean hasContestantWon(char[][] board, char symbol) {
		if (//checking rows
			(board[0][0] == symbol && board [0][1] == symbol && board [0][2] == symbol) ||
			(board[1][0] == symbol && board [1][1] == symbol && board [1][2] == symbol) ||
			(board[2][0] == symbol && board [2][1] == symbol && board [2][2] == symbol) ||
			//checking cols
			(board[0][0] == symbol && board [1][0] == symbol && board [2][0] == symbol) ||
			(board[0][1] == symbol && board [1][1] == symbol && board [2][1] == symbol) ||
			(board[0][2] == symbol && board [1][2] == symbol && board [2][2] == symbol) ||
			//checking diags
			(board[0][0] == symbol && board [1][1] == symbol && board [2][2] == symbol) ||
			(board[0][2] == symbol && board [1][1] == symbol && board [2][0] == symbol) ) {
			return true;
		}
		return false;
	}


	private static void computerTurn(char[][] board) {
		// Random rand = new Random();
		// int computerMove;
		// while (true) {
		// 	computerMove = rand.nextInt(9) + 1;
		// 	if (isValidMove(board, Integer.toString(computerMove))) {
		// 		break;
		// 	}
		// }
		// System.out.println("Computer chose " + computerMove);
		// placeMove(board, Integer.toString(computerMove), 'O');
		
		int computerMove = 0, bestScore = Integer.MIN_VALUE;
		for(int i = 0;i<9;i++){
			if(isValidMove(board,Integer.toString(i))){
				placeMove(board, Integer.toString(i), 'O');//make any of the available move to check if its efficient
				int score = miniMax(board,0,false);
				placeMove(board, Integer.toString(i), ' ');//undo the move
				if(score > bestScore){
					bestScore = score;
					computerMove = i;
				}
			}
		}
		System.out.println("Computer chose " + computerMove);
		placeMove(board, Integer.toString(computerMove), 'O');
	}

	public static int miniMax(char[][] board, int depth,boolean isMax){
		if(hasContestantWon(board,'X'))//If minimizer won
		return -1;
		if(hasContestantWon(board,'O'))//If maximizer won
		return +1;
		if(isGameFinished(board))//if tie 
		return 0;
		if(isMax){
			int bestScore = Integer.MIN_VALUE;
			//comp turn;
			for(int i = 0; i < 9; ++i){
				if(isValidMove(board,Integer.toString(i))){
					placeMove(board, Integer.toString(i), 'O'); // ai move for testing
					int score = miniMax(board, depth+1, false);
					placeMove(board, Integer.toString(i), ' '); // undo the move
					bestScore = Math.max(bestScore, score);
				}
			}
			return bestScore;
		}
		else{
			int bestScore = Integer.MAX_VALUE;
			//player turn;
			for(int i = 0; i < 9; ++i){
				if(isValidMove(board,Integer.toString(i))){
					placeMove(board, Integer.toString(i), 'X'); //player move made for testing
					int score = miniMax(board, depth+1, true);
					placeMove(board, Integer.toString(i), ' '); //undo the move
					bestScore = Math.min(score, bestScore);
				}
			}
			return bestScore;
		}
	}

	//fucntion to check if the move is valid
	private static boolean isValidMove (char[][] board, String position) {
		switch(position) {
			case "1":
				return (board[0][0] == ' ');
			case "2":
				return (board[0][1] == ' ');
			case "3":
				return (board[0][2] == ' ');
			case "4":
				return (board[1][0] == ' ');
			case "5":
				return (board[1][1] == ' ');
			case "6":
				return (board[1][2] == ' ');
			case "7":
				return (board[2][0] == ' ');
			case "8":
				return (board[2][1] == ' ');
			case "9":
				return (board[2][2] == ' ');
			default:
				return false;
		}
	}

	private static void playerTurn(char[][] board, Scanner scanner) {
		String userInput;
		
		while (true) {
			System.out.println("P1: Where would you like to play? (1-9)");
			userInput = scanner.nextLine();
			if (isValidMove(board, userInput)){
				break;
			} else {
				System.out.println(userInput + " is not a valid move.");
			}
		}
		placeMove(board, userInput, 'X');
	}

	private static void player2Turn(char[][] board, Scanner scanner) {
		String userInput;
		
		while (true) {
			System.out.println("P2: Where would you like to play? (1-9)");
			userInput = scanner.nextLine();
			if (isValidMove(board, userInput)){
				break;
			} else {
				System.out.println(userInput + " is not a valid move.");
			}
		}
		//suggestion();
		placeMove(board, userInput, 'O');
	}

	//function used to place the symbol in chosen cell
	private static void placeMove(char[][] board, String position, char symbol) {
		switch(position) {
			case "1":
				board[0][0] = symbol;
				break;
			case "2":
				board[0][1] = symbol;
				break;
			case "3":
				board[0][2] = symbol;
				break;
			case "4":
				board[1][0] = symbol;
				break;
			case "5":
				board[1][1] = symbol;
				break;
			case "6":
				board[1][2] = symbol;
				break;
			case "7":
				board[2][0] = symbol;
				break;
			case "8":
				board[2][1] = symbol;
				break;
			case "9":
				board[2][2] = symbol;
				break;
			default:
				System.out.println(":(");
		}
	}

	private static void printBoard(char[][] board) {
		String s = "         ";
		System.out.println(s+"-----------------------");
		System.out.println(s+ "|  "+"    |   "+"    |      |" );
		System.out.println(s+ "|  " + board[0][0] + "   |   " + board[0][1] + "   |   " + board[0][2] + "  |");
		System.out.println(s+ "|  " + "   1|   " + "   2|     3|" );
		System.out.println(s+"-----------------------");
		System.out.println(s+ "|  "+"    |   "+"    |      |" );
		System.out.println(s+ "|  " + board[1][0] + "   |   " + board[1][1] + "   |   " + board[1][2] + "  |");
		System.out.println(s+ "|  " + "   4|   " + "   5|     6|" );
		System.out.println(s+"-----------------------");
		System.out.println(s+ "|  "+"    |   "+"    |      |" );
		System.out.println(s+ "|  " + board[2][0] + "   |   " + board[2][1] + "   |   " + board[2][2] + "  |");
		System.out.println(s+ "|  " + "   7|   " + "   8|     9|" );
		System.out.println(s+"-----------------------");
	}
	
}
  