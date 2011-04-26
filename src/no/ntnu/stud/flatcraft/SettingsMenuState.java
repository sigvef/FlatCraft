package no.ntnu.stud.flatcraft;

import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import no.ntnu.stud.flatcraft.menu.Menu;
import no.ntnu.stud.flatcraft.menu.MenuItem;
import no.ntnu.stud.flatcraft.messagesystem.Message;

public class SettingsMenuState extends BasicGameState {
	Menu menu;

	public void enter(GameContainer container, StateBasedGame game) {
		Arrays.fill(Main.KEYDOWN, false);
	}

	public void init(GameContainer app, final StateBasedGame game)
			throws SlickException {
		menu = new Menu(game);
		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "Sound";
			}

			public void actionCallback() {
				Main.SETTINGS.setSound(!Main.SETTINGS.getSound());
				if (Main.SETTINGS.getSound()) {
					Main.MS.addMessage(new Message("Sound has been turned on"));
				} else {
					Main.MS.addMessage(new Message("Sound has been turned off"));
				}
			}
		});

		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "Bloom";
			}

			public void actionCallback() {
				Main.SETTINGS.setBloom(!Main.SETTINGS.getBloom());
				if (Main.SETTINGS.getBloom()) {
					Main.MS.addMessage(new Message("Bloom has been turned on"));
				} else {
					Main.MS.addMessage(new Message("Bloom has been turned off"));
				}
			}
		});

		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "B.G. Effects";
			}

			public void actionCallback() {
				Main.SETTINGS.setParticles(!Main.SETTINGS.getParticles());
				if (Main.SETTINGS.getParticles()) {
					Main.MS.addMessage(new Message(
							"B.G. Effects has been turned on"));
				} else {
					Main.MS.addMessage(new Message(
							"B.G. Effects has been turned off"));
				}
			}
		});

		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "Fullscreen";
			}

			public void actionCallback() {
				Main.SETTINGS.setFullScreen(!Main.SETTINGS.getFullScreen());
				if (Main.SETTINGS.getFullScreen()) {
					Main.MS.addMessage(new Message(
							"Fullscreen has been turned on"));
				} else {
					Main.MS.addMessage(new Message(
							"Fullscreen has been turned off"));
				}
			}

		});
		
		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "Screen resolution";
			}

			public void actionCallback() {
				Main.SETTINGS.setResolution(Main.SETTINGS.getResolution().next());
					Main.MS.addMessage(new Message(
							"Resolution is now "+Main.SETTINGS.getResolution().getWidth()+"x"+Main.SETTINGS.getResolution().getHeight()));
			}

		});

		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "<- Back";
			}

			public void actionCallback() {
				game.enterState(0);
				Main.MS.addMessage(new Message(
						"Please restart for changes to take effect."));
			}
		});
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		menu.render(g);
		Main.MS.render(g);
	}

	public void update(GameContainer arg0, StateBasedGame game, int delta)
			throws SlickException {
		menu.update(delta);
		Main.MS.update(delta);
		if (Main.KEYDOWN[Input.KEY_ESCAPE]) {
			game.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 666;
	}

}
