/*
 * FlatCraft - ServerState.java
 * 
 */

package no.ntnu.stud.flatcraft;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryonet.Server;

public class ServerState extends BasicGameState {
	
	private Server server;
	private StateBasedGame game;
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		g.drawString("A new game has been created.\nPress ESC to exit to main menu.", Main.GU*4, Main.GU*3);		
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		this.game = game;
	}

	public void keyReleased(int key, char c) {
		switch(key){
			case Input.KEY_ESCAPE:
				game.enterState(0); //go back to MainMenuState
				break;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}
	
	public void enter(GameContainer container, StateBasedGame game){
		server = new Server();
		server.start();
		try{
			server.bind(54555,54777); //binds to TCP, UDP ports
		}catch(IOException e){
			System.out.print(e.toString());
		}
	}
	
	public void leave(GameContainer container, StateBasedGame game){
		server.close();
	}

	public int getID() {
		return 1;
	}
}
