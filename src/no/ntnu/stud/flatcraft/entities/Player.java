package no.ntnu.stud.flatcraft.entities;

import java.awt.event.KeyEvent;
import java.util.Queue;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	
	Rectangle viewport;
	Character character;
	Queue<Integer> input;
	int clientId;
	int team;
	boolean spawning;
	int kills;
	int deaths;
	boolean localPlayer;

	public Player(GameWorld gw){
		character = new Character(gw);
		respawn();
	}
	
	public void render(Graphics g){
	}

	public void respawn(){
		character.spawn(new Vector2f(10,10)); //Test numbers, should ask for spawn locations
	}
	
	public void SetTeam(int team){
		this.team = team;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		//character.update(container,game,delta);
		
		if(Main.KEYDOWN[Input.KEY_UP]){

			character.velocitize(new Vector2f(0,-Main.GU));
		}
		if(Main.KEYDOWN[Input.KEY_LEFT] || Main.KEYDOWN[Input.KEY_A]){
			character.velocitize(new Vector2f(-Main.GU,0));
		}
		if(Main.KEYDOWN[Input.KEY_DOWN]){

			character.velocitize(new Vector2f(0,Main.GU));
		}
		if(Main.KEYDOWN[Input.KEY_RIGHT] || Main.KEYDOWN[Input.KEY_D]){
			character.velocitize(new Vector2f(Main.GU,0));
		}
		
		
		if(character.position.getY()>Main.GU*64+character.gameworld.getViewportPosition().getY()){
			character.gameworld.setViewportPosition(new Vector2f(character.gameworld.getViewportPosition().getX(),character.position.getY()-Main.GU*64));
		}
		if(character.position.getY()<Main.GU*8+character.gameworld.getViewportPosition().getY()){
			character.gameworld.setViewportPosition(new Vector2f(character.gameworld.getViewportPosition().getX(),character.position.getY()-Main.GU*8));
		}
		if(character.position.getX()>Main.GU*120+character.gameworld.getViewportPosition().getX()){
			character.gameworld.setViewportPosition(new Vector2f(character.position.getX()-Main.GU*120,character.gameworld.getViewportPosition().getY()));
		}
		if(character.position.getX()<Main.GU*8+character.gameworld.getViewportPosition().getX()){
			character.gameworld.setViewportPosition(new Vector2f(character.position.getX()-Main.GU*8,character.gameworld.getViewportPosition().getY()));
		}
	
	}
	
	public Character getCharacter(){
		return character;
	}
	

}
