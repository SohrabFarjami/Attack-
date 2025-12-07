package io.github.some_example_name;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AnimationController{

	public enum WaitType{
		WAIT_END,
		WAIT_START,
		NO_WAIT,
	}

	Queue<AnimationInterface> animationQueue = new LinkedList<>();
	List<AnimationInterface> activeAnimations = new ArrayList<>();
	float timeSinceLastStart = 0;

	public void moveCardTo(float targetX, float targetY, float duration, float delay, Card card, WaitType waitType){
		MoveCardAnimation moveCardAnimation = new MoveCardAnimation(targetX, targetY, duration, delay, card, waitType);
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
