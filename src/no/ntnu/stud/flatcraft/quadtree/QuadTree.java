package no.ntnu.stud.flatcraft.quadtree;

import java.io.*;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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


  public QuadTree(float _x, float _y, float _size,int _maxDepth) throws SlickException{
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
  }

  
  
  public void render(Graphics g,Rectangle viewport){
    dummy = 0;
    depth = 0;
    //enter the recursive render traversing
    g.setDrawMode(g.MODE_NORMAL);
    g.setColor(Color.black);
    g.fill(viewport);
    traverseTree(startNode,g,viewport);
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
  

  //--------------------------------
  // toString
  //--------------------------------
  public String toString(){
    return("Quadtree: nodes:"+numberOfNodes+", leaves:"+numberOfLeaves);
  }


}