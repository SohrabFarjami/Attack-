package io.github.some_example_name;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;



public class GameController{
	private final Player p1 = new Player();
	private final Player p2 = new Player();
	private List<Player> players = Arrays.asList(p1,p2);

    private Player attacker;
    private Player defender;


	private Scanner scan = new Scanner(System.in);
	private Deck deck;
	private Card trumpCard;
    private Set<Card> chosenCards = new HashSet<>();
    private final GameState gamestate;

	public GameController(TextureAtlas atlas){
        Collections.shuffle(players);
        gamestate = new GameState(players.get(0), players.get(1));
	    deck = new Deck(true,atlas);
        gamestate.setTrumpCard(deck.get(1));
        trumpCard = gamestate.getTrumpCard();

		dealCards(p1,4);
		dealCards(p2,4);
		//System.out.printf("The starting player is Player %s %n",attacker.getPlayer()); maybe later add with names
//		while(deck.hasCards()){
//			playRound();
//		}

	}

	public List<Card> attack(){
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



	public List<Card> getCardsToPlay(Player player){
		List<Card> chosenCards = new ArrayList<>();
		List<Card> hand = player.getHand();
		printCards(hand,true,true);
		System.out.println("Enter the cards you wish to play (if defending in order from left to right)");
		String line = scan.nextLine();
		Scanner lineScanner = new Scanner(line);
		while(lineScanner.hasNextInt()){
			int i = lineScanner.nextInt();
			if(!chosenCards.contains(hand.get(i))){
				chosenCards.add(hand.get(i));
			}
			else{
				System.out.println("Invalid Entry. Try again");
				chosenCards = getCardsToPlay(player);
			}
		}
		lineScanner.close();
		return chosenCards;
	}
	public void defend(List<Card> attackHand){
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
		winner.addWonCards(wonCards);
		if(winner == defender){
			Player temp = defender;
			defender = attacker;
			attacker = temp;
		}
		return;



	}
	public void playRound(){
        defender = gamestate.getDefender();
        attacker = gamestate.getAttacker();
		while(defender.handSize() < 4 || attacker.handSize() < 4){
			if(defender.handSize() < 4){
				defender.addtoHand(deck.drawLast());
			}
			if(attacker.handSize() < 4){
				attacker.addtoHand(deck.drawLast());
			}
		}
		List<Card> attackHand = attack();
		System.out.print("Cards in the middle are :");
		printCards(attackHand, true, false);
		System.out.println();
		defend(attackHand);
	}

	public void dealCards(Player player,int count){
		for(int i = 0; i < count; i++){
			player.addtoHand(deck.drawLast());
		}
	}

	public void printCards(List<Card> cards, boolean printWithNewLine, boolean printNumbered){
		int index = 0;
		for(Card card : cards){
			if(printNumbered){
				System.out.printf("[%d] ",index++);
			}
			System.out.print(card);
				System.out.println();
			}
		}

	public boolean getPlayOrPass(){
		while(true){
			System.out.println("Enter y to place cards and n to pass");
			char answer = scan.next().charAt(0);
			scan.nextLine();
			if(answer == 'y' || answer == 'n'){
				return 'y' == answer;
			}
		}
	}

    public Set<Card> getChosenCards(){
        return chosenCards;
    }

    public GameState getGameState(){
        return gamestate;
    }

    public void clickCard(Card chosenCard){
        if(!chosenCards.add(chosenCard)){
            chosenCards.remove(chosenCard);
        };
    }


}
