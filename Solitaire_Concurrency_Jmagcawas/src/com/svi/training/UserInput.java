package com.svi.training;

import java.util.Scanner;

public class UserInput {
	public static int drawcount;
	public static int gamecount;
	public static int gamewon;
	public static int gameplayed;
	public static boolean doshuffle;
	public static Scanner scan = new Scanner(System.in);
	
	//get user input
		public int getDrawType(){
			String userInput = null;
			boolean wrongInput = true;
				while(wrongInput != false){
					System.out.println("Choose your DrawType (Type 1 for DrawOne / Type 3 for DrawThree) :");
					userInput = scan.next();
					
					switch (userInput){
						case "1" :
							System.out.println("You Choose 1");
							wrongInput = false;
							drawcount = 1;
							break;
						case "3" :
							System.out.println("You Choose 3");
							wrongInput = false;
							drawcount = 3;
							break;
						default:
							System.out.println("Wrong input");
							wrongInput = false;
							break;
					}
					
			  }
				System.out.println("*******************************");
				return drawcount;
		}
		
		
		public int getNumberOfGames(){
			String input;
				while(gamecount <= 0){
					System.out.println("Enter Number Of Games: ");
					while(!scan.hasNextInt()){
						 input = scan.next();
						 System.out.println("Invalid number of Game.");
						 System.out.println("Enter number of Games: ");
						
					}
					gamecount = scan.nextInt();
				}
				System.out.println("Number of Games: " + gamecount);
				System.out.println("*******************************");
				return gamecount;
		}
		
		public boolean shuffleOption(){
			String userInput;
			boolean invalidInput = true;
				while (invalidInput != false) {
					System.out.println("Do you want to shuffle the deck?(Enter Y/y for yes or N/n for no): ");
					userInput = scan.next().toUpperCase();
					   switch (userInput){
					   case "Y" : 
						   System.out.println("You choose YES");
						   doshuffle = true;
						   invalidInput = false;
						   break;
					   case "N" : 
						   System.out.println("You choose NO");
						   doshuffle = false;
						   invalidInput = false;
						   break;
						default:
						   System.out.println("Invalid answer");
						   invalidInput = true;
						   break;    
					   }      
				
				}
				System.out.println("*******************************");
				return doshuffle;
			}
		
		public void displayInput(){
			getDrawType();
			getNumberOfGames();
			shuffleOption();
		}
		
}
