package no.ntnu.stud.flatcraft.quadtree;

import java.io.Serializable;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;
import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.geom.Rectangle;

public class Node implements Serializable {
	private static final long serialVersionUID = 7854866124996621554L;
	
	boolean leaf;
	int level;
	Node parent;
	Node[] children;
	StaticBody body;
	public Block type;
	Rectangle rect;
	World world;
	QuadTree tree; //handle back to the quadtree
	
	public Node(int _level, QuadTree _tree, World _world) {
		world = _world;
		children = new Node[4];
		leaf = true;
		level = _level;
		type = Block.EMPTY; // just because
		tree = _tree;
		body = new StaticBody(new Box((float)(_tree.initialSize/(Math.pow(2, level))),(float)(_tree.initialSize/(Math.pow(2, level)))));
		body.setUserData(this);
		body.setRestitution(0);
		body.setFriction(Main.mu);
		body.setRotatable(false);
		body.setPosition(0,0);
		body.setEnabled(false);
		world.add(body);
		rect = new Rectangle(0,0,(float)(_tree.initialSize/(Math.pow(2, level))),(float)(_tree.initialSize/(Math.pow(2, level))));
	}

	public void split() {
		leaf = false;
		body.setEnabled(false);
		world.remove(body);
		for (int i = 0; i < 4; i++) {
			children[i] = new Node(level + 1, tree, world);
			children[i].parent = this;
			children[i].type = type;
			if(children[i].type == Block.EMPTY || children[i].type == Block.WATER) children[i].body.setEnabled(false);
			else children[i].body.setEnabled(true);
			if(children[i].type == Block.RUBBER) children[i].body.setRestitution(1);
			else children[i].body.setRestitution(0);
		}
		children[0].body.setPosition(rect.getX(), rect.getY());
		children[1].body.setPosition(rect.getX()+rect.getWidth()*0.5f, rect.getY());
		children[2].body.setPosition(rect.getX(), rect.getY()+rect.getHeight()*0.5f);
		children[3].body.setPosition(rect.getX()+rect.getWidth()*0.5f, rect.getY()+rect.getHeight()*0.5f);
				
		children[0].rect.setLocation(rect.getX(), rect.getY());
		children[1].rect.setLocation(rect.getX()+rect.getWidth()*0.5f, rect.getY());
		children[2].rect.setLocation(rect.getX(), rect.getY()+rect.getHeight()*0.5f);
		children[3].rect.setLocation(rect.getX()+rect.getWidth()*0.5f, rect.getY()+rect.getHeight()*0.5f);
		
		for(Node child : children){
			child.body.adjustPosition(new Vector2f(child.rect.getWidth()*0.5f,child.rect.getHeight()*0.5f));
		}
	}
	
	public Node getLeaf(float x, float y) {
		// System.out.println("Entered a node. level = "+level+" rect = "+rect.getX()+" "+rect.getY()+" x = "+x+" y = "+y+" size = "+rect.getWidth());
		if (leaf)
			return this;
		if (x >= rect.getX() && x <= rect.getX() + rect.getWidth() && y >= rect.getY() && y <= rect.getY() + rect.getHeight()) {
			if (x >= rect.getX() + rect.getWidth() * 0.5f && y >= rect.getY() + rect.getHeight() * 0.5f) {
				return children[3].getLeaf(x, y);
			}
			if (x <= rect.getX() + rect.getWidth() * 0.5f && y >= rect.getY() + rect.getHeight() * 0.5f) {
				return children[2].getLeaf(x, y);
			}
			if (x >= rect.getX() + rect.getWidth() * 0.5f && y <= rect.getY() + rect.getHeight() * 0.5f) {
				return children[1].getLeaf(x, y);
			}
			return children[0].getLeaf(x, y);
		}
		return null;
	}
	
	public String toString(){
		//return "[NODE | level: "+level+"; x: "+body.getPosition().getX()+"; y: "+body.getPosition().getY()+"]";
		String returnstring = "";
		//returnstring = returnstring + "{"+body.getPosition().getX()+","+body.getPosition().getY()+","+type;
		if(leaf && type != Block.EMPTY){
			returnstring = returnstring + "|"+body.getPosition().getX()+","+body.getPosition().getY()+","+level+","+type;
		}
		if(children[0] != null){
			for(Node c : children){
				returnstring = returnstring +c.toString();
			}
		}
		return returnstring;
	}
}