/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ilumination;

import model.math.Vector3f;

/**
 *
 * @author marcelomuniz
 */
public class Illuminator {
    
    private float kA;
    private Vector3f ambientColor;
    
    private float kD;
    private Vector3f diffuseColor;
    
    private float kS;
    private Vector3f specularColor;
    
    // Light
    private Vector3f lightPos;

    public Illuminator() {
        this.kA = 0.4F;
        this.ambientColor = new Vector3f(1.0F, 1.0F, 1.0F);
        this.kD = 0.5F;
        this.diffuseColor = new Vector3f(1.0F, 1.0F, 1.0F);
        this.kS = 0.1F;
        this.specularColor = new Vector3f(1.0F, 1.0F, 1.0F);
        this.lightPos = new Vector3f(0.0F, 2.0F, -2.0F);
    }

    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }

    public Vector3f getSpecularColor() {
        return specularColor;
    }

    public Vector3f getLightPos() {
        return lightPos;
    }

    public float getkA() {
        return kA;
    }

    public float getkD() {
        return kD;
    }

    public float getkS() {
        return kS;
    }
}
