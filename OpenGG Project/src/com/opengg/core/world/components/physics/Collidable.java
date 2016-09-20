/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.world.components.physics;

import com.opengg.core.world.components.Positioned;

/**
 *
 * @author Javier
 */
public interface Collidable{
    public boolean onCollision(Collidable c);
    public BoundingBox[] getAreas();
}
