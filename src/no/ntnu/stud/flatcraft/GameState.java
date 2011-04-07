/*
 * FlatCraft - ServerState.java
 * 
 */

package no.ntnu.stud.flatcraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState {
	
	private StateBasedGame game;
	private GameWorld gameworld;
	int timer;
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		gameworld.render(g);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		timer += delta;
		while(timer > 50){
			this.game = game;
			if(Main.KEYDOWN[Input.KEY_ESCAPE]){
				game.enterState(0); //go back to MainMenuState
			}
			gameworld.update(container, game, 1);
			timer -= 50;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
			gameworld = new GameWorld();
			timer = 0;
		
	}
	
	public void enter(GameContainer container, StateBasedGame game){
		gameworld.reset();
	}
	
	public void leave(GameContainer container, StateBasedGame game){

	}

	public int getID() {
		return 1;
	}
}
