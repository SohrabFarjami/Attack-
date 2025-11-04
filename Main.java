import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main{
	static Random rand = new Random();
	static Player p1 = new Player();
	static Player p2 = new Player();
	static List<Player> players = Arrays.asList(p1,p2);
	static Map<String,Player> roles= getStartingPlayer(players);
	static Scanner scan = new Scanner(System.in);
	static Deck deck = new Deck();
	static Card trumpCard = deck.get(rand.nextInt(deck.size()));

	public static void main(String[] args){
		//Player attackingPlayer = startingPlayer;
		Player attacker = roles.get("attacker");
		Player defender = roles.get("defender");
		System.out.printf("The trump card is %d of %s %n", trumpCard.getValue(),trumpCard.getSuit());
		dealCards(p1,deck,4);
		dealCards(p2,deck,4);
		//System.out.printf("The starting player is Player %s %n",attacker.getPlayer()); maybe later add with names
		playRound(attacker,defender);

	}

	static List<Card> attack(Player attacker){
		List<Card> attackHand;
		do{
			attackHand = getCardsToPlay(attacker);
			if(!Card.checkSuits(attackHand)){
				System.out.println("Invalid input. Suits must match");
			}
		}while(!Card.checkSuits(attackHand));
		attacker.removeAll(attackHand);
		return attackHand;
	}

	static List<Card> getCardsToPlay(Player player){
		List<Card> chosenCards = new ArrayList<>();
		List<Card> hand = player.getHand();
		var map = new HashMap<Integer,Card>(); 
		for(int i = 0; i < hand.size(); i++){
			Card card = hand.get(i);
			map.put(i,card);
			System.out.printf("[%d] %d of %ss %n",i,card.getValue(),card.getSuit());

		}
		System.out.println("Enter the cards you wish to play (if defending in order from left to right)");
		String line = scan.nextLine();
		Scanner lineScanner = new Scanner(line);
		while(lineScanner.hasNextInt()){
			int i = lineScanner.nextInt();
			if(map.containsKey(i)){
				chosenCards.add(map.get(i));
				map.remove(i);
			}
			else{
				System.out.println("Invalid Entry. Try again");
				chosenCards = getCardsToPlay(player);
			}
		}
		lineScanner.close();
		return chosenCards;
	}
	static void defend(Player defender,Player attacker, List<Card> attackHand){
		List<Card> wonCards = new ArrayList<>();
		List<Card> defendHand = new ArrayList<>();
		Player winner = attacker;
		boolean validPlay = false;
		doloop:
		do{
			defendHand = getCardsToPlay(defender);
			if(attackHand.size() > defendHand.size()){
				System.out.println("Running");
				System.out.println("Too little cards selected. Try again.");
				continue;
			}
			if(attackHand.size() < defendHand.size()){
				System.out.println("Too many cards selected. Try again.");
				continue;
			}

			if(!getPlayOrPass()){
				System.out.println("Passing");
				break doloop;
			}


			for(int i = 0; i < attackHand.size(); i++){
				Card attackCard = attackHand.get(i);
				Card defendCard = defendHand.get(i);
				if(attackCard.getSuit() != defendCard.getSuit() && defendCard.getSuit() != trumpCard.getSuit()){
					System.out.printf("%s does not beat %s (Wrong Suit) %n",defendCard,attackCard);
					break;
				}
				if(defendCard.getValue() < attackCard.getValue() && defendCard.getSuit() != trumpCard.getSuit()){
					System.out.printf("%s does not beat %s (Smaller Value) %n",defendCard,attackCard);
					break;
				}
				if(defendCard.getValue() > attackCard.getValue() && defendCard.getSuit() == attackCard.getSuit()){
					validPlay = true;
				}
				if(defendCard.getSuit() == trumpCard.getSuit() && attackCard.getSuit() != trumpCard.getSuit()){
					validPlay = true;
				}
				if(defendCard.getSuit() == trumpCard.getSuit() && attackCard.getSuit() == trumpCard.getSuit()){
					if(defendCard.getValue() < attackCard.getValue()){
					System.out.printf("%s does not beat %s (Smaller Value) %n",defendCard,attackCard);
					}else{
						validPlay = true;
					}
				}
			}
		}
		while(!validPlay);
		if(validPlay){
			winner = defender;
		}
		wonCards.addAll(attackHand);
		wonCards.addAll(defendHand);
		
		System.out.printf("Winner is play %d",winner.getPlayer());
		System.out.println(wonCards);
		winner.addWonCards(wonCards);
		return;
		


	}
	static void playRound(Player attacker, Player defender){
		System.out.printf("Player %s's turn %n",attacker.getPlayer());
		List<Card> attackHand = attack(attacker);
		System.out.print("Cards in the middle are :");
		printCards(attackHand, false);
		System.out.println();
		defend(defender,attacker,attackHand);
	}

	static void dealCards(Player player, Deck deck, int count){
		for(int i = 0; i < count; i++){
			int randomIndex = rand.nextInt(deck.size());
			Card randomCard = deck.get(randomIndex);
			player.draw(randomCard);
			deck.remove(randomIndex);
		}
	}
	static Map<String,Player> getStartingPlayer(List<Player> players){
		var map = new HashMap<String,Player>();
		int randomIndex = rand.nextInt(players.size());
		Player attacker = players.get(randomIndex);
		Player defender = players.get(1 - randomIndex);
		map.put("attacker",attacker);
		map.put("defender",defender);

		return map;
	}

	static void printCards(List<Card> cards, boolean newLine){
		for(Card card : cards){
			if(newLine){
				System.out.printf("[%d of %s]%n",card.getValue(),card.getSuit());
			}
			else{
				System.out.printf("[%d of %s] ",card.getValue(),card.getSuit());
		}
		}
	}

	static boolean getPlayOrPass(){
		while(true){
			System.out.println("Enter y to place cards and n to pass");
			char answer = scan.next().charAt(0);
			scan.nextLine();
			if(answer == 'y' || answer == 'n'){
				return 'y' == answer;
			}
		}
	}


}
