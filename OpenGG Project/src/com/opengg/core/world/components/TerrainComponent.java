/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.world.components;

import com.opengg.core.math.Vector3f;
import com.opengg.core.render.texture.ArrayTexture;
import com.opengg.core.render.texture.Texture;
import com.opengg.core.world.Terrain;
import com.opengg.core.world.collision.AABB;
import com.opengg.core.world.components.physics.CollisionComponent;
import com.opengg.core.world.collision.TerrainCollider;

/**
 *
 * @author Javier
 */
public class TerrainComponent extends RenderComponent{
    Terrain t;
    CollisionComponent c;
    public Texture blotmap = Texture.blank;
    public ArrayTexture wow;
    
    public TerrainComponent(Terrain t){
        this.t = t;
        this.shader = "terrain";
    }
    
    public void enableRendering(){
        this.setDrawable(t.getDrawable());
        this.getWorld().addRenderable(this);
    }
    
    public void enableCollider(){
        c = new CollisionComponent(new AABB(new Vector3f(-15000,-15000,-15000), 15000,15000,15000), new TerrainCollider(t));
        c.setForceTest(true);
        this.attach(c);
    }
    
    public void setGroundArray(ArrayTexture tex){
        this.wow = tex;
    }
    
    public void setBlotmap(Texture tex){
        this.blotmap = tex;
    }
    
    public Terrain getTerrain(){
        return t;
    }
    
    public float getHeightAt(Vector3f pos){
        Vector3f np = pos.subtract(getPosition()).divide(getScale());
        float height = t.getHeight(np.x, np.z);
        return (height + getPosition().y) * getScale().y;  
    }
    
    public Vector3f getNormalAt(Vector3f pos){
        Vector3f np = pos.subtract(getPosition()).divide(getScale());
        Vector3f normal = t.getNormalAt(np.x, np.z);
        return normal;
    }
    
    @Override
    public void render(){
        this.blotmap.useTexture(1);
        this.wow.useTexture(0);
        super.render();
    }
}
