package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Player{
	private static int nextPlayer = 1;
	private int player;
	//private List<Card> hand = new ArrayList<Card>(4);
	private List<Card> wonCards = new ArrayList<Card>();
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

	public Vector2 getCardSlotPosition(Card card){
		return hand.getCardSlotPosition(card);
	}

	public void addWonCards(List<Card> cards){
		wonCards.addAll(cards);
	}

	public List<Card> getAllWonCards(){
		return wonCards;
	}

	public void remove(Card card){
		hand.remove(card);
	}
	public void removeAll(List<Card> cards){
		hand.removeAll(cards);
	}
	public List<Card> getHand(){
		return hand.getHand();
	}

	public int handSize(){
		return hand.size();
	}

	public void addPoints(int points){
		this.points += points;
	}

}
