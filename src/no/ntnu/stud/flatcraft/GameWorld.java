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
import no.ntnu.stud.flatcraft.entities.Character;
import no.ntnu.stud.flatcraft.entities.GameEntity;
import no.ntnu.stud.flatcraft.quadtree.Block;
import no.ntnu.stud.flatcraft.quadtree.QuadTree;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class GameWorld {

	public QuadTree terrain; 
	ArrayList<GameEntity> entities;
	public World world;
	public float viewportzoom;
	public Vector2f viewportgoal;
	Image bg;
	public Rectangle viewport;
	private String levelName = "";
	private BackgroundParticles backgroundparticles;

	public GameWorld(String level) throws SlickException {
		world = new World(0, 0, 320*Main.GU, 320*Main.GU, Main.GRAVITY.getY(), Main.ITERATIONS);
		bg = new Image("res/bgtex.png");
		backgroundparticles = new BackgroundParticles();
		terrain = new QuadTree(0, 0, 320 * Main.GU, 6, world,this); // hardcoded
																// level width:
																// a square 10x
																// the with of
																// the screen.
		load(level);
		viewport = new Rectangle(0, 0, Main.SCREEN_W/Main.GULOL, Main.SCREEN_H/Main.GULOL);
		viewportgoal = new Vector2f(viewport.getX(), viewport.getY());
		viewportzoom = 1;
		entities = new ArrayList<GameEntity>();
	}

	// reset() - resets the map, calls reset on all the things it controls.
	public void reset() {
		for (GameEntity e : entities) {
			e.reset();
		}
		
		viewport = new Rectangle(0, 0, Main.SCREEN_W/Main.GULOL, Main.SCREEN_H/Main.GULOL);
		viewportzoom = 1;
	}
	
	public void load(String s){
		BufferedReader bufferedReader;
		String level = "";
		try {
			bufferedReader = new BufferedReader(new FileReader(s));
			level = bufferedReader.readLine();
			bufferedReader.close();
		} catch (Exception e) {
			level = "";
		}
		boolean first = true;
		for(String node : level.split("\\|")){
			if(first){
				setLevelName(node);
				first = false;
			}
			else{
				String[] n = node.split(",");
				terrain.fillCell(Float.parseFloat(n[0]), Float.parseFloat(n[1]), Block.valueOf(n[3]),Integer.parseInt(n[2]));
			}
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
	
	public Vector2f getViewportPositionGoal() {
		return new Vector2f(viewportgoal.getX(), viewportgoal.getY());
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
		backgroundparticles.update(delta);
		for(int i=Main.UPDATES;i --> 0;) world.update(1/60f);

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
		
		g.pushTransform();
		g.scale(Main.GULOL*0.1f, Main.GULOL*0.1f);
		g.drawImage(bg, -viewport.getX()*0.25f, -viewport.getY()*0.25f);
		g.popTransform();
		
		if(Main.SETTINGS.getParticles()) {
			backgroundparticles.render(g);
		}
		for (GameEntity entity : entities) {
			entity.render(g);
		}
		terrain.render(g, viewport);
	}

	public void addBody(Body body) {
		world.add(body);

	}

	public void leave() {
	}

	public void add(Character character) {
		entities.add(character);
	}

	public void add(Body body) {
		world.add(body);		
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}

}
