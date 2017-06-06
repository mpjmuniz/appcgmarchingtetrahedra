package View;

/**
 *
 * @author marcelomuniz
 * 
 * @thanks marcoslage
 * 
 */
import controller.GLPresenter;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;


/*
    Classe responsável por:
    - Iniciar ambiente OpenGL;
*/
public class Main {    

    /**
     * General initialization stuff for OpenGL
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        // width and height of window and view port
        
    }
    
    /**
     * entrada do programa
     * @param args pode ter: resolução, 
     * onde resolução é um inteiro com a quantidade de subdivisões da projeção padrão do OpenGL 
     * 
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        
        if(args.length < 1){
            System.out.println("Aviso: Você pode alterar a resolução da imagem gerada, fornecendo um inteiro além do nome do programa.");
        }
        
        GLPresenter example = new GLPresenter(args.length < 1 ? 
                                                512 : Integer.parseInt(args[1]));
        
        int width = 640;
        int height = 480;
        
        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Visualizador de Superficies - MTCGUFF ");
        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 1);
        
        Display.create(pixelFormat, contextAtrributes);
        
        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        
        example.run();
    }
}