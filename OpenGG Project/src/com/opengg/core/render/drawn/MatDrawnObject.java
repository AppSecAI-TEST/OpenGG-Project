/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.render.drawn;

import com.opengg.core.math.Matrix4f;
import com.opengg.core.model.Material;
import com.opengg.core.render.shader.ShaderController;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

/**
 *
 * @author Javier
 */
public class MatDrawnObject implements Drawable {
    Drawable d;
    Material m = Material.defaultmaterial;
    
    public void setM(Material m) {
        this.m = m;
    }

    public MatDrawnObject(Drawable d){
        this.d = d;
    }
    
    public MatDrawnObject(Drawable d, Material m){
        this.d = d;
        this.m = m;
        m.loadTextures();
    }
    
    public MatDrawnObject(FloatBuffer b, int vertsize){
        d = new DrawnObject(b,vertsize);
    }
    
    public MatDrawnObject(List<FloatBuffer> buffers, int vertSize){
        d = new DrawnObject(buffers,vertSize);
    }
    
    public MatDrawnObject(FloatBuffer b, IntBuffer index, Material m){
        d = new DrawnObject(b,index);
        this.m = m;
        m.loadTextures();
    }
    
    public MatDrawnObject(FloatBuffer b, IntBuffer index){
        this(b, index, Material.defaultmaterial);
    }
    
    public Material getMaterial(){
        return m;
    }
    
    @Override
    public void render() {
        if(m.Kd != null)
            m.Kd.useTexture(0);     
        if(m.norm != null) 
            m.norm.useTexture(3);
        if(m.Ks != null) 
            m.Ks.useTexture(4); 
        if(m.Ns != null)
            m.Ns.useTexture(5);
        ShaderController.passMaterial(m);
        d.render();
    }

    @Override
    public void setMatrix(Matrix4f m) {
        d.setMatrix(m);
    }

    @Override
    public Matrix4f getMatrix() {
        return d.getMatrix();
    }

    @Override
    public void destroy() {
        d.destroy();
    }
    
}
