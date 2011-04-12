/*
 * FlatCraft - GameWorld.java
 * 
 * The main game manager that keeps track of all the entities in the game.
 * 
 */

package no.ntnu.stud.flatcraft;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.BodyList;
import net.phys2d.raw.World;
import net.phys2d.raw.strategies.QuadSpaceStrategy;
import no.ntnu.stud.flatcraft.entities.Character;
import no.ntnu.stud.flatcraft.entities.GameEntity;
import no.ntnu.stud.flatcraft.entities.Player;
import no.ntnu.stud.flatcraft.quadtree.Block;
import no.ntnu.stud.flatcraft.quadtree.QuadTree;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class GameWorld {

	public QuadTree terrain;
	
	ArrayList<GameEntity> entities;
	public World world;
	public float viewportzoom;
	public Vector2f viewportgoal;
	Image bg;
	public Rectangle viewport;
	Music music;

	public GameWorld(String level) throws SlickException {
		world = new World(Main.GRAVITY, Main.ITERATIONS, new QuadSpaceStrategy(16,4));
		bg = new Image("res/bgtex.png");
		terrain = new QuadTree(0, 0, 320 * Main.GU, 6, world); // hardcoded
																// level width:
																// a square 10x
																// the with of
																// the screen.
		load(level);
		viewport = new Rectangle(0, 0, Main.SCREEN_W, Main.SCREEN_H);
		viewportgoal = new Vector2f(viewport.getX(), viewport.getY());
		viewportzoom = 1;
		entities = new ArrayList<GameEntity>();
		
		// music = new StreamSound(new OpenALStreamPlayer(0,
		// "res/flatcraft2.ogg"));
//		music = new Music("res/flatcraft2.ogg");
	}

	// reset() - resets the map, calls reset on all the things it controls.
	public void reset() {
		for (GameEntity e : entities) {
			e.reset();
		}
		
		viewport = new Rectangle(0, 0, Main.SCREEN_W, Main.SCREEN_H);
		// music.playAsMusic(1, 1, true);
//		music.play();
		viewportzoom = 1;
		// reload terrain also TODO
	}
	
	public void load(String s){
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(s));
			String level = bufferedReader.readLine();
			for(String node : level.split("\\|")){
				String[] n = node.split(",");
				terrain.fillCell(Float.parseFloat(n[0]), Float.parseFloat(n[1]), Block.valueOf(n[3]),Integer.parseInt(n[2]));
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		world.step();
		System.out.println("numberOfNodes: "+terrain.getNumberOfNodes());
		System.out.println("numberOfLeaves: "+terrain.getNumberOfLeaves());
		System.out.println("numberOfBodies: "+world.getBodies().size());

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
		
		
		
//		g.setColor(Color.red);
//		g.pushTransform();
//		g.translate(-viewport.getX(), -viewport.getY());
//		g.draw(viewport);
//		g.popTransform();
	}

	public void addBody(Body body) {
		world.add(body);

	}

	public void leave() {
//		music.stop();
	}

	public void add(Character character) {
		entities.add(character);
	}

	public void add(Body body) {
		world.add(body);		
	}
}
