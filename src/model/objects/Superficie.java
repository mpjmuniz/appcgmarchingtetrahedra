package model.objects;

import java.util.ArrayList;
import java.util.List;
import model.math.Vector4f;

/**
 *
 * @author marcelomuniz
 */
class Superficie {
    protected ArrayList<Vector4f> colors;
    protected ArrayList<Vector4f> positions;
    
    protected int resolucao,
                  qtdPontos;
    
    protected List<Triangle> triangles;
    
    protected int nverts;
    protected int nfaces;
    
    protected float[][][] grid;
    
    public Superficie(int res){
        
        /*
                 Coordinates:
                      z
                      |
                      |___ y
                      /
                     /
                    x
                 Cube layout:
                    4-------7
                   /|      /|
                  / |     / |
                 5-------6  |
                 |  0----|--3
                 | /     | /
                 |/      |/
                 1-------2
                 Tetrahedrons are:
                     0, 7, 3, 2
                     0, 7, 2, 6
                     0, 4, 6, 7
                     0, 6, 1, 2
                     0, 6, 1, 4
                     5, 6, 1, 4
                 */
        
        this.resolucao = res;
        this.qtdPontos = res + 1;
        
        positions = new ArrayList<>();
        colors    = new ArrayList<>();
        triangles = new ArrayList<>();
        /*
        grid = new float[qtdPontos][qtdPontos][qtdPontos];
        
        float xrange = 2, yrange = 2, zrange = 2,
              xMin = -1, yMin = -1, zMin = -1,
              xMax = 1, yMax = 1, zMax = 1;
        
        for (int i = 0; i <= resolucao; ++i) {
            float x = (float)i/resolucao * xrange + xMin;
            for (int j = 0; j <= resolucao; ++j) {
                float y = (float)j/resolucao * yrange + yMin;
                for (int k = 0; k <= resolucao; ++k) {
                    float z = (float)k/resolucao * zrange + zMin;
                    grid[i][j][k] = f(x, y, z);
                }
            }
        }
        
        for (int i = 0; i < resolucao; ++i) {
        float x1 = (float)i/resolucao * xrange + xMin;
        float x2 = (float)(i+1)/resolucao * xrange + xMin;
        for (int j = 0; j < resolucao; ++j) {
            float y1 = (float)j/resolucao * yrange + yMin;
            float y2 = (float)(j+1)/resolucao * yrange + yMin;
            for (int k = 0; k < resolucao; ++k) {
                float z1 = (float)k/resolucao * zrange + zMin;
                float z2 = (float)(k+1)/resolucao * zrange + zMin;

                Point3D[] v = new Point3D[]{
                    new Point3D(x1, y1, z1, grid[i][j][k]),
                    new Point3D(x2, y1, z1, grid[i + 1][j][k]),
                    new Point3D(x2, y2, z1, grid[i + 1][j + 1][k]),
                    new Point3D(x1, y2, z1, grid[i][j + 1][k]),
                    new Point3D(x1, y1, z2, grid[i][j][k + 1]),
                    new Point3D(x2, y1, z2, grid[i + 1][j][k + 1]),
                    new Point3D(x2, y2, z2, grid[i + 1][j + 1][k + 1]),
                    new Point3D(x1, y2, z2, grid[i][ j + 1][ k + 1])
                };

                Point3D[][] tetrahedra = new Point3D[][]{
                    { v[0], v[7], v[3], v[2] },
                    { v[0], v[7], v[2], v[6] },
                    { v[0], v[4], v[7], v[6] },
                    { v[0], v[1], v[6], v[2] },
                    { v[0], v[4], v[6], v[1] },
                    { v[5], v[1], v[6], v[4] }
                };

                for (int t = 0; t < 6; ++t)
                    drawTetrahedron(tetrahedra[t]);
                }
            }
        }
        */
        
        // Fill the vertices
        positions.add( new Vector4f(-1f,-1f, 1f, 1.0f) );
        positions.add( new Vector4f(-1f, 1f, 1f, 1.0f) );
        positions.add( new Vector4f( 1f, 1f, 1f, 1.0f) );
        positions.add( new Vector4f( 1f,-1f, 1f, 1.0f) );
        positions.add( new Vector4f(-1f,-1f,-1f, 1.0f) );
        positions.add( new Vector4f(-1f, 1f,-1f, 1.0f) );
        positions.add( new Vector4f( 1f, 1f,-1f, 1.0f) );
        positions.add( new Vector4f( 1f,-1f,-1f, 1.0f) );

        // Fill the colors
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        colors.add( new Vector4f( 1.0f, 0.0f, 0.0f, 1.0f) ); 
        
        triangularize(1, 0, 3, 2);
        triangularize(2, 3, 7, 6);
        triangularize(3, 0, 4, 7);
        triangularize(6, 5, 1, 2);
        triangularize(4, 5, 6, 7);
        triangularize(5, 4, 0, 1);

    }
    
    private void triangularize(int a, int b, int c, int d){
        triangles.add(new Triangle( positions.get(a),
                                    positions.get(c),
                                    positions.get(b),
                                    colors.get(a),
                                    colors.get(c),
                                    colors.get(b)));
        
        triangles.add(new Triangle( positions.get(a),
                                    positions.get(d),
                                    positions.get(c),
                                    colors.get(a),
                                    colors.get(d),
                                    colors.get(c)));
    }

    private float f(float x, float y, float z) {
        return  (float) (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    private void drawTetrahedron(Point3D[] p) {

        /*
         Tetrahedron layout:
               0
               *
              /|
             / |
          3 *-----* 1
             \ | /
              \|/
               *
               2
         */

        float isolevel = 1;

        char index = 0;
        for (int i = 0; i < 4; ++i)
            if (p[i].valor <= isolevel)
                index |= (1 << i);

    switch (index) {

        // we don't do anything if everyone is inside or outside
        case 0x00:
        case 0x0F:
            break;

        // only vert 0 is inside
        case 0x01:
            triangles.add(new Triangle(drawVert(p[0], p[1]),
                                       drawVert(p[0], p[3]),
                                       drawVert(p[0], p[2]),
                                       red(),
                                       red(),
                                       red()
                                      )
                         );
            break;

        // only vert 1 is inside
        case 0x02:
            triangles.add(new Triangle(drawVert(p[1], p[0]),
                                       drawVert(p[1], p[2]),
                                       drawVert(p[1], p[3]),
                                       red(),
                                       red(),
                                       red()
                                      )
                         );
            break;

        // only vert 2 is inside
        case 0x04:
            triangles.add(new Triangle(drawVert(p[2], p[0]),
                                       drawVert(p[2], p[3]),
                                       drawVert(p[2], p[1]),
                                       red(),
                                       red(),
                                       red()
                                      )
                         );
            break;

        // only vert 3 is inside
        case 0x08:
            triangles.add(new Triangle(drawVert(p[3], p[1]),
                                       drawVert(p[3], p[2]),
                                       drawVert(p[3], p[0]),
                                       red(),
                                       red(),
                                       red()
                                      )
                         );
            break;

        // verts 0, 1 are inside
        case 0x03:
            triangles.add(new Triangle(drawVert(p[3], p[0]),
                           drawVert(p[2], p[0]),
                           drawVert(p[1], p[3]),
                           red(),
                           red(),
                           red()
                          )
             );
            
            triangles.add(new Triangle(drawVert(p[2], p[0]),
                           drawVert(p[2], p[1]),
                           drawVert(p[1], p[3]),
                           red(),
                           red(),
                           red()
                          )
             );
            
            break;

        // verts 0, 2 are inside
        case 0x05:
            triangles.add(new Triangle(drawVert(p[3], p[0]),
                                       drawVert(p[1], p[2]),
                                       drawVert(p[1], p[0]),
                                       red(),
                                       red(),
                                       red()
                                      )
                         );
            
            triangles.add(new Triangle(drawVert(p[1], p[2]),
                                       drawVert(p[3], p[0]),
                                       drawVert(p[2], p[3]),
                                       red(),
                                       red(),
                                       red()
                                      )
                         );
            
            break;

        // verts 0, 3 are inside
        case 0x09:
            triangles.add(new Triangle(drawVert(p[0], p[1]),
                           drawVert(p[1], p[3]),
                           drawVert(p[0], p[2]),
                           red(),
                           red(),
                           red()
                          )
             );
            triangles.add(new Triangle(drawVert(p[1], p[3]),
                           drawVert(p[3], p[2]),
                           drawVert(p[0], p[2]),
                           red(),
                           red(),
                           red()
                          )
             );
            break;

        // verts 1, 2 are inside
        case 0x06:
            triangles.add(new Triangle(drawVert(p[0], p[1]),
                           drawVert(p[0], p[2]),
                           drawVert(p[1], p[3]),
                           red(),
                           red(),
                           red()
                          )
             );
            triangles.add(new Triangle(drawVert(p[1], p[3]),
                           drawVert(p[0], p[2]),
                           drawVert(p[3], p[2]),
                           red(),
                           red(),
                           red()
                          )
             );

            break;

        // verts 2, 3 are inside
        case 0x0C:
            triangles.add(new Triangle(drawVert(p[1], p[3]),
                           drawVert(p[2], p[0]),
                           drawVert(p[3], p[0]),
                           red(),
                           red(),
                           red()
                          )
             );
            
            triangles.add(new Triangle(drawVert(p[2], p[0]),
                                        drawVert(p[1], p[3]),
                                        drawVert(p[2], p[1]),
                                        red(),
                                        red(),
                                        red()
                                       )
                        );
            
            break;

        // verts 1, 3 are inside
        case 0x0A:
            triangles.add(new Triangle(drawVert(p[3], p[0]),
                           drawVert(p[1], p[0]),
                           drawVert(p[1], p[2]),
                           red(),
                           red(),
                           red()
                          )
             );
            triangles.add(new Triangle(drawVert(p[1], p[2]),
                           drawVert(p[2], p[3]),
                           drawVert(p[3], p[0]),
                           red(),
                           red(),
                           red()
                          )
             );
            
            break;

        // verts 0, 1, 2 are inside
        case 0x07:
            triangles.add(new Triangle(drawVert(p[3], p[0]),
                           drawVert(p[3], p[2]),
                           drawVert(p[3], p[1]),
                           red(),
                           red(),
                           red()
                          )
             );
            break;

        // verts 0, 1, 3 are inside
        case 0x0B:
            triangles.add(new Triangle(drawVert(p[2], p[1]),
                           drawVert(p[2], p[3]),
                           drawVert(p[2], p[0]),
                           red(),
                           red(),
                           red()
                          )
             );
            
            break;

        // verts 0, 2, 3 are inside
        case 0x0D:
            triangles.add(new Triangle(drawVert(p[1], p[0]),
                           drawVert(p[1], p[3]),
                           drawVert(p[1], p[2]),
                           red(),
                           red(),
                           red()
                          )
             );
            break;

        // verts 1, 2, 3 are inside
        case 0x0E:
            triangles.add(new Triangle(drawVert(p[0], p[1]),
                           drawVert(p[0], p[2]),
                           drawVert(p[0], p[3]),
                           red(),
                           red(),
                           red()
                          )
             );
            
            break;

        // what is this I don't even
        default:
            assert(false);
    }

}

    private Vector4f drawVert(Point3D p1, Point3D p2) {

        float x, y, z;

        x = (p1.x + p2.x) / 2.0f;
        y = (p1.y + p2.y) / 2.0f;
        z = (p1.z + p2.z) / 2.0f;
        
        return new Vector4f(x, y, z, 1);
    }
    
    private Vector4f red(){
        return new Vector4f(1f, 0f, 0f, 1f);
    }
}
