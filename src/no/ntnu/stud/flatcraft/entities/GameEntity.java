/*
 * FlatCraft - GameEntity.java
 * 
 * The base entity class that all entities inherit from.
 * 
 */

package no.ntnu.stud.flatcraft.entities;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class GameEntity {

	public void init(GameWorld gw){
		gameworld = gw;
		position = new Vector2f(0,0);
		velocity = new Vector2f(0,0);
		acceleration = new Vector2f(0,0);
	}
	
	//reset() - Called when the GameWorld resets. May destroy the object if
	//needed.
	public void reset(){
		position.set(0,0);
		velocity.set(0,0);
		acceleration.set(0,0);
	}
	
	public void render(Graphics g){
		
	}
	
	//update(GameContainer, StateBasedGame, int) - Updates the entity one tick.
	public void update(GameContainer container, StateBasedGame game, int delta){
		acceleration.add(Main.GRAVITY.copy().scale(delta));
		velocity.add(acceleration.copy().scale(delta));
		position.add(velocity.copy().scale(delta));
	}
	
	Rectangle boundingBox; //physical bounding box of the entity relative to the position vector.
	Vector2f position; //physical position of the entity;
	Vector2f velocity; //physical velocity of the entity;
	Vector2f acceleration; //physical acceleration of the entity;
	GameWorld gameworld; //handle back to the parent GameWorld.
}
