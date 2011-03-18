package no.ntnu.stud.flatcraft.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	
	public Player(int clientId, int team){
		
	}

	public void respawn(){
		
	}
	
	public void SetTeam(int team){
		
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		
	}
	
	public Character getCharacter(){
		return character;
	}
	
	Rectangle viewport;
	Character character;
	int clientId;
	int team;
	boolean spawning;
	int kills;
	int deaths;
}
