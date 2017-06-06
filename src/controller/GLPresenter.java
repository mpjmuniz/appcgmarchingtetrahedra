/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cubo.CubeGL;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.projection.Projection;

/* TODO:
    implementar mudança de projeção
    */

/**
 *
 * @author marcelomuniz
 * classe responsável por:
 *  - controlar matrizes
 *  - ouvir eventos do usuário
*/
public class GLPresenter {

    // View Matrix
    private Vector3f eye;
    private float kA;
    // Final Matrix
    private Matrix4f modelMatrix;
    // Camera
    private Camera cam;
    // Creates a new cube
    private CubeGL controller;
    private Matrix4f scaleMatrix;
    private float kD;
    private Vector3f diffuseColor;
    private Vector3f ambientColor;
    private Matrix4f viewMatrix;
    // Animation:
    private float currentAngleX;
    private float currentAngleY;
    // Projection Matrix
    private Projection proj;
    private Vector3f up;
    private float sN;
    // Light
    private Vector3f lightPos;
    private Vector3f speclarColor;
    // Model Matrix:
    private Matrix4f rotationMatrixY;
    private Matrix4f rotationMatrixX;
    private Matrix4f projMatrix;
    private float kS;
    private Vector3f at;

    public GLPresenter(int resolution){
        kA = 0.4F;
        kD = 0.5F;
        scaleMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
        controller = new CubeGL(resolution);
        eye = new Vector3f(0.0F, 2.0F, 2.0F);
        
        diffuseColor = new Vector3f(1.0F, 1.0F, 1.0F);
        ambientColor = new Vector3f(1.0F, 1.0F, 1.0F);
        viewMatrix = new Matrix4f();
    
        // Animation:
        currentAngleX = 0.0F;
    
        // Projection Matrix
        proj = new Projection(45, 1.3333F, 0.0F, 100.0F);
        up = new Vector3f(0.0F, 1.0F, 2.0F);
        sN = 60.0F;
    
        // Light
        lightPos = new Vector3f(0.0F, 2.0F, -2.0F);
        speclarColor = new Vector3f(1.0F, 1.0F, 1.0F);
        
        // Model Matrix:
        rotationMatrixY = new Matrix4f();
        rotationMatrixX = new Matrix4f();
        projMatrix = new Matrix4f();
        kS = 0.1F;
        at = new Vector3f(0.0F, 0.0F, 0.0F);
        cam = new Camera(eye, at, up);
    }
    
    public void run() {
        // Creates the vertex array object.
        // Must be performed before shaders compilation.
        controller.fillVAOs();
        controller.loadShaders();
        // Model Matrix setup
        scaleMatrix.m11 = 0.5F;
        scaleMatrix.m22 = 0.5F;
        scaleMatrix.m33 = 0.5F;
        // light setup
        controller.setVector("lightPos", lightPos);
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
            
            modelMatrix.setToIdentity();
            pollInput();
            modelMatrix.multiply(rotationMatrixY);
            modelMatrix.multiply(rotationMatrixX);
            modelMatrix.multiply(scaleMatrix);
            controller.setMatrix("modelmatrix", modelMatrix);
            controller.setMatrix("viewmatrix", viewMatrix);
            controller.setMatrix("projection", projMatrix);
            controller.render();
            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }
            // swap buffers and sync frame rate to 60 fps
           
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
     * Função responsável por tratar eventos de entrada/saída,
     * no caso interface de rotações do objeto e troca de projeções
     *
     * TODO: implementar chamamento das funções na SuperficieGL
     */
    private void pollInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            throw new UnsupportedOperationException("Troca de proje\u00e7\u00e3o n\u00e3o implementada");
        } else if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            rotateL();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            rotateR();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            rotateC();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
            rotateB();
        }
    }
    
    private void rotateC(){
        currentAngleX += 0.01F;
        
        float c = FastMath.cos(currentAngleX);
        float s = FastMath.sin(currentAngleX);
        
        rotationMatrixY.m22 = c;
        rotationMatrixY.m32 = -s;
        rotationMatrixY.m23 = s;
        rotationMatrixY.m33 = c;
    }
    
    private void rotateB(){
        currentAngleX -= 0.01F;
        
        float c = FastMath.cos(currentAngleX);
        float s = FastMath.sin(currentAngleX);
        
        rotationMatrixY.m22 = c;
        rotationMatrixY.m32 = -s;
        rotationMatrixY.m23 = s;
        rotationMatrixY.m33 = c;
    }
    
    private void rotateL(){
        currentAngleY += 0.01F;
        
        float c = FastMath.cos(currentAngleY);
        float s = FastMath.sin(currentAngleY);
        
        rotationMatrixX.m11 = c;
        rotationMatrixX.m13 = s;
        rotationMatrixX.m31 = -s;
        rotationMatrixX.m33 = c;
    }
    
    private void rotateR(){
        currentAngleY -= 0.01F;
        
        float c = FastMath.cos(currentAngleY);
        float s = FastMath.sin(currentAngleY);
        
        rotationMatrixX.m11 = c;
        rotationMatrixX.m13 = s;
        rotationMatrixX.m31 = -s;
        rotationMatrixX.m33 = c;
    }
    
}
