package io.github.some_example_name;



public class GameState{
    enum RoundState{
        ATTACKING,
        DEFENDING
    }
    private Player attacker;
    private Player defender;
    private RoundState roundState = RoundState.ATTACKING;
    private Card trumpCard;

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

    public Card getTrumpCard(){
        return trumpCard;
    }

    public void setTrumpCard(Card trumpCard){
        this.trumpCard = trumpCard;
    }
}
