package io.github.some_example_name;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
// import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.some_example_name.AnimationController.WaitType;
import io.github.some_example_name.GameState.RoundPhase;
import io.github.some_example_name.GameState.RoundState;



public class GameController{
	private final Player p1 = new Player(4,Vector2.Zero);
	private final Player p2 = new Player(4,Vector2.Zero);

	private List<Player> players = Arrays.asList(p1,p2);

    	private Player attacker;
    	private Player defender;

	private Deck deck;
	private Card trumpCard;
    	private List<Card> chosenCards = new ArrayList<>();
    	private final GameState gamestate;
	private final AnimationController animationController;
	
	private Card clickedCard;
	private River river = new River(Position.RIVER.position, 4);

	public GameController(TextureAtlas atlas){
		animationController = new AnimationController();
		Collections.shuffle(players);
		Array<Slot> totalSlots = new Array<Slot>(12);
		totalSlots.addAll(river.getSlots());
		totalSlots.addAll(p1.getSlots());
		totalSlots.addAll(p2.getSlots());
		gamestate = new GameState(players.get(0), players.get(1),totalSlots);
		players.get(0).setHandPosition(Position.CURRENT_HAND.position);
		players.get(1).setHandPosition(Position.OPPONENT_HAND.position);
		deck = new Deck(true,atlas);

	}

	public void startGame(){
		SpriteController.stackDeck(0.02f, deck);
		gamestate.setTrumpCard(deck.get(1));
		trumpCard = gamestate.getTrumpCard();
		dealCards(4);
	}

	public void playRound(){
		if(gamestate.getRoundState() == RoundState.ATTACKING){
			attack();
		}else{
			defend();
		}
		if(gamestate.getCurrentPlayer().getHand().size == 0){
			endGame();
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
				animationController.moveCardTo(pos++, 2, 0.1f,0.1f,card,WaitType.WAIT_START);
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
		Player winner = attacker;

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
			winner = defender;
			gamestate.switchRoles();
		}
		else if(gamestate.getPass()){
			switchPlaces(defender, attacker);
		}
		winner.addWonCards(wonCards);
		gamestate.setRoundState(RoundState.ATTACKING);
		gamestate.setRoundPhase(RoundPhase.ANIMATION);
		for(Card card : wonCards){
			card.turn(true);
			animationController.moveCardTo(Position.CURRENT_HAND.x + 4, (winner == defender) ? 0 : 4 , 5f,1f,card, WaitType.WAIT_START); //Comeup with better logic
		}
		defender.removeAll(chosenCards);
		dealCards(gamestate.getRiverCards().size());
		gamestate.clearRiver();
		chosenCards.clear();
		gamestate.setPass(false);

	}

	public void endGame(){
		float x = 2; //Fix these placeholders
		for(Player player : players){
			for(Card card : player.getAllWonCards()){
				card.turn(false);
				animationController.moveCardTo(x, 2, 0.1f,1f,card,WaitType.WAIT_START);
				player.addPoints(card.getPoints());
			}
			x++;
		}
	}


	private void switchPlaces(Player defender, Player attacker){
		for(Card card : defender.getHand()){
			card.turn();
			animationController.moveCardTo(card.getX(), card.getY() + 4, 0f, 0f, card, WaitType.WAIT_END);
		}
		for(Card card : attacker.getHand()){
			card.turn();
			animationController.moveCardTo(card.getX(), card.getY() - 4, 0f, 0f, card, WaitType.WAIT_END);
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
		for(int i = 0 ; i < Math.min(count, deck.size()/2) ; i++){
			for(Player player : players){
				Card lastCard = deck.drawLast();
				player.addtoHand(lastCard);
				if(player == gamestate.getCurrentPlayer()){
					lastCard.turn(false);
				}
				else{
					lastCard.turn(true);
				}
				animationController.moveCardTo(player.getCardSlotPosition(lastCard), 0.5f,0.1f,lastCard, WaitType.WAIT_START); //Remove hardcodes
				if(lastCard == trumpCard){
					lastCard.rotate90(true); //TODO fix this is not efficient
				}
				if(deck.size() == 0){
					trumpCard = lastCard;
					lastCard.turn(false);
				}
			}
		}
	}


    public List<Card> getChosenCards(){
        return chosenCards;
    }

    public GameState getGameState(){
        return gamestate;
    }

    public void drag(Vector2 touchpos){
	    if(clickedCard != null){
		    clickedCard.setPosition(touchpos.x - 0.5f, touchpos.y - 0.5f);
	    }
    }

    public void click(Vector2 touchpos){
	    Array<Card> validCards = new Array<>(4); //maybe remove hardcode if adding more card games in future
	    validCards.addAll(gamestate.getCurrentPlayer().getHand());
	    validCards.addAll(river.getCards());

	    for(Card card : validCards){
		    if(card.getBoundingRectangle().contains(touchpos)){
			    clickedCard = card;
			    return;
		    };
	    }
    }

    public void releaseDrag(){
	    if(clickedCard == null){
		    return;
	    }

	    Player player = gamestate.getCurrentPlayer();

	    Array<Slot> riverSlots = river.getEmptySlots();


	    float minDistance = 2f;
	    Slot minSlot = null;

	    for(Slot slot : riverSlots){
		    float distanceBetweenSlots = slot.getPosition().dst(clickedCard.getPosition());
		    if(distanceBetweenSlots <= minDistance){
			    minDistance = distanceBetweenSlots;
			    minSlot = slot;
		    };
	    }
	    if(minDistance <= 1f){ //Change logic to check min distance and then set it to that
		    player.remove(clickedCard);
		    river.removeCard(clickedCard);

		    minSlot.setCard(clickedCard);
		    clickedCard = null;
		    return;
	    }

	    river.removeCard(clickedCard);
	    player.remove(clickedCard);
	    player.addtoHand(clickedCard);

	    clickedCard = null;
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

    public AnimationController getAnimationController(){
	    return animationController;
    }


}
