package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;

public class GameState{
    public enum RoundState{
        ATTACKING,
        DEFENDING,
    }

    public enum RoundPhase{
	    PLAYER_TURN,
	    ANIMATION
    }

    private boolean pass = false;;
    private Player attacker;
    private Player defender;
    private RoundState roundState = RoundState.ATTACKING;
    private Card trumpCard;
    private RoundPhase roundPhase;
    private final Array<Slot> slots;

    public GameState(Player attacker, Player defender, Array<Slot> slots){
        this.attacker = attacker;
        this.defender = defender;
	this.slots = slots;
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


    public Array<Slot> getSlots(){
	    return slots;
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


    public void setRoundPhase(RoundPhase roundPhase){
	    this.roundPhase = roundPhase;
    }



}
