package no.ntnu.stud.flatcraft.entities;

import net.phys2d.math.Vector2f;
import no.ntnu.stud.flatcraft.GameWorld;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class Character extends GameEntity {

	Animation runningLeftAnimation;
	Animation runningRightAnimation;

	public Character(GameWorld gw, float _x, float _y, float _width,
			float _height, float _mass) {
		super.init(gw, _x, _y, _width, _height, _mass);
		body.setCanRest(true);
		try {
			// image = new Image("res/character.png");
			runningLeftAnimation = new Animation(new SpriteSheet("res/runningLeft_ss.png",
					50, 50), 0, 0, 4, 0, true, 50, true);
			runningRightAnimation = new Animation(new SpriteSheet("res/runningRight_ss.png",
					50, 50), 0, 0, 4, 0, true, 50, true);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		super.reset();
	}

	public void render(Graphics g) {
		g.pushTransform();
		g.translate(-gameworld.viewport.getX(), -gameworld.viewport.getY());
		// g.drawImage(image, body.getPosition().getX(),
		// body.getPosition().getY());
		if(facingRight)
			runningRightAnimation.draw(boundingBox.getX(), boundingBox.getY());
		else	runningLeftAnimation.draw(boundingBox.getX(), boundingBox.getY());
		g.popTransform();
		super.render(g);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
	}

	public void spawn(Vector2f pos) {
		body.setPosition(pos.getX(), pos.getY());
	}

	Player player; // handle to the player that controls this character.
	Image image;
}
