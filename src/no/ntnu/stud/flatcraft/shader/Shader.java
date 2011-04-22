package no.ntnu.stud.flatcraft.shader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Class used to use and access shaders without having to deal
 * with all of the fidly openGL bits.
 * @author Chronocide (Jeremy Klix)
 *
 */
public class Shader {
  public static final int BRIEF = 128;
  public static final int MODERATE = 512;
  public static final int VERBOSE = 1024;

  private static int logging = MODERATE;
  
  private ShaderResourceManager srm;
  private int programID = -1; //ID of the compiled Shader program
  
  private Shader(ShaderResourceManager srm,
                 String vertexFileName,
                 String fragmentFileName) throws SlickException{
    this.srm = srm;
    StringBuilder errorMessage = new StringBuilder();
    
    programID = GL20.glCreateProgram();
    int vsid = srm.getVertexShaderID(vertexFileName);
    int fsid = srm.getFragementShaderID(fragmentFileName);
    
    srm.createProgramShaderDependancy(programID, vsid);
    srm.createProgramShaderDependancy(programID, fsid);
    
    GL20.glShaderSource(vsid, getProgramCode(vertexFileName));
      GL20.glCompileShader(vsid);
      if(!compiledSuccessfully(vsid)){
        errorMessage.append("Could not compile Vertex Shader ");
        errorMessage.append(vertexFileName);
        errorMessage.append(" failed to compile.\n");
        errorMessage.append(getShaderInfoLog(vsid));
        errorMessage.append("\n\n");
      }
    
    GL20.glShaderSource(fsid, getProgramCode(fragmentFileName));
      GL20.glCompileShader(fsid);
      if(!compiledSuccessfully(fsid)){
        errorMessage.append("Could not compile Fragment Shader ");
        errorMessage.append(fragmentFileName);
        errorMessage.append(" failed to compile.\n");
        errorMessage.append(getShaderInfoLog(fsid));
        errorMessage.append("\n\n");
      }

      GL20.glAttachShader(programID, vsid);
      GL20.glAttachShader(programID, fsid);
    
      GL20.glLinkProgram(programID);
      if(!linkedSuccessfully()){
        errorMessage.append("Linking Error\n");
        errorMessage.append(getProgramInfoLog());
        errorMessage.append("\n\n");
      }
      
      if(errorMessage.length()!=0){
        srm.removeProgram(programID);
        programID = -1;
        errorMessage.append("Stack Trace:");
        throw new SlickException(errorMessage.toString());
      }
  }
  
  
  
  /**
   * Factory method to create a new Shader.
   * @param vertexFileName
   * @param fragmentFileName
   * @return
   * @throws SlickException
   */
  public static Shader makeShader(String vertexFileName,
                                  String fragmentFileName)throws SlickException{
    return new Shader(ShaderResourceManagerImpl.getSRM(),
                      vertexFileName,
                      fragmentFileName);
  }

  
  
  public void deleteShader(){
    srm.removeProgram(programID);
    programID = -1;
  }
  
  
  
  /**
   * Activates the shader.</br>
   */
  public void startShader(){
    if(programID == -1){
      throw new IllegalStateException("Cannot start shader; this" +
                                      " Shader has been deleted");
    }
    forceFixedShader(); //Not sure why this is necessary but it is.
    GL20.glUseProgram(programID);
  }
  
  
  
  /**
   * Reverts GL context back to the fixed pixel pipeline.<br>
   */
  public static void forceFixedShader(){
    GL20.glUseProgram(0);
  }
  
  
  
  /**
   * Sets the value of the uniform integer Variable <tt>name</tt>.</br>
   * @param name the variable to set.
   * @param value the value to be set.
   */
  public void setUniformIVariable(String name, int value){
    CharSequence param = new StringBuffer(name);
    int location = GL20.glGetUniformLocation(programID, param);
    
    GL20.glUniform1i(location, value);
  }

  
  
  
  
  /**
   * Sets the value of the uniform integer Variable <tt>name</tt>.</br>
   * @param name the variable to set.
   * @param value the value to be set.
   */
  public void setUniformFVariable(String name, float value){
    CharSequence param = new StringBuffer(name);
    int location = GL20.glGetUniformLocation(programID, param);
    
    GL20.glUniform1f(location, value);
  }
  
  
  
  public void setUniform2fVariable(String name,
                                   float v0,
                                   float v1){
    CharSequence param = new StringBuffer(name);
    int location = GL20.glGetUniformLocation(programID, param);
    GL20.glUniform2f(location, v0, v1);
  }
  
  
  /**
   * Returns true if the shader compiled successfully.</br>
   * @param shaderID
   * @return true if the shader compiled successfully.</br>
   */
  private boolean compiledSuccessfully(int shaderID){
    return GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_TRUE;
  }
  
  
  
  /**
   * Returns true if the shader program linked successfully.</br>
   * @param shaderID
   * @return true if the shader program linked successfully.</br>
   */
  private boolean linkedSuccessfully(){
    return GL20.glGetShader(programID, GL20.GL_LINK_STATUS)==GL11.GL_TRUE;
  }
  
  
  
  private String getShaderInfoLog(int shaderID){
    return GL20.glGetShaderInfoLog(shaderID, logging).trim();
  }
  
  
  
  private String getProgramInfoLog(){
    return GL20.glGetProgramInfoLog(programID, logging).trim();
  }
  
  
  
  /**
   * Gets the program code from the file "filename" and puts in into a 
   * byte buffer.
   * @param filename the full name of the file.
   * @return a ByteBuffer containing the program code.
   * @throws SlickException
   */
  private ByteBuffer getProgramCode(String filename)throws SlickException{
    InputStream fileInputStream = null;
    byte[] shaderCode = null;
        
    fileInputStream = ResourceLoader.getResourceAsStream(filename);
    DataInputStream dataStream = new DataInputStream(fileInputStream);
    try {
    dataStream.readFully(shaderCode = new byte[fileInputStream.available()]);
    fileInputStream.close();
    dataStream.close();
    } catch (IOException e) {
    throw new SlickException(e.getMessage());
    }

 
    ByteBuffer shaderPro = BufferUtils.createByteBuffer(shaderCode.length);

    shaderPro.put(shaderCode);
    shaderPro.flip();

    return shaderPro;
  }
  
  
}
