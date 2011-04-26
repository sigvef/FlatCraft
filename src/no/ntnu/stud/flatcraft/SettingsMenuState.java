package no.ntnu.stud.flatcraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import no.ntnu.stud.flatcraft.menu.Menu;
import no.ntnu.stud.flatcraft.menu.MenuItem;
import no.ntnu.stud.flatcraft.settings.Settings;

public class SettingsMenuState extends BasicGameState{
	Menu menu;
	
	
	

	public void init(GameContainer app, final StateBasedGame game)
			throws SlickException {
		menu = new Menu(game);
		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "Sound";
			}
			public void actionCallback() {
				Main.SETTINGS.setSound(!Main.SETTINGS.getSound());
			}
		});

		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "Bloom";
			}
			public void actionCallback() {
				Main.SETTINGS.setBloom(!Main.SETTINGS.getBloom());
			}
		});
		
		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "B.G. Effects";
			}
			public void actionCallback() {
				Main.SETTINGS.setParticles(!Main.SETTINGS.getParticles());
			}
		});
		
		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "Fullscreen";
			}
			public void actionCallback() {
				Main.SETTINGS.setFullScreen(!Main.SETTINGS.getFullScreen());
			}
			
		});
		
		menu.addMenuItem(new MenuItem() {
			public String getText() {
				return "<- Back";
			}
			public void actionCallback() {
				game.enterState(0);
			}
		});
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		menu.render(g);
	}

	public void update(GameContainer arg0, StateBasedGame game, int delta)
			throws SlickException {
		menu.update(delta);
		if(Main.KEYDOWN[Input.KEY_ESCAPE]){
			game.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 666;
	}
	
	
	
	
}