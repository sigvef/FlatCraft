package no.ntnu.stud.flatcraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import no.ntnu.stud.flatcraft.menu.Menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LevelSelectMenuState extends BasicGameState{
	
	private Menu menu;
	private int pointer = 0;
	
	private ArrayList<String> getLevels() {
		ArrayList<String> levels = new ArrayList<String>();
		// Directory path here
		String path = "res/levels/";
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".flt")) {
					levels.add(files.substring(0,files.length()-4)); //loololololol
				}
			}
		}
		return levels;
	}

	public void enter(GameContainer app, StateBasedGame game){
//		Main.KEYDOWN[Input.KEY_ESCAPE] = false;
//		Main.KEYDOWN[Input.KEY_ENTER] = false;
//		Main.KEYDOWN[Input.KEY_SPACE] = false;
		Arrays.fill(Main.KEYDOWN, false);
		if(menu != null ){
			pointer = menu.getPointer();
		}
		menu = new Menu(getLevels(),game);
		menu.setPointer(pointer);
		System.out.println("PPOPOOOOIIINTTERRR: "+pointer);
	}
	
	public void leave(){

	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		menu.render(g);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame game, int dt)
			throws SlickException {
		menu.update(dt);
		if(Main.KEYDOWN[Input.KEY_ESCAPE]){
			game.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 123;
	}
	
	
}
