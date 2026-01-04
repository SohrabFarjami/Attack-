package sohrabfarjami.com;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class AnimationController{

	public enum WaitType{
		WAIT_END,
		WAIT_START,
		NO_WAIT,
	}

	Queue<AnimationInterface> animationQueue = new LinkedList<>();
	List<AnimationInterface> activeAnimations = new ArrayList<>();
	float timeSinceLastStart = 0;

	public void moveCardTo(Vector2 targetPosition, float duration, float delay, Card card, WaitType waitType){
		moveCardTo(targetPosition.x, targetPosition.y, duration, delay, card, waitType, null);
	}

	public void moveCardTo(Vector2 targetPosition, float duration, float delay, Card card, WaitType waitType, Sound sound){
		moveCardTo(targetPosition.x, targetPosition.y, duration, delay, card, waitType, sound);
	}

	public void moveCardTo(float targetX, float targetY, float duration, float delay, Card card, WaitType waitType){
		moveCardTo(targetX, targetY, duration, delay, card, waitType, null);
	}
	public void moveCardTo(float targetX, float targetY, float duration, float delay, Card card, WaitType waitType,Sound sound){
		MoveCardAnimation moveCardAnimation = new MoveCardAnimation(targetX,targetY, duration, delay, card, waitType, sound); //Later make this also accept vector
		animationQueue.add(moveCardAnimation);
	}
	public void update(float delta){
		timeSinceLastStart += delta;
		AnimationInterface nextAnimation = animationQueue.peek();
		if(nextAnimation != null){
			if(nextAnimation.getWaitType() == WaitType.WAIT_END){
				if(activeAnimations.isEmpty()){
					activeAnimations.add(animationQueue.poll());
					timeSinceLastStart = 0;
				}
			}else if(nextAnimation.getWaitType() == WaitType.WAIT_START){
				if(timeSinceLastStart >= nextAnimation.getDelay()){
					activeAnimations.add(animationQueue.poll());
					timeSinceLastStart = 0;
				}}
			else if(nextAnimation.getWaitType() == WaitType.NO_WAIT){
				activeAnimations.add(animationQueue.poll());
				timeSinceLastStart = 0;
			}
		}

		for(AnimationInterface activeAnimation : activeAnimations){
			activeAnimation.update(delta);
		}
		activeAnimations.removeIf(activeAnimation -> activeAnimation.isFinished());

	}
}
