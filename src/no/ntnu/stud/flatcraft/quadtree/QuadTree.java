package no.ntnu.stud.flatcraft.quadtree;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import no.ntnu.stud.flatcraft.GameWorld;
import no.ntnu.stud.flatcraft.Hack;
import no.ntnu.stud.flatcraft.Main;
import no.ntnu.stud.flatcraft.entities.GameEntity;
import no.ntnu.stud.flatcraft.entities.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class QuadTree implements Serializable {
	private static final long serialVersionUID = -8798887499766580371L;
	
	Node startNode;
	Node containingNode; //for debug only
	float initialSize;
	int depth;
	int maxDepth;
	int numberOfNodes;
	int numberOfLeaves;
	int dummy;
	float x;
	float y;
	Polygon forwards[];

	public QuadTree(float _x, float _y, float _size, int _maxDepth)
			throws SlickException {
		forwards = new Polygon[4];
		x = _x;
		y = _y;
		initialSize = _size;
		depth = 0;
		maxDepth = _maxDepth;
		numberOfNodes = 1;
		numberOfLeaves = 1;
		startNode = new Node(0,this);
		startNode.rect = new Rectangle(x, y, initialSize, initialSize);
		fillCell(100, 600, Block.METAL);
//		fillCell(51, 123, Block.ROCK);
//		fillCell(425, 425, Block.ACID);
		nodelines = new Line[4];
		movelines = new Line[4];
	}

	Line nodelines[];
	Line movelines[];

	public void render(Graphics g, Rectangle viewport) {
		dummy = 0;
		depth = 0;
		g.pushTransform();
		g.translate(-viewport.getX(), -viewport.getY());
		// enter the recursive render traversing
		g.setDrawMode(g.MODE_NORMAL);
		g.setColor(Color.black);
		g.fill(viewport);
		traverseTree(startNode, g, viewport);
		if (Main.DEBUG) {
			g.setColor(Color.red);
			g.draw(viewport);
			if(containingNode != null){
				g.draw(containingNode.rect);
			}
			for (Line n : nodelines) {
				if (n != null)
					g.draw(n);
			}
			for (Line m : movelines) {
				if (m != null)
					g.draw(m);
			}
			for (Polygon p : forwards) {
				if (p != null)
					g.draw(p);
			}
			g.setColor(Color.white);
		}
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
						g.setColor(Color.blue);
						break;
					case ACID:
						g.setColor(Color.green);
						break;
					case GOAL:
						g.setColor(Color.white);
					}
					g.fill(node.rect);
					// draw outline of node - this is for debugging
					g.setColor(Color.white);
					if(Main.DEBUG){
						g.draw(node.rect);
					}

				}
			}
			// ...or go deeper until we find a leaf to draw
			else {
				// draw outline of node - this is for debugging
				g.setColor(Color.white);
				if(Main.DEBUG)g.draw(node.rect);
				for (int i = 0; i < 4; i++) {
					traverseTree(node.children[i], g, viewport);
				}
			}
		}
		depth--;
	}

	public void trySimplify(Node n) {
		// System.out.println("Simplifying...");
		if (n != startNode) {
			n = n.parent;
			if (n.children[0].leaf &&
				 n.children[1].leaf &&
				  n.children[2].leaf &&
				   n.children[3].leaf &&
				    n.children[0].type == n.children[1].type &&
				     n.children[1].type == n.children[2].type &&
				      n.children[2].type == n.children[3].type) {
				n.type = n.children[0].type;
				for (int i = 0; i < 4; i++) {
					n.children[i] = null;
				}
				numberOfLeaves -= 3;
				numberOfNodes -= 4;
				n.leaf = true;
				trySimplify(n);
			}
		}
	}

	public void fillCell(float _x, float _y, Block _type) {
		Node temp = getLeaf(_x, _y);
		if (temp != null) {
			if (temp.type != _type) {
				while (temp.level < maxDepth) {
					temp.split();
					temp = getLeaf(_x, _y);
					numberOfLeaves += 3;
					numberOfNodes += 4;
				}
				temp.type = _type;
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
		return ("Quadtree: nodes:" + numberOfNodes + ", leaves:" + numberOfLeaves);
	}
	
	public Node getContainingNode(GameEntity ge){
		return startNode.getContainingNode(ge);
	}

	public float intervalDistance(float[] A, float[] B) {
	    if (A[0] < B[0]) {
	        return B[0] - A[1];
	    } else {
	        return A[0] - B[1];
	    }
	}
	
	
	
	public float[] projectPolygon(Vector2f axis, Shape shape) {
	
		float[] points = shape.getPoints();
		float dotProduct = axis.dot(new Vector2f(points[0], points[1]));
		float[] A = new float[2];
		A[0] = dotProduct;
		A[1] = dotProduct;
		return projectPolygon(axis, shape, A);
	}	
	public float[] projectPolygon(Vector2f axis, Shape shape,float[] A) {
		// To project a point on an axis use the dot product

		float[] points = shape.getPoints();

		//float dotProduct = axis.dot(new Vector2f(points[0], points[1]));
		float min = A[0];
		float max = A[1];
		for (int i = 0; i < points.length; i += 2) {
			float dotProduct = axis.dot(new Vector2f(points[i], points[i + 1]));
			if (dotProduct < min) {
				min = dotProduct;
			} else {
				if (dotProduct > max) {
					max = dotProduct;
				}
			}
		}
		float[] result = { min, max };
		return result;
	}
	
	public boolean collide(GameEntity ge) {

		ge.grounded = false;
		// get the nodes that are close enough to collide with the ge
		containingNode = getContainingNode(ge);
		ArrayList<Node> collnodes = new ArrayList<Node>();
		
		Node decidingNode = null;
		Vector2f decidingAxis = null;
		
		try{
		containingNode.getAllLeaves(collnodes);
		}
		catch(Exception e){
			containingNode = getContainingNode(ge); //hook for debugging only
			containingNode.getAllLeaves(collnodes);
		}
		
		
		boolean collision = false;
		
		int[] remover = new int[collnodes.size()];
		Arrays.fill(remover,4);
		for (Polygon forward : ge.forwards) {
			for(int i=0; i<collnodes.size();i++){
				if(collnodes.get(i) != null && (collnodes.get(i).type == Block.EMPTY || (!Hack.contains(forward, collnodes.get(i).rect) && !Hack.contains(collnodes.get(i).rect, forward) && !Hack.intersects(collnodes.get(i).rect, forward)))){
					remover[i]--;
				}
			}
		}

		Vector2f target = null;
//		float distanceToRevert = Integer.MIN_VALUE;
		float distanceToRevert = 0;
		Vector2f axis = null;
		
		for (int i=0;i<collnodes.size();i++) {
				if(remover[i] == 0) continue;
				System.out.println("COLLISION!");
				
				Line[] nodelines = Hack.getLines(collnodes.get(i).rect);
				
				int[] nodelineintersects = new int[4];
				
				for(Polygon forward : ge.forwards){
					Line[] forwardlines = Hack.getLines(forward);
					for(Line fl : forwardlines){
						if(fl == forwardlines[forwardlines.length-1]) continue;
						for(int j=0;j<nodelines.length;j++){
							if(Hack.intersects(nodelines[j], fl)){
								nodelineintersects[j]++;
								
							}
						}
					}
				}
				
				
				int index = 0;
				int max = nodelineintersects[index];
				for(int j=0;j<nodelineintersects.length;j++){
					if(nodelineintersects[j]>max){
						max = nodelineintersects[j];
						index = j;
					}
				}
				
				axis = new Vector2f(nodelines[index].getDX(),nodelines[index].getDY());	
				axis = axis.normalise().getPerpendicular();
				System.out.println(axis);
				
				
				
				collision = true;
				float A[] = projectPolygon(axis, collnodes.get(i).rect);
				
				
					float B[] = projectPolygon(axis, ge.boundingBox);
					for(Polygon forward : ge.forwards){
						B = projectPolygon(axis, forward, B);
					}
				float dist = intervalDistance(A,B);
				
				System.out.println("dist: "+dist);
				
				if (dist < distanceToRevert) {
					distanceToRevert = dist;
					decidingAxis = axis.copy();
				}
			}
		
			
			//ge.position.add(ge.velocity.copy().normalise().scale(-distanceToRevert).scale(Main.mu));
			if(collision){
				if(distanceToRevert <= -99999999) distanceToRevert = 0;
				System.out.println("distanceToRevert: "+distanceToRevert);
				Vector2f reverter = new Vector2f(ge.velocity.getTheta());
				reverter.normalise();
				if(distanceToRevert < 0){
					System.out.println("REVERTING!");
					System.out.println(distanceToRevert);
					reverter.scale((float) (distanceToRevert));
					//float theta = (float) ((Math.PI*0.5)-ge.velocity.getTheta()*Math.PI/180f);
					float theta = (float) decidingAxis.dot(ge.velocity.copy().normalise());
					if(theta == 0) theta = 1;
					System.out.println("scaler: "+1/theta);
					reverter.scale((float) (1/theta));
					System.out.println(reverter);
					ge.position.add(reverter);
					ge.boundingBox.setLocation(ge.position);
				
				if(decidingAxis.getY() == 0){
					System.out.println("verticoll");
					ge.velocity.set(0,ge.velocity.getY());
				}else{
					if(ge.velocity.getY()>0d)ge.grounded = true;
					ge.velocity.set(ge.velocity.getX(), 0);
				}
				ge.position.add(ge.velocity);
				ge.boundingBox.setLocation(ge.position);
				}
				
			}
	
		

		
		return false;
	}

}