import java.util.List;

public class Card{
	private final int value;
	private final Suit suit;

	public Card(int v, Suit s){
		value = v;
		suit = s;

	}
	public Suit getSuit()
	{
		return suit;
	}
	
	public int getValue()
	{
		return value;
	}

	public static boolean checkSuits(List<Card> cards){
		Suit initialSuit =  cards.get(0).getSuit();
		for(Card card : cards){
			if(initialSuit != card.getSuit()){
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString(){
		return value + " of " + suit;
	}


}
