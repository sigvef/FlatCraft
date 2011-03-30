/*
 * FlatCraft - Main.java
 * 
 * The entry point of the game. Initializes slick, kryonet, and 
 * starts the state manager.
 * 
 */

package no.ntnu.stud.flatcraft;
 
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
 
public class Main extends StateBasedGame{
	
	public static int SCREEN_W = 800; //hard-coded screen sizes to begin with
	public static int SCREEN_H = 480;
	public static float GU = SCREEN_W/128;
	public static boolean[] KEYDOWN;
	public static Vector2f GRAVITY = new Vector2f(0,Main.GU*0.05f);
	
	public Main() {
		super("FlatCraft");
		KEYDOWN = new boolean[256];
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main());
		app.setDisplayMode(SCREEN_W, SCREEN_H, false);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainMenuState());
		addState(new GameState());
	}
	
	@Override
	public void keyPressed(int key, char c) {
		KEYDOWN[key] = true;
		System.out.println(key);
	}

	@Override
	public void keyReleased(int key, char c) {
		KEYDOWN[key] = false;
		
	}
}