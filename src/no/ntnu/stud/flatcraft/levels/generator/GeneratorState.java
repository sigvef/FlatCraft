package no.ntnu.stud.flatcraft.levels.generator;

import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GeneratorState extends BasicGameState {
	private GeneratorWorld generatorWorld;
	int timer;
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		generatorWorld.render(g);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		timer += delta;
		while(timer > 20){
			if(Main.KEYDOWN[Input.KEY_ESCAPE]){
				game.enterState(0); //go back to MainMenuState
			}
			generatorWorld.update(container, game, 1);
			timer -= 20;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		generatorWorld = new GeneratorWorld();
			timer = 0;
	}
	
	public void enter(GameContainer container, StateBasedGame game){
		generatorWorld.reset();
	}
	
	public void leave(GameContainer container, StateBasedGame game){
	}

	@Override
	public int getID() {
		return 1337;
	}
}
