package no.ntnu.stud.flatcraft.levels.generator;

import no.ntnu.stud.flatcraft.Main;
import no.ntnu.stud.flatcraft.quadtree.Block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Creator {
	GeneratorWorld world;
	Block activeBlock;
	
	private boolean changedBlock = false;
	private final float speed = (float)Main.GU;
	
	public Creator (GeneratorWorld world)
			throws SlickException {
		this.world = world;
		activeBlock = Block.METAL;
	}
	

	public void update(GameContainer container, StateBasedGame game, int delta){
		if(Main.MOUSEDOWN[0]){
			world.terrain.fillCell(Main.MOUSEX+world.viewport.getX(), Main.MOUSEY+world.viewport.getY(),activeBlock);
		}
		if(Main.MOUSEDOWN[1]){
			world.terrain.fillCell(Main.MOUSEX+world.viewport.getX(), Main.MOUSEY+world.viewport.getY(),Block.EMPTY);	
		}

		if(Main.KEYDOWN[Input.KEY_UP]) {
			world.setViewportPosition(new Vector2f(world.viewport.getX(), world.viewport.getY() - speed));
			world.setViewportPositionGoal(new Vector2f(world.viewport.getX(), world.viewport.getY() - speed));
		}
		if(Main.KEYDOWN[Input.KEY_DOWN]) {
			world.setViewportPosition(new Vector2f(world.viewport.getX(), world.viewport.getY() + speed));
			world.setViewportPositionGoal(new Vector2f(world.viewport.getX(), world.viewport.getY() + speed));
		}
		if(Main.KEYDOWN[Input.KEY_LEFT]) {
			world.setViewportPosition(new Vector2f(world.viewport.getX() - speed, world.viewport.getY()));
			world.setViewportPositionGoal(new Vector2f(world.viewport.getX() - speed, world.viewport.getY()));
		}
		if(Main.KEYDOWN[Input.KEY_RIGHT]) {
			world.setViewportPosition(new Vector2f(world.viewport.getX() + speed, world.viewport.getY()));
			world.setViewportPositionGoal(new Vector2f(world.viewport.getX() + speed, world.viewport.getY()));
		}
		
		if (!changedBlock && Main.KEYDOWN[Input.KEY_Q]) {
			activeBlock = activeBlock.previous();
			changedBlock = true;
		}
		if (!changedBlock && Main.KEYDOWN[Input.KEY_E]) {
			activeBlock = activeBlock.next();
			changedBlock = true;
		}
		
		if (changedBlock && !Main.KEYDOWN[Input.KEY_Q] && !Main.KEYDOWN[Input.KEY_E]) {
			changedBlock = false;
		}
	}
	
	public void render(Graphics g) {
		switch (activeBlock) {
		case METAL:
			g.setColor(Color.darkGray);
			break;
		case EARTH:
			g.setColor(Color.orange);
			break;
		case ROCK:
			g.setColor(Color.lightGray);
			break;
		case RUBBER:
			g.setColor(Color.pink);
			break;
		case WATER:
			g.setColor(Color.blue);
			break;
		case ACID:
			g.setColor(Color.green);
			break;
		case GOAL:
			g.setColor(Color.white);
		}
		
		g.translate(0, 0);
		g.fillRect(Main.SCREEN_W-32, 0, 32, 32);
		g.setColor(Color.magenta);
		g.drawRect(Main.SCREEN_W-32, 0, 32, 32);
	}
}
