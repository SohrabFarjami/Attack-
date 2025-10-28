


public class Card{
	private int value;
	private Suit suit;

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


}
