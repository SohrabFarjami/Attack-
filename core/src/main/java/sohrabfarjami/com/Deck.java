package sohrabfarjami.com;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Deck {
    private Array<Card> cards = new Array<Card>();

    public Deck() {
    }

    public Deck(boolean shuffle, TextureAtlas atlas) {
        for (Suit suit : Suit.values()) {
            for (Pip pip : Pip.values()) {
                if (pip.index < 11) { // Set to 6 for normal game
                    continue;
                }
                TextureRegion front = atlas.findRegion("card_" + suit.name, pip.index);
                TextureRegion back = atlas.findRegion("card_back");
                Card card = new Card(pip, suit, front, back);
                card.turn(true);
                card.setPosition(Position.DECK.x, Position.DECK.y);
                cards.add(card);
            }
        }
        if (shuffle) {
            shuffle(); // remove this stupid
        }

    }

    public void remove(int index) {
        cards.removeIndex(index);
    }

    public void remove(Card card) {
        cards.removeValue(card, true);
    }

    public void add(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size;
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public Array<Card> getAll() {
        return cards;
    }

    public Card drawLast() {
        // Card card = cards.getLast(); java 8 doesnt support sadge
        Card card = cards.get(cards.size - 1);
        cards.removeValue(card, true);
        return card;
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }

    public void shuffle() {
        cards.shuffle();
    }

}
