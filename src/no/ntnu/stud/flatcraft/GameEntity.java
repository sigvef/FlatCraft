/*
 * FlatCraft - GameEntity.java
 * 
 * The base entity class that all entities inherit from.
 * 
 */

package no.ntnu.stud.flatcraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class GameEntity {

	//reset() - Called when the GameWorld resets. May destroy the object if
	//needed.
	public void reset(){
		
	}
	
	//update(GameContainer, StateBasedGame, int) - Updates the entity one tick.
	public void update(GameContainer container, StateBasedGame game, int delta){
		
	}
	
	Rectangle boundingBox; //physical bounding box of the entity relative to the position vector.
	Vector2f position; //physical position of the entity;
	Vector2f velocity; //physical velocity of the entity;
	Vector2f acceleration; //physical acceleration of the entity;
	GameWorld gameworld; //handle back to the parent GameWorld.
}
