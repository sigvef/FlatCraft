package no.ntnu.stud.flatcraft;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

public class Hack {
	
	public static Line[] getLines(Shape shape){
		float points[] = shape.getPoints();
		Line[] result = new Line[(int) (points.length*0.5)];
		result[(int) (points.length*0.5-1)] = new Line(points[points.length-2],points[points.length-1],points[0],points[1]);
		for(int i=0;i<points.length-2;i+=2){
			result[(int) (i*0.5)] = new Line(points[i],points[i+1],points[i+2],points[i+3]);
		}
		return result;
	}
	
	public static boolean intersects(Shape shape, Shape otherShape){
		boolean result = false;
	    float points[] = shape.getPoints();           // (x3, y3)  and (x4, y4)
	    float thatPoints[] = otherShape.getPoints(); // (x1, y1)  and (x2, y2)
	    int length = points.length;
	    int thatLength = thatPoints.length;
	    double unknownA;
	    double unknownB;
		
	    if (!shape.closed()) {
	    	length -= 2;
	    }
	    if (!otherShape.closed()) {
	    	thatLength -= 2;
	    }
	    
		for(int i=0;i<length;i+=2) {        	
	    	int iNext = i+2;
	    	if (iNext >= points.length) {
	    		iNext = 0;
	    	}
	    	
	        for(int j=0;j<thatLength;j+=2) {
	        	int jNext = j+2;
	        	if (jNext >= thatPoints.length) {
	        		jNext = 0;
	        	}
	        	
	            unknownA = (((points[iNext] - points[i]) * (double) (thatPoints[j + 1] - points[i + 1])) - 
	                    ((points[iNext+1] - points[i + 1]) * (thatPoints[j] - points[i]))) / 
	                    (((points[iNext+1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j])) - 
	                            ((points[iNext] - points[i]) * (thatPoints[jNext+1] - thatPoints[j + 1])));
	            unknownB = (((thatPoints[jNext] - thatPoints[j]) * (double) (thatPoints[j + 1] - points[i + 1])) - 
	                    ((thatPoints[jNext+1] - thatPoints[j + 1]) * (thatPoints[j] - points[i]))) / 
	                    (((points[iNext+1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j])) - 
	                            ((points[iNext] - points[i]) * (thatPoints[jNext+1] - thatPoints[j + 1])));
	            
	            
	            
	            if(unknownA >= 0 && unknownA <= 1 && unknownB >= 0 && unknownB <= 1) {
	                result = true;
	                break;
	            }
	        }
	        if(result) {
	            break;
	        }
	    }
		return result;
	}
	
	
	public static boolean contains(Shape container, Shape other) {
    	if (Hack.intersects(container, other)) {
    		return false;
    	}
    	
    	for (int i=0;i<other.getPointCount();i++) {
    		float[] pt = other.getPoint(i);
    		if (!container.contains(pt[0], pt[1])) {
    			return false;
    		}
    	}
    	
    	return true;
    }
}
