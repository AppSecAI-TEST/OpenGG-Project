/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.world.components.physics;

import com.opengg.core.math.Vector3f;

/**
 *
 * @author Javier
 */
public class Force {
    public Vector3f force = new Vector3f();
    public float velLimit = 0;
    public boolean frictionDisable = false;
}
