package no.ntnu.stud.flatcraft.entities;

import java.awt.event.KeyEvent;
import java.util.Queue;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	
	public Player(int clientId, int team, boolean localPlayer, GameWorld gw){
		character = new Character(gw);
		this.clientId = clientId;
		this.team = team;
		this.localPlayer = localPlayer;
		respawn();
	}
	
	public void render(Graphics g){
		//character.render(g);
	}

	public void respawn(){
		character.spawn(new Vector2f(10,10)); //Test numbers, should ask for spawn locations
	}
	
	public void SetTeam(int team){
		this.team = team;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		character.update(container,game,delta);
		if(character.position.getY()>Main.GU*7){
			character.gameworld.setViewportPosition(new Vector2f(character.position.getX(),character.position.getY()-Main.GU*7));
		}
	}
	
	public Character getCharacter(){
		return character;
	}
	
	Rectangle viewport;
	Character character;
	Queue<Integer> input;
	int clientId;
	int team;
	boolean spawning;
	int kills;
	int deaths;
	boolean localPlayer;
}
