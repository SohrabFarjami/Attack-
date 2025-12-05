package io.github.some_example_name;

import io.github.some_example_name.AnimationController.WaitType;

public class MoveCardAnimation implements AnimationInterface{

	float targetX;
	float targetY;
	float startX;
	float startY;

	float speed = 1;
	float elapsedTime;
	float duration;
	float delay;
	Card card;
	
	boolean isFinished = false;
	boolean isStarted = true;
	private WaitType waitType;

	    public MoveCardAnimation(float targetX,float targetY, float duration, float delay, Card card, WaitType waitType){
		    this.card = card;
		    startX = card.getX();
		    startY = card.getY();
		    this.targetX = targetX;
		    this.targetY = targetY;
		    this.duration = duration;
		    this.delay = delay;
		    elapsedTime = 0;
		    this.waitType = waitType;
	    }
	    public void update(float delta){
		    float nextX,nextY;
			    if(!isFinished){
				    elapsedTime += delta;
				    float progress = Math.min(elapsedTime/duration, 1f);
				    nextX = startX + (targetX - startX) * progress;
				    nextY = startY + (targetY - startY) * progress;
				    card.setPosition(nextX, nextY);

				    if(progress >= 1f){
					    isFinished = true;
				    }
			    }
		}

	    public boolean isFinished(){
		    return isFinished;
	    }

	    public float getDelay(){
		    return delay;
	    }

	    public boolean isStarted(){
		    return isStarted;
	    }
	    public WaitType getWaitType(){
		    return waitType;
	    }
}
