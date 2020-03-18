package com.svi.training;

import java.util.ArrayList;
import java.util.Collections;

import com.svi.training.enums.Rank;
import com.svi.training.enums.Suit;

public class Deck {
	public ArrayList<Card> cards = new ArrayList<Card>();
	
	public ArrayList<Card> populate() {
		for(Suit suit : Suit.values()) {
			if (suit == Suit.NONE) continue;
			for(Rank rank : Rank.values()) {
				if (rank == Rank.NONE) continue;
				Card card = new Card(suit, rank);
				cards.add(card);
			}
		}
		return cards;
	 }
	
	public ArrayList<Card> shuffle(){
		if (UserInput.doshuffle == true){
			Collections.shuffle(cards);
		}
		return cards;
	}
	
	public void printDeck(){
		System.out.println("The following cards are: ");
		System.out.println(cards);
	}
	
	public void initializeDeck(){
		populate();
		shuffle();
	//	printDeck();
	}

	
	
	
   }
	


