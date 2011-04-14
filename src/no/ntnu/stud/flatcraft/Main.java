/*
 * FlatCraft - Main.java
 * 
 * The entry point of the game. Initializes slick, kryonet, and 
 * starts the state manager.
 * 
 */

package no.ntnu.stud.flatcraft;

import java.util.ArrayList;

import no.ntnu.stud.flatcraft.levels.generator.GeneratorState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	public static final int ITERATIONS = 30;
	public static final int UPDATES = 10;	
	public static int SCREEN_W = 1280; // hard-coded screen sizes to begin with
	public static int SCREEN_H = 720;
	public static float GULOL = SCREEN_W / 128;
	public static float GU = 1;	
	public static boolean[] KEYDOWN;
	public static boolean[] MOUSEDOWN;
	public static float MOUSEX, MOUSEY;
	public static Vector2f GRAVITY = new Vector2f(0,10);
	public static float mu = 0.8f;
	public static boolean FULLSCREEN = false;
	public static boolean DEBUG = false;
	public static ArrayList<Rectangle> debugrect;

	public Main() {
		super("FlatCraft");
		KEYDOWN = new boolean[256];
		MOUSEDOWN = new boolean[3];
		debugrect = new ArrayList<Rectangle>();
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
		MOUSEX = x/Main.GU;
		MOUSEY = y/Main.GU;
		// System.out.println("mouse button "+button+" pressed.");
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		MOUSEDOWN[button] = false;
		MOUSEX = x/Main.GU;
		MOUSEY = y/Main.GU;
		// System.out.println("mouse button "+button+" released.");
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		MOUSEX = newx/Main.GU;
		MOUSEY = newy/Main.GU;
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		MOUSEX = newx/Main.GU;
		MOUSEY = newy/Main.GU;
	}
}