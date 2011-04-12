/*
 * FlatCraft - GameEntity.java
 * 
 * The base entity class that all entities inherit from.
 * 
 */

package no.ntnu.stud.flatcraft.entities;

import javax.swing.text.Position;

import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.shapes.Box;
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

	Body body;
	public boolean grounded = false;
	private boolean moving = false;
	private boolean falling = false;
	protected boolean facingRight = true;
	public Rectangle boundingBox; // physical bounding box of the entity
	GameWorld gameworld; // handle back to the parent GameWorld.
	private int offGroundTimer;
	private boolean jumped;

	public void init(GameWorld gw, float _x, float _y, float _width,
			float _height, float _mass) {

		body = new Body(new Box(_width, _height), _mass);
		body.setUserData(this);
		body.setRestitution(0);
		body.setFriction(Main.mu);
		body.setMaxVelocity(40*Main.GU, 80*Main.GU);
		body.setRotatable(false);
		body.setPosition(_x + _width * 0.5f, _y + _height * 0.5f);

		gameworld = gw;
		boundingBox = new Rectangle(_x, _y, _width, _height);
	}

	// reset() - Called when the GameWorld resets. May destroy the object if
	// needed.
	public void reset() {

	}
	
	public void applyForce(float x, float y) {
		body.addForce(new net.phys2d.math.Vector2f(x,y));
		moving = true;
		if (y < 0) {
			jumped = true;
		}
		
		if (x > 0) {
			facingRight = true;
		}
		if (x < 0) {
			facingRight = false;
		}
	}

	public void render(Graphics g) {
		if (Main.DEBUG) {
			g.pushTransform();
			g.translate(-gameworld.viewport.getX(), -gameworld.viewport.getY());
			g.setColor(Color.red);
			g.setColor(Color.white);
			g.draw(boundingBox);
			g.popTransform();
		}
	}

	// update(GameContainer, StateBasedGame, int) - Updates the entity one tick.
	public void update(GameContainer container, StateBasedGame game, int delta) {
		grounded = onGroundImpl(body);

		if (grounded && body.getVelocity().getY()>0) {
			body.adjustVelocity( new net.phys2d.math.Vector2f(0,-body.getVelocity().getY()));
		}
		
		if (grounded && !moving) {
			body.adjustVelocity( new net.phys2d.math.Vector2f(-body.getVelocity().getY()*0.1f,0));
		}
		
//		body.setGravityEffected(!grounded);
	//		if (!on) {
//			offGroundTimer += delta;
//			if (offGroundTimer > 20) {
//				grounded = false;
//			}
//		} else {
//			offGroundTimer = 0;
//			grounded = true;
//		}
//		System.out.println(body.getVelocity());
		boundingBox.setLocation(
				body.getPosition().getX() - boundingBox.getWidth() * 0.5f, body
						.getPosition().getY() - boundingBox.getHeight() * 0.5f);
		moving = false;
	}
	
	protected boolean onGroundImpl(Body body) {
		if (gameworld.world == null) {
			return false;
		}
		
		// loop through the collision events that have occured in the
		// world
		CollisionEvent[] events = gameworld.world.getContacts(body);
		
		for (int i=0;i<events.length;i++) {
			// if the point of collision was below the centre of the actor
			// i.e. near the feet
			if (events[i].getPoint().getY() > body.getPosition().getY()+(boundingBox.getHeight()/4)) {
				// check the normal to work out which body we care about
				// if the right body is involved and a collision has happened
				// below it then we're on the ground
				if (events[i].getNormal().getY() < 0) {
					if (events[i].getBodyB() == body) {
						return true;
					}
				}
				if (events[i].getNormal().getY() > 0) {
					if (events[i].getBodyA() == body) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

