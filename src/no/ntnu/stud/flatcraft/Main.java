/*
 * FlatCraft - Main.java
 * 
 * The entry point of the game. Initializes slick, kryonet, and 
 * starts the state manager.
 * 
 */

package no.ntnu.stud.flatcraft;

import net.phys2d.math.Vector2f;
import no.ntnu.stud.flatcraft.levels.generator.GeneratorState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	public static final int ITERATIONS = 4;
	public static int SCREEN_W = 1280; // hard-coded screen sizes to begin with
	public static int SCREEN_H = 720;
	public static float GU = SCREEN_W / 128;
	public static boolean[] KEYDOWN;
	public static boolean[] MOUSEDOWN;
	public static int MOUSEX, MOUSEY;
	public static Vector2f GRAVITY = new Vector2f(0, Main.GU * 100);
	public static float mu = 0.8f;
	public static boolean FULLSCREEN = false;
	public static boolean DEBUG = false;

	public Main() {
		super("FlatCraft");
		KEYDOWN = new boolean[256];
		MOUSEDOWN = new boolean[3];
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main());
		app.setDisplayMode(SCREEN_W, SCREEN_H, FULLSCREEN);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainMenuState());
		addState(new GameState());
		addState(new GeneratorState());
	}

	@Override
	public void keyPressed(int key, char c) {
		KEYDOWN[key] = true;
		// System.out.println(key);
		if (key == 57)
			DEBUG = !DEBUG; // spacebar
	}

	@Override
	public void keyReleased(int key, char c) {
		KEYDOWN[key] = false;

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		MOUSEDOWN[button] = true;
		MOUSEX = x;
		MOUSEY = y;
		// System.out.println("mouse button "+button+" pressed.");
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		MOUSEDOWN[button] = false;
		MOUSEX = x;
		MOUSEY = y;
		// System.out.println("mouse button "+button+" released.");
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		MOUSEX = newx;
		MOUSEY = newy;
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		MOUSEX = newx;
		MOUSEY = newy;
	}
}