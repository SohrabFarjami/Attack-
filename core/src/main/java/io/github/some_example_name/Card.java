package io.github.some_example_name;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Card{
	private final Suit suit;
	private final Pip pip;
    	private final Sprite front;
    	private final Sprite back;
    	private boolean turned;

	public Card(Pip pip, Suit suit, Sprite front, Sprite back){
		this.pip = pip;
		this.suit = suit;
		this.front = front;
		this.back = back;
		front.setSize(1, 1);
		back.setSize(1, 1);
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

    public void setPosition(float x,float y){
        front.setPosition(x, y);
        back.setPosition(x, y);

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
        if(turned){
            back.draw(batch);
        }else{
            front.draw(batch);
        }

    }

    public void drawBack(Batch batch){
	back.draw(batch);
    }

    public void rotate90(boolean clockwise){
	front.rotate90(clockwise);
	back.rotate90(clockwise);
    }

    public Rectangle getBoundingRectangle(){
        return front.getBoundingRectangle();
    }


}
