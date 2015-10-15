/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.world;

import com.opengg.core.Vector3f;
import com.opengg.core.world.entities.Entity;
import com.opengg.core.world.entities.EntityFactory;
import com.opengg.core.render.DrawnObject;

/**
 *
 * @author Javier
 */
public class WorldObject {
    private Vector3f pos;
    private Vector3f rot;
    private Entity e;
    private DrawnObject d;
    public WorldObject(Vector3f pos, Vector3f rot){
        this.pos = pos;
        this.rot = rot;
        e = EntityFactory.generateEntity(Entity.EntityType.Static, pos.x, pos.y, pos.z, rot, 10, 2);
    }
    public WorldObject(){
        pos = new Vector3f(0,0,0);
        rot = new Vector3f(0,0,0);
        e = EntityFactory.generateEntity(Entity.EntityType.Static, pos.x, pos.y, pos.z, rot, 10, 2);
    }
    public WorldObject(Vector3f pos, Vector3f rot, Entity e){
        this.pos = pos;
        this.rot = rot;
        this.e = EntityFactory.generateEntity(e);
        this.e.setXYZ(pos.x, pos.y, pos.z);
        this.e.setForce(rot);
    }
    public WorldObject(Entity e){
        pos = new Vector3f(0,0,0);
        rot = new Vector3f(0,0,0);
        this.e = EntityFactory.generateEntity(e);
    }
}