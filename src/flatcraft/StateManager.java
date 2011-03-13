/*
 * FlatCraft - StateManager.java
 * 
 * This class manages all the states in the game,
 * and effectively does all the calling from the main game loop.
 * 
 */

import java.util.Stack;

public class StateManager {
	private Stack<State> states;
	
	public void push(State state){
		if(states.size() > 0){
			states.peek().pause();
		}
		states.push(state);
		states.peek().init();
	}
	
	public void pop(State state){
		states.peek().cleanup();
		if(states.size() == 1){			
			//There are no more states, so we quit the game
			//TODO: quit the game
			return;
		}
		states.pop();
		states.peek().resume();
	}
	
	public void update(double dt){
		states.peek().update(dt);
	}
	
	public void draw(){
		states.peek().draw();
	}
}
