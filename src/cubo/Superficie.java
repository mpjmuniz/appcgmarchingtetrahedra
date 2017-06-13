package cubo;

import java.util.ArrayList;
import util.math.Vector4f;

/**
 *
 * @author marcoslage
 */
class Superficie {
    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;
    
    protected int nverts = 8;
    protected int nfaces = 12;
    
    public Superficie(int resolucao, int maxX, int maxY){
        positions = new ArrayList<>();
        colors    = new ArrayList<>();
        
        for(float i = -1; i < 1; i += 0.1){
            for(float j = -1; j < 1; j += 0.1){
                for(float k = -1; k < 1; k += 0.1){
                    positions.add( new Vector4f(i, j, k, 1.0f) );
                    colors.add(new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f));
                }
            }
        }
        
        
        /*
        // Fill the vertices
        positions.add( new Vector4f(-0.5f,-0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f, 0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f, 0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f,-0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f,-0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f, 0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f, 0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f,-0.5f,-0.5f, 1.0f) );

        // Fill the colors
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
*/
    }
}
