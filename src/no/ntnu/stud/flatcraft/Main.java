/*
 * FlatCraft - Main.java
 * 
 * The entry point of the game. Initializes slick, and 
 * starts the state manager.
 * 
 */

package no.ntnu.stud.flatcraft;

import java.util.ArrayList;

import no.ntnu.stud.flatcraft.levels.generator.GeneratorState;
import no.ntnu.stud.flatcraft.messagesystem.MessageSystem;
import no.ntnu.stud.flatcraft.settings.Resolution;
import no.ntnu.stud.flatcraft.settings.Settings;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class Main extends StateBasedGame {

	public static final int ITERATIONS = 30;
	public static final int UPDATES = 8;
	public static String LEVEL = null;
	public static int SCREEN_W;
	public static int SCREEN_H;
	public static float GULOL;
	public static float GU = 1;
	public static boolean[] KEYDOWN;
	public static boolean[] MOUSEDOWN;
	public static float MOUSEX, MOUSEY;
	public static Vector2f GRAVITY = new Vector2f(0, 10);
	public static float mu = 0.8f;
	public static boolean FULLSCREEN = false;
	public static boolean DEBUG = false;
	public static ArrayList<Rectangle> debugrect;
	public static Settings SETTINGS;
	public static UnicodeFont FONT;
	public static UnicodeFont FONT_BOLD;
	public static MessageSystem MS;
	

	public Main() throws SlickException {
		super("FlatCraft");
		KEYDOWN = new boolean[256];
		MOUSEDOWN = new boolean[3];
		debugrect = new ArrayList<Rectangle>();
	}

	public static void main(String[] args) throws SlickException {
		SETTINGS = new Settings();
		for (int i = 0; i < args.length-1; i+=2) {
			if (args[i].equals("--sound")) {
				SETTINGS.setSound(Boolean.parseBoolean(args[i+1]));
				System.out.println("Sound " + Boolean.parseBoolean(args[i+1]));
			} else if (args[i].equals("--fullscreen")) {
				SETTINGS.setFullScreen(Boolean.parseBoolean(args[i+1]));
				System.out.println("Fullscreen " + Boolean.parseBoolean(args[i+1]));
			} else if (args[i].equals("--bloom")) {
				SETTINGS.setBloom(Boolean.parseBoolean(args[i+1]));
				System.out.println("Bloom " + Boolean.parseBoolean(args[i+1]));
			} else if (args[i].equals("--particles")) {
				SETTINGS.setParticles(Boolean.parseBoolean(args[i+1]));
				System.out.println("Particles " + Boolean.parseBoolean(args[i+1]));
			} else if (args[i].equals("--resolution")) {
				SETTINGS.setResolution(Resolution.valueOf(args[i+1]));
				System.out.println("Resolution " + Resolution.valueOf(args[i+1]));
			}
		}
		
		SCREEN_W = SETTINGS.getResolution().getWidth();
		SCREEN_H = SETTINGS.getResolution().getHeight();
		GULOL = SCREEN_W / 128;
		
		AppGameContainer app = new AppGameContainer(new Main());
		app.setDisplayMode(SCREEN_W, SCREEN_H, Main.FULLSCREEN);
		app.setTargetFrameRate(60);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new LoadingState());
		addState(new MainMenuState());
		addState(new GameState());
		addState(new GeneratorState());
		addState(new SettingsMenuState());
		addState(new LevelSelectMenuState());
	}

	@Override
	public void keyPressed(int key, char c) {
		KEYDOWN[key] = true;
	}

	@Override
	public void keyReleased(int key, char c) {
		KEYDOWN[key] = false;
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		MOUSEDOWN[button] = true;
		MOUSEX = x / Main.GU;
		MOUSEY = y / Main.GU;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		MOUSEDOWN[button] = false;
		MOUSEX = x / Main.GU;
		MOUSEY = y / Main.GU;
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		MOUSEX = newx / Main.GU;
		MOUSEY = newy / Main.GU;
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		MOUSEX = newx / Main.GU;
		MOUSEY = newy / Main.GU;
	}
}
