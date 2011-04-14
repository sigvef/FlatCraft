package no.ntnu.stud.flatcraft.entities;

import java.util.HashMap;

import no.ntnu.stud.flatcraft.Main;
import no.ntnu.stud.flatcraft.quadtree.Block;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Inventory {
	private Block activeElement;
	
	private HashMap<Block, Integer> inventory;
	
	public Inventory() {
		inventory = new HashMap<Block, Integer>();
		inventory.put(Block.ROCK, 99);
		inventory.put(Block.RUBBER, 99);
		inventory.put(Block.WATER, 99);
	}
	
	public void render(Graphics g) {
		g.pushTransform();
		
		g.translate((Main.SCREEN_W/2f) - 9 *Main.GULOL, Main.SCREEN_H - 3*Main.GU - 50);
//		g.scale(Main.GULOL, Main.GULOL);
		g.setColor(new Color(192, 192, 192, 120));
		g.fillRoundRect(0, 0, 20*Main.GULOL, 6*Main.GULOL, 15);
		
		g.setColor(Color.lightGray);
		g.fillRect(4*Main.GULOL, 1*Main.GULOL, 3*Main.GULOL, 3*Main.GULOL);
		
		g.setColor(Color.pink);
		g.fillRect(9*Main.GULOL, 1*Main.GULOL, 3*Main.GULOL, 3*Main.GULOL);
		
		g.setColor(new Color(0f, 0.6f, 1, 0.4f));
		g.fillRect(14*Main.GULOL, 1*Main.GULOL, 3*Main.GULOL, 3*Main.GULOL);
		
		g.setColor(Color.black);
		if (activeElement == Block.ROCK) {
			g.setColor(Color.white);
			g.drawString(inventory.get(Block.ROCK).toString(), 5*Main.GULOL, 3*Main.GULOL);
			g.setColor(Color.black);
		} else {
			g.drawString(inventory.get(Block.ROCK).toString(), 5*Main.GULOL, 3*Main.GULOL);
		}
		
		if (activeElement == Block.RUBBER) {
			g.setColor(Color.white);
			g.drawString(inventory.get(Block.RUBBER).toString(), 10*Main.GULOL, 3*Main.GULOL);
			g.setColor(Color.black);
		} else {
			g.drawString(inventory.get(Block.RUBBER).toString(), 10*Main.GULOL, 3*Main.GULOL);
		}
		
		if (activeElement == Block.WATER) {
			g.setColor(Color.white);
			g.drawString(inventory.get(Block.WATER).toString(), 15*Main.GULOL, 3*Main.GULOL);
			g.setColor(Color.black);
		} else {
			g.drawString(inventory.get(Block.WATER).toString(), 15*Main.GULOL, 3*Main.GULOL);
		}
		
		g.popTransform();		
	}
	
	public void next() {
		if (activeElement == null) {
			for (Block key : inventory.keySet()) {
				if (inventory.get(key) != 0) {
					activeElement = key;
					return;
				}
			}
		} else if (activeElement == Block.ROCK) {
			if (inventory.get(Block.RUBBER) != 0) {
				activeElement = Block.RUBBER;
			} else if (inventory.get(Block.WATER) != 0) {
				activeElement = Block.WATER;
			} else if (inventory.get(Block.ROCK) == 0) {
				activeElement = null;
			}
		} else if (activeElement == Block.RUBBER) {
			if (inventory.get(Block.WATER) != 0) {
				activeElement = Block.WATER;
			} else if (inventory.get(Block.ROCK) != 0) {
				activeElement = Block.ROCK;
			} else if (inventory.get(Block.RUBBER) == 0) {
				activeElement = null;
			}
		} else if (activeElement == Block.WATER) {
			if (inventory.get(Block.ROCK) != 0) {
				activeElement = Block.ROCK;
			} else if (inventory.get(Block.RUBBER) != 0) {
				activeElement = Block.RUBBER;
			} else if (inventory.get(Block.WATER) == 0) {
				activeElement = null;
			}
		}
	}
	
	public void prev() {
		int n = 0;
		for (int i : inventory.values()) {
			if (i > 0) {
				n++;
			}
		}
		//UGLY HACK! ;3
		if (n == 3) {
			next();
		}
		next();
	}
	
	public Block peek() {
		if (activeElement == null || inventory.get(activeElement) == 0) {
			prev();
			return null;
		}
		
		return activeElement;
	}
	
	public Block pop() {
		if (activeElement == null || inventory.get(activeElement) == 0) {
			prev();
			return null;
		}
		
		inventory.put(activeElement, inventory.get(activeElement) - 1);
		
		return activeElement;
	}
	
	public void push(Block block) {
		inventory.put(block, inventory.get(block) + 1);
		
		if (activeElement == null) {
			next();
		}
	}
	
	public Block getCurrent() {
		return activeElement;
	}
}