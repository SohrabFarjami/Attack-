package io.github.some_example_name;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Deck{
	private ArrayList<Card> cards = new ArrayList<Card>();


	public Deck(boolean shuffle,TextureAtlas atlas){
    Sprite back = atlas.createSprite("card_back");
		for(Suit suit: Suit.values()){
            for(Pip pip : Pip.values()){
                Sprite front = atlas.createSprite("card_" + suit.name, pip.value);
                cards.add(new Card(pip,suit,front,back));
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
