/*
 * FlatCraft - ServerState.java
 * 
 */

package no.ntnu.stud.flatcraft;

import no.ntnu.stud.flatcraft.entities.Player;
import no.ntnu.stud.flatcraft.quadtree.Block;
import no.ntnu.stud.flatcraft.messagesystem.Message;
import no.ntnu.stud.flatcraft.messagesystem.MessageSystem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState {

	private StateBasedGame game;
	private GameWorld gameworld;
	private MessageSystem ms;
	Player player;
	int timer;
	
	

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.pushTransform();
		gameworld.render(g);
		player.render(g);
		ms.render(g);
		g.popTransform();
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		timer += delta;
		while (timer > 20) {
			this.game = game;
			if (Main.KEYDOWN[Input.KEY_ESCAPE]) {
				game.enterState(0); // go back to MainMenuState
			}

			player.update(container, game, 20);
			gameworld.update(container, game, 20);
			ms.update(20);
			timer -= 20;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		

	}

	public void enter(GameContainer container, StateBasedGame game) {
try {
	gameworld = new GameWorld("level.flt");
} catch (SlickException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		ms = new MessageSystem();
		Vector2f startPos = gameworld.terrain.findBlockPosition(Block.START);
		if (startPos != null) {
			gameworld.terrain.emptyCell(startPos.getX(), startPos.getY());
		} else {
			startPos = new Vector2f(Main.GU, Main.GU);
		}
		player = new Player(gameworld, startPos.getX(), startPos.getY(), Main.GU * 5, Main.GU * 5, 1);
		gameworld.add(player.getCharacter());
		timer = 0;
		gameworld.reset();
		player.reset();
		ms.addMessage((new Message(gameworld.getLevelName(),10000)));
	}

	public void leave(GameContainer container, StateBasedGame game) {
		gameworld.leave();

	}

	public int getID() {
		return 1;
	}
}
