package com.svi.training.enums;

public enum Rank {
	
	   KING(13,"K"), QUEEN(12,"Q"), JACK(11,"J"),
	   TEN(10,"10"), NINE(9,"9"), EIGHT(8,"8"), SEVEN(7,"7"),
	   SIX(6,"6"), FIVE(5,"5"), FOUR(4,"4"),
	   THREE(3,"3"), TWO(2,"2"), ACE(1,"A"), NONE(0, "X");
		
		//private fields
	    private final int RANK_VALUE;
	    private final String RANK_NAME;
		
		//Constructor
		private Rank(int rankValue, String rankName) {
			this.RANK_VALUE = rankValue;
			this.RANK_NAME = rankName;
		}
		
		//Public Method
		public int getRankVaue(){
			return RANK_VALUE;
		}
		
		public String getRankName(){
			return RANK_NAME;
		}
		
		

}
