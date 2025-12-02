package io.github.some_example_name;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.some_example_name.GameState.RoundState;



public class GameController{
	private final Player p1 = new Player();
	private final Player p2 = new Player();
	private List<Player> players = Arrays.asList(p1,p2);

    	private Player attacker;
    	private Player defender;

	private Deck deck;
	private Card trumpCard;
    	private List<Card> chosenCards = new ArrayList<>();
    	private final GameState gamestate;

	
	public GameController(TextureAtlas atlas){
		Collections.shuffle(players);
		gamestate = new GameState(players.get(0), players.get(1));
		deck = new Deck(true,atlas);

	}

	public void startGame(){
		gamestate.setTrumpCard(deck.get(1));
		trumpCard = gamestate.getTrumpCard();
		dealCards(4);
	}

		//System.out.printf("The starting player is Player %s %n",attacker.getPlayer()); maybe later add with names
	public void playRound(){
		if(gamestate.getRoundState() == RoundState.ATTACKING){
			attack();
		}else{
			defend();
		}
	}

	public void attack(){
		if(chosenCards.isEmpty()){
			return;
		}
        	defender = gamestate.getDefender();
        	attacker = gamestate.getAttacker();
		if(validAttack(chosenCards)){
			int pos = 2; //TODO remove hardcodes
			for(Card card : chosenCards){
				card.moveTo(pos++, 2, 0.1f, 0);
			}
			gamestate.setRiverCards(chosenCards);
			attacker.removeAll(chosenCards);
			switchPlaces(attacker, defender);

			gamestate.setRoundState(RoundState.DEFENDING);

			chosenCards.clear();
			// TODO fix ugly code
		}
	}
	
	public void defend(){
		List<Card> wonCards = new ArrayList<>();
        	defender = gamestate.getDefender();
        	attacker = gamestate.getAttacker();

		wonCards.addAll(chosenCards);
		wonCards.addAll(gamestate.getRiverCards());

		if(chosenCards.size() != gamestate.getRiverCards().size()){
			System.out.println("Too many or too little cards selected");
			return;
		}
		if(!gamestate.getPass()){
			// keep in here as valid defence doesnt know if user passed or not
			if(!validDefence(gamestate.getRiverCards(),chosenCards)){
				return;
			}
			defender.removeAll(chosenCards);
			defender.addWonCards(wonCards);
			gamestate.switchRoles();
		}
		else if(gamestate.getPass()){
			attacker.addWonCards(wonCards);;
		}
		gamestate.setRoundState(RoundState.ATTACKING);
		for(Card card : wonCards){
			card.turn(true);
			card.moveTo(Position.CURRENT_HAND.x + 4, Position.CURRENT_HAND.y, 0.3f, 0);
		}
		defender.removeAll(chosenCards);
		switchPlaces(defender, attacker);
		dealCards(gamestate.getRiverCards().size());
		gamestate.clearRiver();
		chosenCards.clear();
		gamestate.setPass(false);

		// TODO FIX GARBAGE CODE

		// TODO FIX GARBAGE CODE
	}


	private void switchPlaces(Player defender, Player attacker){
		for(Card card : defender.getHand()){
			card.turn();
			card.setY(card.getY() + 4);
		}
		for(Card card : attacker.getHand()){
			card.turn();
			card.setY(card.getY() - 4);
		}
	}
	private boolean validAttack(List<Card> hand){
		if(gamestate.getRoundState() == RoundState.ATTACKING){
			if(!Card.checkSuits(hand)){
				System.out.println("Invalid input. Suits must match");
				return false;
			}
		}
		return true;

	}

	private boolean validDefence(List<Card> attackHand, List<Card> defendHand){
		for(int i = 0; i < attackHand.size(); i++){
			Card attackCard = attackHand.get(i);
			Card defendCard = defendHand.get(i);
			if(attackCard.getSuit() != defendCard.getSuit() && defendCard.getSuit() != trumpCard.getSuit()){
				System.out.printf("%s does not beat %s (Wrong Suit) %n",defendCard,attackCard);
				return false;
			}
			if(defendCard.getValue() < attackCard.getValue() && defendCard.getSuit() == attackCard.getSuit()){
				System.out.printf("%s does not beat %s (Smaller Value) %n",defendCard,attackCard);
				if(defendCard.getSuit() == trumpCard.getSuit() && attackCard.getSuit() != trumpCard.getSuit()){
					return true;
				}
				return false;
			}
			if(defendCard.getSuit() == trumpCard.getSuit() && attackCard.getSuit() == trumpCard.getSuit()){
				if(defendCard.getValue() < attackCard.getValue()){
				System.out.printf("%s does not beat %s (Smaller Value) %n",defendCard,attackCard);
				return false;
				}
			}
		}

		return true;
	}

	public void dealCards(int count){
		float delay = 0;
		for(int i = 0 ; i < count ; i++){
			for(Player player : players){
				Card lastCard = deck.drawLast();
				player.addtoHand(lastCard);
				if(player == gamestate.getCurrentPlayer()){
					lastCard.turn(false);
					lastCard.moveTo(Position.CURRENT_HAND.x + player.getSlot(lastCard), Position.CURRENT_HAND.y, 0.5f,delay); //Remove hardcodes
					delay += 0.1f;
				}
				else{
					lastCard.turn(true);
					lastCard.moveTo(Position.OPPONENT_HAND.x + player.getSlot(lastCard), Position.OPPONENT_HAND.y, 0.5f,delay); //Remove hardcodes
					delay += 0.1f;
				}
			}
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

    public List<Card> getChosenCards(){
        return chosenCards;
    }

    public GameState getGameState(){
        return gamestate;
    }

    public void clickCard(Card chosenCard){
        if(chosenCards.contains(chosenCard)){
		chosenCards.remove(chosenCard);
		chosenCard.setY(chosenCard.getY() - 0.1f);
        }else{
		chosenCards.add(chosenCard);
		chosenCard.setY(chosenCard.getY() + 0.1f);
	} //TODO remove hardcodes and maybe add animation


    }

    public void clickPass(){
	boolean passState = gamestate.getPass();
	for(Card card : chosenCards){
		card.turn(!passState);
	}
	gamestate.setPass(!passState);
    }

    public Deck getDeck(){
	return deck;
    }


}
