package no.ntnu.stud.flatcraft.entities;

import java.io.File;
import java.util.ArrayList;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Main;
import no.ntnu.stud.flatcraft.quadtree.Block;
import no.ntnu.stud.flatcraft.quadtree.Node;

import org.newdawn.fizzy.Body;
import org.newdawn.slick.Color;
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
	private boolean dead;

	public Player(GameWorld gw, float _x, float _y, float _width,
			float _height, float _mass) {
		dead = true;
		startPosition = new Vector2f(_x, _y);
		character = new Character(gw, _x, _y, _width / 2, _height / 2, _mass);
		respawn();
		fireVector = new Vector2f(0, 0);
		inventory = new Inventory();
	}

	public void reset() {
		character
				.spawn(new Vector2f(startPosition.getX(), startPosition.getY()));
	}

	public void render(Graphics g) {
		g.pushTransform();
		g.translate(-character.gameworld.viewport.getX()*Main.GULOL,
				-character.gameworld.viewport.getY()*Main.GULOL);
		g.setColor(new Color(0,0,0,0.5f));
//		g.draw(new Line(character.physrect.getCenterX()*Main.GULOL, character.physrect
//				.getCenterY()*Main.GULOL, character.physrect.getCenterX()*Main.GULOL
//				+ fireVector.getX()*Main.GULOL, character.physrect.getCenterY()*Main.GULOL
//				+ fireVector.getY()*Main.GULOL));
//		
		
		float boxSize = (float) (character.gameworld.terrain.initialSize/Math.pow(2, character.gameworld.terrain.getMaxDepth()));
		float xpos = 
			fireVector.getX()
			+ character.physrect.getCenterX();
		float ypos = 
			fireVector.getY()
			+ character.physrect.getCenterY();
		
		xpos = boxSize*(int)(xpos/boxSize);
		ypos = boxSize*(int)(ypos/boxSize);
		
		g.drawRect(xpos*Main.GULOL,ypos*Main.GULOL,
				boxSize*Main.GULOL,boxSize*Main.GULOL);
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

	public void die() {
		System.out.println("DIE DIE DIE");
		Main.KEYDOWN[Input.KEY_ESCAPE] = true;
		dead = true;
	}
	public void win() {
		System.out.println("WIN WIN WIN");
		Main.KEYDOWN[Input.KEY_ESCAPE] = true;
		dead = true;
	}
	
	
	public void update(GameContainer container, StateBasedGame game, int delta) {

		if (character.touchingAcid) {
			die();
		}
		
		if (character.touchingGoal) {
			win();
		}

		if (Main.KEYDOWN[Input.KEY_Q] && !blockSelected) {
			inventory.prev();
			blockSelected = true;
		}
		if (Main.KEYDOWN[Input.KEY_E] && !blockSelected) {
			inventory.next();
			blockSelected = true;
		}

		if (!Main.KEYDOWN[Input.KEY_Q] && !Main.KEYDOWN[Input.KEY_E]
				&& blockSelected) {
			blockSelected = false;
		}

		if (Main.KEYDOWN[Input.KEY_UP] || Main.KEYDOWN[Input.KEY_W]
				|| Main.KEYDOWN[Input.KEY_SPACE]) {
			if (character.grounded) {
				if (character.gameworld.terrain.getLeaf(
						character.physrect.getX() + Main.GU,
						character.physrect.getY() + 5.1f * Main.GU).type == Block.RUBBER
						|| character.gameworld.terrain.getLeaf(
								character.physrect.getX() + 4 * Main.GU,
								character.physrect.getY() + 5.1f * Main.GU).type == Block.RUBBER) {
					character.applyForce(0, -Main.UPDATES * 20000f);
				} else {
					character.applyForce(0, -Main.UPDATES * 10000f);
				}
			}
			if (character.swimming) {
				character.applyForce(0, -Main.UPDATES * 2500f);
			}
		}
		if (Main.KEYDOWN[Input.KEY_LEFT] || Main.KEYDOWN[Input.KEY_A]) {
			character.applyForce(-Main.UPDATES * 500, 0);
		}
		if (Main.KEYDOWN[Input.KEY_RIGHT] || Main.KEYDOWN[Input.KEY_D]) {
			character.applyForce(Main.UPDATES * 500, 0);
		}
		float x = character.gameworld.viewport.getX() + Main.MOUSEX
		/ Main.GULOL - character.physrect.getCenterX();
		float y = character.gameworld.viewport.getY() + Main.MOUSEY
		/ Main.GULOL - character.physrect.getCenterY();
		fireVector.set(x, y);
		fireVector.normalise();
		fireVector.scale(6);

		if (Main.MOUSEDOWN[0]) {

			Block out = inventory.peek();

			if (out != null
					&& character.gameworld.terrain
							.getLeaf(character.physrect.getCenterX()
									+ fireVector.getX(),
									character.physrect.getCenterY()
											+ fireVector.getY()).type == Block.EMPTY) {
				inventory.pop();
				character.gameworld.terrain.fillCell(
						character.physrect.getCenterX() + fireVector.getX(),
						character.physrect.getCenterY() + fireVector.getY(),
						out);
			}
		}
		if (Main.MOUSEDOWN[1]) {


			Node block = character.gameworld.terrain.getLeaf(
					character.physrect.getCenterX() + fireVector.getX(),
					character.physrect.getCenterY() + fireVector.getY());
			if (block != null) {
				switch (block.type) {
				case ROCK:
				case RUBBER:
				case WATER:
					inventory.push(block.type);
				case EARTH:
					character.gameworld.terrain
							.emptyCell(character.physrect.getCenterX()
									+ fireVector.getX(),
									character.physrect.getCenterY()
											+ fireVector.getY());
					break;
				default:
					break;
				}
			}
		}

		if (character.physrect.getMaxY() > 44 + character.gameworld
				.getViewportPosition().getY()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.gameworld.getViewportPositionGoal().getX(),
					character.physrect.getMaxY() - 44));
		}
		if (character.physrect.getMinY() < 28 + character.gameworld
				.getViewportPosition().getY()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.gameworld.getViewportPositionGoal().getX(),
					character.physrect.getMinY() - 28));
		}
		if (character.physrect.getMaxX() > 80 + character.gameworld
				.getViewportPosition().getX()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.physrect.getMaxX() - 80, character.gameworld
							.getViewportPositionGoal().getY()));
		}
		if (character.physrect.getMinX() < 48 + character.gameworld
				.getViewportPosition().getX()) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.physrect.getMinX() - 48, character.gameworld
							.getViewportPositionGoal().getY()));
		}

		if (character.grounded) {
			character.gameworld.setViewportPositionGoal(new Vector2f(
					character.gameworld.viewportgoal.getX(), character.body
							.getY() - Main.GU * 48));
		}
	}

	public Character getCharacter() {
		return character;
	}

	public Body getBody() {
		return character.body;
	}

	public boolean isDead() {
		return dead;
	}
}
