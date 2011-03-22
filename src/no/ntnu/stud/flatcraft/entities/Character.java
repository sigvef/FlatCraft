package no.ntnu.stud.flatcraft.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import quicktime.std.anim.Sprite;

public class Character extends GameEntity {
	
	public Character(){
		try {
			image = new Image("res/character.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		position = new Vector2f(0,0);
		velocity = new Vector2f(0,0);
		acceleration = new Vector2f(0,0);
	}
	
	public void reset(){
		super.reset();
		ammo = 0;
		health = 0;
		armor = 0;
		reloadTimer = 0;
		weapon = null;
	}
	
	public void render(Graphics g){
		g.drawImage(image, position.getX(), position.getY());
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		if(reloadTimer > 0){
			reloadTimer -= delta;
		}
		if(reloadTimer < 0){
			reloadTimer = 0;
		}
	}
	
	public void setWeapon(WeaponType weapon){
		this.weapon = weapon;
	}
	
	public void fireWeapon(){
		reloadTimer = 1000; //time in ms to reload;
	}
	
	public void die(){
		alive = false;
		reset();
	}
	
	public void takeDamage(Vector2f force, int fromPlayer, WeaponType weapon){
		if(armor > 0){
			armor -= force.length();
			health -= force.length()*0.3;
		}
		if(armor < 0){
			armor = 0;
		}
		if(health < 0){
			die();
		}
	}
	
	public void spawn(Vector2f position){
		alive = true;
		this.position.set(position);
		weapon = WeaponType.PISTOL;
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
	
	public void giveWeapon(WeaponType weapon, int ammo){
		this.weapon = weapon;
		this.ammo = ammo;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	Player player; //handle to the player that controls this character.
	Image image;
	boolean alive;
	WeaponType weapon;
	int reloadTimer;
	int damageTaken;
	int ammo;
	int health;
	int armor;
}
