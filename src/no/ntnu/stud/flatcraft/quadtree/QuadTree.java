package no.ntnu.stud.flatcraft.quadtree;

import java.io.*;
import java.util.ArrayList;

import net.phys2d.raw.World;
import no.ntnu.stud.flatcraft.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class QuadTree implements Serializable {
	private static final long serialVersionUID = -8798887499766580371L;

	Node startNode;
	public float initialSize;
	int depth;
	int maxDepth;
	int numberOfNodes;
	int numberOfLeaves;
	int dummy;
	float x;
	float y;
	ArrayList<Node> waternodes;
	public World world;

	public QuadTree(float _x, float _y, float _size, int _maxDepth, World _world)
			throws SlickException {
		x = _x;
		y = _y;
		initialSize = _size;
		depth = 0;
		maxDepth = _maxDepth;
		numberOfNodes = 1;
		numberOfLeaves = 1;
		world = _world;
		waternodes = new ArrayList<Node>();
		startNode = new Node(0, this, world);
//		fillCell(105, 605, Block.METAL);
	}

	public void update() {
		
		for (int i = 0; i < waternodes.size(); i++) {
			float x = waternodes.get(i).rect.getX();
			float y = waternodes.get(i).rect.getY();
			float width = waternodes.get(i).rect.getWidth();
			float height = waternodes.get(i).rect.getHeight();
			Node leaf = getLeaf(x, y + height);
			if (leaf.type == Block.EMPTY) {
				fillCell(x, y + height, Block.WATER);
				emptyCell(x, y);
			} else if (getLeaf(x + width, y).type == Block.EMPTY) {
				fillCell(x + width, y, Block.WATER);
				emptyCell(x, y);
			} else if (getLeaf(x - width, y).type == Block.EMPTY) {
				fillCell(x - width, y, Block.WATER);
				emptyCell(x, y);
			}
		}
	}

	public void render(Graphics g, Rectangle viewport) {
		dummy = 0;
		depth = 0;
		g.pushTransform();
		g.translate(-viewport.getX(), -viewport.getY());
		// enter the recursive render traversing
		g.setDrawMode(g.MODE_NORMAL);
		traverseTree(startNode, g, viewport);
		g.popTransform();
	}

	// -----------------------------------
	// the recursion needed for rendering
	// -----------------------------------
	public void traverseTree(Node node, Graphics g, Rectangle viewport) {
		depth++;
		dummy++;
		// check to see if node is in the viewport
		 if (viewport.contains(node.rect) || viewport.intersects(node.rect)) {

			// draw the box if we reached a leaf...
			if ((node.leaf) || (depth > maxDepth)) {
				if (node.type != Block.EMPTY) {
					switch (node.type) {
					case METAL:
						g.setColor(Color.darkGray);
						break;
					case EARTH:
						g.setColor(Color.orange);
						break;
					case ROCK:
						g.setColor(Color.lightGray);
						break;
					case RUBBER:
						g.setColor(Color.pink);
						break;
					case WATER:
						g.setColor(new Color(0f, 0.6f, 1, 0.4f));
						break;
					case ACID:
						g.setColor(new Color(0f, 1f, 0f, 0.4f));
						break;
					case GOAL:
						g.setColor(Color.white);
					}
					g.fill(node.rect);
					// draw outline of node - this is for debugging
					g.setColor(Color.white);
					if (Main.DEBUG) {
						g.draw(node.rect);
					}

				}
			}
			// ...or go deeper until we find a leaf to draw
			else {
				// draw outline of node - this is for debugging
				g.setColor(Color.darkGray);
				if (Main.DEBUG)
					g.draw(node.rect);
				for (int i = 0; i < 4; i++) {
					traverseTree(node.children[i], g, viewport);
				}
			}
		}
		depth--;
	}

	public void trySimplify(Node n) {
		if (n != startNode) {
			n = n.parent;
			if (n.children[0].leaf && n.children[1].leaf && n.children[2].leaf
					&& n.children[3].leaf
					&& n.children[0].type == n.children[1].type
					&& n.children[1].type == n.children[2].type
					&& n.children[2].type == n.children[3].type
					&& n.children[0].type != Block.WATER) {
				n.type = n.children[0].type;
				for (int i = 0; i < 4; i++) {
					n.children[i].body.setEnabled(false);
					world.remove(n.children[i].body);
					if (waternodes.contains(n.children[i]))
						waternodes.remove(n.children[i]);
					n.children[i] = null;
				}
				numberOfLeaves -= 3;
				numberOfNodes -= 4;
				n.leaf = true;
			if (n.type == Block.EMPTY || n.type == Block.WATER)
				n.body.setEnabled(false);
			else
				n.body.setEnabled(true);
			if (n.type == Block.WATER) {
				waternodes.add(n);
			}
			if(n.type == Block.RUBBER){
				n.body.setRestitution(1);
			}else 
				n.body.setRestitution(0);
			
				trySimplify(n);
			}
		}
	}

	
	public void fillCell(float _x, float _y, Block _type){
		fillCell(_x, _y, _type, maxDepth);
	}
	
	public void fillCell(float _x, float _y, Block _type, int level) {
		Node temp = getLeaf(_x, _y);
		if (temp != null) {
			if (temp.type != _type && temp.type == Block.EMPTY) {
				while (temp.level < level) {
					temp.split();
					temp = temp.getLeaf(_x, _y);
					numberOfLeaves += 3;
					numberOfNodes += 4;
				}
				if (temp.type == Block.WATER)
					waternodes.remove(temp);
				temp.type = _type;
				trySimplify(temp);
				if (temp.type == Block.WATER)
					waternodes.add(temp);
				if (temp.type == Block.EMPTY || temp.type == Block.WATER)
					temp.body.setEnabled(false);
				else
					temp.body.setEnabled(true);

			}
		}
	}

	public void emptyCell(float _x, float _y) {
		Node temp = getLeaf(_x, _y);
		if (temp != null) {
			if (temp.type != Block.EMPTY) {
				while (temp.level < maxDepth) {
					temp.split();
					temp = temp.getLeaf(_x, _y);
					numberOfLeaves += 3;
					numberOfNodes += 4;
				}

				if (temp.type == Block.WATER)
					waternodes.remove(temp);
				temp.type = Block.EMPTY;
					temp.body.setEnabled(false);
				trySimplify(temp);
			}
		}
	}

	public Node getLeaf(float _x, float _y) {
		return startNode.getLeaf(_x, _y);
	}

	public Node getLeaf(Vector2f v) {
		return startNode.getLeaf(v.getX(), v.getY());
	}

	// --------------------------------
	// toString
	// --------------------------------
	public String toString() {
		//return ("Quadtree: nodes:" + numberOfNodes + ", leaves:" + numberOfLeaves);
		return startNode.toString();
	}
	
	public int getNumberOfNodes(){
		return numberOfNodes;
	}
	
	public int getNumberOfLeaves(){
		return numberOfLeaves;
	}
	
}