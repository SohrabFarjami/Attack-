package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class River{
	Array<Slot> slots = new Array<Slot>(4);
	Vector2 position;

	River(Vector2 position, int size){
		this.position = position;
		for (int i = 0; i < size; i++){
			slots.add(new Slot(this.position.x + i, this.position.y));
		}
	}

	public Array<Slot> getSlots(){
		return slots;
	}

	public Array<Slot> getEmptySlots(){
		Array<Slot> emptySlots = new Array<>(4);
		for(Slot slot : slots){
			if(!slot.hasCard()){
				emptySlots.add(slot);
			}
		}
		return emptySlots;
	}

	public void removeCard(Card card){
		for(Slot slot : slots){
			if(slot.getCard() == card){
				slot.removeCard();
				return;
			}
		}
	}

	public Array<Card> getCards(){
		Array<Card> cards = new Array<Card>(4);
		for(Slot slot : slots){
			if(slot.hasCard()){
				cards.add(slot.getCard());
			}
		}
		return cards;
	}

}
