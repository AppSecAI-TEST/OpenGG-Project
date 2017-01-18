/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.render.objects;

import com.opengg.core.math.Vector3f;
import com.opengg.core.io.objloader.parser.OBJParser;
import com.opengg.core.render.drawn.Drawable;
import com.opengg.core.render.drawn.DrawnObject;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class ObjectCreator {
    public static Drawable createQuadPrism(Vector3f c1, Vector3f c2){
        Buffer[] b = createQuadPrismBuffers(c1,c2);
        return new DrawnObject((FloatBuffer)b[0], (IntBuffer)b[1]);
    }
    public static Buffer[] createQuadPrismBuffers(Vector3f c1, Vector3f c2){
        return ObjectBuffers.genQuadPrism(c1,c2);
    }
    public static Drawable createCube(float size){
        FloatBuffer b = ObjectBuffers.genCube(size);
        return new DrawnObject(b);
    }
    public static Drawable createOldModel(URL model){
        return new DrawnObject(createOldModelBuffer(model));
    }
    public static FloatBuffer createOldModelBuffer(URL model){
        try {
            return ObjectBuffers.genBuffer(new OBJParser().parse(model), 1f, 1f, new Vector3f());
        } catch (IOException ex) {
            Logger.getLogger(ObjectCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
