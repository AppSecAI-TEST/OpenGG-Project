/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opengg.core.util;

import java.nio.ByteBuffer;

/**
 *
 * @author Javier
 */
public class BufferUtils {
    public static byte[] get(ByteBuffer source){
        if(source.hasArray()){
            return source.array();
        }else{
            source.rewind();
            System.out.println(source.limit());
            byte[] array = new byte[source.limit()];
            for(int i = 0; i < array.length; i++){
                array[i] = source.get();
                System.out.println(array[i]);
            }
            return array;
        }
    }
}
