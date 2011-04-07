/*
 * FlatCraft - GameEntity.java
 * 
 * The base entity class that all entities inherit from.
 * 
 */

package no.ntnu.stud.flatcraft.entities;


import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class GameEntity {


	public boolean grounded = false;
	public Rectangle boundingBox; //physical bounding box of the entity relative to the position vector.
	public Polygon[] forwards;
	public Vector2f position; //physical position of the entity;
	public Vector2f oldposition;
	public Vector2f velocity; //physical velocity of the entity;
	public Vector2f acceleration; //physical acceleration of the entity;
	GameWorld gameworld; //handle back to the parent GameWorld.

	public void init(GameWorld gw){
		forwards = new Polygon[4];
		gameworld = gw;
		boundingBox = new Rectangle(-Main.GU*5,-Main.GU*5,Main.GU*5,Main.GU*5);
		position = new Vector2f(0,0);
		velocity = new Vector2f(0,0);
		oldposition = new Vector2f(0,0);
		rebuildForwards();
	}
	
	//reset() - Called when the GameWorld resets. May destroy the object if
	//needed.
	public void reset(){
		position.set(0,0);
		oldposition.set(0,0);
		velocity.set(0,0);
	}
	
	public void rebuildForwards(){
		float points[] = boundingBox.getPoints();

		forwards[0] = new Polygon();
		forwards[0].addPoint(points[0], points[1]);
		forwards[0].addPoint(points[2], points[3]);
		forwards[0].addPoint(points[2] - velocity.getX(), points[3] - velocity.getY());
		forwards[0].addPoint(points[0] - velocity.getX(), points[1] - velocity.getY());
		forwards[0].setClosed(true);

		forwards[1] = new Polygon();
		forwards[1].addPoint(points[2], points[3]);
		forwards[1].addPoint(points[4], points[5]);
		forwards[1].addPoint(points[4] - velocity.getX(), points[5] - velocity.getY());
		forwards[1].addPoint(points[2] - velocity.getX(), points[3] - velocity.getY());
		forwards[1].setClosed(true);

		forwards[2] = new Polygon();
		forwards[2].addPoint(points[4], points[5]);
		forwards[2].addPoint(points[6], points[7]);
		forwards[2].addPoint(points[6] - velocity.getX(), points[7] - velocity.getY());
		forwards[2].addPoint(points[4] - velocity.getX(), points[5] - velocity.getY());
		forwards[2].setClosed(true);

		forwards[3] = new Polygon();
		forwards[3].addPoint(points[6], points[7]);
		forwards[3].addPoint(points[0], points[1]);
		forwards[3].addPoint(points[0] - velocity.getX(), points[1] - velocity.getY());
		forwards[3].addPoint(points[6] - velocity.getX(), points[7] - velocity.getY());
		forwards[3].setClosed(true);
	}
	
	public void render(Graphics g){
		if(Main.DEBUG){
			g.pushTransform();
			g.setColor(Color.red);
			for(Polygon forward : forwards){
				g.draw(forward);
			}
			g.popTransform();
			g.setColor(Color.white);
			g.draw(boundingBox);
		}
	}
	
	
	
	//update(GameContainer, StateBasedGame, int) - Updates the entity one tick.
	public void update(GameContainer container, StateBasedGame game, int delta){
		oldposition.set(position.copy());
//		velocity.add(Main.GRAVITY.copy());
		position.add(velocity.scale(Main.mu));
		boundingBox.setLocation(position);
		rebuildForwards();
		gameworld.terrain.collide(this);
		boundingBox.setLocation(position);
		
	}
}
