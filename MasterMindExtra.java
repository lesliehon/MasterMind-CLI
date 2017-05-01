//Java CLI Master Mind
//Created by Leslie Hon in 2009

import java.util.*;

public class MasterMindExtra {
	public static int steptoguess = 10, colourAvailable = 6;	//Default allow 10 step and 6 colors Available for guess
	public static int playerGuess [], playerStep; //This array is used for store player guess data and a variable for counting step
	public static boolean playerWonGame; //The boolean show the player status (win or not)
	public static int black[], white[]; //2 array to store the number of Black / White of each step
	public static int finalAns[]; // This array is used for store final Answer
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]){
		do{
			gameSetting();
			setvalue();//This is new game, all value reset
			switch (checkPlayMode()){ //The value of playMode is already check in checkPlay Mode method, so the value of playMode must be 0 or 1
				case(0):
					for(int i=0 ; i<4 ; i++)
						finalAns[i] =(int)((Math.random()*colourAvailable)+1);//gen final ans and put it in finalAns array
					break;
				case(1): //Not play with computer
					do{
						System.out.print("Player1 input the answer: ");	
						finalAns= cutAns(sc.nextInt()); //Cut the final ans of player1 input
					}while(!checkInputRange( finalAns , "Your input is wrong, each number should be between 1-"+colourAvailable)); //check the player 1 input range
					for(int i = 1 ; i <= 60; i++)	//for loop to control print blank line (60times)
						System.out.println();		//Print blank line
					break;
			}//End the switch
			//Start player2 Guess 
			System.out.println("--- Player2 Starts to guess ---\nType '0' to leave the game.");
			do{
				player2guess(playerStep-1);  //Call function to let player 2 input guess
				checkAns( cutAns(playerGuess[playerStep-1]) ); //check the ans
				showResult(); //showing the result
			}while( (!playerWonGame) && (playerStep <= steptoguess) ); //break while the step is over (lose) or player 2 won the game
		}while (endGame());//when player want to quit , the loop will stop, than the program will terminated
		return;
	}//End main method
	
	public static int checkPlayMode(){
		int playMode;
		do{
			System.out.print("Computer VS Player (0-Yes / 1-No)? ");
			playMode = sc.nextInt(); //User input the playmode
		}while (!checkInputRange(playMode , "Please input 0 or 1 (0-Yes / 1-No)!"));// If user input not 0 /1  , will ask user input again
		return playMode; //return playMode to main method
	}

	public static void player2guess(int step){
		do{
			System.out.print("Step "+playerStep+": Please input your guess: "); //Show the message to tell player2 input the guess
			playerGuess [step] = sc.nextInt(); // Player 2 input the guess
			if(playerGuess [step] == 0) System.exit(0); //If user input '0' , the program will terminate.
		}while ( !checkInputRange( cutAns(playerGuess [step]) , "Your input is wrong!") ); //If user input wrong, it will show the error message.
		return;
	}

	public static void checkAns(int [] ans){
		boolean [] isBlack =new boolean [4]; //Array to store which number position* (playerGuess) is marked as Black
		boolean [] isWhite =new boolean [4]; //Array to store which number position* (playerGuess) is marked as White
		boolean [] ansReged = new boolean [4]; //Array to store which number (answer) is marked as Black or White
		//*Position: for example 1 6 5 2 , array[0] is 1 ...... array[3] is 2
		for (int i = 0 ; i < ans.length ; i++){ //first check black
			if(ans[i] == finalAns [i]){ //if ans [i] == finalAns [i] that means right colour and right position
				isBlack[i] = true; // if it is black, mark the isBlack[position] is true.
				black[playerStep-1]++; //add the number of black of this Step
				ansReged[i]= true; //marks this answer position is used
			}
		} //End Check Black	
		
		for (int i = 0 ; i < ans.length ; i++){ //check white
			for(int j=0 ; j < ans.length; j++){
				if( (ans[i] == finalAns[j]) && (!ansReged[j]) && (!isBlack[i]) && (!isWhite[i])){//White should be: the colour of guess should be same as answer (any position which is not used)
					isWhite[i] = true;// if it is white, mark the isWhite[position] is true.
					white[playerStep-1]++;//add the number of white of this Step
					ansReged[j]= true;//marks this answer position is used
					}
			}
		}	 //end check white
		
		if(black[playerStep-1] == 4) playerWonGame =  true; //if 4 black, mean player won the game
		return;
	}
	
	public static void showResult(){
		for(int i=1 ; i<=playerStep ; i++) System.out.println("Guess "+i+": "+playerGuess[i-1]+" Black:"+black[i-1]+" White:"+white[i-1]);
		if(!playerWonGame) System.out.println("==================================\n\n"); // if the game not end, show this line
		playerStep++;
		return;
	}
	
	public static boolean endGame(){
		int userCon;
		if(playerWonGame)	System.out.println("You Win!"); //if player won the game, show this message
		else 	System.out.println("You Lose! The answer is "+finalAns[0]+finalAns[1]+finalAns[2]+finalAns[3]); //if player lose the game, show this message
		do{
			System.out.print("Do you want to continue (0-Yes / 1-No) ?");
			userCon = sc.nextInt(); //Player input continue the game or not
		}while(!checkInputRange( userCon , "Please input 0 or 1 (0-Yes / 1-No)!")); //if player input not 0 or 1 , tell user input again.
		if(userCon ==0) return true; //if user want to continue, return 'true'
		else return false; //if user want to continue, return 'false'
	}

	public static int [] cutAns(int ansRecieved){
		int ans[]= new int [4];
		ans[0]= ansRecieved / 1000; //get the first number for example 1234 get 1
		ans[1]= ansRecieved %1000/100; //get the second number for example 1234 get 2
		ans[2]= ansRecieved %1000%100/10; //get the third number for example 1234 get 3
		ans[3]= ansRecieved %1000%100%10; //get the fourth number for example 1234 get 4
		return ans;
	}
	
	public static void setvalue(){  
		playerGuess = new int[steptoguess]; // set/ reset a array for player guess data
		black=new int[steptoguess]; // set/ reset the array for Number of Black for each step
		white=new int[steptoguess]; // set/ reset the array for Number of White for each step
		finalAns = new int[4]; //  set/ reset the array for final Answer
		playerStep = 1; // set/ reset the playerStep to 1
		playerWonGame = false; // set/reset the player is not won the game now
	}
		
	public static boolean checkInputRange(int [] ansRecieved, String errorMessage) {
		for(int i = 0 ; i < ansRecieved.length ; i++)//Check each number (guess / ans)
			if( ansRecieved[i] < 1 || ansRecieved[i]>colourAvailable ){//If the range is smaller than 1 or more than color 
				System.out.println(errorMessage);//Show the error message if the number is out of range
				return false; // If one number is not in range, return false
			}
		return true; //if all inRange, return true after for loop
	}
	
	public static boolean checkInputRange(int numRecieved , String errorMessage){
			if(numRecieved !=0 && numRecieved != 1){//if the number is not 0 and not 1
				System.out.println(errorMessage);//Show the error message if the number is out of range
				return false; //return false if not in range
			}
			else return true; //return true if in range
	}
	//this is bonus method for edit the step and colors
	public static void gameSetting(){
		System.out.print("How many step you want to let Player 2 guess: ");
		steptoguess = sc.nextInt();
		do{
			System.out.print("How many colours you want to let Player 2 guess (max 9 colours): ");
			colourAvailable = sc.nextInt();
		} while (colourAvailable < 2 || colourAvailable > 9);
		return;
	}
}//end class