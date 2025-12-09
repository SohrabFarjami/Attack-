package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Hand{
	private Vector2 position;
	private Array<Slot> slots;

	Hand(Vector2 position, int maxSize){
		this.position = position;
		System.out.printf("Hand initialize x:%f y: %f %n",position.x,position.y);
		slots = new Array<Slot>(true,maxSize);
		for(int i = 0; i < maxSize; i++){
			Vector2 slotPosition = new Vector2(this.position.x + i, this.position.y);
			slots.add(new Slot(slotPosition));
		}
	}

	public List<Card> getHand(){
		List<Card> hand = new ArrayList<>();
		for(Slot slot : slots){
			if(slot.hasCard()){
				hand.add(slot.getCard());
			}
		}
		return hand;
	}

	public void setPosition(Vector2 position){
		Vector2 change = this.position.cpy().sub(position);
		this.position = position;
		for(Slot slot : slots){
			slot.setPosition(slot.getPosition().sub(change));
		}

	}

	public void addCard(Card card){
		for(Slot slot : slots){
			if(!slot.hasCard()){
				slot.setCard(card);
				return;
			}
		}
	}

	public void remove(Card card){
		for(Slot slot : slots){
			if(slot.getCard() == card){
				slot.removeCard();
				return;
			}
		}
	}

	public void removeAll(List<Card> cards){
		for(Card card : cards){
			for(Slot slot: slots){
				if(slot.getCard() == card){
					slot.removeCard();
				}
			}
		}
	}
	public Vector2 getCardSlotPosition(Card card){
		for(Slot slot: slots){
			if(slot.getCard() == card){
			return slot.getPosition();
			}
		}
		return null;
	}

	public int size(){
		int size = 0;
		for(Slot slot : slots){
			if(slot.hasCard()){
				size++;
			}
		}
		return size;
	}
}

