package sohrabfarjami.com;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Hand {
    private Vector2 position;
    private Array<Slot> slots;

    Hand() { // These empty are needed for json deserialaztion dont remove
    }

    Hand(Vector2 position, int maxSize) {
        this.position = position;
        slots = new Array<Slot>(true, maxSize);
        for (int i = 0; i < maxSize; i++) {
            Vector2 slotPosition = new Vector2(this.position.x + i, this.position.y);
            slots.add(new Slot(slotPosition));
        }
    }

    public Array<Card> getHand() {
        Array<Card> hand = new Array<>(4);
        for (Slot slot : slots) {
            if (slot.hasCard()) {
                hand.add(slot.getCard());
            }
        }
        return hand;
    }

    public void setPosition(Vector2 position) {
        Vector2 change = this.position.cpy().sub(position);
        this.position = position;
        for (Slot slot : slots) {
            slot.setPosition(slot.getPosition().sub(change));
        }

    }

    public Slot getFirstEmptySlot() {
        for (Slot slot : slots) {
            if (!slot.hasCard()) {
                return slot;
            }
        }
        return null;
    }

    public void addCard(Card card) {
        for (Slot slot : slots) {
            if (!slot.hasCard()) {
                slot.setCard(card);
                return;
            }
        }
    }

    public void addCard(Card card, boolean changePos) {
        for (Slot slot : slots) {
            if (!slot.hasCard()) {
                slot.setCard(card, changePos);
                return;
            }
        }
    }

    public Array<Slot> getSlots() {
        return slots;
    }

    public void remove(Card card) {
        for (Slot slot : slots) {
            if (slot.getCard() == card) {
                slot.removeCard();
                return;
            }
        }
    }

    public void removeAll(Array<Card> cards) {
        for (Card card : cards) {
            for (Slot slot : slots) {
                if (slot.getCard() == card) {
                    slot.removeCard();
                }
            }
        }
    }

    public Vector2 getCardSlotPosition(Card card) {
        for (Slot slot : slots) {
            if (slot.getCard() == card) {
                return slot.getPosition();
            }
        }
        return null;
    }

    public int size() {
        int size = 0;
        for (Slot slot : slots) {
            if (slot.hasCard()) {
                size++;
            }
        }
        return size;
    }
}
