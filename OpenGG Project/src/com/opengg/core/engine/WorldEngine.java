/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 *
 */
package com.opengg.core.engine;

import com.opengg.core.math.Vector3f;
import com.opengg.core.util.Time;
import com.opengg.core.world.World;
import com.opengg.core.world.components.Component;
import com.opengg.core.world.components.ComponentHolder;
import com.opengg.core.world.components.physics.Collider;
import com.opengg.core.world.components.physics.CollisionData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Javier Coindreau
 */
public class WorldEngine{
    static LinkedList<Collider> colliders = new LinkedList<>();
    static ArrayList<Component> objs = new ArrayList<>();
    static Time t;
    
    static{
        t = new Time();
    }
    
    public static void addCollider(Collider c) {
        colliders.add(c);
    }
    
    static void processCollision(List<CollisionData> collisions){
        CollisionData info = collisions.get(0);
        if(info.c1physact){
            info.c1phys.velocity = info.c1phys.velocity.inverse();
        }
        if(info.c2physact){
            info.c2phys.velocity = info.c2phys.velocity.inverse();
        }
    }
    
    public static void checkColliders(){
        for(int i = 0; i < colliders.size(); i++){
            for(int j = i + 1; j < colliders.size(); j++){
                List<CollisionData> info = colliders.get(i).testForCollision(colliders.get(j));
                if(info == null || !info.isEmpty()){
                    processCollision(info);
                }
            }
        }
    }
    
    public static void addObjects(Component e){
        objs.add(e);
    }
    
    public static void update(){
        checkColliders();
        float delta = t.getDeltaMs();
        for(Component c : OpenGG.curworld.getChildren()){
            traverseUpdate(c, delta);
        }
    }
    
    private static void traverseUpdate(Component c, float delta){
        c.update(delta);
        if(c instanceof ComponentHolder){
            for(Component comp : ((ComponentHolder)c).getChildren()){
                traverseUpdate(comp, delta);
            }
        }
    }
    
    public static void useWorld(World w){
        OpenGG.curworld = w;
        w.useRenderables();
        colliders = w.useColliders();
    }
    
    public static World getCurrent(){
        return OpenGG.curworld;
    }
}
