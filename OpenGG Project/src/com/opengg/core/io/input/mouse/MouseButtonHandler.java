/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.io.input.mouse;

/**
 *
 * @author Javier
 */
public class MouseButtonHandler implements IMouseButtonHandler{
    boolean[] buttons = new boolean[50];
    
    @Override
    public boolean isButtonDown(int button) {
        return buttons[button];
    }
    
}
