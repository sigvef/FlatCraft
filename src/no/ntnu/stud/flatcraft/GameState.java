/*
 * FlatCraft - ServerState.java
 * 
 */

package no.ntnu.stud.flatcraft;

import java.io.IOException;

import no.ntnu.stud.flatcraft.entities.Player;
import no.ntnu.stud.flatcraft.quadtree.Block;
import no.ntnu.stud.flatcraft.shader.Shader;
import no.ntnu.stud.flatcraft.messagesystem.Message;
import no.ntnu.stud.flatcraft.music.MusicPlayer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.pbuffer.FBOGraphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState {

	private GameWorld gameworld;
	private Image buffer;
	private Image buffer2;
	
	private MusicPlayer mp;

	private FBOGraphics fbog;
	private FBOGraphics fbog2;

	private Shader horblur;
	private Shader vertblur;
	Player player;
	int timer;
	private BackgroundParticles backgroundparticles;

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		long profileTime = System.currentTimeMillis();

		fbog.setWorldClip(0, 0, Main.SCREEN_W, Main.SCREEN_H);
		fbog.pushTransform();

		gameworld.render(fbog);
		Main.MS.render(fbog);
		fbog.popTransform();

		g.pushTransform();
		g.drawImage(buffer, 0, 0);
		if (Main.SETTINGS.getBloom()) {
			vertblur.startShader();
			fbog2.drawImage(buffer, 0, 0);
			Shader.forceFixedShader();
			g.setDrawMode(Graphics.MODE_ADD);
			horblur.startShader();
			g.drawImage(buffer2, 0, 0);
			Shader.forceFixedShader();
		}
		g.setDrawMode(Graphics.MODE_NORMAL);
		player.render(g);
		g.popTransform();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		timer += delta;
		while (timer > 20) {
				//play the level
				if (Main.KEYDOWN[Input.KEY_ESCAPE]) {
					game.enterState(123); // go back to MainMenuState
				}
				if (Main.SETTINGS.getSound()) {
					mp.update(20);
				}
				player.update(container, game, 20);
				gameworld.update(container, game, 20);
				Main.MS.update(20);
				backgroundparticles.update(20);
			timer -= 20;
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		try {
			buffer = new Image(Main.SCREEN_W, Main.SCREEN_H);
			buffer2 = new Image(Main.SCREEN_W, Main.SCREEN_H);
			fbog = new FBOGraphics(buffer);
			fbog2 = new FBOGraphics(buffer2);
			if(Main.SETTINGS.getBloom()){
			horblur = Shader.makeShader("res/shaders/horblur.vrt",
					"res/shaders/horblur.frg");
			vertblur = Shader.makeShader("res/shaders/vertblur.vrt",
					"res/shaders/vertblur.frg");
			}
			if (Main.SETTINGS.getSound()) {
				mp = new MusicPlayer();
				mp.addMusic("res/sounds/flatcraft2.ogg");
				mp.addMusic("res/sounds/flatcraft5.ogg");
				mp.addMusic("res/sounds/flatcraft8.ogg");
				
				mp.addAmbientNoise("res/sounds/amb1.ogg");
				mp.addAmbientNoise("res/sounds/amb2.ogg");
				mp.addAmbientNoise("res/sounds/amb3.ogg");
				mp.addAmbientNoise("res/sounds/amb4.ogg");
				mp.addAmbientNoise("res/sounds/amb5.ogg");
				mp.addAmbientNoise("res/sounds/amb6.ogg");
				mp.addAmbientNoise("res/sounds/amb7.ogg");
				mp.addAmbientMusic("res/sounds/ambientloop.ogg");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		Main.KEYDOWN[Input.KEY_ESCAPE] = false;
		Main.KEYDOWN[Input.KEY_ENTER] = false;
		Main.KEYDOWN[Input.KEY_SPACE] = false;
		try {
			gameworld = new GameWorld("res/levels/"+Main.LEVEL+".flt"); //TIHIHIHI n� bruker vi globals over en lav sko
		} catch (SlickException e) {
			e.printStackTrace();
		}
		backgroundparticles = new BackgroundParticles();
		Vector2f startPos = gameworld.terrain.findBlockPosition(Block.START);
		if (startPos != null) {
			gameworld.terrain.emptyCell(startPos.getX(), startPos.getY());
		} else {
			startPos = new Vector2f(Main.GU, Main.GU);
		}
		player = new Player(gameworld, startPos.getX(), startPos.getY(),
				Main.GU * 5, Main.GU * 5, 4);
		gameworld.add(player.getCharacter());
		timer = 0;
		gameworld.reset();
		player.reset();
		Main.MS.addMessage((new Message(gameworld.getLevelName(), 3000)));

		if (Main.SETTINGS.getSound()) {
			mp.startMusic(true);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		gameworld.leave();
		if (Main.SETTINGS.getSound()) {
			mp.stopMusic();
		}

	}

	@Override
	public int getID() {
		return 1;
	}
}
