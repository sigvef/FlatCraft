package no.ntnu.stud.flatcraft.entities;

import java.awt.event.KeyEvent;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	
	public Player(int clientId, int team, boolean localPlayer){
		character = new Character();
		this.clientId = clientId;
		this.team = team;
		this.localPlayer = localPlayer;
		respawn();
	}
	
	public void render(Graphics g){
		character.render(g);
	}

	public void respawn(){
		character.spawn(new Vector2f(10,10)); //Test numbers, should ask for spawn locations
	}
	
	public void SetTeam(int team){
		this.team = team;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		while(input.size() > 0){
			switch(input.peek()){
			default: System.out.print(input.poll());
			}
		}
		character.update(container,game,delta);
		character.gameworld.setViewportPosition(character.position);
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
