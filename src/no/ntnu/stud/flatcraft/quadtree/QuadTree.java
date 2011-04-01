package no.ntnu.stud.flatcraft.quadtree;

import java.io.*;
import java.util.ArrayList;

import no.ntnu.stud.flatcraft.GameWorld;
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
import org.newdawn.slick.geom.Vector2f;

public class QuadTree {
Node startNode;
float initialSize;
int depth;
int maxDepth;
int numberOfNodes;
int numberOfLeaves;
int dummy;
float x;
float y;
Polygon forwards[];


  public QuadTree(float _x, float _y, float _size,int _maxDepth) throws SlickException{
	  forwards = new Polygon[4];
	  x = _x;
    y = _y;
    initialSize = _size;
    depth = 0;
    maxDepth = _maxDepth;
    numberOfNodes = 1;
    numberOfLeaves = 1;
	startNode = new Node(0);
	startNode.rect = new Rectangle(x,y,initialSize,initialSize);
    fillCell(253,63);
    fillCell(51,123);
    fillCell(425,425);
    nodelines = new Line[4];
    movelines = new Line[4];
    }
  Line nodelines[];
  Line movelines[];
  
  
  public void render(Graphics g,Rectangle viewport){
    dummy = 0;
    depth = 0;
    g.pushTransform();
    g.translate(-viewport.getX(),-viewport.getY());
    //enter the recursive render traversing
    g.setDrawMode(g.MODE_NORMAL);
    g.setColor(Color.black);
    g.fill(viewport);
    traverseTree(startNode,g,viewport);
    if(Main.DEBUG){
	    g.setColor(Color.red);
	    g.draw(viewport);
	    for(Line n: nodelines){
	    	if(n!=null)g.draw(n);
	    }
	    for(Line m: movelines){
	    	if(m!=null)g.draw(m);
	    }
	    for(Polygon p : forwards){
	    	if(p!= null)g.draw(p);
	    }
	    g.setColor(Color.white);
    }
    g.popTransform();
    }

  //-----------------------------------
  // the recursion needed for rendering
  //-----------------------------------
  public void traverseTree(Node node, Graphics g,Rectangle viewport){
    depth++;
    dummy++;
    //check to see if node is in the viewport
    if(viewport.contains(node.rect) ||
    	viewport.intersects(node.rect)){
	    
    	
	    //draw the box if we reached a leaf...
	    if ((node.leaf)||(depth>maxDepth)){
	      if(node.filled){
	    	  g.setColor(Color.orange);
	    	  g.fill(node.rect);
	    	//draw outline of node - this is for debugging
	      	g.setColor(Color.white);
	  	    g.draw(node.rect);
	  	    
	      }
	    }
	    //...or go deeper until we find a leaf to draw
	    else{
	    	//draw outline of node - this is for debugging
	    	g.setColor(Color.white);
		    g.draw(node.rect);
		  for(int i=0;i<4;i++){  
	      traverseTree(node.children[i],g,viewport);
		  }
		}
    }
    depth--;
  }

  public void trySimplify(Node n){
	  //System.out.println("Simplifying...");
	  if(n!=startNode){ n = n.parent;
		  if(n.children[0].leaf && n.children[1].leaf &&
		      n.children[2].leaf && n.children[3].leaf &&
			   n.children[0].filled == n.children[1].filled &&
			    n.children[1].filled == n.children[2].filled &&
		         n.children[2].filled == n.children[3].filled){
			      n.filled = n.children[0].filled;
			  for(int i=0;i<4;i++){ n.children[i]=null;}
			  numberOfLeaves-=3;
			  numberOfNodes-=4;
			  n.leaf = true;
			  trySimplify(n);
		  }
	  }
  }
  
  public void fillCell(float _x,float _y){
	  fillCell(_x,_y,true);
  }
  public void fillCell(float _x, float _y,boolean _filled){
	  Node temp = getLeaf(_x,_y);
	  if(temp != null){
		  if(temp.filled != _filled){
			  while(temp.level < maxDepth){
				  temp.split();
				  temp =  getLeaf(_x,_y);
				  numberOfLeaves += 3;
				  numberOfNodes += 4;
			  }
			  temp.filled = _filled;
			  trySimplify(temp);
		  }
	  }
  }
  
  public Node getLeaf(float _x, float _y){
	  return startNode.getLeaf(_x, _y);
  }
  
  public Node getLeaf(Vector2f v){
	  return startNode.getLeaf(v.getX(), v.getY());
  }
  

  //--------------------------------
  // toString
  //--------------------------------
  public String toString(){
    return("Quadtree: nodes:"+numberOfNodes+", leaves:"+numberOfLeaves);
  }



public boolean collide(GameEntity ge) {

	ge.grounded = false;
	//get the nodes that are close enough to collide with the ge
	Node[] nodes = new Node[5];
	
	ArrayList<Node> collnodes = new ArrayList<Node>();
	nodes[0] = getLeaf(ge.boundingBox.getX(),ge.boundingBox.getY()); //topleft
	nodes[1] = getLeaf(ge.boundingBox.getX()+ge.boundingBox.getWidth(),ge.boundingBox.getY()); //topright
	nodes[2] = getLeaf(ge.boundingBox.getX(),ge.boundingBox.getY()+ge.boundingBox.getHeight()); //bottomleft
	nodes[3] = getLeaf(ge.boundingBox.getX()+ge.boundingBox.getWidth(),ge.boundingBox.getY()+ge.boundingBox.getHeight()); //bottomright
	
	boolean collision = false;
	float points[] =  ge.boundingBox.getPoints();	
	
	forwards[0] = new Polygon();
	forwards[0].addPoint(points[0], points[1]);
	forwards[0].addPoint(points[2], points[3]);
	forwards[0].addPoint(points[2]+ge.velocity.getX()*Main.µ, points[3]+ge.velocity.getY()*Main.µ);
	forwards[0].addPoint(points[0]+ge.velocity.getX()*Main.µ, points[1]+ge.velocity.getY()*Main.µ);
	forwards[0].setClosed(true);
	
	forwards[1] = new Polygon();
	forwards[1].addPoint(points[2], points[3]);
	forwards[1].addPoint(points[4], points[5]);
	forwards[1].addPoint(points[4]+ge.velocity.getX()*Main.µ, points[5]+ge.velocity.getY()*Main.µ);
	forwards[1].addPoint(points[2]+ge.velocity.getX()*Main.µ, points[3]+ge.velocity.getY()*Main.µ);
	forwards[1].setClosed(true);
	
	forwards[2] = new Polygon();
	forwards[2].addPoint(points[4], points[5]);
	forwards[2].addPoint(points[6], points[7]);
	forwards[2].addPoint(points[6]+ge.velocity.getX()*Main.µ, points[7]+ge.velocity.getY()*Main.µ);
	forwards[2].addPoint(points[4]+ge.velocity.getX()*Main.µ, points[5]+ge.velocity.getY()*Main.µ);
	forwards[2].setClosed(true);
	
	forwards[3] = new Polygon();
	forwards[3].addPoint(points[6], points[7]);
	forwards[3].addPoint(points[0], points[1]);
	forwards[3].addPoint(points[0]+ge.velocity.getX()*Main.µ, points[1]+ge.velocity.getY()*Main.µ);
	forwards[3].addPoint(points[6]+ge.velocity.getX()*Main.µ, points[7]+ge.velocity.getY()*Main.µ);
	forwards[3].setClosed(true);
	for(Polygon forward : forwards){
		for(Node n : nodes){
			if(n != null && n.filled && (forward.contains(n.rect) || forward.intersects(n.rect) || n.rect.contains(forward)) && !collnodes.contains(n)){
				collnodes.add(n);
			}
		}
	}
	
	Vector2f target = null;
	
	for(Node n :collnodes){
		if(Math.abs(n.rect.getCenterX()-ge.boundingBox.getCenterX()) >= Math.abs(n.rect.getCenterY()-ge.boundingBox.getCenterY())){
			if(ge.velocity.getX()>0 && ge.position.getX() != n.rect.getMaxX()){ //slight hack after the && to stop phasing through stuff from right to left
				//going right
				target = new Vector2f(n.rect.getX()-ge.boundingBox.getWidth(),ge.position.getY());
			}
			else if(ge.velocity.getX()<0){
				//going left
				//ge.position.add(new Vector2f(ge.position.getX()+ge.velocity.getX()-(n.rect.getX()+n.rect.getWidth()),0));
				target = new Vector2f(n.rect.getMaxX(),ge.position.getY());
			}
			ge.velocity.set(0, ge.velocity.getY());
			System.out.println("verticoll");
		}
		else{
			if(ge.velocity.getY()<0){
				//going up
				target = new Vector2f(ge.position.getX(),n.rect.getMaxY());
				ge.velocity.add(new Vector2f(ge.velocity.getX(), Main.GRAVITY.getY()));
			}
			else if(ge.velocity.getY()>0 && ge.position.getY() != n.rect.getMaxY()){
				//going down
				target = new Vector2f(ge.position.getX(),n.rect.getY()-ge.boundingBox.getHeight());
				ge.grounded = true;
				ge.velocity.set(ge.velocity.getX(), 0);
				System.out.println("grounded.");
			}
		}
	}
	
	
	if(target != null){
		ge.position.set(target);
		ge.boundingBox.setLocation(ge.position);
	}
	
	if(collnodes.size()>0){
		System.out.println("");
		return true;
	}
	return false;
}


}