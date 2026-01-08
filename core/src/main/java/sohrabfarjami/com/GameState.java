package sohrabfarjami.com;

import com.badlogic.gdx.utils.Array;

public class GameState {
    public enum RoundState {
        ATTACKING,
        DEFENDING,
        ENDED
    }

    public enum RoundPhase {
        PLAYER_TURN,
        ANIMATION
    }

    private boolean pass = false;;
    private Array<Player> players;
    private Player attacker;
    private Player defender;
    private RoundState roundState = RoundState.ATTACKING;
    private Card trumpCard;
    private Deck deck;
    private RoundPhase roundPhase;
    private Array<Slot> slots;
    private River river;
    private boolean paused = false;

    public GameState() {
    }

    public GameState(Array<Player> players, Array<Slot> slots, Deck deck, River river) {
        this.river = river;
        this.players = players;
        this.attacker = players.get(0);
        this.defender = players.get(1);
        this.slots = slots;
        this.deck = deck;
    }

    public Player getDefender() {
        return defender;
    }

    public Player getAttacker() {
        return attacker;
    }

    public RoundState getRoundState() {
        return roundState;
    }

    public Player getCurrentPlayer() {
        if (roundState == RoundState.ATTACKING) {
            return attacker;
        } else {
            return defender;
        }
    }

    public Player getNextPlayer() {
        if (roundState == RoundState.ATTACKING) {
            return defender;
        } else {
            return attacker;
        }
    }

    public Card getTrumpCard() {
        return trumpCard;
    }

    public void setRoundState(RoundState roundstate) {
        this.roundState = roundstate;
    }

    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    public Array<Slot> getSlots() {
        return slots;
    }

    public void switchRoles() {
        Player temp = defender;
        defender = attacker;
        attacker = temp;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean getPass() {
        return pass;
    }

    public void setRoundPhase(RoundPhase roundPhase) {
        this.roundPhase = roundPhase;
    }

    public boolean getPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Array<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public River getRiver() {
        return river;
    }

    public Array<Card> getAllCards() {
        Array<Card> allCards = new Array<>();
        allCards.addAll(deck.getAll());
        allCards.addAll(river.getCards());
        for (Player player : players) {
            allCards.addAll(player.getHand());
        }
        return allCards;
    }
}
