package io.github.some_example_name;

public enum Suit {
	SPADES("spades"),
	DIAMONDS("diamonds"),
	CLUBS("clubs"),
	HEARTS("hearts");

    public final String name;

    private Suit(String name){
        this.name = name;
    }
}
