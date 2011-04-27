package no.ntnu.stud.flatcraft;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
	
	private ArrayList<String> getLevels() throws UnsupportedEncodingException, IOException {
		ArrayList<String> levels = new ArrayList<String>();
		// Directory path here
		String path = "res/levels/";
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		if (listOfFiles == null) {
			URL dirURL = getClass().getClassLoader().getResource(path);
			String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
			JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
			Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
			//Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
			while(entries.hasMoreElements()) {
				String name = entries.nextElement().getName();
				if (name.startsWith(path)) { //filter according to the path
					String entry = name.substring(path.length());
					int checkSubdir = entry.indexOf("/");
					if (checkSubdir >= 0) {
						// if it is a subdirectory, we just return the directory name
						entry = entry.substring(0, checkSubdir);
					}
					if (!entry.equals("")) {
						levels.add(entry.substring(0,entry.length()-4));
					}
				}
			}
		} else {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					files = listOfFiles[i].getName();
					if (files.endsWith(".flt")) {
						levels.add(files.substring(0,files.length()-4)); //loololololol
					}
				}
			}
		}
		return levels;
	}

	@Override
	public void enter(GameContainer app, StateBasedGame game){
		Arrays.fill(Main.KEYDOWN, false);
		if(menu != null ){
			pointer = menu.getPointer();
		}
		try {
			menu = new Menu(getLevels(),game);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		menu.setPointer(pointer);
	}
	
	public void leave(){

	}
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		menu.render(g);
		Main.MS.render(g);
	}

	public void update(GameContainer arg0, StateBasedGame game, int dt)
			throws SlickException {
		menu.update(dt);
		Main.MS.update(dt);
		if(Main.KEYDOWN[Input.KEY_ESCAPE]){
			game.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 123;
	}
	
	
}
