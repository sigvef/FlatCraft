/*
 * FlatCraft - GameEntity.java
 * 
 * The base entity class that all entities inherit from.
 * 
 */

package no.ntnu.stud.flatcraft.entities;

import net.phys2d.raw.Body;
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
	public Rectangle boundingBox; // physical bounding box of the entity
	GameWorld gameworld; // handle back to the parent GameWorld.

	public void init(GameWorld gw, float _x, float _y, float _width,
			float _height, float _mass) {

		body = new Body(new Box(_width, _height), _mass);
		body.setUserData(this);
		body.setRestitution(0);
		body.setFriction(Main.mu);
		body.setRotatable(false);
		body.setPosition(_x + _width * 0.5f, _y + _height * 0.5f);

		gameworld = gw;
		boundingBox = new Rectangle(_x, _y, _width, _height);
	}

	// reset() - Called when the GameWorld resets. May destroy the object if
	// needed.
	public void reset() {

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

		boundingBox.setLocation(
				body.getPosition().getX() - boundingBox.getWidth() * 0.5f, body
						.getPosition().getY() - boundingBox.getHeight() * 0.5f);
	}
}
