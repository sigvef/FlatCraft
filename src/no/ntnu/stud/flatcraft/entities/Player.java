package no.ntnu.stud.flatcraft.entities;

import java.awt.event.KeyEvent;
import java.util.Queue;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;
import no.ntnu.stud.flatcraft.quadtree.Block;

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
	Block activeBlock;

	public Player(GameWorld gw){
		character = new Character(gw);
		activeBlock = Block.METAL;
		respawn();
	}
	
	public void reset(){
		character.spawn(new Vector2f(Main.GU*10,Main.GU*10));
	}
	
	public void render(Graphics g){
	}

	public void respawn(){
		character.spawn(new Vector2f(Main.GU*10,Main.GU*10)); //Test numbers, should ask for spawn locations
	}
	
	public void SetTeam(int team){
		this.team = team;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		//character.update(container,game,delta);
		
		if(Main.KEYDOWN[Input.KEY_Q]){
			activeBlock = activeBlock.next();
			System.out.println(activeBlock);
		}
		if(Main.KEYDOWN[Input.KEY_E]){
			activeBlock = activeBlock.previous();
			System.out.println(activeBlock);
		}
		
		if(Main.KEYDOWN[Input.KEY_UP] || Main.KEYDOWN[Input.KEY_W] || Main.KEYDOWN[Input.KEY_SPACE]){
			if(character.grounded){
				character.velocitize(new Vector2f(0,-Main.GU*8f));
			}
		}
		if(Main.KEYDOWN[Input.KEY_LEFT] || Main.KEYDOWN[Input.KEY_A]){
			character.velocitize(new Vector2f(-Main.GU,0));
		}
		if(Main.KEYDOWN[Input.KEY_RIGHT] || Main.KEYDOWN[Input.KEY_D]){
			character.velocitize(new Vector2f(Main.GU,0));
		}
		if(Main.KEYDOWN[Input.KEY_DOWN] || Main.KEYDOWN[Input.KEY_S]){
			character.velocitize(new Vector2f(0,Main.GU));
		}
		
		if(Main.MOUSEDOWN[0]){
			character.gameworld.terrain.fillCell(Main.MOUSEX+character.gameworld.viewport.getX(), Main.MOUSEY+character.gameworld.viewport.getY(),activeBlock);
		}
		if(Main.MOUSEDOWN[1]){
			character.gameworld.terrain.fillCell(Main.MOUSEX+character.gameworld.viewport.getX(), Main.MOUSEY+character.gameworld.viewport.getY(),Block.EMPTY);	
		}
		
		
		if(character.position.getY()>Main.GU*48+character.gameworld.getViewportPosition().getY()){
			character.gameworld.setViewportPositionGoal(new Vector2f(character.gameworld.getViewportPosition().getX(),character.position.getY()-Main.GU*48));
		}
		if(character.position.getY()<Main.GU*16+character.gameworld.getViewportPosition().getY()){
			character.gameworld.setViewportPositionGoal(new Vector2f(character.gameworld.getViewportPosition().getX(),character.position.getY()-Main.GU*16));
		}
		if(character.position.getX()>Main.GU*100+character.gameworld.getViewportPosition().getX()){
			character.gameworld.setViewportPositionGoal(new Vector2f(character.position.getX()-Main.GU*100,character.gameworld.getViewportPosition().getY()));
		}
		if(character.position.getX()<Main.GU*16+character.gameworld.getViewportPosition().getX()){
			character.gameworld.setViewportPositionGoal(new Vector2f(character.position.getX()-Main.GU*16,character.gameworld.getViewportPosition().getY()));
		}
		
		if(character.grounded){
			//tween this
			character.gameworld.setViewportPositionGoal(new Vector2f(character.gameworld.getViewportPosition().getX(),character.position.getY()-Main.GU*48));
		}
	}
	
	public Character getCharacter(){
		return character;
	}
	

}
