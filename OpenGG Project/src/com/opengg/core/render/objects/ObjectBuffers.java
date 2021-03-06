/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.render.objects;

import com.opengg.core.io.objloader.parser.OBJFace;
import com.opengg.core.io.objloader.parser.OBJMesh;
import com.opengg.core.io.objloader.parser.OBJModel;
import com.opengg.core.math.Vector2f;
import com.opengg.core.math.Vector3f;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.system.MemoryUtil;

/**
 *
 * @author Javier
 */
public class ObjectBuffers {
    static FloatBuffer genBuffer(OBJModel m, float transparency, float scale, Vector3f poffset){
        
        List<OBJFace> f = new ArrayList<>();
      
        m.getObjects().stream().forEach((obj) -> {
            obj.getMeshes().stream().forEach((ms) -> {
                f.addAll(ms.getFaces());
            });
        });
        
       
        FloatBuffer elements = MemoryUtil.memAllocFloat(m.getVertices().size() * 78);
        for (OBJFace fa : f){

            int i1 = fa.getReferences().get(0).vertexIndex;
            int i2 = fa.getReferences().get(1).vertexIndex;
            int i3 = fa.getReferences().get(2).vertexIndex;
            
//            if(i1 > 11950){
//                continue;
//            }
            
            float x1 = (m.getVertices().get(i1).x + poffset.x)*scale;
            float y1 = (m.getVertices().get(i1).y + poffset.y)*scale;
            float z1 = (m.getVertices().get(i1).z + poffset.z)*scale;
            float x2 = (m.getVertices().get(i2).x + poffset.x)*scale;
            float y2 = (m.getVertices().get(i2).y + poffset.y)*scale;
            float z2 = (m.getVertices().get(i2).z + poffset.z)*scale;
            float x3 = (m.getVertices().get(i3).x + poffset.x)*scale;
            float y3 = (m.getVertices().get(i3).y + poffset.y)*scale;
            float z3 = (m.getVertices().get(i3).z + poffset.z)*scale;
            
            int ni1 = 0, ni2 = 0, ni3 = 0;
            float xn = 0.1f, yn = 0.1f, zn = 0.1f;
            float xn2 = 0.1f, yn2 = 0.1f, zn2 = 0.1f;
            float xn3 = 0.1f, yn3 = 0.1f, zn3 = 0.1f;
            float u = 0.1f, v = 0.1f;
            float u2 = 0.1f, v2 = 0.1f;
            float u3 = 0.1f, v3 = 0.1f;
            int uv1= 0, uv2 = 0, uv3 = 0;
            if(fa.hasNormals()){
                ni1 = fa.getReferences().get(0).normalIndex;
                ni2 = fa.getReferences().get(1).normalIndex;
                ni3 = fa.getReferences().get(2).normalIndex;
                xn = m.getNormals().get(ni1).x;
                yn = m.getNormals().get(ni1).y;
                zn = m.getNormals().get(ni1).z;
                xn2 = m.getNormals().get(ni2).x;
                yn2 = m.getNormals().get(ni2).y;
                zn2 = m.getNormals().get(ni2).z;
                xn3 = m.getNormals().get(ni3).x;
                yn3 = m.getNormals().get(ni3).y;
                zn3 = m.getNormals().get(ni3).z;
            }
            if(fa.hasTextureCoordinates()){
                uv1 = fa.getReferences().get(0).texCoordIndex;
                uv2 = fa.getReferences().get(1).texCoordIndex;
                uv3 = fa.getReferences().get(2).texCoordIndex;
                u = m.getTexCoords().get(uv1).u;
                v = m.getTexCoords().get(uv1).v;
                u2 = m.getTexCoords().get(uv2).u;
                v2 = m.getTexCoords().get(uv2).v;
                u3 = m.getTexCoords().get(uv3).u;
                v3 = m.getTexCoords().get(uv3).v;
            }
            Random random = new Random();
            float colorg = random.nextFloat() % 10;
            float colorr = random.nextFloat() % 10;
            float colorb = random.nextFloat() % 10; 
            elements.put(x1).put(y1).put(z1).put(colorr).put(colorg).put(colorb).put(transparency).put(xn).put(yn).put(zn).put(u).put(v);
            float colorg2 = random.nextFloat() % 10;
            float colorr2 = random.nextFloat() % 10;
            float colorb2 = random.nextFloat() % 10;  
            elements.put(x2).put(y2).put(z2).put(colorr2).put(colorg2).put(colorb2).put(transparency).put(xn2).put(yn2).put(zn2).put(u2).put(v2);
            float colorg3 = random.nextFloat() % 10;
            float colorr3 = random.nextFloat() % 10;
            float colorb3 = random.nextFloat() % 10; 
            elements.put(x3).put(y3).put(z3).put(colorr3).put(colorg3).put(colorb3).put(transparency).put(xn3).put(yn3).put(zn3).put(u3).put(v3);

        }
        
        elements.flip();
        return elements;
    }  
    static FloatBuffer genBuffer(OBJModel m, OBJMesh msh, float transparency, float scale){
        
        List<OBJFace> f = msh.getFaces();
       
        FloatBuffer elements = MemoryUtil.memAllocFloat(m.getVertices().size() * 78);
        for (OBJFace fa : f){

            int i1 = fa.getReferences().get(0).vertexIndex;
            int i2 = fa.getReferences().get(1).vertexIndex;
            int i3 = fa.getReferences().get(2).vertexIndex;
            
//            if(i1 > 11950){
//                continue;
//            }
            
            float x1 = m.getVertices().get(i1).x*scale;
            float y1 = m.getVertices().get(i1).y*scale;
            float z1 = m.getVertices().get(i1).z*scale;
            float x2 = m.getVertices().get(i2).x*scale;
            float y2 = m.getVertices().get(i2).y*scale;
            float z2 = m.getVertices().get(i2).z*scale;
            float x3 = m.getVertices().get(i3).x*scale;
            float y3 = m.getVertices().get(i3).y*scale;
            float z3 = m.getVertices().get(i3).z*scale;
            
            int ni1 = 0, ni2 = 0, ni3 = 0;
            float xn = 0.1f, yn = 0.1f, zn = 0.1f;
            float xn2 = 0.1f, yn2 = 0.1f, zn2 = 0.1f;
            float xn3 = 0.1f, yn3 = 0.1f, zn3 = 0.1f;
            float u = 0.1f, v = 0.1f;
            float u2 = 0.1f, v2 = 0.1f;
            float u3 = 0.1f, v3 = 0.1f;
            int uv1= 0, uv2 = 0, uv3 = 0;
            if(fa.hasNormals()){
                ni1 = fa.getReferences().get(0).normalIndex;
                ni2 = fa.getReferences().get(1).normalIndex;
                ni3 = fa.getReferences().get(2).normalIndex;
                xn = m.getNormals().get(ni1).x;
                yn = m.getNormals().get(ni1).y;
                zn = m.getNormals().get(ni1).z;
                xn2 = m.getNormals().get(ni2).x;
                yn2 = m.getNormals().get(ni2).y;
                zn2 = m.getNormals().get(ni2).z;
                xn3 = m.getNormals().get(ni3).x;
                yn3 = m.getNormals().get(ni3).y;
                zn3 = m.getNormals().get(ni3).z;
            }
            if(fa.hasTextureCoordinates()){
                uv1 = fa.getReferences().get(0).texCoordIndex;
                uv2 = fa.getReferences().get(1).texCoordIndex;
                uv3 = fa.getReferences().get(2).texCoordIndex;
                u = m.getTexCoords().get(uv1).u;
                v = m.getTexCoords().get(uv1).v;
                u2 = m.getTexCoords().get(uv2).u;
                v2 = m.getTexCoords().get(uv2).v;
                u3 = m.getTexCoords().get(uv3).u;
                v3 = m.getTexCoords().get(uv3).v;
            }
            Random random = new Random();
            float colorg = random.nextFloat() % 10;
            float colorr = random.nextFloat() % 10;
            float colorb = random.nextFloat() % 10; 
            elements.put(x1).put(y1).put(z1).put(colorr).put(colorg).put(colorb).put(transparency).put(xn).put(yn).put(zn).put(u).put(v);
            float colorg2 = random.nextFloat() % 10;
            float colorr2 = random.nextFloat() % 10;
            float colorb2 = random.nextFloat() % 10; 
            elements.put(x2).put(y2).put(z2).put(colorr2).put(colorg2).put(colorb2).put(transparency).put(xn2).put(yn2).put(zn2).put(u2).put(v2);
            float colorg3 = random.nextFloat() % 10;
            float colorr3 = random.nextFloat() % 10;
            float colorb3 = random.nextFloat() % 10; 
            elements.put(x3).put(y3).put(z3).put(colorr3).put(colorg3).put(colorb3).put(transparency).put(xn3).put(yn3).put(zn3).put(u3).put(v3);

        }
        
        elements.flip();
        return elements;
    }
    static List<FloatBuffer> genMTLBuffer(OBJModel m, float transparency, float scale){
        List<FloatBuffer> buffets = new ArrayList();
        List<OBJFace> f = m.getObjects().get(0).getMeshes().get(0).getFaces();
        
        for(OBJMesh ms :m.getObjects().get(0).getMeshes()){
            FloatBuffer elements = MemoryUtil.memAllocFloat(m.getVertices().size() * 78);
            for (OBJFace fa : ms.getFaces()){

                int i1 = fa.getReferences().get(0).vertexIndex;
                int i2 = fa.getReferences().get(1).vertexIndex;
                int i3 = fa.getReferences().get(2).vertexIndex;

    //            if(i1 > 11950){
    //                continue;
    //            }

                float x1 = m.getVertices().get(i1).x*scale;
                float y1 = m.getVertices().get(i1).y*scale;
                float z1 = m.getVertices().get(i1).z*scale;
                float x2 = m.getVertices().get(i2).x*scale;
                float y2 = m.getVertices().get(i2).y*scale;
                float z2 = m.getVertices().get(i2).z*scale;
                float x3 = m.getVertices().get(i3).x*scale;
                float y3 = m.getVertices().get(i3).y*scale;
                float z3 = m.getVertices().get(i3).z*scale;

                int ni1 = 0, ni2 = 0, ni3 = 0;
                float xn = 0.1f, yn = 0.1f, zn = 0.1f;
                float xn2 = 0.1f, yn2 = 0.1f, zn2 = 0.1f;
                float xn3 = 0.1f, yn3 = 0.1f, zn3 = 0.1f;
                float u = 0.1f, v = 0.1f;
                float u2 = 0.1f, v2 = 0.1f;
                float u3 = 0.1f, v3 = 0.1f;
                int uv1= 0, uv2 = 0, uv3 = 0;
                if(fa.hasNormals()){
                    ni1 = fa.getReferences().get(0).normalIndex;
                    ni2 = fa.getReferences().get(1).normalIndex;
                    ni3 = fa.getReferences().get(2).normalIndex;
                    xn = m.getNormals().get(ni1).x;
                    yn = m.getNormals().get(ni1).y;
                    zn = m.getNormals().get(ni1).z;
                    xn2 = m.getNormals().get(ni2).x;
                    yn2 = m.getNormals().get(ni2).y;
                    zn2 = m.getNormals().get(ni2).z;
                    xn3 = m.getNormals().get(ni3).x;
                    yn3 = m.getNormals().get(ni3).y;
                    zn3 = m.getNormals().get(ni3).z;
                }
                if(fa.hasTextureCoordinates()){
                    uv1 = fa.getReferences().get(0).texCoordIndex;
                    uv2 = fa.getReferences().get(1).texCoordIndex;
                    uv3 = fa.getReferences().get(2).texCoordIndex;
                    u = m.getTexCoords().get(uv1).u;
                    v = m.getTexCoords().get(uv1).v;
                    u2 = m.getTexCoords().get(uv2).u;
                    v2 = m.getTexCoords().get(uv2).v;
                    u3 = m.getTexCoords().get(uv3).u;
                    v3 = m.getTexCoords().get(uv3).v;
                }
                Random random = new Random();
                float colorg = random.nextFloat() % 10;
                float colorr = random.nextFloat() % 10;
                float colorb = random.nextFloat() % 10; 
                elements.put(x1).put(y1).put(z1).put(colorr).put(colorg).put(colorb).put(transparency).put(xn).put(yn).put(zn).put(u).put(v);
                float colorg2 = random.nextFloat() % 10;
                float colorr2 = random.nextFloat() % 10;
                float colorb2 = random.nextFloat() % 10; 
                elements.put(x2).put(y2).put(z2).put(colorr2).put(colorg2).put(colorb2).put(transparency).put(xn2).put(yn2).put(zn2).put(u2).put(v2);
                float colorg3 = random.nextFloat() % 10;
                float colorr3 = random.nextFloat() % 10;
                float colorb3 = random.nextFloat() % 10; 
                elements.put(x3).put(y3).put(z3).put(colorr3).put(colorg3).put(colorb3).put(transparency).put(xn3).put(yn3).put(zn3).put(u3).put(v3);

            }

            elements.flip();
            buffets.add(elements);
        }
        
       return buffets;
        
    }
    
    public static Buffer[] getSquare(Vector2f v1, Vector2f v2, float z1 ,float transparency, boolean flippedTex){
        FloatBuffer sq = MemoryUtil.memAllocFloat(4*12);

        
        sq.put(v1.x).put(v1.y).put(z1).put(1).put(0).put(0).put(transparency).put(1f).put(0f).put(0f).put(0).put(0);
        sq.put(v1.x).put(v2.y).put(z1).put(0).put(1).put(0).put(transparency).put(1f).put(0f).put(0f).put(0).put(1);
        sq.put(v2.x).put(v2.y).put(z1).put(0).put(0).put(1).put(transparency).put(1f).put(0f).put(0f).put(1).put(1);   
        sq.put(v2.x).put(v1.y).put(z1).put(0).put(0).put(1).put(transparency).put(1f).put(0f).put(0f).put(1).put(0);           
        sq.flip();
        
        IntBuffer indices = MemoryUtil.memAllocInt(6);
        indices.put(new int[]{0,1,2,
            2,3,0});
        indices.flip();
        return new Buffer[]{sq, indices};
    }

    static FloatBuffer getSquare(float x1, float z1, float x2, float z2, float y1,float y2, float y3, float y4,  float transparency,boolean flippedTex){
        FloatBuffer sq = MemoryUtil.memAllocFloat(6*12);
        int i, i2;
        if(flippedTex){
            i = 1;
            i2 = 0;
            
        }else{
            i = 0;
            i2 = 1;
        }
        
        sq.put(x1).put(y1).put(z1).put(1).put(0).put(0).put(transparency).put(0f).put(0f).put(1f).put(1).put(i);
        sq.put(x1).put(y2).put(z2).put(0).put(1).put(0).put(transparency).put(0f).put(0f).put(1f).put(1).put(i2);
        sq.put(x2).put(y3).put(z1).put(0).put(0).put(1).put(transparency).put(0f).put(0f).put(1f).put(0).put(i);
        sq.put(x2).put(y3).put(z1).put(0).put(1).put(0).put(transparency).put(0f).put(0f).put(1f).put(0).put(i);
        sq.put(x2).put(y4).put(z2).put(0).put(0).put(1).put(transparency).put(0f).put(0f).put(1f).put(0).put(i2);
        sq.put(x1).put(y2).put(z2).put(1).put(0).put(0).put(transparency).put(0f).put(0f).put(1f).put(1).put(i2);
        
        sq.flip();
        return sq;
    }

    static FloatBuffer createDefaultBufferData(int size){
        FloatBuffer f = MemoryUtil.memAllocFloat(size);
        for(int i = 0; i < size; i++){
            f.put(0);
        }
        f.flip();
        return f;
    }
    
    static Buffer[] genCube(float size){
        FloatBuffer sq = MemoryUtil.memAllocFloat(8*12);
        
        sq.put(new float[]{-size,-size,-size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.put(new float[]{size,-size,-size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.put(new float[]{size,size,-size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.put(new float[]{-size,size,-size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.put(new float[]{-size,-size,size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.put(new float[]{size,-size,size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.put(new float[]{size,size,size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.put(new float[]{-size,size,size});
        sq.put(new float[]{1,1,1,1,1,1,1,1,1});
        
        sq.flip();
        
        IntBuffer ib = MemoryUtil.memAllocInt(6 * 6);
        
        ib.put(new int[]{
                1,2,0,
                2,3,0,
                6,2,1,
                1,5,6,
                6,5,4,
                4,7,6,
                6,3,2,
                7,3,6,
                3,7,0,
                7,4,0,
                5,1,0,
                4,5,0
               });
        ib.flip();
        
        return new Buffer[]{sq,ib};
    }
    static Buffer[] genQuadPrism(Vector3f c1, Vector3f c2){
        FloatBuffer d = MemoryUtil.memAllocFloat(8*12);
        d.put(c1.x).put(c1.y).put(c1.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1);
        d.put(c1.x).put(c1.y).put(c2.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(0).put(1);
        d.put(c1.x).put(c2.y).put(c2.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1);
        d.put(c1.x).put(c2.y).put(c1.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(0);
        
        d.put(c2.x).put(c1.y).put(c1.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1);
        d.put(c2.x).put(c1.y).put(c2.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(0).put(1);
        d.put(c2.x).put(c2.y).put(c2.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(0);
        d.put(c2.x).put(c2.y).put(c1.z).put(1).put(1).put(1).put(1).put(1).put(1).put(1).put(0).put(0);
        d.flip();
        
        IntBuffer d2 = MemoryUtil.memAllocInt(6*4);
        d2.put(new int[]{0,1,3,1,2,3});
        d2.put(new int[]{4,6,7,5,6,7});
        
        d2.put(0).put(1).put(4).put(1).put(5).put(4);
        d2.put(2).put(3).put(6).put(3).put(7).put(6);
        
        
        d2.flip();
        return new Buffer[]{d,d2};
    }
}
