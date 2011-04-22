package no.ntnu.stud.flatcraft.shader;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.SGL;

/**
 * Class to support the concept of a single artifact being
 * comprised of multiple image resources.</br>
 * For example a colourmap, normalmap, diffusemap, and specularmap.
 * This is currently extremely buggy, and I don't know why some
 * things have to be the way the are. 
 * @author Chronocide (Jeremy Klix)
 *
 */
//TODO Make interface feel a little more like the familiar Image class
//TODO Determine a method of dealing with the case were textures
//are not all the same size.  For instance should textures be
//stretched, tiled, clamped?
//TODO Needs way more attention to documenting inheritance.
public class MultiTex implements Renderable{

  //TODO make a collection of textures.
  public Texture tex1;
  public Texture tex2;
  
  public MultiTex(String t1, String t2)throws SlickException{
    //TODO bypass making an Image by the using the
    //InternalTextureLoader directly
    //TODO make this take an indexed collection of strings. Note
    //the collection index must correspond to the textureUnit the
    //resource is bound to.
    //TODO check that maxTextureUnits supports the number of
    //textures loaded.
    //TODO load texture in reverse order so that we end on texture0
    //which is the default used by Slick which assumes only a
    //single texture unit.
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    GL11.glEnable(GL11.GL_TEXTURE_2D);
    tex1 = new Image(t1).getTexture();
    GL11.glDisable(GL11.GL_TEXTURE_2D);
    
    GL13.glActiveTexture(GL13.GL_TEXTURE1);
    GL11.glEnable(GL11.GL_TEXTURE_2D);
    tex2 = new Image(t2).getTexture();
    GL11.glDisable(GL11.GL_TEXTURE_2D);
    
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
  }
  
  
  
  /**
   * When extending please note that this method relies on the
   * private method drawEmbedded.</br>
   */
  public void draw(float x, float y){
    //TODO remove hard-coding 
    //TODO change to using push and pop to avoid fp errors
    GL11.glTranslatef(x, y, 0);
    
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex1.getTextureID());
  
      GL13.glActiveTexture(GL13.GL_TEXTURE1);
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex2.getTextureID());
      
  
      GL11.glBegin(SGL.GL_QUADS); 
        drawEmbedded(0,0,
                     tex1.getImageWidth(),
                     tex1.getImageHeight()); 
      GL11.glEnd(); 
    
    GL11.glTranslatef(-x, -y, 0);
    
    //Clean up texture setting to allow basic slick to operate correctly.
    GL13.glActiveTexture(GL13.GL_TEXTURE1);
    GL11.glDisable(GL11.GL_TEXTURE_2D);
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    GL11.glDisable(GL11.GL_TEXTURE_2D);
    
    GL11.glEnable(GL11.GL_TEXTURE_2D);
  }


  
  private void drawEmbedded(int x, int y, int width, int height){
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE0, 0, 0);
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE1, 0, 0);
    GL11.glVertex3f(x, y, 0);
    
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE0, 0, 1);
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE1, 0, 1);
    GL11.glVertex3f(x, y + height, 0);
    
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE0, 1, 1);
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE1, 1, 1);
    GL11.glVertex3f(x + width, y + height, 0);
    
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE0, 1, 0);
    GL13.glMultiTexCoord2f(GL13.GL_TEXTURE1, 1, 0);
    GL11.glVertex3f(x + width, y, 0);
  }
  
}
