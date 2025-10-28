import java.util.ArrayList;	

public class Deck extends ArrayList<Card>{
	public Deck(){
		super(36);
		for(Suit suit: Suit.values()){
			for(int i = 6; i < 14; i++)
			{
				this.add(new Card(i,suit));
			}
		}
	}

	public void getDeck(){
		for(Card card : this){
			System.out.print(card.getValue());
			System.out.println(card.getSuit());
		}
	}

}
