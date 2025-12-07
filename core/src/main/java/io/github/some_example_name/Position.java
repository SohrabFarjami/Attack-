package io.github.some_example_name;



public enum Position{
	DECK(7f,2f),
	CURRENT_HAND(2f,0f),
	OPPONENT_HAND(2f,4f),
	RIVER(2f,2f);

	public final float x;
	public final float y;

	private Position(float x, float y){
		this.x = x;
		this.y = y;
	}

}
