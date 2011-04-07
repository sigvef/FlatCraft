package no.ntnu.stud.flatcraft.entities;

import no.ntnu.stud.flatcraft.GameWorld;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Character extends GameEntity {
	
	public Character(GameWorld gw){
		super.init(gw);
		try {
			image = new Image("res/character.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void reset(){
		super.reset();
		ammo = 0;
		health = 0;
		armor = 0;
		reloadTimer = 0;
	}
	
	public void render(Graphics g){
		g.pushTransform();
		g.translate(-gameworld.viewport.getX(), -gameworld.viewport.getY());
		g.drawImage(image, position.getX(), position.getY());
		super.render(g);
		g.popTransform();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		if(reloadTimer > 0){
			reloadTimer -= delta;
		}
		if(reloadTimer < 0){
			reloadTimer = 0;
		}
		super.update(container, game, delta);
	}
	
	
	public void die(){
		alive = false;
		reset();
	}
	
	
	public void spawn(Vector2f position){
		alive = true;
		this.position.set(position);
		ammo = 7;
	}
	
	public int increaseHealth(int amount){
		health += amount;
		return health;
	}
	
	public int increaseArmor(int amount){
		armor += amount;
		return armor;
	}
	
	
	public boolean isAlive(){
		return alive;
	}
	
	Player player; //handle to the player that controls this character.
	Image image;
	boolean alive;
	int reloadTimer;
	int damageTaken;
	int ammo;
	int health;
	int armor;
	public void velocitize(Vector2f vel) {
		velocity.add(vel);
	}
}
