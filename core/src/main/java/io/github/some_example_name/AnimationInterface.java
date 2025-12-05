package io.github.some_example_name;

import io.github.some_example_name.AnimationController.WaitType;

public interface AnimationInterface{

	void update(float delta);
	boolean isFinished();
	boolean isStarted();
	float getDelay();
	WaitType getWaitType();
}
