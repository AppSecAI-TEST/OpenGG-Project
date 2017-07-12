/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.world.components.viewmodel;

import com.opengg.core.world.components.Component;

/**
 *
 * @author Javier
 */
public class GenericComponentViewModel extends ComponentViewModel{

    @Override
    public void createMainViewModel() {}

    @Override
    public ViewModelInitializer getInitializer() {
        throw new UnsupportedOperationException("Cannot create a generic viewmodel initializer, this class is only usable for emergency support for components without custom ViewModels");
    }

    @Override
    public Component getFromInitializer(ViewModelInitializer init) {
        throw new UnsupportedOperationException("Cannot create a generic viewmodel, this class is only usable for emergency support for components without custom ViewModels");
    }

    @Override
    public void onChange(ViewModelElement element) {}
    
}
