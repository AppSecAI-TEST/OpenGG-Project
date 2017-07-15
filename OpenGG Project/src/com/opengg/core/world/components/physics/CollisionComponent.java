/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.world.components.physics;

import com.opengg.core.math.Vector3f;
import com.opengg.core.world.components.physics.PhysicsComponent;
import com.opengg.core.world.collision.AABB;
import com.opengg.core.world.collision.Collider;
import com.opengg.core.world.collision.Collision;
import com.opengg.core.world.components.triggers.Trigger;
import com.opengg.core.world.components.triggers.TriggerInfo;
import static com.opengg.core.world.components.triggers.TriggerInfo.SINGLE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ethachu19
 */
public class CollisionComponent extends Trigger{
    AABB main;
    List<Collider> boxes = new ArrayList<>();
    boolean lastcollided = false;
    boolean forcetest = false;

    public CollisionComponent(AABB main, Collection<Collider> all) {
        this.main = main;
        boxes.addAll(all);
        setColliderParent();
    }
    
    public CollisionComponent(AABB main, Collider... all) {
        this.main = main;
        boxes.addAll(Arrays.asList(all));
        setColliderParent();
    }
    
    public void setForceTest(boolean test){
        forcetest = test;
    }
    
    private void setColliderParent(){
        for(Collider c : boxes){
            c.setParent(this);
        }
    }
    
    public void addBoundingBox(Collider bb) {
        boxes.add(bb);
    }

    public List<Collision> testForCollision(CollisionComponent other) {
        List<Collision> dataList = new ArrayList<>();
        
        if (!main.isColliding(other.main) && !(this.forcetest || other.forcetest))
            return dataList;

        boolean collided = false;
        for (Collider x: this.boxes) {
            for(Collider y: other.boxes) {
                Collision data = x.isColliding(y);
                if ((data) != null){
                    collided = true;
                    data.thiscollider = this;
                    data.other = other;
                    dataList.add(data);
                }
            }
        }
        
        this.collisionTrigger(dataList);
        other.collisionTrigger(dataList);
        
        return dataList;
    }

    public PhysicsComponent getPhysicsComponent(){
        if(parent instanceof PhysicsComponent){
            return (PhysicsComponent)parent;
        }
        return null;
    }
    
    @Override
    public void update(float delta) {
        Vector3f fpos = getPosition();
        main.recenter(fpos);
    }
    
    public void collisionTrigger(List<Collision> data){
        TriggerInfo ti = new TriggerInfo();
        ti.info = "collision";
        ti.type = SINGLE;
        ti.source = this;
        ti.data = data;
        trigger(ti);
        lastcollided = true;
    }
}
