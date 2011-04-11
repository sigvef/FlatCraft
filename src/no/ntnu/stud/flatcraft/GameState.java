/*
 * FlatCraft - ServerState.java
 * 
 */

package no.ntnu.stud.flatcraft;

import no.ntnu.stud.flatcraft.entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState {

	private StateBasedGame game;
	private GameWorld gameworld;
	Player player;
	int timer;

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		gameworld.render(g);
		player.render(g);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		timer += delta;
		while (timer > 20) {
			this.game = game;
			if (Main.KEYDOWN[Input.KEY_ESCAPE]) {
				game.enterState(0); // go back to MainMenuState
			}

			player.update(container, game, 1);
			gameworld.update(container, game, 1);
			timer -= 20;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		gameworld = new GameWorld("level.flt");

		player = new Player(gameworld, Main.GU, Main.GU, Main.GU * 5, Main.GU * 5, 1);
		gameworld.add(player.getCharacter());
		gameworld.add(player.getBody());
		timer = 0;

	}

	public void enter(GameContainer container, StateBasedGame game) {
		gameworld.reset();
		player.reset();
	}

	public void leave(GameContainer container, StateBasedGame game) {
		gameworld.leave();

	}

	public int getID() {
		return 1;
	}
}
