package no.ntnu.stud.flatcraft;
/*
 * FlatCraft - MainMenuState.java
 * 
 */


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	
	StateBasedGame game;
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		g.drawString("This is the main menu!\nPress Enter to start a game.\nPress Space to join localhost's game.", Main.GU*4, Main.GU*3);		
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		this.game = game;
	}

	public void keyReleased(int key, char c) {
		switch(key){
			case Input.KEY_ENTER:
				game.enterState(1); //enter ServerState
				break;
			case Input.KEY_SPACE:
				game.enterState(2); //enter ClientState
				break;
			case Input.KEY_ESCAPE:
				System.exit(0);
				break;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	public int getID() {
		return 0;
	}
}
