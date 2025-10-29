import java.util.ArrayList;	

public class Deck{
	private ArrayList<Card> cards = new ArrayList<Card>();

	public Deck(){
		for(Suit suit: Suit.values()){
			for(int i = 6; i < 14; i++){
				cards.add(new Card(i,suit));
			}
		}
	}

	public void getDeck(){
		for(Card card : cards){
			System.out.print(card.getValue());
			System.out.println(card.getSuit());
		}
	}

	public void remove(Card card){
		cards.remove(card);
	}

	public void add(Card card){
		cards.add(card);
	}

}
