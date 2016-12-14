/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.world.components.particle;

import com.opengg.core.math.Vector3f;

/**
 *
 * @author Warren
 * 
 * This class represents a single particle
 */
public class Particle {
    private Vector3f position;

    public Vector3f getPosition() {
        return position;
    }
    private Vector3f velocity;
    private Vector3f gravity;
    private float timeAlive = 0;
    private float timeOfLife;
    private float scale;
    
    public Particle(Vector3f position, Vector3f velocity, Vector3f gravity, float lifeLength, float scale) {
        this.position = position;
        this.velocity = velocity;
        this.gravity = gravity;
        this.timeOfLife = lifeLength;
        this.scale = scale;
    }
    
    public boolean update(){
        velocity = velocity.add(gravity);
        position = position.add(velocity);

        timeAlive++;
        return timeAlive > timeOfLife;
    }
    
    
    
    
}