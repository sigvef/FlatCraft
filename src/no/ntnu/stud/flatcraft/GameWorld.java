/*
 * FlatCraft - GameWorld.java
 * 
 * The main game manager that keeps track of all the entities in the game.
 * 
 */

package no.ntnu.stud.flatcraft;

import java.util.ArrayList;

import no.ntnu.stud.flatcraft.entities.GameEntity;
import no.ntnu.stud.flatcraft.entities.Player;
import no.ntnu.stud.flatcraft.quadtree.QuadTree;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class GameWorld {
	
	public QuadTree terrain;
	public Rectangle viewport;
	Player player;
	ArrayList<GameEntity> entities;
	float viewportzoom;
	
	public GameWorld() throws SlickException{
		terrain = new QuadTree(0,0,1280*Main.GU,8); //hardcoded level width: a square 10x the with of the screen.
		viewport = new Rectangle(0,0,128*Main.GU,72*Main.GU);
		viewportzoom = 1;
		player = new Player(this);
		entities = new ArrayList<GameEntity>();
		entities.add(player.getCharacter());
	}
	
	//reset() - resets the map, calls reset on all the things it controls.
	public void reset(){
		
	}
	
	public void setViewportPosition(Vector2f position){
		viewport.setLocation(position);
	}
	
	public Vector2f getViewportPosition(){
		return new Vector2f(viewport.getX(),viewport.getY());
	}
	
	//removeEntities() - removes all the GameEntities it controls.
	public void removeEntities(){
		
	}
	
	//findEntities(Vector2f, float, ArrayList<GameEntity>, int) - finds and adds
	//entities close to position to the Entities list.Returns the the number of
	//entities found and added to the Entities list.
	public int findEntities(Vector2f position, float radius, ArrayList<GameEntity> Entities, int type){
		return 0;
	}
	
	//interSectCharacter(Rectangle, GameEntity) - returns a Character that 
	//collides with the boundingBox. Does not check for collisions against
	//notThis. returns null if there is no collision.
	Character intersectCharacter(Rectangle boundingBox, GameEntity notThis){
		return null;
	}
	
	//closestCharacter(Vector2f, GameEntity) - returns the Character closest
	//to the point. Does not check notThis. Returns null if no Character is
	//close enough.
	Character closestCharacter(Vector2f point, GameEntity notThis){
		return null;
	}
	
	
	//update(GameContainer, StateBasedGame,int) - updates itself and all it's
	//children one tick.
	public void update(GameContainer container, StateBasedGame game, int delta){
		player.update(container, game, delta);		
		for(GameEntity entity : entities){
			entity.update(container, game, delta);
		}
	}
	
	public void render(Graphics g){
		terrain.render(g, viewport);
		for(GameEntity entity : entities){
			entity.render(g);
		}
		player.render(g);
	}
}
