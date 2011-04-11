package no.ntnu.stud.flatcraft.levels.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;
import no.ntnu.stud.flatcraft.quadtree.Block;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GeneratorState extends BasicGameState {
	private GameWorld generatorWorld;
	int timer;
	Block activeBlock;
	boolean changedBlock = false;
	
	public void save(){
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter("level.flt"));
			String level = generatorWorld.terrain.toString();
			if(level.length() > 0){
				bufferedWriter.write(generatorWorld.terrain.toString().substring(1));
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load(){
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("level.flt"));
			String level = bufferedReader.readLine();
			for(String node : level.split("\\|")){
				String[] n = node.split(",");
				generatorWorld.terrain.fillCell(Float.parseFloat(n[0]), Float.parseFloat(n[1]), Block.valueOf(n[2]));
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
//		g.pushTransform();
//		g.scale(generatorWorld.viewportzoom,generatorWorld.viewportzoom);
		generatorWorld.render(g);
//		g.popTransform();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta){
		timer += delta;
		while(timer > 20){
			if(Main.KEYDOWN[Input.KEY_ESCAPE]){
				game.enterState(0); //go back to MainMenuState
			}
			
			if(Main.KEYDOWN[Input.KEY_W]){
				generatorWorld.viewportgoal.set(generatorWorld.viewportgoal.getX(), generatorWorld.viewportgoal.getY()-Main.GU);
			}
			if(Main.KEYDOWN[Input.KEY_A]){
				generatorWorld.viewportgoal.set(generatorWorld.viewportgoal.getX()-Main.GU, generatorWorld.viewportgoal.getY());
			}
			if(Main.KEYDOWN[Input.KEY_S]){
				generatorWorld.viewportgoal.set(generatorWorld.viewportgoal.getX(), generatorWorld.viewportgoal.getY()+Main.GU);
			}
			if(Main.KEYDOWN[Input.KEY_D]){
				generatorWorld.viewportgoal.set(generatorWorld.viewportgoal.getX()+Main.GU, generatorWorld.viewportgoal.getY());
			}
			
			if(Main.KEYDOWN[Input.KEY_0]){
				save();
			}
			if(Main.KEYDOWN[Input.KEY_8]){
				load();
			}
			
			if (!changedBlock && Main.KEYDOWN[Input.KEY_Q]) {
				activeBlock = activeBlock.previous();
				changedBlock = true;
			}
			if (!changedBlock && Main.KEYDOWN[Input.KEY_E]) {
				activeBlock = activeBlock.next();
				changedBlock = true;
			}
			
			if (changedBlock && !Main.KEYDOWN[Input.KEY_Q] && !Main.KEYDOWN[Input.KEY_E]) {
				changedBlock = false;
			}
			
			if (Main.MOUSEDOWN[0]) {
				 generatorWorld.terrain.fillCell(Main.MOUSEX+generatorWorld.viewport.getX(),
				 Main.MOUSEY+generatorWorld.viewport.getY(),activeBlock);
			}
			if (Main.MOUSEDOWN[1]) {
				 generatorWorld.terrain.emptyCell(Main.MOUSEX+generatorWorld.viewport.getX(),
				 Main.MOUSEY+generatorWorld.viewport.getY());
			}

			generatorWorld.update(container, game, 1);
			timer -= 20;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		generatorWorld = new GameWorld("level.flt");
		activeBlock = Block.METAL;
			timer = 0;
	}
	
	public void enter(GameContainer container, StateBasedGame game){
		generatorWorld.reset();
	}
	
	public void leave(GameContainer container, StateBasedGame game){
	}

	@Override
	public int getID() {
		return 1337;
	}
}
