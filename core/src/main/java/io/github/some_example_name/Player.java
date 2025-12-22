package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player{
	private static int nextPlayer = 1;
	private int player;
	//private List<Card> hand = new ArrayList<Card>(4);
	private Array<Card> wonCards = new Array<Card>();
	private Hand hand;
	private int points;

	public Player(int handSize, Vector2 handPosition){
		player = nextPlayer;
		nextPlayer++;
		hand = new Hand(handPosition, handSize);
	}
	public int getPlayer(){
		return player;
	}

	public void setHandPosition(Vector2 position){
		hand.setPosition(position);
	}

	public void addtoHand(Card... cards){
		for(Card card:cards){
			hand.addCard(card);
		}

	}

	public void addtoHand(boolean changePos, Card... cards){
		for(Card card:cards){
			hand.addCard(card,changePos);
		}

	}
	public Vector2 getCardSlotPosition(Card card){
		return hand.getCardSlotPosition(card);
	}

	public void addWonCards(Array<Card> cards){
		System.out.println("Running won cards");
		wonCards.addAll(cards);
	}

	public Slot getFirstEmptySlot(){
		return hand.getFirstEmptySlot();
	}

	public Array<Slot> getSlots(){
		return hand.getSlots();
	}


	public Array<Card> getAllWonCards(){
		return wonCards;
	}

	public void remove(Card card){
		hand.remove(card);
	}
	public void removeAll(Array<Card> cards){
		hand.removeAll(cards);
	}
	public Array<Card> getHand(){
		return hand.getHand();
	}

	public int handSize(){
		return hand.size();
	}

	public void addPoints(int points){
		this.points += points;
	}


}
