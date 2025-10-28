import java.util.ArrayList;
import java.util.List;

public class Player{
	private static int nextPlayer = 1;
	private int player;
	private List<Card> hand = new ArrayList<Card>(4);
	private List<Card> wonCards = new ArrayList<Card>();


	public Player(){
		player = nextPlayer;
		nextPlayer++;
	}
	public int getPlayer(){
		return player;
	}

	public void draw(Card... cards){
		for(Card card:cards){
			hand.add(card);
		}

	}
	public void play(Card... cards){
		for(Card card : cards){
			hand.remove(card);
			System.out.println("Removing card " + card);
		}
	}
	public List<Card> getHand(){
		return hand;
	}


}
