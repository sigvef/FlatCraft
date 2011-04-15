package no.ntnu.stud.flatcraft.entities;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class Character extends GameEntity {

	Animation runningLeftAnimation;
	Animation runningRightAnimation;
	Image standingLeftSprite;
	Image standingRightSprite;

	public Character(GameWorld gw, float _x, float _y, float _width,
			float _height, float _mass) {
		super.init(gw, _x, _y, _width, _height, _mass);
//		body.setCanRest(true);
		try {
			// image = new Image("res/character.png");
			runningLeftAnimation = new Animation(new SpriteSheet("res/runningLeft_ss.png",
					50, 50), 0, 0, 4, 0, true, 50, true);
			runningRightAnimation = new Animation(new SpriteSheet("res/runningRight_ss.png",
					50, 50), 0, 0, 4, 0, true, 50, true);
			standingLeftSprite = new Image("res/standingLeft.png");
			standingRightSprite = new Image("res/standingRight.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		super.reset();
	}

	public void render(Graphics g) {
		super.render(g);
		g.pushTransform();

		g.translate(-gameworld.viewport.getX()*Main.GULOL, -gameworld.viewport.getY()*Main.GULOL);
		//		g.translate(-gameworld.viewport.getX(), -gameworld.viewport.getY());
//		g.scale(Main.GULOL, Main.GULOL);
		// g.drawImage(image, body.getPosition().getX(),
		// body.getPosition().getY());
		if(facingRight && moving)
			runningRightAnimation.draw(physrect.getX()*Main.GULOL, physrect.getY()*Main.GULOL);
		else if (moving)	runningLeftAnimation.draw(physrect.getX()*Main.GULOL, physrect.getY()*Main.GULOL);
		else if (facingRight) standingRightSprite.draw(physrect.getX()*Main.GULOL, physrect.getY()*Main.GULOL);
		else standingLeftSprite.draw(physrect.getX()*Main.GULOL, physrect.getY()*Main.GULOL);
		g.popTransform();
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		runningRightAnimation.setSpeed(Math.abs(body.getXVelocity()*0.15f));
		runningLeftAnimation.setSpeed(Math.abs(body.getXVelocity()*0.15f));
	}

	public void spawn(Vector2f pos) {
		body.setPosition(pos.getX(), pos.getY());
	}

	Player player; // handle to the player that controls this character.
	Image image;
}
