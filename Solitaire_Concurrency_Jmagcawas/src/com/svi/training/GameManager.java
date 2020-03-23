package com.svi.training;

import java.util.ArrayList;
import java.util.Collections;

import com.svi.training.enums.Rank;

public class GameManager{
	private int loseIndicator = 10;
	private int currentIndicator = 0;

	boolean hasMove = false;

	ArrayList<Card> waste = new ArrayList<Card>();
	ArrayList<Card> manuever[] = new ArrayList [7];
	ArrayList<Card> foundation[] = new ArrayList [4];


	Deck deck = new Deck();
	/**
	 * Create Manuever 
	 */
	public void createManuever(){
		//initiate deck
		deck.initializeDeck();

		for (int i = 0; i < manuever.length; i++) { 
			manuever[i] = new ArrayList<>(); 
		}

		for (int i = 0; i < 7; i++){
			for (int j=0; j<=i; j++) {
				manuever[i].add(deck.cards.remove(0));
			}

		}
	}

	/**
	 * Create Foundation 
	 */
	public void createFoundation() {
		for (int i = 0; i < foundation.length; i++) { 
			foundation[i] = new ArrayList<>(); 
		}
	}
	/**
	 * faceuplast cards
	 */
	public void lastFaceUp(){
		for (int i = 0; i < manuever.length; i++) {
			manuever[i].get(manuever[i].size()-1).setFaceup();
		}
		System.out.println("Starting Cards:");
		for (int i = 0; i < manuever.length; i++) {
			System.out.println("Manuever#"+ (i + 1) + manuever[i]);   
		}
		System.out.println();
	}

	public Card getTalon() {
		if (!waste.isEmpty())
			return waste.get(waste.size() - 1);

		return null;
	}

	public boolean moveToTalon() {
		for (int i = 0; i < manuever.length; i++){
			ArrayList<Card> sourceManeuver = manuever[i];
			
			for (int m = 0; m < sourceManeuver.size(); m++) {
				Card movingCard = sourceManeuver.get(m);

				// skip this card if it is face down
				if (!movingCard.getFaceStatus()) continue;

				// check if this movingCard can be turn over to any foundation
				// and also if this moving card is the last card in maneuver
				for (int j = 0; j < foundation.length; j++) {
					ArrayList<Card> sourceFoundation = foundation[j];
					if (movingCard.canTurnOverToFoundation(sourceFoundation) &&
							m == sourceManeuver.size() - 1) {

						if (sourceFoundation.size() > 0) {
							Card lastCardOnFoundation = sourceFoundation.get(sourceFoundation.size() - 1);
							// move only if this is the first-time maneuver
							if (movingCard.lastParent != lastCardOnFoundation) {
								sourceFoundation.add(movingCard);
								sourceManeuver.remove(movingCard);

								// face up the last card after the movingCard (if not empty)
								if (sourceManeuver.size() > 0)
									sourceManeuver.get(sourceManeuver.size() - 1).setFaceup();

								// set hasMove to true to end the loop check
								hasMove = true;

								movingCard.lastParent = lastCardOnFoundation;
							}
						} else {
							sourceFoundation.add(movingCard);
							sourceManeuver.remove(movingCard);

							// face up the last card after the movingCard (if not empty)
							if (sourceManeuver.size() > 0)
								sourceManeuver.get(sourceManeuver.size() - 1).setFaceup();

							// set hasMove to true to end the loop check
							hasMove = true;
						}
					}
				}
			}
		}
		return hasMove;
	}

	public boolean moveToManuever() {
		// skip this card if it is face down

		for (int i = 0; i < manuever.length; i++){
			ArrayList<Card> sourceManeuver = manuever[i];
			for (int m = 0; m < sourceManeuver.size(); m++) {
				Card movingCard = sourceManeuver.get(m);
				//manuever to manuever prioritizing KING to move on the empty spaces
				for (int j = 0; j < manuever.length; j++) {
					ArrayList<Card> destinationManeuver = manuever[j];
					if (!movingCard.getFaceStatus()) continue;
					if (destinationManeuver.size() < 1) {
						// move the movingCard in this blank maneuver if it is a king
						if (movingCard.getRank() == Rank.KING) {
							for(int n = m; n < sourceManeuver.size(); n++) {

								// move only if this is the first-time maneuver
								if (movingCard.lastParent != null) {

									// move the movingCard together with its children
									destinationManeuver.addAll(sourceManeuver.subList(m, sourceManeuver.size()));
									sourceManeuver.removeAll(sourceManeuver.subList(m, sourceManeuver.size()));

									// face up the last card after the movingCard (if not empty)
									if (sourceManeuver.size() > 0)
										sourceManeuver.get(sourceManeuver.size()-1).setFaceup();

									// assign movingCard last parent as null
									movingCard.lastParent = null;
									printResult();
								}
							}
						}
					}

					// skip moving cards no card was found in this destination maneuver
					if (destinationManeuver.size() < 1) continue;

					Card destinationCard = destinationManeuver.get(destinationManeuver.size() - 1);
					if (movingCard.compareTo(destinationCard) == -1) {

						if (movingCard.lastParent != destinationCard) {

							// move the movingCard together with its children
							destinationManeuver.addAll(sourceManeuver.subList(m, sourceManeuver.size()));
							sourceManeuver.removeAll(sourceManeuver.subList(m, sourceManeuver.size()));
							hasMove = true;

							// face up the last card after the movingCard (if not empty)
							if (sourceManeuver.size() > 0)
								sourceManeuver.get(sourceManeuver.size()-1).setFaceup();

							// assign movingCard last parent as destinationCard
							movingCard.lastParent = destinationCard;

							printResult();
						}
					}
				}
			}
		}
		return hasMove;
	}

	public boolean DrawTalon() {
		if (hasMove == true) {
			System.out.println("No possible move in maneuver, picking talon card from deck");

			// Draw card from deck
			int drawCount = UserInput.drawcount;

			ArrayList<Card> deckCards = deck.cards;
			printResult();

			Card talon = getTalon();
			if (talon != null) {
				// Assume that the first source of talon card is in the waste list
				ArrayList<Card> talonSource = waste;

				// check if this talon can be turn over to any foundation
				for (int j = 0; j < foundation.length; j++) {
					if (talon.canTurnOverToFoundation(foundation[j])) {
						Card lastCardOnFoundation = Card.Blank;
						if (foundation[j].size() > 1)
							lastCardOnFoundation = foundation[j].get(foundation[j].size() - 1);
						foundation[j].add(talon);
						waste.remove(talon);
						// Change the talon's source to this foundation
						talonSource = foundation[j];
						talon.lastParent = null;

						break;
					}
				}

				// run maneuver once again to fill in talon card
				for (int i = 0; i < manuever.length; i++) {
					ArrayList<Card> destinationManeuver = manuever[i];

					// move the talon card in this blank maneuver if it is a king
					if (destinationManeuver.size() < 1) {
						if (talon.getRank() == Rank.KING) {
							// move the talon to blank maneuver
							if (talon.lastParent != null) {
								destinationManeuver.add(talon);
								waste.remove(talon);
								talon.lastParent = null;
							}
						}
					}

					// skip moving cards as no card was found in this destination maneuver
					if (destinationManeuver.size() < 1) continue;

					Card destinationCard = destinationManeuver.get(destinationManeuver.size() - 1);
					if (talon.compareTo(destinationCard) == -1) {
						if (talon.lastParent != destinationCard) {
							// move the talon to the end of the maneuver
							destinationManeuver.add(talon);

							// remove talon to its depending on its source
							talonSource.remove(talon);

							// set hasMove to true to end the loop check
							hasMove = true;
							
							printResult();
							talon.lastParent = destinationCard;
							break;
						}
					}
				}
			}

			// draw cards to waste
			if (deckCards.size() > 0) {
				// If the deck is not empty
				for (int i = 0; i < drawCount; i++) {
					// Push cards to talon n times
					if (!deckCards.isEmpty()) {
						Card drawCard = deckCards.get(deckCards.size() - 1);
						waste.add(drawCard);
						drawCard.setFaceup();
						deckCards.remove(deckCards.size() - 1);

					}
				}
			} else {
				// Put all card in waste list into the deck
				System.out.println("Resetting deck");
				deck.cards = new ArrayList<Card>(waste);
				Collections.reverse(deck.cards);
				waste.clear();
				// increment lose indicator
				currentIndicator++;
			}
		}
		System.out.println("--------------------");
		printResult();
		System.out.println("--------------------");
		//pressAnyKeyToContinue();
		return hasMove;
		
		
	}
	public void checkFoundation() {
		if (!foundationHasAllKing()) {
			// reset lose indicator to zero if there is any move happened last maneuver
			if (hasMove) {currentIndicator = 0;} 

			if (currentIndicator <= loseIndicator) {
				executeProcedure();
			} else {
				System.out.println("You've lost the game!");
				System.out.println("Score: " + UserInput.gamewon + " out of " + UserInput.gameplayed);
				printResult();
			}
		} else {
			UserInput.gamewon++;
			System.out.println("You've won the game!");
			System.out.println("Score: " + UserInput.gamewon + " out of " + UserInput.gameplayed);
		}
	}

//	public void executeProcedure(){
//		boolean hasMove = false;
//		for (int i = 0; i < manuever.length; i++){
//			ArrayList<Card> sourceManeuver = manuever[i];
//
//			for (int m = 0; m < sourceManeuver.size(); m++) {
//				Card movingCard = sourceManeuver.get(m);
//
//				// skip this card if it is face down
//				if (!movingCard.getFaceStatus()) continue;
//
//				// check if this movingCard can be turn over to any foundation
//				// and also if this moving card is the last card in maneuver
//				for (int j = 0; j < foundation.length; j++) {
//					ArrayList<Card> sourceFoundation = foundation[j];
//					if (movingCard.canTurnOverToFoundation(sourceFoundation) &&
//							m == sourceManeuver.size() - 1) {
//
//						if (sourceFoundation.size() > 0) {
//							Card lastCardOnFoundation = sourceFoundation.get(sourceFoundation.size() - 1);
//							// move only if this is the first-time maneuver
//							if (movingCard.lastParent != lastCardOnFoundation) {
//								sourceFoundation.add(movingCard);
//								sourceManeuver.remove(movingCard);
//
//								// face up the last card after the movingCard (if not empty)
//								if (sourceManeuver.size() > 0)
//									sourceManeuver.get(sourceManeuver.size() - 1).setFaceup();
//
//								// set hasMove to true to end the loop check
//								hasMove = true;
//
//								movingCard.lastParent = lastCardOnFoundation;
//							}
//						} else {
//							sourceFoundation.add(movingCard);
//							sourceManeuver.remove(movingCard);
//
//							// face up the last card after the movingCard (if not empty)
//							if (sourceManeuver.size() > 0)
//								sourceManeuver.get(sourceManeuver.size() - 1).setFaceup();
//
//							// set hasMove to true to end the loop check
//							hasMove = true;
//						}
//
//						break;
//					}
//				}
//
//				// skip this card if it is face down
//				if (!movingCard.getFaceStatus()) continue;
//
//				//manuever to manuever prioritizing KING to move on the empty spaces
//				for (int j = 0; j < manuever.length; j++) {
//					ArrayList<Card> destinationManeuver = manuever[j];
//
//					if (destinationManeuver.size() < 1) {
//						// move the movingCard in this blank maneuver if it is a king
//						if (movingCard.getRank() == Rank.KING) {
//							for(int n = m; n < sourceManeuver.size(); n++) {
//
//								// move only if this is the first-time maneuver
//								if (movingCard.lastParent != null) {
//
//									// move the movingCard together with its children
//									destinationManeuver.addAll(sourceManeuver.subList(m, sourceManeuver.size()));
//									sourceManeuver.removeAll(sourceManeuver.subList(m, sourceManeuver.size()));
//
//									// face up the last card after the movingCard (if not empty)
//									if (sourceManeuver.size() > 0)
//										sourceManeuver.get(sourceManeuver.size()-1).setFaceup();
//
//									// assign movingCard last parent as null
//									movingCard.lastParent = null;
//									printResult();
//								}
//							}
//						}
//					}
//
//					// skip moving cards no card was found in this destination maneuver
//					if (destinationManeuver.size() < 1) continue;
//
//					Card destinationCard = destinationManeuver.get(destinationManeuver.size() - 1);
//					if (movingCard.compareTo(destinationCard) == -1) {
//
//						if (movingCard.lastParent != destinationCard) {
//
//							// move the movingCard together with its children
//							destinationManeuver.addAll(sourceManeuver.subList(m, sourceManeuver.size()));
//							sourceManeuver.removeAll(sourceManeuver.subList(m, sourceManeuver.size()));
//							hasMove = true;
//
//							// face up the last card after the movingCard (if not empty)
//							if (sourceManeuver.size() > 0)
//								sourceManeuver.get(sourceManeuver.size()-1).setFaceup();
//
//							// assign movingCard last parent as destinationCard
//							movingCard.lastParent = destinationCard;
//
//							printResult();
//						}
//					}
//				}
//			}
//		}
////uppercopied
//		if (!hasMove) {
//			System.out.println("No possible move in maneuver, picking talon card from deck");
//
//			// Draw card from deck
//			int drawCount = UserInput.drawcount;
//
//			ArrayList<Card> deckCards = deck.cards;
//			printResult();
//
//			Card talon = getTalon();
//			if (talon != null) {
//				// Assume that the first source of talon card is in the waste list
//				ArrayList<Card> talonSource = waste;
//
//				// check if this talon can be turn over to any foundation
//				for (int j = 0; j < foundation.length; j++) {
//					if (talon.canTurnOverToFoundation(foundation[j])) {
//						Card lastCardOnFoundation = Card.Blank;
//						if (foundation[j].size() > 1)
//							lastCardOnFoundation = foundation[j].get(foundation[j].size() - 1);
//						foundation[j].add(talon);
//						waste.remove(talon);
//						// Change the talon's source to this foundation
//						talonSource = foundation[j];
//						talon.lastParent = null;
//
//						break;
//					}
//				}
//
//				// run maneuver once again to fill in talon card
//				for (int i = 0; i < manuever.length; i++) {
//					ArrayList<Card> destinationManeuver = manuever[i];
//
//					// move the talon card in this blank maneuver if it is a king
//					if (destinationManeuver.size() < 1) {
//						if (talon.getRank() == Rank.KING) {
//							// move the talon to blank maneuver
//							if (talon.lastParent != null) {
//								destinationManeuver.add(talon);
//								waste.remove(talon);
//								talon.lastParent = null;
//							}
//						}
//					}
//
//					// skip moving cards as no card was found in this destination maneuver
//					if (destinationManeuver.size() < 1) continue;
//
//					Card destinationCard = destinationManeuver.get(destinationManeuver.size() - 1);
//					if (talon.compareTo(destinationCard) == -1) {
//						if (talon.lastParent != destinationCard) {
//							// move the talon to the end of the maneuver
//							destinationManeuver.add(talon);
//
//							// remove talon to its depending on its source
//							talonSource.remove(talon);
//
//							// set hasMove to true to end the loop check
//							hasMove = true;
//
//							printResult();
//							talon.lastParent = destinationCard;
//							break;
//						}
//					}
//				}
//			}
//
//			// draw cards to waste
//			if (deckCards.size() > 0) {
//				// If the deck is not empty
//				for (int i = 0; i < drawCount; i++) {
//					// Push cards to talon n times
//					if (!deckCards.isEmpty()) {
//						Card drawCard = deckCards.get(deckCards.size() - 1);
//						waste.add(drawCard);
//						drawCard.setFaceup();
//						deckCards.remove(deckCards.size() - 1);
//
//					}
//				}
//			} else {
//				// Put all card in waste list into the deck
//				System.out.println("Resetting deck");
//				deck.cards = new ArrayList<Card>(waste);
//				Collections.reverse(deck.cards);
//				waste.clear();
//				// increment lose indicator
//				currentIndicator++;
//			}
//		}
//		System.out.println("--------------------");
//		printResult();
//		System.out.println("--------------------");
//		//pressAnyKeyToContinue();
////uppercopied
//		
//		if (!foundationHasAllKing()) {
//			// reset lose indicator to zero if there is any move happened last maneuver
//			if (hasMove) {currentIndicator = 0;} 
//
//			if (currentIndicator <= loseIndicator) {
//				executeProcedure();
//			} else {
//				System.out.println("You've lost the game!");
//				System.out.println("Score: " + UserInput.gamewon + " out of " + UserInput.gameplayed);
//				printResult();
//			}
//		} else {
//			UserInput.gamewon++;
//			System.out.println("You've won the game!");
//			System.out.println("Score: " + UserInput.gamewon + " out of " + UserInput.gameplayed);
//		}
//	}
	//uppercopied
	//printBoardChange
	private void printResult() {
		int deckCount = deck.cards.size();

		String wasteCards = "";
		// Get the last up to third to the last in waste list and display it on console
		for (int i = waste.size() - 1; i >= waste.size() - 3; i--) {
			// Check the waste list if the index is not out of bound
			if (i >= 0 && i < waste.size()) {
				if (i < waste.size() - 1) {
					wasteCards = waste.get(i).toString() + ", " + wasteCards;
				} else {
					wasteCards += waste.get(i).toString();
				}
			}
		}

		String foundationCards = "";
		for (int i = 0; i < foundation.length; i++) {
			String foundationFrontCard = "";
			if (foundation[i].isEmpty()) {
				foundationFrontCard = "None ";
			} else {
				foundationFrontCard = foundation[i].get(foundation[i].size()-1).toString();
			}
			foundationCards += foundationFrontCard + " ";
		}

		int max = 0;
		for (int i = 0; i < manuever.length; i++) {
			if (max < manuever[i].size()) max = manuever[i].size();
		}

		System.out.println("[ " + deckCount + " ] — [ " + wasteCards + " ] — [ " + foundationCards + "]");

		for (int j = 0; j < max; j++) {
			for (int i = 0; i < manuever.length; i++) {
				String line = "";
				if (manuever[i].size() > j) {
					line += manuever[i].get(j) + "  ";
				} else {
					line += "     ";
				}
				System.out.print(line);
			}
			System.out.println();
		}
		System.out.println();


	}


	public boolean foundationHasAllKing() {
		boolean allKing = false;

		for (int f = 0; f < foundation.length; f++) {
			if (foundation[f].size() > 0) {
				Card lastCardOnFoundation = foundation[f].get(foundation[f].size() - 1);
				if (lastCardOnFoundation.getRank() == Rank.KING) {
					allKing = true;
					return allKing;

				}
				else {
					allKing = false;
					return allKing;
				}
			} else {
				allKing = false;
				return allKing;
			}
		}
		return allKing;
	}
//
	public void executeProcedure(){

			moveToTalon();
			moveToManuever();
			DrawTalon();
			foundationHasAllKing();
			checkFoundation();
		
	}
	
	private void pressAnyKeyToContinue() {
		System.out.println("Press Enter key to continue...");
		try {
			// Comment this line for automatic process
			//	    	UserInput.scan.nextLine();
			// ----
		} catch(Exception e) {}

	}
} 


