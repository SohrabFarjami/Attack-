package io.github.some_example_name;



public class SpriteController{

	public static void stackDeck(float margin, Deck deck){
		float initialMargin = margin;
		for(Card card : deck.getAll()){
			card.setY(card.getY() + margin);
			margin += initialMargin;
		}
	}
}
