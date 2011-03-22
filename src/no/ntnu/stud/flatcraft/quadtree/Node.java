package no.ntnu.stud.flatcraft.quadtree;

import org.newdawn.slick.geom.Rectangle;

public class Node {
  boolean leaf;
  boolean filled;
  int level;
  Node parent;
  Node[] children;
  Rectangle rect;
  
  public Node(int _level){
      children = new Node[4];
      filled = false;
      leaf = true;
      level = _level;
  }
  
  public void split(){
	  leaf = false;
	  for(int i=0;i<4;i++){
		  children[i] = new Node(level+1);
		  children[i].parent = this;
		  children[i].filled = filled;
	  }
	  children[0].rect = new Rectangle(rect.getX(),rect.getY(),rect.getWidth()*0.5f,rect.getHeight()*0.5f);
	  children[1].rect = new Rectangle(rect.getX()+rect.getWidth()*0.5f,rect.getY(),rect.getWidth()*0.5f,rect.getHeight()*0.5f);
	  children[2].rect = new Rectangle(rect.getX(),rect.getY()+rect.getHeight()*0.5f,rect.getWidth()*0.5f,rect.getHeight()*0.5f);
	  children[3].rect = new Rectangle(rect.getX()+rect.getWidth()*0.5f,rect.getY()+rect.getHeight()*0.5f,rect.getWidth()*0.5f,rect.getHeight()*0.5f);
  }
  
  public Node getLeaf(float x,float y){
	  //System.out.println("Entered a node. level = "+level+" rect = "+rect.getX()+" "+rect.getY()+" x = "+x+" y = "+y+" size = "+rect.getWidth());
	  if(leaf) return this;
	  if(x>=rect.getX() && x<=rect.getX()+rect.getWidth() &&
			  y>=rect.getY() && y<=rect.getY()+rect.getHeight()){
		  if(x>=rect.getX()+rect.getWidth()*0.5f && y>=rect.getY()+rect.getHeight()*0.5f) {return children[3].getLeaf(x,y);}
		  if(x<=rect.getX()+rect.getWidth()*0.5f && y>=rect.getY()+rect.getHeight()*0.5f) {return children[2].getLeaf(x,y);}
		  if(x>=rect.getX()+rect.getWidth()*0.5f && y<=rect.getY()+rect.getHeight()*0.5f) {return children[1].getLeaf(x,y);}
		  return children[0].getLeaf(x,y);
	  }
	  return null;
  }
}