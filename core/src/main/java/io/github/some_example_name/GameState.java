package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

public class GameState{
    public enum RoundState{
        ATTACKING,
        DEFENDING
    }

    private boolean pass = false;;
    private Player attacker;
    private Player defender;
    private RoundState roundState = RoundState.ATTACKING;
    private Card trumpCard;
    private List<Card> riverCards = new ArrayList<>();

    public GameState(Player attacker, Player defender){
        this.attacker = attacker;
        this.defender = defender;
    }

    public Player getDefender(){
        return defender;
    }

    public Player getAttacker(){
        return attacker;
    }

    public RoundState getRoundState(){
        return roundState;
    }

    public Player getCurrentPlayer(){
        if(roundState == RoundState.ATTACKING){
            return attacker;
        }
        else{
            return defender;
        }
    }

    public Player getNextPlayer(){
	if(roundState == RoundState.ATTACKING){
		return defender;
	}
	else{
		return attacker;
	}
    }

    public Card getTrumpCard(){
        return trumpCard;
    }

    public void setRoundState(RoundState roundstate){
	this.roundState = roundstate;
    }

    public void setTrumpCard(Card trumpCard){
        this.trumpCard = trumpCard;
    }

    public List<Card> getRiverCards(){
	return riverCards;
    }

    public void setRiverCards(List<Card> cards){
	    riverCards.addAll(cards);
    }

    public void clearRiver(){
	    riverCards.clear();
    }

    public void switchRoles(){
	Player temp = defender;
	defender = attacker;
	attacker = temp;
    }

    public void setPass(boolean pass){
	    this.pass = pass;
    }
    public boolean getPass(){
	return pass;
    }



}
