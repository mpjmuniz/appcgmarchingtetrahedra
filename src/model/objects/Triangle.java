/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

import java.nio.FloatBuffer;
import model.math.Vector4f;

/**
 *
 * @author 917001
 */
public class Triangle {
    private Vector4f[] vertices;
    private Vector4f[] colors;

    public Triangle(Vector4f vert1, Vector4f vert2, Vector4f vert3,
                    Vector4f col1, Vector4f col2, Vector4f col3) {
        this.vertices = new Vector4f[]{vert1, vert2, vert3};
        this.colors = new Vector4f[]{col1, col2, col3}; 
    }
    
    public void storeTri(FloatBuffer posBuffer, FloatBuffer normalBuffer, FloatBuffer colorBuffer){
        for(Vector4f vert : vertices){
            vert.store(posBuffer);
            vert.store(normalBuffer);
        }
        
        for(Vector4f vert : colors){
            vert.store(colorBuffer);
        }
    }
}
