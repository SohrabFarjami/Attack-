package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;

public class Slot{
	private Card activeCard;
	private Vector2 position;

	Slot(Vector2 position){
		this.position = position;
	}

	public boolean setCard(Card card){
		boolean wasEmpty = activeCard == null;
		activeCard = card;
		return wasEmpty;
	}

	public Card getCard(){
		return activeCard;
	}

	public boolean hasCard(){
		return activeCard != null;
	}

	public void removeCard(){
		activeCard = null;
	}

	public Vector2 getPosition(){
		return position;
	}

	public void setPosition(Vector2 position){
		this.position = position;
	}
}
