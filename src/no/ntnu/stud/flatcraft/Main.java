/*
 * FlatCraft - Main.java
 * 
 * The entry point of the game. Initializes slick, kryonet (TODO), and 
 * starts the state manager.
 * 
 */

package no.ntnu.stud.flatcraft;
 
import javax.script.ScriptContext;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
 
public class Main extends BasicGame{
	
	StateManager SM;
	public static int SCREEN_W = 800; //hard-coded screen sizes to begin with
	public static int SCREEN_H = 480;
	
	public Main() {
		super("FlatCraft");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		SM = new StateManager();
		SM.push(new MainMenuState());
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		SM.update(delta);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		SM.render(g);
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main());
		app.setDisplayMode(SCREEN_W, SCREEN_H, false);
		app.start();
	}
}