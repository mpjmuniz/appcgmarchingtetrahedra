package View;

/**
 *
 * @author marcelomuniz
 * 
 * @thanks marcoslage
 * 
 */
import cubo.CubeGL;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.projection.Projection;


/*
    Classe responsável por:
    - Iniciar ambiente OpenGL;
    - Ouvir inputs do usuário;
    - Mandar Objeto para placa gráfica;
    - 
*/
public class Main {

    // Creates a new cube
    private final CubeGL controller;

    // Animation:
    private float  currentAngle = 0.0f;
    
    // Projection Matrix
    private final Projection proj = new Projection(45, 1.3333f, 0.0f, 100f);
    
    // View Matrix
    private final Vector3f eye = new Vector3f( 0.0f, 2.0f, 2.0f);
    private final Vector3f at  = new Vector3f( 0.0f, 0.0f, 0.0f);
    private final Vector3f up  = new Vector3f( 0.0f, 1.0f, 2.0f);
    
    // Camera
    private final Camera cam = new Camera(eye, at, up);

    // Light
    private final Vector3f lightPos     = new Vector3f(0.0f, 2.0f,-2.0f);
    private final Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f speclarColor = new Vector3f(1.0f, 1.0f, 1.0f);
    
    private final float kA =  0.4f;
    private final float kD =  0.5f;
    private final float kS =  0.1f;
    private final float sN = 60.0f;
    
    // Model Matrix:
    private final Matrix4f rotationMatrixY = new Matrix4f();
    private final Matrix4f scaleMatrix = new Matrix4f();
    
    // Final Matrix
    private final Matrix4f modelMatrix = new Matrix4f();
    private final Matrix4f viewMatrix  = new Matrix4f();      
    private final Matrix4f projMatrix  = new Matrix4f();
    
    public Main(int resolution, String expression){
        controller = new CubeGL(resolution, expression);
    }
    
    /**
     * General initialization stuff for OpenGL
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        
        // width and height of window and view port
        int width = 640;
        int height = 480;

        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 1);
        Display.create(pixelFormat, contextAtrributes);
        
        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: "   + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
        
        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        controller.fillVAOs();
        controller.loadShaders();
       
        // Model Matrix setup
        scaleMatrix.m11 = 0.5f;
        scaleMatrix.m22 = 0.5f;
        scaleMatrix.m33 = 0.5f;
        
        // light setup
        controller.setVector("lightPos"    , lightPos);
        controller.setVector("ambientColor", ambientColor);
        controller.setVector("diffuseColor", diffuseColor);
        controller.setVector("speclarColor", speclarColor);
        
        controller.setFloat("kA", kA);
        controller.setFloat("kD", kD);
        controller.setFloat("kS", kS);
        controller.setFloat("sN", sN);
        
        while (Display.isCloseRequested() == false) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);
            
            // Projection and View Matrix Setup
            projMatrix.setTo(proj.perspective());            
            viewMatrix.setTo(cam.viewMatrix());
            
            currentAngle += 0.01f;
            float c = FastMath.cos(currentAngle);
            float s = FastMath.sin(currentAngle);
            
            rotationMatrixY.m22 = c; rotationMatrixY.m32 = -s;
            rotationMatrixY.m23 =s; rotationMatrixY.m33 = c;

            modelMatrix.setToIdentity();
            modelMatrix.multiply(rotationMatrixY);
            modelMatrix.multiply(scaleMatrix);
                        
            controller.setMatrix("modelmatrix", modelMatrix);
            controller.setMatrix("viewmatrix" , viewMatrix);
            controller.setMatrix("projection" , projMatrix);
            controller.render();

            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            pollInput();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
     * entrada do programa
     * @param args deve ter 2 valores: expressão e resolução, 
     * onde expressão é uma string com a expressão sem espaços, 
     * e    resolução é um inteiro com a quantidade de subdivisões da projeção padrão do OpenGL 
     * 
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        
        if(args.length < 1){
            System.out.println("Erro de invocação. Para utilizar o aplicativo, por favor forneça:");
            System.out.println("uma expressão da superfície nas variáveis x, y, z, pe.: x^2+y^2+z^2");
            System.out.println("um inteiro, representando a resolução final da superfície gerada");
        }
        
        Main example = new Main(args.length < 2 ? 
                                512 : Integer.parseInt(args[1]), 
                                args[0]);
        example.initGl();
        example.run();
    }

    
    /**
     * Função responsável por tratar eventos de entrada/saída,
     * no caso interface de rotações do objeto e troca de projeções
     * 
     * TODO: implementar chamamento das funções na SuperficieGL
     */
    private void pollInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            throw new UnsupportedOperationException("Troca de projeção não implementada");
        } else if(Keyboard.isKeyDown(Keyboard.KEY_L)){
            throw new UnsupportedOperationException("Rotação para esquerda não implementada");
        } else if(Keyboard.isKeyDown(Keyboard.KEY_R)){
            throw new UnsupportedOperationException("Rotação para direita não implementada");
        } else if(Keyboard.isKeyDown(Keyboard.KEY_C)){
            throw new UnsupportedOperationException("Rotação para frente não implementada");
        } else if(Keyboard.isKeyDown(Keyboard.KEY_B)){
            throw new UnsupportedOperationException("Rotação para trás não implementada");
        }
    }
}
