/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.world.components.viewmodel;

import com.opengg.core.world.components.Component;
import com.opengg.core.world.components.WorldObject;

/**
 *
 * @author Javier
 */
public class WorldObjectViewModel extends ViewModel{

    @Override
    public void createMainViewModel() {
        
    }

    @Override
    public Initializer getInitializer() {
        return new Initializer();
    }

    @Override
    public Component getFromInitializer(Initializer init) {
        return new WorldObject();
    }

    @Override
    public void onChange(Element element) {
        
    }

    @Override
    public void updateViews() {
       
    }
    
}