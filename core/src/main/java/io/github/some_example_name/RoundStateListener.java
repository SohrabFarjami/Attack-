package io.github.some_example_name;

import io.github.some_example_name.GameState.RoundState;

public interface RoundStateListener{
	void notifyStateChange(RoundState newState);
}
