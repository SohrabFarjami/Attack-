package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;

public enum Position{
	DECK(7f,2f),
	CURRENT_HAND(2f,0f),
	OPPONENT_HAND(2f,4f),
	RIVER(2f,2f);

	public final Vector2 position;
	public final float x;
	public final float y;

	private Position(float x, float y){
		position = new Vector2(x,y);
		this.x = x;
		this.y = y;
	}

}
