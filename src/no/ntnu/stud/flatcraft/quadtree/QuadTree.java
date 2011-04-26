package no.ntnu.stud.flatcraft.quadtree;

import java.io.*;
import java.util.ArrayList;

import no.ntnu.stud.flatcraft.GameWorld;
//import no.ntnu.stud.flatcraft.Hack;
import no.ntnu.stud.flatcraft.Main;

import org.lwjgl.opengl.GL11;
import org.newdawn.fizzy.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.SlickCallable;

public class QuadTree implements Serializable {
	private static final long serialVersionUID = -8798887499766580371L;

	Node startNode;
	public float initialSize;
	int depth;
	private int maxDepth;
	int numberOfNodes;
	int numberOfLeaves;

	int nodesVisited = 0;
	int dummy;
	float x;
	float y;
	ArrayList<Node> waternodes;
	ArrayList<Node> acidnodes;
	public World world;
	public GameWorld gameworld;

	public QuadTree(float _x, float _y, float _size, int _maxDepth,
			World _world, GameWorld _gameworld) throws SlickException {
		gameworld = _gameworld;
		x = _x;
		y = _y;
		initialSize = _size;
		depth = 0;
		setMaxDepth(_maxDepth);
		numberOfNodes = 1;
		numberOfLeaves = 1;
		world = _world;
		waternodes = new ArrayList<Node>();
		acidnodes = new ArrayList<Node>();
		startNode = new Node(0, this, world);
		// fillCell(105, 605, Block.METAL);
	}

	public void update() {

		for (int i = 0; i < waternodes.size(); i++) {
			float x = waternodes.get(i).physrect.getX();
			float y = waternodes.get(i).physrect.getY();
			float width = waternodes.get(i).physrect.getWidth();
			float height = waternodes.get(i).physrect.getHeight();
			Node leaf = getLeaf(x, y + height);
			if (leaf != null) {
				if (leaf.type == Block.EMPTY) {
					fillCell(x, y + height, Block.WATER);
					emptyCell(x, y);
					continue;
				}
			}
			leaf = getLeaf(x + width, y);
			if (leaf != null) {
				if (leaf.type == Block.EMPTY) {
					fillCell(x + width, y, Block.WATER);
					emptyCell(x, y);
					continue;
				}
			}
			leaf = getLeaf(x - width, y);
			if (leaf != null) {
				if (leaf.type == Block.EMPTY) {
					fillCell(x - width, y, Block.WATER);
					emptyCell(x, y);
					continue;
				}
			}
		}
		for (int i = 0; i < acidnodes.size(); i++) {
			float x = acidnodes.get(i).physrect.getX();
			float y = acidnodes.get(i).physrect.getY();
			float width = acidnodes.get(i).physrect.getWidth();
			float height = acidnodes.get(i).physrect.getHeight();
			Node leaf = getLeaf(x, y + height);
			if (leaf != null) {
				if (leaf.type == Block.EMPTY) {
					fillCell(x, y + height, Block.ACID);
					emptyCell(x, y);
					continue;
				}
			}
			leaf = getLeaf(x + width, y);
			if (leaf != null) {
				if (leaf.type == Block.EMPTY) {
					fillCell(x + width, y, Block.ACID);
					emptyCell(x, y);
					continue;
				}
			}
			leaf = getLeaf(x - width, y);
			if (leaf != null) {
				if (leaf.type == Block.EMPTY) {
					fillCell(x - width, y, Block.ACID);
					emptyCell(x, y);
					continue;
				}
			}
		}
	}

	public void render(Graphics g, Rectangle viewport) {
		dummy = 0;
		depth = 0;

		nodesVisited = 0;
		g.pushTransform();
		g.translate(-gameworld.viewport.getX() * Main.GULOL,
				-gameworld.viewport.getY() * Main.GULOL);
		g.scale(Main.GULOL, Main.GULOL);
		// enter the recursive render traversing
		g.setDrawMode(Graphics.MODE_NORMAL);
		traverseTree(startNode, g, viewport);
		System.out.println("Nodesvisited: " + nodesVisited);
		g.popTransform();
	}

	// -----------------------------------
	// the recursion needed for rendering
	// -----------------------------------
	public void traverseTree(Node node, Graphics g, Rectangle viewport) {
		depth++;
		dummy++;
		// check to see if node is in the viewport
		if (viewport.contains(node.physrect)
				|| viewport.intersects(node.physrect)) {
			// draw the box if we reached a leaf...
			if ((node.leaf) || (depth > getMaxDepth())) {
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
						break;
					case START:
						g.setColor(Color.magenta);
						break;
					}

					nodesVisited++;
					g.fillRect(node.physrect.getX(), node.physrect.getY(),
							node.physrect.getWidth() - 1 / Main.GULOL,
							node.physrect.getHeight() - 1 / Main.GULOL);
					// draw outline of node - this is for debugging
					g.setColor(Color.white);
					if (Main.DEBUG) {
						g.drawRect(node.physrect.getX(), node.physrect.getY(),
								node.physrect.getWidth() - 1 / Main.GULOL,
								node.physrect.getHeight() - 1 / Main.GULOL);
						// g.draw(renderRect);
					}
				}
			}
			// ...or go deeper until we find a leaf to draw
			else {
				// draw outline of node - this is for debugging
				g.setColor(Color.darkGray);
				if (Main.DEBUG) {
					g.drawRect(node.physrect.getX(), node.physrect.getY(),
							node.physrect.getWidth(), node.physrect.getHeight());
				}
				for (int i = 0; i < 4; i++) {
					traverseTree(node.children[i], g, viewport);
				}
			}
		}
		depth--;
	}

	public Vector2f findBlockPosition(Block block) {
		return findBlockPosition(block, startNode);
	}

	public Vector2f findBlockPosition(Block block, Node node) {
		for (Node child : node.children) {
			if (child.leaf && child.type == block) {
				return new Vector2f(child.body.getX(), child.body.getY());
			} else if (!child.leaf) {
				Vector2f temp;

				if ((temp = findBlockPosition(block, child)) != null) {
					return temp;
				}
			}
		}
		return null;
	}

	public void trySimplify(Node n) {
		if (n != startNode) {
			n = n.parent;
			if (n.children[0].leaf && n.children[1].leaf && n.children[2].leaf
					&& n.children[3].leaf
					&& n.children[0].type == n.children[1].type
					&& n.children[1].type == n.children[2].type
					&& n.children[2].type == n.children[3].type
					&& n.children[0].type != Block.WATER
					&& n.children[0].type != Block.ACID) {
				n.type = n.children[0].type;
				for (int i = 0; i < 4; i++) {
					n.children[i].physDisable();
					if (waternodes.contains(n.children[i]))
						waternodes.remove(n.children[i]);
					if (acidnodes.contains(n.children[i]))
						acidnodes.remove(n.children[i]);
					n.children[i] = null;
				}
				numberOfLeaves -= 3;
				numberOfNodes -= 4;
				n.leaf = true;
				if (n.type == Block.EMPTY || n.type == Block.WATER
						|| n.type == Block.ACID || n.type == Block.GOAL) { //in retrospect, this could be less messy ^^
					n.physDisable();
				} else {
					n.physEnable();
				}
				if (n.type == Block.WATER) {
					waternodes.add(n);
				}
				if (n.type == Block.ACID) {
					acidnodes.add(n);
				}
				trySimplify(n);
			}
		}
	}

	public void fillCell(float _x, float _y, Block _type) {
		fillCell(_x, _y, _type, getMaxDepth());
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
				if (temp.type == Block.ACID)
					acidnodes.remove(temp);
				temp.type = _type;
				trySimplify(temp);
				if (temp.type == Block.WATER)
					waternodes.add(temp);
				if (temp.type == Block.ACID)
					acidnodes.add(temp);
				if (temp.type == Block.EMPTY || temp.type == Block.WATER
						|| temp.type == Block.ACID || temp.type == Block.GOAL) {
					temp.physDisable();
				} else {
					temp.physEnable();
				}
			}
		}
	}

	public void emptyCell(float _x, float _y) {
		Node temp = getLeaf(_x, _y);
		if (temp != null) {
			if (temp.type != Block.EMPTY) {
				while (temp.level < getMaxDepth()) {
					temp.split();
					temp = temp.getLeaf(_x, _y);
					numberOfLeaves += 3;
					numberOfNodes += 4;
				}

				if (temp.type == Block.WATER) {
					waternodes.remove(temp);
				}
				if (temp.type == Block.ACID) {
					acidnodes.remove(temp);
				}
				temp.type = Block.EMPTY;
				// temp.body.setEnabled(false);
				temp.physDisable();
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
		return startNode.toString();
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public int getNumberOfLeaves() {
		return numberOfLeaves;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

}