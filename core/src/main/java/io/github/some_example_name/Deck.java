package io.github.some_example_name;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Deck{
	private ArrayList<Card> cards = new ArrayList<Card>();


	public Deck(boolean shuffle,TextureAtlas atlas){
		for(Suit suit: Suit.values()){
            		for(Pip pip : Pip.values()){
		    		if(pip.index < 5){ // Set to 5 for normal game
				continue;
				}
			TextureRegion front = atlas.findRegion("card_" + suit.name, pip.index);
			TextureRegion back = atlas.findRegion("card_back");
			Card card = new Card(pip,suit,front,back);
			card.turn(true);
			card.setPosition(Position.DECK.x, Position.DECK.y);
			cards.add(card);
			}
		}
		if(shuffle){
			Collections.shuffle(cards);
		}

	}


	public void remove(int index){
		cards.remove(index);
	}
	public void remove(Card card){
		cards.remove(card);
	}

	public void add(Card card){
		cards.add(card);
	}

	public int size(){
		return cards.size();
	}

	public Card get(int index){
		return cards.get(index);
	}

	public List<Card> getAll(){
		return cards;
	}

	public Card drawLast(){
		//Card card = cards.getLast(); java 8 doesnt support sadge
        Card card = cards.get(cards.size() - 1);
		cards.remove(card);
		return card;
	}

	public boolean hasCards(){
		return !cards.isEmpty();
	}

	public void shuffle(){
		Collections.shuffle(cards);
	}

}
