/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.gui;

import com.opengg.core.Vector2f;
import com.opengg.core.Vector3f;
import com.opengg.core.render.shader.ShaderHandler;
import com.opengg.core.render.shader.ShaderProgram;
import com.opengg.core.world.Camera;

/**
 *
 * @author Javier
 */
public class GUI {
    Camera c;
    Vector2f low,high;
    public void setupGUI(Vector2f lowBound, Vector2f highBound){
        low = lowBound;
        high = highBound;
        c = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,0));
    }
    public void startGUI(){
        ShaderHandler.setOrtho(low.x, high.x, low.y, high.y, 0.2f, 10);      
        ShaderHandler.setLightPos(new Vector3f(0,50,0));
        c.setPos(new Vector3f(0,0,0));
        c.setRot(new Vector3f(0,0,0));
        ShaderHandler.setView(c);
    }
}
