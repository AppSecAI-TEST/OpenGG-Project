/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.engine;

import com.opengg.core.Vector3f;
import com.opengg.core.util.Time;
import com.opengg.core.world.components.Updatable;
import com.opengg.core.world.components.physics.Collider;
import com.opengg.core.world.components.physics.CollisionData;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Javier Coindreau 
 */
public class UpdateEngine{
    static LinkedList<Collider> colliders = new LinkedList<>();
    static ArrayList<Updatable> objs = new ArrayList<>();
    static Time t;
    
    static{
        t = new Time();
    }
    
    public static void processCollision(CollisionData info){
        if(info.c1physact){
            info.c1phys.velocity = new Vector3f(-info.c1phys.velocity.x, -info.c1phys.velocity.y, -info.c1phys.velocity.z);
        }
        if(info.c2physact){
            info.c2phys.velocity = new Vector3f(-info.c2phys.velocity.x, -info.c2phys.velocity.y, -info.c2phys.velocity.z);
        }
    }
    
    public static void checkColliders(){
        for(int i = 0; i < colliders.size(); i++){
            for(int j = i + 1; i < colliders.size(); i++){
                CollisionData info = colliders.get(i).testForCollision(colliders.get(j));
                if(info != null){
                    processCollision(info);
                }
            }
        }
    }
    
    public static void addObjects(Updatable e){
        objs.add(e);
    }
    
    public static void update(){
        float i = t.getDeltaSec();
        objs.stream().forEach((e) -> {
            e.update(i);
        });
        checkColliders();
    }
    
}
