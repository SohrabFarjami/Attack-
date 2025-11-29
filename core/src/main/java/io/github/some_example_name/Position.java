package io.github.some_example_name;



public enum Position{
	DECK(6f,2f),
	CURRENT_HAND(2f,0f),
	OPPONENT_HAND(2f,4f);

	public final float x;
	public final float y;

	private Position(float x, float y){
		this.x = x;
		this.y = y;
	}
}
