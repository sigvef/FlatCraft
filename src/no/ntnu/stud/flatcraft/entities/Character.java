package no.ntnu.stud.flatcraft.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Character extends GameEntity {
	public void reset(){
		
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		
	}
	
	public void setWeapon(WeaponType weapon){
		
	}
	
	public void fireWeapon(){
		
	}
	
	public void die(){
		
	}
	
	public void takeDamage(Vector2f force, int fromPlayer, WeaponType weapon){
		
	}
	
	public void spawn(Vector2f position){
		
	}
	
	public int increaseHealth(int amount){
		return 0; //returns health after increase;
	}
	
	public int increaseArmor(int amount){
		return 0; //returns armor after increase;
	}
	
	public void giveWeapon(WeaponType weapon, int ammo){
		
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	Player player; //handle to the player that controls this character.
	boolean alive;
	float reloadTimer;
	int damageTaken;
	int ammo;
	int health;
	int armor;
}
