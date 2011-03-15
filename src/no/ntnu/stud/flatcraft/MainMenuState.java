package no.ntnu.stud.flatcraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		g.drawString("Hello World!", Main.GU*4, Main.GU*3);		
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		
	}

	public void keyReleased(int key, char c) {
		switch(key){
			case Input.KEY_ESCAPE:
				//TODO close
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
