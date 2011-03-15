package no.ntnu.stud.flatcraft;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState implements GameState {
	
	public void enter(GameContainer container, StateBasedGame game){
		
	}
	
	public int getID(){
		return 0;
	}
	
	public void leave(GameContainer container, StateBasedGame game){
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		g.drawString("Hello World!", Main.GU*4, Main.GU*3);		
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
	}

	@Override
	public void mouseClicked(int oldx, int oldy, int newx, int newy) {
		
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		
	}

	@Override
	public void mouseWheelMoved(int change) {
		
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input input) {
		
	}

	@Override
	public void keyPressed(int key, char c) {
		
	}

	@Override
	public void keyReleased(int key, char c) {
		switch(key){
			case Input.KEY_ESCAPE:
				//TODO close
				break;
		}
	}

	@Override
	public void controllerButtonPressed(int controller, int button) {
		
	}

	@Override
	public void controllerButtonReleased(int controller, int button) {
		
	}

	@Override
	public void controllerDownPressed(int controller) {
		
	}

	@Override
	public void controllerDownReleased(int controller) {
		
	}

	@Override
	public void controllerLeftPressed(int controller) {
		
	}

	@Override
	public void controllerLeftReleased(int controller) {
		
	}

	@Override
	public void controllerRightPressed(int controller) {
		
	}

	@Override
	public void controllerRightReleased(int controller) {
		
	}

	@Override
	public void controllerUpPressed(int controller) {
		
	}

	@Override
	public void controllerUpReleased(int controller) {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
	}
}
