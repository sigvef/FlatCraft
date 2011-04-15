package no.ntnu.stud.flatcraft.entities;


import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;
import no.ntnu.stud.flatcraft.quadtree.Block;

import org.newdawn.fizzy.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	Character character;
	Vector2f fireVector;
	
	Inventory inventory;
	
	private Vector2f startPosition;
	
	private boolean blockSelected = false;

	public Player(GameWorld gw, float _x, float _y, float _width,
			float _height, float _mass) {
		startPosition = new Vector2f(_x, _y);
		character = new Character(gw, _x, _y, _width, _height, _mass);
		respawn();
		fireVector = new Vector2f(0, 0);
		inventory = new Inventory();
	}

	public void reset() {
		character.spawn(new Vector2f(startPosition.getX(), startPosition.getY()));
	}

	public void render(Graphics g) {
		g.pushTransform();
		g.translate(-character.gameworld.viewport.getX(),
				-character.gameworld.viewport.getY());
		g.draw(new Line(character.physrect.getCenterX(),
				character.physrect.getCenterY(), character.physrect
						.getCenterX() + fireVector.getX(),
				character.physrect.getCenterY() + fireVector.getY()));
		g.popTransform();
		
		inventory.render(g);
	}

	public void respawn() {
		character.spawn(new Vector2f(Main.GU * 10, Main.GU * 10)); // Test
																	// numbers,
																	// should
																	// ask for
																	// spawn
																	// locations
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		if (Main.KEYDOWN[Input.KEY_Q] && !blockSelected) {
			inventory.prev();
			blockSelected = true;
		}
		if (Main.KEYDOWN[Input.KEY_E] && !blockSelected) {
			inventory.next();
			blockSelected = true;
		}
		
		if (!Main.KEYDOWN[Input.KEY_Q] && !Main.KEYDOWN[Input.KEY_E] && blockSelected) {
			blockSelected = false;
		}

		if (Main.KEYDOWN[Input.KEY_UP] || Main.KEYDOWN[Input.KEY_W]
				|| Main.KEYDOWN[Input.KEY_SPACE]) {
			if (character.grounded) {
				if (character.gameworld.terrain.getLeaf(character.physrect.getX()+Main.GU, character.physrect.getY() + 5.1f*Main.GU).type == Block.RUBBER || character.gameworld.terrain.getLeaf(character.physrect.getX()+4*Main.GU, character.physrect.getY() + 5.1f*Main.GU).type == Block.RUBBER) {
					character.applyForce(0, -Main.UPDATES * 80000f);
				} else {
					character.applyForce(0, -Main.UPDATES * 40000f);
				}
			}
			if(character.swimming){
				character.applyForce(0, -Main.UPDATES * 10000f);
			}
		}	
		if (Main.KEYDOWN[Input.KEY_LEFT] || Main.KEYDOWN[Input.KEY_A]) {
			character.applyForce(-Main.UPDATES*1000, 0);
		}
		if (Main.KEYDOWN[Input.KEY_RIGHT] || Main.KEYDOWN[Input.KEY_D]) {
			character.applyForce(Main.UPDATES*1000, 0);
		}
//		if (Main.KEYDOWN[Input.KEY_DOWN] || Main.KEYDOWN[Input.KEY_S]) {
//			character.body.addForce((new Vector2f(0, Main.GU * 1000)));
//		}
//		if (Main.KEYDOWN[Input.KEY_UP] || Main.KEYDOWN[Input.KEY_W]) {
//			character.body.addForce((new Vector2f(0, -Main.GU * 1000)));
//		}

		if (Main.MOUSEDOWN[0]) {
			// character.gameworld.terrain.fillCell(Main.MOUSEX+character.gameworld.viewport.getX(),
			// Main.MOUSEY+character.gameworld.viewport.getY(),activeBlock);
			float x = character.gameworld.viewport.getX()+Main.MOUSEX/Main.GULOL-character.physrect.getCenterX();
			float y = character.gameworld.viewport.getY()+Main.MOUSEY/Main.GULOL-character.physrect.getCenterY();
			
			Block out = inventory.peek();	
			fireVector.set(x, y);
			fireVector.normalise();
			fireVector.scale(6);
			
			if (out != null && character.gameworld.terrain.getLeaf(character.physrect.getCenterX() + fireVector.getX(),
					character.physrect.getCenterY() + fireVector.getY()).type == Block.EMPTY) {
				inventory.pop();
				character.gameworld.terrain.fillCell(
						character.physrect.getCenterX() + fireVector.getX(),
						character.physrect.getCenterY() + fireVector.getY(),
						out);
			}
		}
		if (Main.MOUSEDOWN[1]) {

			float x = character.gameworld.viewport.getX()+Main.MOUSEX/Main.GULOL-character.physrect.getCenterX();
			float y = character.gameworld.viewport.getY()+Main.MOUSEY/Main.GULOL-character.physrect.getCenterY();
			
			fireVector.set(x,y);
			fireVector.normalise();
			fireVector.scale(Main.GU * 6);
			
			switch (character.gameworld.terrain.getLeaf(character.physrect.getCenterX() + fireVector.getX(),
					character.physrect.getCenterY() + fireVector.getY()).type) {
				case ROCK:
				case RUBBER:
				case WATER:
					inventory.push(character.gameworld.terrain.getLeaf(character.physrect.getCenterX() + fireVector.getX(),
					character.physrect.getCenterY() + fireVector.getY()).type);
				case EARTH:
					character.gameworld.terrain.emptyCell(
							character.physrect.getCenterX() + fireVector.getX(),
							character.physrect.getCenterY() + fireVector.getY());
					break;
				default:
					break;
			}
		}

		if (character.physrect.getMaxY() > 44
				+ character.gameworld.getViewportPosition().getY()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.gameworld.getViewportPosition().getX(),
					character.physrect.getMaxY() - 44));
		}
		if (character.physrect.getMinY() < 28
				+ character.gameworld.getViewportPosition().getY()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.gameworld.getViewportPosition().getX(),
					character.physrect.getMinY() - 28));
		}
		if (character.physrect.getMaxX() > 80
				+ character.gameworld.getViewportPosition().getX()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.physrect.getMaxX() - 80,
					character.gameworld.getViewportPosition().getY()));
		}
		if (character.physrect.getMinX() < 48
				+ character.gameworld.getViewportPosition().getX()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.physrect.getMinX() - 48,
					character.gameworld.getViewportPosition().getY()));
		}

		if (character.grounded) {
			// tween this
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.gameworld.viewportgoal.getX(),
					character.body.getY() - Main.GU * 48));
		}
	}

	public Character getCharacter() {
		return character;
	}

	public Body getBody() {
		return character.body;
	}

}
