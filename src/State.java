/*
 * FlatCraft - StateManager.java
 * 
 * This interface is the template for the game states that the
 * state manager will control. Examples of states that will implement
 * State:
 * - MainMenuState
 * - ServerPlayState
 * - ClientPlayState
 * - CreditsState
 * - etc
 * 
 */

public interface State {
	public void init();
	public void pause();
	public void resume();
	public void cleanup();
	public void update(double dt);
	public void draw();
	
	
}
