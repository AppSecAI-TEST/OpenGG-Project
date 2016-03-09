/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.world.physics;

import com.opengg.core.Vector3f;

/**
 *
 * @author ethachu19
 */
public class BoundingBox {
    public static final int MAX = 1, MIN = 0;
    private float length, width, height;
    private Vector3f[] vertices = {new Vector3f(), new Vector3f()};
    
    public BoundingBox(Vector3f pos, float length, float width, float height) {
        this.length = length;
        this.width = width;
        this.height = height;
        recenter(pos);
    }
    
    public void recenter(Vector3f pos) {
        vertices[MIN].y = pos.y;
        vertices[MIN].x = pos.x - width / 2;
        vertices[MIN].z = pos.z - length / 2;

        vertices[MAX].y = pos.y + height;
        vertices[MAX].x = pos.x + width / 2;
        vertices[MAX].z = pos.z + length / 2;
    }
    
    public boolean isColliding(BoundingBox x) {
        return !(vertices[MAX].x < x.vertices[MIN].x || 
             vertices[MAX].y < x.vertices[MIN].y ||
             vertices[MAX].z < x.vertices[MIN].z ||
             vertices[MIN].x > x.vertices[MAX].x || 
             vertices[MIN].y > x.vertices[MAX].y ||
             vertices[MIN].z > x.vertices[MAX].z);
    }
}