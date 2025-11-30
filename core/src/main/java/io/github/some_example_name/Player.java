package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

public class Player{
	private static int nextPlayer = 1;
	private int player;
	private List<Card> hand = new ArrayList<Card>(4);
	private List<Card> wonCards = new ArrayList<Card>();
	private Card[] handSlots = new Card[4];

	public Player(){
		player = nextPlayer;
		nextPlayer++;
	}
	public int getPlayer(){
		return player;
	}

	public void addtoHand(Card... cards){
		for(Card card:cards){
			hand.add(card);
			for(int i = 0; i < 4; i++){
				if(handSlots[i] == null){
					handSlots[i] = card; // Come up with better solution;
					break;
				}
			}
		}

	}

	public void addWonCards(List<Card> cards){
		wonCards.addAll(cards);
	}
	public void remove(Card card){
		removeSlot(card);
		hand.remove(card);
	}
	public void removeAll(List<Card> cards){
		for(Card card : cards){
			removeSlot(card);
		}
		hand.removeAll(cards);
	}
	public List<Card> getHand(){
		return hand;
	}

	public int handSize(){
		return hand.size();
	}

	private void removeSlot(Card... cards){
		for(Card card : cards){
			for(int i = 0; i < 4; i++){
				if(handSlots[i] == card){
					handSlots[i] = null;
					break;
				}
			}
		}
	}


	public int getSlot(Card card){
		for(int i = 0; i < 4; i++){
			if(handSlots[i] == card){
				return i;
			}
		}
		return -1;
		}

}
