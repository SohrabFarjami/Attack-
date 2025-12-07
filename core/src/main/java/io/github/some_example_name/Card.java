package io.github.some_example_name;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Card{
	private final Suit suit;
	private final Pip pip;
    	private final TextureRegion front;
    	private final TextureRegion back;
    	private boolean turned;
	// Texture stuff move out later
	float x;
	float y;
	float width = 1f;
	float height = 1f;
	float originX = width/2f;
	float originY = height/2f;
	float rotation = 0;

	public Card(Pip pip, Suit suit, TextureRegion front, TextureRegion back){
		this.pip = pip;
		this.suit = suit;
		this.front = front;
		this.back = back;
	}

	public Suit getSuit()
	{
		return suit;
	}

	public int getValue()
	{
		return pip.value;
	}

	public static boolean checkSuits(List<Card> cards){
		Suit initialSuit =  cards.get(0).getSuit();
		for(Card card : cards){
			if(initialSuit != card.getSuit()){
				return false;
			}
		}
		return true;
	}

	public int getPoints(){
		return pip.points;
	}

    public void setPosition(float x,float y){
	    this.x = x;
	    this.y = y;
    }

    public void setPosition(Vector2 position){
	    this.x = position.x;
	    this.y = position.y;
    }
    public void turn(){
	    turned = !turned;
    }

    public void turn(boolean turned){
	    this.turned = turned;
    }

	@Override
	public String toString(){
		return pip + " of " + suit;
	}

    public void draw(Batch batch){
		batch.draw((turned) ? back : front, x,y, originX, originY ,width, height,1f,1f, rotation);
    }

    public void drawBack(Batch batch){
	    batch.draw(back, x,y, originX, originY ,width, height,1f,1f, rotation);
    }

    public void drawFront(Batch batch){
	    batch.draw(front, x,y, originX, originY ,width, height,1f,1f, rotation);
    }

    public void rotate90(boolean clockwise){
	    rotation += (clockwise) ? -90f : 90f;
    }

    public Rectangle getBoundingRectangle(){
        return new Rectangle(x, y, width, height);
    }




    public void setX(float x){
	    this.x = x;
    }

    public void setY(float y){
	    this.y = y;
    }

    public float getX(){
	    return this.x;
    }
    public float getY(){
	    return this.y;
    }



}
