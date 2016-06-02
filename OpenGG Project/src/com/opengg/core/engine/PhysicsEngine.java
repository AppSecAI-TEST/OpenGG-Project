/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.engine;

import static com.opengg.core.util.GlobalUtil.print;
import com.opengg.core.world.entities.Entity;
import com.opengg.core.world.entities.EntityBuilder;
import com.opengg.core.world.entities.resources.EntitySupportEnums.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ethachu19
 * @deprecated
 */
public class PhysicsEngine extends EntityBuilder {

    private static boolean shouldClose = false;
    private static long currentTime, newTime;
    private static float delta, accumulator = 0f, t = 0f; 
    private static final float dt = 0.01f;
    public static void process() {
        currentTime = System.currentTimeMillis();
        shouldClose = false;
        while (!shouldClose) {
//            print("in loop");
            if (EntityList.size() > entityCap) {
                for (int i = 0; EntityList.size() > entityCap; ++i) {
                    EntityBuilder.destroyEntity(i);
                }
            }
            newTime = System.currentTimeMillis();
            delta = (newTime - currentTime) / 1000f;
            if (delta > 0.0f) {
                accumulator += delta;
                for (; accumulator >= dt; accumulator -= dt, t+= dt)
                    for (Entity update: EntityList)
                        update.update(t, dt);
            }
                
            for (Entity collide : EntityList) {
                if (collide.collision != Collide.Collidable) {
                    continue;
                }
                for (Entity collidee : EntityList) {
                    if (collide.equals(collidee) || collidee.collision == Collide.Uncollidable) {
                        continue;
                    }
                }
            }
            for (int i = 0; i < AddStack.size(); i++) {
                EntityList.add(AddStack.get(i));
            }
            AddStack.clear();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(PhysicsEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void killProcess() {
        PhysicsEngine.shouldClose = true;
    }
}