import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main{
	public static void main(String[] args){
		//Player attackingPlayer = startingPlayer;
		Player p1 = new Player();
		Player p2 = new Player();

		Player startingPlayer = getStartingPlayer(p1,p2);
		Random rand = new Random();

		List<Card> p1Hand = new ArrayList<Card>();
		List<Card> p2Hand = new ArrayList<Card>();
		Deck deck = new Deck();
		Card trumpCard = deck.get(rand.nextInt(deck.size()));
		System.out.printf("The trump card is %d of %s %n", trumpCard.getValue(),trumpCard.getSuit());
		dealCards(p1Hand,deck,4);
		dealCards(p2Hand,deck,4);

		System.out.print("Player 1 Hand:");
		for(Card card : p1Hand){
			System.out.printf("[%d of %s] ",card.getValue(),card.getSuit());
		}
		System.out.println();
		System.out.print("Player 2 Hand:");
		for(Card card : p2Hand){
			System.out.printf("[%d of %s] ",card.getValue(),card.getSuit());
		}
		System.out.println();
		System.out.printf("The starting player is Player %s %n",startingPlayer.getPlayer());
	}

	static void dealCards(List<Card> hand, Deck deck, int count){
		Random rand = new Random();
		for(int i = 0; i < count; i++){
			int randomIndex = rand.nextInt(deck.size());
			Card randomElement = deck.get(randomIndex);
			hand.add(randomElement);
			deck.remove(randomIndex);
		}
	}
	static Player getStartingPlayer(Player... players){
		Random rand = new Random();
		return players[rand.nextInt(players.length)];
	}

}
