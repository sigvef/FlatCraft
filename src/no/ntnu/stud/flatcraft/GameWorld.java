/*
 * FlatCraft - GameWorld.java
 * 
 * The main game manager that keeps track of all the entities in the game.
 * 
 */

package no.ntnu.stud.flatcraft;

import java.util.ArrayList;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.BodyList;
import net.phys2d.raw.World;
import no.ntnu.stud.flatcraft.entities.GameEntity;
import no.ntnu.stud.flatcraft.entities.Player;
import no.ntnu.stud.flatcraft.quadtree.QuadTree;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.OpenALStreamPlayer;
import org.newdawn.slick.openal.StreamSound;
import org.newdawn.slick.state.StateBasedGame;

public class GameWorld {

	public QuadTree terrain;
	Player player;
	ArrayList<GameEntity> entities;
	public World world;
	float viewportzoom;
	Vector2f viewportgoal;
	Image bg;
	public Rectangle viewport;
	StreamSound music;

	public GameWorld() throws SlickException {
		world = new World(Main.GRAVITY, Main.ITERATIONS);
		bg = new Image("res/bgtex.png");
		terrain = new QuadTree(0, 0, 1280 * Main.GU, 8, world); // hardcoded
																// level width:
																// a square 10x
																// the with of
																// the screen.
		viewport = new Rectangle(0, 0, Main.SCREEN_W, Main.SCREEN_H);
		viewportgoal = new Vector2f(viewport.getX(), viewport.getY());
		viewportzoom = 1;
		player = new Player(this, Main.GU, Main.GU, Main.GU * 5, Main.GU * 5, 1);
		entities = new ArrayList<GameEntity>();
		entities.add(player.getCharacter());
		world.add(player.getBody());
		// music = new StreamSound(new OpenALStreamPlayer(0,
		// "res/flatcraft2.ogg"));

	}

	// reset() - resets the map, calls reset on all the things it controls.
	public void reset() {
		for (GameEntity e : entities) {
			e.reset();
		}
		player.reset();
		viewport = new Rectangle(0, 0, Main.SCREEN_W, Main.SCREEN_H);
		// music.playAsMusic(1, 1, true);
		viewportzoom = 1;
		// reload terrain also TODO
	}

	public void setViewportPosition(Vector2f position) {
		viewport.setLocation(position.getX(), position.getY());
	}

	public void setViewportPositionGoal(Vector2f pos) {
		viewportgoal = pos;
	}

	public Vector2f getViewportPosition() {
		return new Vector2f(viewport.getX(), viewport.getY());
	}

	// removeEntities() - removes all the GameEntities it controls.
	public void removeEntities() {

	}

	// findEntities(Vector2f, float, ArrayList<GameEntity>, int) - finds and
	// adds
	// entities close to position to the Entities list.Returns the the number of
	// entities found and added to the Entities list.
	public int findEntities(Vector2f position, float radius,
			ArrayList<GameEntity> Entities, int type) {
		return 0;
	}

	// interSectCharacter(Rectangle, GameEntity) - returns a Character that
	// collides with the boundingBox. Does not check for collisions against
	// notThis. returns null if there is no collision.
	Character intersectCharacter(Rectangle boundingBox, GameEntity notThis) {
		return null;
	}

	// closestCharacter(Vector2f, GameEntity) - returns the Character closest
	// to the point. Does not check notThis. Returns null if no Character is
	// close enough.
	Character closestCharacter(Vector2f point, GameEntity notThis) {
		return null;
	}

	// update(GameContainer, StateBasedGame,int)x| - updates itself and all it's
	// children one tick.
	public void update(GameContainer container, StateBasedGame game, int delta) {
		terrain.update();
		player.update(container, game, delta);
		// world.collide(1);
		world.step();
		

		for (GameEntity entity : entities) {
			entity.update(container, game, delta);
		}
		if (viewportgoal.getX() != viewport.getX()
				|| viewportgoal.getY() != viewport.getY()) {
			viewport.setLocation(viewport.getX()
					- (viewport.getX() - viewportgoal.getX()) * 0.5f,
					viewport.getY() - (viewport.getY() - viewportgoal.getY())
							* 0.5f);
		}
	}

	public void render(Graphics g) {
		g.drawImage(bg, -viewport.getX() / Main.GU, -viewport.getY() / Main.GU);
		for (GameEntity entity : entities) {
			entity.render(g);
		}
		terrain.render(g, viewport);

//		g.pushTransform();
//		g.translate(-viewport.getX(), -viewport.getY());
//		for(int i=world.getBodies().size();i-->0;)
//			if(!world.getBodies().get(i).disabled())g.fill(new Rectangle(world.getBodies().get(i).getPosition().getX()
//														, world.getBodies().get(i).getPosition().getY(), 
//															Main.GU, Main.GU));
//		g.popTransform();
		player.render(g);
		
		
		
//		g.setColor(Color.red);
//		g.pushTransform();
//		g.translate(-viewport.getX(), -viewport.getY());
//		g.draw(viewport);
//		g.popTransform();
	}

	public void addBody(Body body) {
		world.add(body);

	}
}
