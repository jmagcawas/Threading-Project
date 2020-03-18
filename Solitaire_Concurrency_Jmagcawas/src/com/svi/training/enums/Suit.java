package com.svi.training.enums;

public enum Suit {
	   DIAMONDS("D","RED"), 
       HEARTS("H","RED"),
       SPADES("S","BLACK"), 
       CLUBS("C","BLACK"),
       NONE("X", "X");
	   

	   private final String SUIT_NAME;
	   private final String SUIT_COLOR;
		

	   //Contructor for compile time
	   private Suit(String suitName, String suitColor) {

		   SUIT_NAME = suitName;
		   SUIT_COLOR = suitColor;
		
	   }
	   
	   public String getSuitName(){
		   return SUIT_NAME;
	   }
	   
	   public String getSuitColor(){
		   return SUIT_COLOR;
	   }
}
