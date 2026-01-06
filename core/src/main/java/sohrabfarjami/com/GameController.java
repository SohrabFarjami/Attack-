package sohrabfarjami.com;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import sohrabfarjami.com.AnimationController.WaitType;
import sohrabfarjami.com.GameState.RoundPhase;
import sohrabfarjami.com.GameState.RoundState;

public class GameController {
    private final Player p1 = new Player(4, Vector2.Zero);
    private final Player p2 = new Player(4, Vector2.Zero);

    private List<Player> players = Arrays.asList(p1, p2);

    private Player attacker;
    private Player defender;

    private Deck deck;
    private Card trumpCard;
    private final GameState gamestate;
    private final AnimationController animationController;

    private Card clickedCard;
    private River river = new River(Position.RIVER.position, 4);
    private Array<RoundStateListener> roundStateListeners = new Array<>();
    private Array<Warnable> warnables = new Array<>();
    Sound cardSlide = Gdx.audio.newSound(Gdx.files.internal("audio/card-slide-1.ogg"));

    public GameController(TextureAtlas atlas) {
        animationController = new AnimationController();
        Collections.shuffle(players);
        Array<Slot> totalSlots = new Array<Slot>(16);
        totalSlots.addAll(river.getSlots());
        totalSlots.addAll(p1.getSlots());
        totalSlots.addAll(p2.getSlots());
        gamestate = new GameState(players.get(0), players.get(1), totalSlots);
        players.get(0).setHandPosition(Position.CURRENT_HAND.position);
        players.get(1).setHandPosition(Position.OPPONENT_HAND.position);
        deck = new Deck(true, atlas);

    }

    public void startGame() {
        SpriteController.stackDeck(0.02f, deck);
        gamestate.setTrumpCard(deck.get(1));
        trumpCard = gamestate.getTrumpCard();
        dealCards(4);
    }

    public void playRound() {
        if (gamestate.getRoundState() == RoundState.ATTACKING) {
            attack();
        } else if (gamestate.getRoundState() == RoundState.DEFENDING) {
            defend();
        } else {
            endGame();
        }
    }

    public void attack() {
        if (river.isEmpty()) {
            return;
        }
        Array<Card> attackCards = river.getCards();
        defender = gamestate.getDefender();
        attacker = gamestate.getAttacker();
        if (validAttack(attackCards)) {
            int pos = 2; // TODO remove hardcodes
            for (Card card : attackCards) {
                animationController.moveCardTo(pos++, 2, 0.1f, 0.1f, card, WaitType.WAIT_START);
            }
            switchPlaces(attacker, defender);

            this.setRoundState(RoundState.DEFENDING);

            attackCards.clear();
            // TODO fix ugly code
        }
    }

    public void defend() {
        Array<Card> wonCards = new Array<>();
        defender = gamestate.getDefender();
        attacker = gamestate.getAttacker();
        Player winner = attacker;
        Array<Card> riverCards = river.getCards();
        Array<Card> defenderCards = river.getDefenderCards();
        Array<Card> attackerCards = river.getAttackerCards();

        wonCards.addAll(riverCards);

        if (attackerCards.size != defenderCards.size) {
            warn("Too many or too little cards selected");
            warn("Attack has " + attackerCards.size + " cards and defence has " + defenderCards.size + " cards"); // GWT
                                                                                                                  // doesnt
                                                                                                                  // support
                                                                                                                  // String.format
            return;
        }
        if (!gamestate.getPass()) {
            // keep in here as valid defence doesnt know if user passed or not
            if (!validDefence()) {
                return;
            }
            defender.removeAll(defenderCards);
            winner = defender;
            gamestate.switchRoles();
        } else if (gamestate.getPass()) {
            switchPlaces(defender, attacker);
        }

        winner.addWonCards(wonCards);
        river.clearCards();

        setRoundState(RoundState.ATTACKING);
        gamestate.setRoundPhase(RoundPhase.ANIMATION);

        for (Card card : wonCards) {
            card.turn(true);
            animationController.moveCardTo(Position.CURRENT_HAND.x + 4, (winner == defender) ? 0 : 4, 1f,
                    0.1f, card, WaitType.WAIT_START); // Comeup with better logic
        }

        dealCards(attackerCards.size);
        gamestate.setPass(false);

    }

    public void endGame() {
        float x = 2; // Fix these placeholders
        for (Player player : players) {
            Array<Card> wonCards = player.getAllWonCards();
            wonCards.reverse();
            for (Card card : player.getAllWonCards()) {
                card.turn(false);
                animationController.moveCardTo(x, 2, 0.5f, 1f, card, WaitType.WAIT_START);
                player.addPoints(card.getPoints());
            }
            x++;
        }
    }

    private void switchPlaces(Player defender, Player attacker) {
        Vector2 tempPos = null;
        Array<Slot> defenderSlots = defender.getSlots();
        Array<Slot> attackerSlots = attacker.getSlots();
        for (int i = 0; i < 4; i++) {
            Slot attackerSlot = attackerSlots.get(i);
            Slot defenderSlot = defenderSlots.get(i);
            if (attackerSlot.hasCard()) {
                attackerSlot.getCard().turn(false);
            }
            if (defenderSlot.hasCard()) {
                defenderSlot.getCard().turn(true);
            }
            tempPos = attackerSlot.getPosition();
            attackerSlot.setPosition(defenderSlot.getPosition());
            defenderSlot.setPosition(tempPos);
        }
    }

    private boolean validAttack(Array<Card> hand) {
        if (gamestate.getRoundState() == RoundState.ATTACKING) {
            if (!Card.checkSuits(hand)) {
                warn("Invalid input. Suits must match");
                return false;
            }
        }
        return true;

    }

    private boolean validDefence() {
        Array<Card> attackHand = river.getAttackerCards();
        Array<Card> defendHand = river.getDefenderCards();
        for (int i = 0; i < attackHand.size; i++) {
            Card attackCard = attackHand.get(i);
            Card defendCard = defendHand.get(i);
            if (attackCard.getSuit() != defendCard.getSuit()
                    && defendCard.getSuit() != trumpCard.getSuit()) {
                warn(defendCard + " does not beat " + attackCard + " (Wrong Suit)");
                return false;
            }
            if (defendCard.getValue() < attackCard.getValue()
                    && defendCard.getSuit() == attackCard.getSuit()) {
                warn(defendCard + " does not beat " + attackCard + " (Smaller Value)");
                if (defendCard.getSuit() == trumpCard.getSuit()
                        && attackCard.getSuit() != trumpCard.getSuit()) {
                    return true;
                }
                return false;
            }
            if (defendCard.getSuit() == trumpCard.getSuit()
                    && attackCard.getSuit() == trumpCard.getSuit()) {
                if (defendCard.getValue() < attackCard.getValue()) {
                    warn(defendCard + " does not beat" + attackCard + "(Smaller Value)");
                    return false;
                }
            }
        }

        return true;
    }

    public void dealCards(int count) {
        if (!deck.hasCards()) {
            gamestate.setRoundState(RoundState.ENDED);
        }
        int deckSize = deck.size();
        for (int i = 0; i < Math.min(count, deckSize / 2); i++) {
            for (Player player : players) {
                Card lastCard = deck.drawLast();
                player.addtoHand(false, lastCard);
                if (player == gamestate.getCurrentPlayer()) {
                    lastCard.turn(false);
                } else {
                    lastCard.turn(true);
                }
                animationController.moveCardTo(player.getCardSlotPosition(lastCard), 0.2f, 0.1f,
                        lastCard, WaitType.WAIT_START, cardSlide); // Remove hardcodes
                if (lastCard == trumpCard) {
                    lastCard.rotate90(true); // TODO fix this is not efficient
                }
                if (deck.size() == 0) {
                    trumpCard = lastCard;
                    lastCard.turn(false);
                }
            }
        }
    }

    public GameState getGameState() {
        return gamestate;
    }

    public void drag(Vector2 touchpos) {
        if (clickedCard != null) {
            clickedCard.setPosition(touchpos.x - 0.5f, touchpos.y - 0.5f);
        }
    }

    public void click(Vector2 touchpos) {
        Array<Card> validCards = new Array<>(4); // maybe remove hardcode if adding more card games in future
        validCards.addAll(gamestate.getCurrentPlayer().getHand());
        validCards.addAll(river.getCards());

        for (Card card : validCards) {
            if (card.getBoundingRectangle().contains(touchpos)) {
                clickedCard = card;
                card.hover(false);
                return;
            }
            ;
        }
    }

    public void releaseDrag() {
        if (clickedCard == null) {
            return;
        }

        Player player = gamestate.getCurrentPlayer();
        RoundState state = gamestate.getRoundState();

        Array<Slot> riverSlots = (state == RoundState.ATTACKING) ? river.getEmptyAttackerSlots()
                : river.getEmptyDefenderSlots();

        float minDistance = 2f;
        Slot minSlot = null;

        for (Slot slot : riverSlots) {
            float distanceBetweenSlots = slot.getPosition().dst(clickedCard.getPosition());
            if (distanceBetweenSlots <= minDistance) {
                minDistance = distanceBetweenSlots;
                minSlot = slot;
            }
            ;
        }
        if (minDistance <= 1f) { // Change logic to check min distance and then set it to that
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

    public void clickPass() {
        gamestate.setPass(true);
        playRound();
    }

    public Deck getDeck() {
        return deck;
    }

    public AnimationController getAnimationController() {
        return animationController;
    }

    public void mouseAt(Vector2 mousePos) {
        Array<Card> validCards = new Array<>(8); // maybe remove hardcode if adding more card games in future
        validCards.addAll(gamestate.getCurrentPlayer().getHand());
        validCards.addAll(river.getCards());

        for (Card card : validCards) {
            if (card.getBoundingRectangle().contains(mousePos)) {
                card.hover(true);
            } else {
                card.hover(false);
            }
        }
    }

    private void setRoundState(GameState.RoundState roundState) {
        gamestate.setRoundState(roundState);

        if (roundStateListeners.notEmpty()) {
            for (RoundStateListener listener : roundStateListeners) {
                listener.notifyStateChange(roundState);
            }
        }
    }

    public void addRoundStateListener(RoundStateListener listener) {
        roundStateListeners.add(listener);
    }

    public void addWarnable(Warnable warnable) {
        warnables.add(warnable);
    };

    public void warn(String warning) {
        if (warnables.notEmpty()) {
            for (Warnable warnable : warnables) {
                warnable.warn(warning);
            }
        }
    }

    public void saveGameState() {
        Json json = new Json();
        String gameData = json.toJson(gamestate); // TODO change all gameStates to gamestates

        FileHandle file = Gdx.files.local("savegame.json");

        file.writeString(gameData, false);
    }
}
