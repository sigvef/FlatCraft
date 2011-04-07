package no.ntnu.stud.flatcraft.quadtree;

import java.io.Serializable;
import java.util.ArrayList;

import no.ntnu.stud.flatcraft.Hack;
import no.ntnu.stud.flatcraft.entities.GameEntity;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Node implements Serializable {
	private static final long serialVersionUID = 7854866124996621554L;
	
	boolean leaf;
	int level;
	Node parent;
	Node[] children;
	Rectangle rect;
	Block type;
	QuadTree tree; //handle back to the quadtree
	
	public Node(int _level, QuadTree _tree) {
		children = new Node[4];
		leaf = true;
		level = _level;
		type = Block.EMPTY; // just because
		tree = _tree;
	}

	public void split() {
		leaf = false;
		for (int i = 0; i < 4; i++) {
			children[i] = new Node(level + 1, tree);
			children[i].parent = this;
			children[i].type = type;
		}
		children[0].rect = new Rectangle(rect.getX(), rect.getY(), rect.getWidth() * 0.5f, rect.getHeight() * 0.5f);
		children[1].rect = new Rectangle(rect.getX() + rect.getWidth() * 0.5f, rect.getY(), rect.getWidth() * 0.5f, rect.getHeight() * 0.5f);
		children[2].rect = new Rectangle(rect.getX(), rect.getY() + rect.getHeight() * 0.5f, rect.getWidth() * 0.5f, rect.getHeight() * 0.5f);
		children[3].rect = new Rectangle(rect.getX() + rect.getWidth() * 0.5f, rect.getY() + rect.getHeight() * 0.5f, rect.getWidth() * 0.5f, rect.getHeight() * 0.5f);
	}
	
	public Node getContainingNode(GameEntity ge){
		float x = ge.boundingBox.getX();
		float y = ge.boundingBox.getY();
		for(Polygon forward : ge.forwards){
			if(forward.getMinX() < x) x= forward.getMinX();
			if(forward.getMinY() < y) y= forward.getMinY();
		}
		if(!(Hack.contains(rect, ge.boundingBox)&&Hack.contains(rect, ge.forwards[0])&&
				Hack.contains(rect, ge.forwards[1]) && Hack.contains(rect,ge.forwards[2]) && Hack.contains(rect, ge.forwards[3]))){
			return parent;
		}else if(leaf || level == tree.maxDepth){
			return this;
		}else{
			if (x >= rect.getX() + rect.getWidth() * 0.5f && y >= rect.getY() + rect.getHeight() * 0.5f) {
				return children[3].getContainingNode(ge);
			}
			if (x <= rect.getX() + rect.getWidth() * 0.5f && y >= rect.getY() + rect.getHeight() * 0.5f) {
				return children[2].getContainingNode(ge);
			}
			if (x >= rect.getX() + rect.getWidth() * 0.5f && y <= rect.getY() + rect.getHeight() * 0.5f) {
				return children[1].getContainingNode(ge);
			}
			return children[0].getContainingNode(ge);
		}
	}
	
	public void getAllLeaves(ArrayList<Node> list){
		if(leaf || level == tree.maxDepth) list.add(this);
		else for(Node n : children){
			n.getAllLeaves(list);
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
		return "[NODE | level: "+level+"; x: "+rect.getX()+"; y: "+rect.getY()+"]";
	}
}