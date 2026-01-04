package sohrabfarjami.com;

import sohrabfarjami.com.AnimationController.WaitType;

public interface AnimationInterface{

	void update(float delta);
	boolean isFinished();
	boolean isStarted();
	float getDelay();
	WaitType getWaitType();
}
