package no.ntnu.stud.flatcraft.quadtree;

import java.io.Serializable;

import no.ntnu.stud.flatcraft.Main;

import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.World;
import org.newdawn.slick.geom.Rectangle;

public class Node implements Serializable {
	private static final long serialVersionUID = 7854866124996621554L;

	boolean leaf;
	int level;
	Node parent;
	Node[] children;
	Body body;
	public Block type;
	Rectangle rect;
	World world;
	QuadTree tree; // handle back to the quadtree
	Rectangle physrect;

	public Node(int _level, QuadTree _tree, World _world) {
		world = _world;
		children = new Node[4];
		leaf = true;
		level = _level;
		type = Block.EMPTY; // just because
		tree = _tree;
		physrect = new Rectangle(0, 0, (float) (_tree.initialSize / (Math.pow(
				2, level))), (float) (_tree.initialSize / (Math.pow(2, level))));
		rect = new Rectangle(
				0,
				0,
				(float) (_tree.initialSize * Main.GULOL / (Math.pow(2, level))),
				(float) (_tree.initialSize * Main.GULOL / (Math.pow(2, level))));
	}

	public void physEnable() {
		body = new Body(new org.newdawn.fizzy.Rectangle(physrect.getWidth(),
				physrect.getHeight()), physrect.getX() + physrect.getWidth()
				* 0.5f, physrect.getY() + physrect.getHeight() * 0.5f, true);
		world.add(body);
		body.setUserData(this);
		body.setRestitution(0);
		body.setFriction(0.01f);

	}

	public void physDisable() {
		if (body != null) {
			world.remove(body);
			body = null;
		}
	}

	public void split() {
		leaf = false;
		physDisable();
		for (int i = 0; i < 4; i++) {
			children[i] = new Node(level + 1, tree, world);
			children[i].parent = this;
			children[i].type = type;
		}
		children[0].physrect.setLocation(physrect.getX(), physrect.getY());
		children[1].physrect.setLocation(physrect.getX() + physrect.getWidth()
				* 0.5f, physrect.getY());
		children[2].physrect.setLocation(physrect.getX(), physrect.getY()
				+ physrect.getHeight() * 0.5f);
		children[3].physrect.setLocation(physrect.getX() + physrect.getWidth()
				* 0.5f, physrect.getY() + physrect.getHeight() * 0.5f);

		children[0].rect.setLocation(rect.getX(), rect.getY());
		children[1].rect.setLocation(rect.getX() + rect.getWidth() * 0.5f,
				rect.getY());
		children[2].rect.setLocation(rect.getX(),
				rect.getY() + rect.getHeight() * 0.5f);
		children[3].rect.setLocation(rect.getX() + rect.getWidth() * 0.5f,
				rect.getY() + rect.getHeight() * 0.5f);

		for (int i = 0; i < 4; i++) {
			if (children[i].type == Block.EMPTY
					|| children[i].type == Block.WATER
					|| children[i].type == Block.ACID
					|| children[i].type == Block.GOAL) {
				children[i].physDisable();
			} else {
				children[i].physEnable();
			}
		}
	}

	public Node getLeaf(float x, float y) {
		if (leaf)
			return this;
		if (x >= physrect.getX() && x <= physrect.getX() + physrect.getWidth()
				&& y >= physrect.getY()
				&& y <= physrect.getY() + physrect.getHeight()) {
			if (x >= physrect.getX() + physrect.getWidth() * 0.5f
					&& y >= physrect.getY() + physrect.getHeight() * 0.5f) {
				return children[3].getLeaf(x, y);
			}
			if (x <= physrect.getX() + physrect.getWidth() * 0.5f
					&& y >= physrect.getY() + physrect.getHeight() * 0.5f) {
				return children[2].getLeaf(x, y);
			}
			if (x >= physrect.getX() + physrect.getWidth() * 0.5f
					&& y <= physrect.getY() + physrect.getHeight() * 0.5f) {
				return children[1].getLeaf(x, y);
			}
			return children[0].getLeaf(x, y);
		}
		return null;
	}

	public String toString() {
		String returnstring = "";
		if (leaf && type != Block.EMPTY) {
			returnstring = returnstring + "|" + physrect.getX() + ","
					+ physrect.getY() + "," + level + "," + type;

		}
		if (children[0] != null) {
			for (Node c : children) {
				returnstring = returnstring + c.toString();
			}
		}
		return returnstring;
	}
}