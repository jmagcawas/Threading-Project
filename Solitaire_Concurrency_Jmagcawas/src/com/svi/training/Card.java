package com.svi.training;

import java.util.ArrayList;

import com.svi.training.enums.Rank;
import com.svi.training.enums.Suit;

public class Card implements Comparable <Card>{
	//Private Fields
	private Suit suit;

	private Rank rank;
	
	public Boolean isfaceup;
	
	public static Card Blank = new Card(Suit.NONE, Rank.NONE);
	
	// Controls the card for repetitive maneuver
	// Blank defines that the card didn't moved since last maneuver
	public Card lastParent = Blank;
	
	//Constructor Method
	public Card(Suit suit, Rank rank){
		this.rank = rank;
	    this.suit = suit;
	    isfaceup = false;
	 }
	
	//Public Methods
	public Suit getSuit(){
	     return suit;
	}
	public Rank getRank(){
		return rank;
	}
	
	public Card setFaceup(){
		isfaceup = true;
		return this;
	}
	
	public Card setFacedown(){
		isfaceup = false;
		return this;
	}
	
	public Boolean getFaceStatus(){
		return isfaceup;
	}
	
	//To string method
	public String toString(){
		String str = "";
		if (isfaceup) {
			str += rank.getRankName() + "-" + suit.getSuitName();
		} else {
           str = "X-X";
		}
		
		return str;
	}
	
	@Override
	public int compareTo(Card compareCard) {
      int compareValue = 0;
      
      if (this.suit.getSuitColor() != compareCard.getSuit().getSuitColor()) {
		if (this.rank.getRankVaue() == compareCard.rank.getRankVaue()-1) {
			return compareValue = -1;
		} else {
				return compareValue = 1;
		}
      } 
      return compareValue;
	}
	
	public boolean canTurnOverToFoundation(ArrayList<Card> foundation) {
		if (!this.getFaceStatus()) return false;
		
		if (foundation.size() < 1) {
			if (this.getRank() == Rank.ACE) {
				return true;
			}
		} else {
			Card destinationCard = foundation.get(foundation.size() - 1);
			if (this.rank.getRankVaue() == destinationCard.rank.getRankVaue() + 1 &&
				this.getSuit() == destinationCard.getSuit()) {
				
				return true;
			}
		}
		return false;
	}
}
// end rank class

	
