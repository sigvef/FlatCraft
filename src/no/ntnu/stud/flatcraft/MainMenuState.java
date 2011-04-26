package no.ntnu.stud.flatcraft;

/*
 * FlatCraft - MainMenuState.java
 * 
 */

import java.util.Arrays;

import no.ntnu.stud.flatcraft.menu.Menu;
import no.ntnu.stud.flatcraft.menu.MenuItem;
import no.ntnu.stud.flatcraft.messagesystem.Message;
import no.ntnu.stud.flatcraft.messagesystem.MessageSystem;

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
		menu.render(g);
	}

	public void enter(GameContainer container, StateBasedGame game) {
		Arrays.fill(Main.KEYDOWN, false);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		menu.update(delta);
		Main.MS.update(delta);
		if (Main.KEYDOWN[Input.KEY_ESCAPE]) {
			System.exit(0);
		}

	}

	public void init(GameContainer container, final StateBasedGame game) {
		System.out.println("TRYING TO PRINT FONTS, YO");
		try {
			Main.MS = new MessageSystem();

			Main.FONT_BOLD = new UnicodeFont("res/fontb.ttf",
					(int) (Main.GULOL * 10), false, false);
			Main.FONT_BOLD.getEffects().add(new ColorEffect());
			Main.FONT_BOLD.addAsciiGlyphs();
			Main.FONT_BOLD.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		System.out.println("STAGE 8 COMPLETE");
		menu = new Menu(game);
		System.out.println("STAGE 9 COMPLETE");

		menu.addMenuItem(new MenuItem() {

			public String getText() {
				return "Play";
			}

			public void actionCallback() {
				game.enterState(123);
			}
		});

		menu.addMenuItem(new MenuItem() {

			public String getText() {
				return "Level editor";
			}

			public void actionCallback() {

				Main.MS.addMessage(new Message("Here be dragons."));
				game.enterState(1337);
			}
		});

		menu.addMenuItem(new MenuItem() {

			public String getText() {
				return "Settings";
			}

			public void actionCallback() {
				Main.MS.addMessage(new Message(
						"Restart for changes to take effect."));
				game.enterState(666);
			}

		});

		menu.addMenuItem(new MenuItem() {

			public String getText() {
				return "Exit";
			}

			public void actionCallback() {
				System.exit(0);
			}
		});
	}

	public int getID() {
		return 0;
	}
}
