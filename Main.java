import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main{
	static  Player p1 = new Player();
	static	Player p2 = new Player();

	static	Player attacker= getStartingPlayer(p1,p2);

	public static void main(String[] args){
		//Player attackingPlayer = startingPlayer;
		Random rand = new Random();

		Deck deck = new Deck();
		Card trumpCard = deck.get(rand.nextInt(deck.size()));
		System.out.printf("The trump card is %d of %s %n", trumpCard.getValue(),trumpCard.getSuit());
		dealCards(p1,deck,4);
		dealCards(p2,deck,4);

		System.out.print("Player 1 Hand:");
		for(Card card : p1.getHand()){
			System.out.printf("[%d of %s] ",card.getValue(),card.getSuit());
		}
		System.out.println();
		System.out.print("Player 2 Hand:");
		for(Card card : p2.getHand()){
			System.out.printf("[%d of %s] ",card.getValue(),card.getSuit());
		}
		System.out.println();
		System.out.printf("The starting player is Player %s %n",attacker.getPlayer());
		playRound();

	}
	static void playRound(){
		System.out.printf("Player %s's turn %n",attacker.getPlayer());
		List<Card> hand = attacker.getHand();
		var map = new HashMap<Integer,Card>(); 
		for(int i = 0; i < hand.size(); i++){
			Card card = hand.get(i);
			map.put(i,card);
			System.out.printf("[%d] %d of %ss %n",i,card.getValue(),card.getSuit());

		}
		List<Card> attackHand = new ArrayList<Card>();
		System.out.println("Enter the cards you wish to play");
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		Scanner lineScanner = new Scanner(line);
		while(lineScanner.hasNextInt()){
			int i = lineScanner.nextInt();
			if(map.containsKey(i)){
				attackHand.add(map.get(i));
				attacker.play((map.get(i)));
				map.remove(i);
			}
		}
		scan.close();
		lineScanner.close();
		System.out.println("Cards in the middle are :");
		printCards(attackHand);


	}

	static void dealCards(Player player, Deck deck, int count){
		Random rand = new Random();
		for(int i = 0; i < count; i++){
			int randomIndex = rand.nextInt(deck.size());
			Card randomCard = deck.get(randomIndex);
			player.draw(randomCard);
			deck.remove(randomIndex);
		}
	}
	static Player getStartingPlayer(Player... players){
		Random rand = new Random();
		return players[rand.nextInt(players.length)];
	}

	static void printCards(List<Card> cards){
		for(Card card : cards){
			System.out.printf("[%d of %s]%n",card.getValue(),card.getSuit());
		}
	}


}
