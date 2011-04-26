package no.ntnu.stud.flatcraft;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState {
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("LOADING", Main.GULOL*32, Main.GULOL*18);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (LoadingList.get().getRemainingResources() > 0) {
	        DeferredResource nextResource = LoadingList.get().getNext(); 
	        try {
				nextResource.load();
			} catch (IOException e) {
				throw new SlickException("Could not load sound!");
			}
		} else {
			game.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 9001;
	}

}
