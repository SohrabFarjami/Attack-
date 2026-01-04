package sohrabfarjami.com;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class River{
	Array<Slot> attackSlots = new Array<Slot>(4);
	Array<Slot> defendSlots = new Array<Slot>(4);
	Array<Slot> slots = new Array<Slot>(8);

	Vector2 position;

	River(Vector2 position, int size){
		this.position = position;
		for (int i = 0; i < size; i++){
			attackSlots.add(new Slot(this.position.x + i, this.position.y));
			defendSlots.add(new Slot(this.position.x + i, this.position.y - 1f));
		}
		slots.addAll(attackSlots);
		slots.addAll(defendSlots);
	}

	public Array<Slot> getAttackSlots(){
		return attackSlots;
	}

	public Array<Slot> getDefendSlots(){
		return defendSlots;
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

	public Array<Slot> getEmptyAttackerSlots(){
		Array<Slot> emptySlots = new Array<>(4);
		for(Slot slot : attackSlots){
			if(!slot.hasCard()){
				emptySlots.add(slot);
			}
		}
		return emptySlots;
	}
	public Array<Slot> getEmptyDefenderSlots(){
		Array<Slot> emptySlots = new Array<>(4);
		for(Slot slot : defendSlots){
			if(!slot.hasCard()){
				emptySlots.add(slot);
			}
		}
		return emptySlots;
	} // Change these logics to all use one function
	
	public void removeCard(Card card){
		for(Slot slot : slots){
			if(slot.getCard() == card){
				slot.removeCard();
				return;
			}
		}
	}
	
	public void clearCards(){
		for(Slot slot : slots){
			slot.removeCard();
		}
	}

	public int cardCount(){
		int cardCount = 0;
		for(Slot slot : slots){
			if(slot.hasCard()){
				cardCount++;
			}
		}
		return cardCount;

	}

	public boolean isEmpty(){
		return(this.getCards().size == 0);
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

	public Array<Card> getDefenderCards(){
		Array<Card> cards = new Array<Card>(4);
		for(Slot slot : defendSlots){
			if(slot.hasCard()){
				cards.add(slot.getCard());
			}
		}
		return cards;
	}
	public Array<Card> getAttackerCards(){
		Array<Card> cards = new Array<Card>(4);
		for(Slot slot : attackSlots){
			if(slot.hasCard()){
				cards.add(slot.getCard());
			}
		}
		return cards;
	}
}
