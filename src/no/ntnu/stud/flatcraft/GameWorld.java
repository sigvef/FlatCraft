/*
 * FlatCraft - GameWorld.java
 * 
 * The main game manager that keeps track of all the entities in the game.
 * 
 */

package no.ntnu.stud.flatcraft;

import java.util.ArrayList;

import no.ntnu.stud.flatcraft.entities.GameEntity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class GameWorld {
	
	public GameWorld(){
		
	}
	
	//reset() - resets the map, calls reset on all the things it controls.
	public void reset(){
		
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
		
	}
}
