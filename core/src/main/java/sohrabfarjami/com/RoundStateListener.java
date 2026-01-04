package sohrabfarjami.com;

import sohrabfarjami.com.GameState.RoundState;

public interface RoundStateListener{
	void notifyStateChange(RoundState newState);
}
