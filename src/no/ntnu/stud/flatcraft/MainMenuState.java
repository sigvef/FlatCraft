package no.ntnu.stud.flatcraft;

/*
 * FlatCraft - MainMenuState.java
 * 
 */

import no.ntnu.stud.flatcraft.menu.Menu;
import no.ntnu.stud.flatcraft.menu.MenuItem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	
	Menu menu;
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
//		g.drawString(
//				"This is the main menu!\nPress Enter to start a game.\nPress Space to join localhost's game.",
//				Main.GU * 4, Main.GU * 3);
		menu.render(g);
	}

	public void enter(GameContainer container, StateBasedGame game) {
		Main.KEYDOWN[Input.KEY_ESCAPE] = false;
		Main.KEYDOWN[Input.KEY_ENTER] = false;
		Main.KEYDOWN[Input.KEY_SPACE] = false;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		menu.update(delta);
		
//		if (Main.KEYDOWN[Input.KEY_ENTER] || Main.MOUSEDOWN[0]) {
//			game.enterState(1); // enter GameState
//		}
//		if (Main.KEYDOWN[Input.KEY_F11]) {
//			game.enterState(1337);
//		}
		if (Main.KEYDOWN[Input.KEY_ESCAPE]) {
			System.exit(0);
		}

	}

	public void init(GameContainer container, final StateBasedGame game)
			 {
		System.out.println("TRYING TO PRINT FONTS, YO");
		try {
		Main.FONT_BOLD = new UnicodeFont("res/fontb.ttf",(int) (Main.GULOL*10), false ,false);
		System.out.println("STAGE 1 COMPLETE");
		Main.FONT_BOLD.getEffects().add(new ColorEffect());
		System.out.println("STAGE 2 COMPLETE");
		Main.FONT_BOLD.addAsciiGlyphs();
		System.out.println("STAGE 3 COMPLETE");
		Main.FONT_BOLD.loadGlyphs();
		System.out.println("STAGE 4 COMPLETE");
//		Main.FONT= new UnicodeFont("res/font.ttf",(int) (Main.GULOL*10), false ,false);
//		System.out.println("STAGE 5 COMPLETE");
//		Main.FONT.getEffects().add(new ColorEffect());
//		System.out.println("STAGE 6 COMPLETE");
//		Main.FONT.addAsciiGlyphs();
//		System.out.println("STAGE 7 COMPLETE");
//		Main.FONT.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("STAGE 8 COMPLETE");
		menu = new Menu(game);
		System.out.println("STAGE 9 COMPLETE");
		
		menu.addMenuItem(new MenuItem(){

			public String getText() {
				return "Play";
			}

			public void actionCallback() {
				game.enterState(123);
			}
		});
		
		menu.addMenuItem(new MenuItem(){

			public String getText() {
				return "Level editor";
			}

			public void actionCallback() {
				game.enterState(1337);
			}
		});
		
		menu.addMenuItem(new MenuItem(){

			public String getText() {
				return "Settings";
			}

			public void actionCallback() {
				game.enterState(666);
			}
			
		});
		
		menu.addMenuItem(new MenuItem(){

			public String getText() {
				return "Exit";
			}

			public void actionCallback() {
				System.exit(0);
			}
		});
		
		System.out.println("NOW DONE INITING MAINMENUSTATE!");
	
	}

	public int getID() {
		return 0;
	}
}
