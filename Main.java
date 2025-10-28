import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main{
	public static void main(String[] args){
		List<Card> p1Hand = new ArrayList<Card>();
		List<Card> p2Hand = new ArrayList<Card>();
		Deck deck = new Deck();
		Suit[] suits = Suit.values(); 
		Random rand = new Random();
		Suit trumpSuit = suits[(rand.nextInt(4))];
		System.out.printf("The trump suit is %s %n", trumpSuit);
		dealCards(p1Hand,deck);
		for(Card card : p1Hand){
			System.out.printf("[%d of %s] ",card.getValue(),card.getSuit());
		}
		System.out.println();
	}

	static void dealCards(List<Card> hand, Deck deck){
		Random rand = new Random();
		System.out.println(deck.size());
		for(int i = 0; i < 4; i++){
			int randomIndex = rand.nextInt(deck.size());
			Card randomElement = deck.get(randomIndex);
			hand.add(randomElement);
			deck.remove(randomIndex);
		}
	}

}
