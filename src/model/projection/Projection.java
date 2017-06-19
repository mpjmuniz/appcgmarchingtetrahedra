package model.projection;

import model.math.FastMath;
import model.math.Matrix4f;

/**
 *
 * @author mlage
 */
public class Projection {
    
    private float fovY;
    private float aspect;
    private float zNear;
    private float zFar;
    private float right;
    private float left;
    private float top;
    private float bottom;
    
    boolean isPerspective;
    
    public Projection(float fovY, float aspect, float zNear, float zFar,
                      float top, float bottom, float right, float left){
        this.fovY   = fovY;
        this.aspect = aspect;
        this.zNear  = zNear;
        this.zFar   = zFar;
        this.top = top;
        this.bottom = bottom;
        this.right = right;
        this.left = left;
        
        this.isPerspective = true;
    }
    
    public Matrix4f perspective() {
        float angle = fovY / 2.0f * FastMath.DEG_TO_RAD;
        float cotangent = FastMath.cos(angle) / FastMath.sin(angle);

        float e = cotangent / aspect; // focal length of the camera

        float fpn = zFar + zNear;  // far plus near
        float fmn = zFar - zNear;  // far minus near

        Matrix4f tempMatrix = new Matrix4f();
        tempMatrix.m11 = e;     tempMatrix.m21 = 0.0f;       tempMatrix.m31 = 0.0f;        tempMatrix.m41 = 0.0f;
        tempMatrix.m12 = 0.0f;  tempMatrix.m22 = cotangent;  tempMatrix.m32 = 0.0f;        tempMatrix.m42 = 0.0f;
        tempMatrix.m13 = 0.0f;  tempMatrix.m23 = 0.0f;       tempMatrix.m33 = -(fpn/fmn);  tempMatrix.m43 = -2.0f * zNear * zFar / fmn;
        tempMatrix.m14 = 0.0f;  tempMatrix.m24 = 0.0f;       tempMatrix.m34 = -1.0f;       tempMatrix.m44 = 0.0f;

        return tempMatrix;
    }
    
    
    public Matrix4f orthographic() {
        float m11 = 2.0f / (right - left);
        float m22 = 2.0f / (top - bottom);
        float m33 = - 2.0f / (zFar - zNear);
        float m41 = - (left + right) / (right - left);
        float m42 = - (top + bottom) / (top - bottom);
        float m43 = - (zFar + zNear) / (zFar - zNear);

        Matrix4f tempMatrix = new Matrix4f();
        tempMatrix.m11 = m11;  tempMatrix.m21 = 0.0f;       tempMatrix.m31 = 0.0f;        tempMatrix.m41 = m41;
        tempMatrix.m12 = 0.0f;  tempMatrix.m22 = m22;       tempMatrix.m32 = 0.0f;        tempMatrix.m42 = m42;
        tempMatrix.m13 = 0.0f;  tempMatrix.m23 = 0.0f;       tempMatrix.m33 = m33;        tempMatrix.m43 = m43;
        tempMatrix.m14 = 0.0f;  tempMatrix.m24 = 0.0f;       tempMatrix.m34 = 0.0f;        tempMatrix.m44 = 1.0f;

        return tempMatrix;
    }

    public Matrix4f getProjection(){
        return isPerspective ? perspective() : orthographic();
    }
    
    public Matrix4f changeProjection() {
        Matrix4f retMat = isPerspective ? orthographic() : perspective();
        
        isPerspective = !isPerspective;
        
        return retMat;
    }
}
