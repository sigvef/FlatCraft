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

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawString(
				"This is the main menu!\nPress Enter to start a game.\nPress Space to join localhost's game.",
				Main.GU * 4, Main.GU * 3);
	}

	public void enter(GameContainer container, StateBasedGame game) {
		Main.KEYDOWN[Input.KEY_ESCAPE] = false;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		this.game = game;

		if (Main.KEYDOWN[Input.KEY_ENTER] || Main.MOUSEDOWN[0]) {
			game.enterState(1); // enter GameState
		}
		if (Main.KEYDOWN[Input.KEY_F11]) {
			game.enterState(1337);
		}
		if (Main.KEYDOWN[Input.KEY_ESCAPE]) {
			System.exit(0);
		}

	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	public int getID() {
		return 0;
	}
}
