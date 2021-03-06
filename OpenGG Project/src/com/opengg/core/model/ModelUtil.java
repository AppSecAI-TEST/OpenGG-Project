/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.opengg.core.model;

import com.opengg.core.engine.OpenGG;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.system.MemoryUtil;

/**
 *
 * @author Javier
 */
public class ModelUtil {
    public static void findAdjacencies(Mesh m){
        List<Edge> edges = new ArrayList<>();
        int i = 0;
        for(Face f : m.faces){
            
            Edge e1 = new Edge();
            e1.faceid = i;
            e1.edgeid = 1;
            e1.f1 = f.v1;
            e1.f2 = f.v2;
            edges.add(e1);
            
            Edge e2 = new Edge();
            e2.faceid = i;
            e2.edgeid = 2;
            e2.f1 = f.v2;
            e2.f2 = f.v3;
            edges.add(e2);
            
            Edge e3 = new Edge();
            e3.faceid = i;
            e3.edgeid = 3;
            e3.f1 = f.v3;
            e3.f2 = f.v1;
            edges.add(e3);
            
            i++;
        }
        
        for(int ii = 0; ii < edges.size(); ii++){
            for(int j = ii + 1; j < edges.size(); j++){
                Edge e1 = edges.get(ii);
                Edge e2 = edges.get(j);
                if((e1.f1.equals(e2.f1) && e1.f2.equals(e2.f2)) || (e1.f1.equals(e2.f2) && e1.f2.equals(e2.f1))){
                    e1.adjfaceid = e2.faceid;
                    e2.adjfaceid = e1.faceid;
                }
            }
        }
        
        edges.stream().filter((edge) -> (edge.adjfaceid != -1)).forEach((edge) -> {
            Face f = m.faces.get(edge.faceid);
            if(edge.edgeid == 1)
                f.adj1 = edge.adjfaceid;
            else if(edge.edgeid == 2)
                f.adj2 = edge.adjfaceid;
            else if(edge.edgeid == 3)
                f.adj3 = edge.adjfaceid;
        });
        
        m.adjacency = true;
    }
    
    public static void makeadamnadjacencyvbo(Mesh mesh){
        int nextVertexIndex = 0;
        HashMap<FaceVertex, Integer> indexMap = new HashMap<>();
        ArrayList<FaceVertex> faceVertexList = new ArrayList<>();
        for (Face face : mesh.faces) {
            FaceVertex vertex = face.v1;
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            vertex = face.v2;
            
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            vertex = face.v3;
            
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            vertex = findAdjacentVertex(face, 1, mesh.faces);
            
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            vertex = findAdjacentVertex(face, 2, mesh.faces);
            
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            vertex = findAdjacentVertex(face, 3, mesh.faces);
            
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            
        }

        FloatBuffer verticeAttributes;
        if(OpenGG.lwjglInitialized()){
            verticeAttributes = MemoryUtil.memAllocFloat(faceVertexList.size() * 12);
        }else{
            verticeAttributes = FloatBuffer.allocate(faceVertexList.size() * 12);
        }
        
        for (FaceVertex vertex : faceVertexList) {
            verticeAttributes.put(vertex.v.x);
            verticeAttributes.put(vertex.v.y);
            verticeAttributes.put(vertex.v.z);
            verticeAttributes.put(1f);
            verticeAttributes.put(1f);
            verticeAttributes.put(1f);
            verticeAttributes.put(1f);

            verticeAttributes.put(vertex.n.x);
            verticeAttributes.put(vertex.n.y);
            verticeAttributes.put(vertex.n.z);

            verticeAttributes.put(vertex.t.x);
            verticeAttributes.put(vertex.t.y);

        }
        verticeAttributes.flip();

        int indicesCount = mesh.faces.size() * 6;
        
        IntBuffer indices;
        if(OpenGG.lwjglInitialized()){
            indices = MemoryUtil.memAllocInt(indicesCount);
        }else{
            indices = IntBuffer.allocate(indicesCount);
        }
        
        for (Face face : mesh.faces) {
            
            FaceVertex vertex = face.v1;
            int index = indexMap.get(vertex);
            indices.put(index);
            
            vertex = findAdjacentVertex(face, 1, mesh.faces);
            index = indexMap.get(vertex);
            indices.put(index);
            
            vertex = face.v2;
            index = indexMap.get(vertex);
            indices.put(index);
            
            vertex = findAdjacentVertex(face, 2, mesh.faces);
            index = indexMap.get(vertex);
            indices.put(index);
            
            vertex = face.v3;
            index = indexMap.get(vertex);
            indices.put(index);
            
            vertex = findAdjacentVertex(face, 3, mesh.faces);
            index = indexMap.get(vertex);
            indices.put(index);
        }     
        indices.flip();

        mesh.updateVBO(verticeAttributes, indices);
    }
    
    public static void makeadamnvbo(Mesh mesh){
        HashMap<FaceVertex, Integer> indexMap = new HashMap<>();
        int nextVertexIndex = 0;
        ArrayList<FaceVertex> faceVertexList = new ArrayList<>();
        for (Face face : mesh.faces) {
            FaceVertex vertex = face.v1;
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            vertex = face.v2;
            
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
            
            vertex = face.v3;
            
            if (!indexMap.containsKey(vertex)) {
                indexMap.put(vertex, nextVertexIndex++);
                faceVertexList.add(vertex);
            }
        }       
        
        FloatBuffer verticeAttributes;
        if(OpenGG.lwjglInitialized()){
            verticeAttributes = MemoryUtil.memAllocFloat(faceVertexList.size() * 12);
        }else{
            verticeAttributes = FloatBuffer.allocate(faceVertexList.size() * 12);
        }
        
        for (FaceVertex vertex : faceVertexList) {
            verticeAttributes.put(vertex.v.x);
            verticeAttributes.put(vertex.v.y);
            verticeAttributes.put(vertex.v.z);
            
            verticeAttributes.put(1f);
            verticeAttributes.put(1f);
            verticeAttributes.put(1f);
            verticeAttributes.put(1f);

            verticeAttributes.put(vertex.n.x);
            verticeAttributes.put(vertex.n.y);
            verticeAttributes.put(vertex.n.z);

            verticeAttributes.put(vertex.t.x);
            verticeAttributes.put(vertex.t.y);

        }
        verticeAttributes.flip();
        
        
        int indicesCount = mesh.faces.size() * 3;
        IntBuffer indices;
        if(OpenGG.lwjglInitialized()){
            indices = MemoryUtil.memAllocInt(indicesCount);
        }else{
            indices = IntBuffer.allocate(indicesCount);
        }
        
        for (Face face : mesh.faces) {
            FaceVertex vertex = face.v1;
            int index = indexMap.get(vertex);
            indices.put(index);
            
            vertex = face.v2;
            index = indexMap.get(vertex);
            indices.put(index);
            
            vertex = face.v3;
            index = indexMap.get(vertex);
            indices.put(index);
        }
        indices.flip();
        
        mesh.updateVBO(verticeAttributes, indices);
    }
    
    public static void makeadamnvbo(List<Mesh> meshes) {
        meshes.stream().parallel().forEach((mesh) -> {
            makeadamnvbo(mesh);
        });
    }
    public static void makeadamnfacelist(Mesh mesh, int[] adjacencies){
        makeadamnfacelist(mesh);
        for(int i = 0; i < mesh.faces.size(); i++){
            int j = i * 3;
            Face f = mesh.faces.get(i);
            f.adj1 = adjacencies[i + 0];
            f.adj2 = adjacencies[i + 1];
            f.adj3 = adjacencies[i + 2];
        }
        mesh.adjacency = true;
    }
    public static void makeadamnfacelist(Mesh mesh){
        List<Face> faces = new ArrayList<>();
            
        List<FaceVertex> vertices = new ArrayList<>();
        mesh.inddata.rewind();
        mesh.vbodata.rewind();
        for(int i = 0; i < mesh.inddata.limit(); i++){
            int index = mesh.inddata.get(i);
            mesh.vbodata.position(index * 12);
            
            FaceVertex fv = new FaceVertex();
            fv.v.x = mesh.vbodata.get();
            fv.v.y = mesh.vbodata.get();
            fv.v.z = mesh.vbodata.get();

            mesh.vbodata.get();
            mesh.vbodata.get();
            mesh.vbodata.get();
            mesh.vbodata.get();
            
            fv.n.x = mesh.vbodata.get();
            fv.n.y = mesh.vbodata.get();
            fv.n.z = mesh.vbodata.get();
      
            fv.t.x = mesh.vbodata.get();
            fv.t.y = mesh.vbodata.get();
            
            
            vertices.add(fv);
            
        }
        mesh.vbodata.rewind();
        
        for(int i = 0; i < vertices.size(); i += 3){
            Face f = new Face();

            f.v1 = (vertices.get(i + 0));
            f.v2 = (vertices.get(i + 1));
            f.v3 = (vertices.get(i + 2));

            faces.add(f);
        }

        mesh.faces = faces;
    }
    
    public static void makeadamnfacelist(List<Mesh> meshes){
        for(Mesh mesh : meshes){
            makeadamnfacelist(mesh);
        }
    }
    
    public static ArrayList<BuilderFace> splitQuads(ArrayList<BuilderFace> faceList) {
        ArrayList<BuilderFace> triangleList = new ArrayList<>();
        int countTriangles = 0;
        int countQuads = 0;
        int countNGons = 0;
        for (BuilderFace face : faceList) {
            if (face.vertices.size() == 3) {
                countTriangles++;
                triangleList.add(face);
            } else if (face.vertices.size() == 4) {
                countQuads++;
                FaceVertex v1 = face.vertices.get(0);
                FaceVertex v2 = face.vertices.get(1);
                FaceVertex v3 = face.vertices.get(2);
                FaceVertex v4 = face.vertices.get(3);
                BuilderFace f1 = new BuilderFace();
                f1.map = face.map;
                f1.material = face.material;
                f1.add(v1);
                f1.add(v2);
                f1.add(v3);
                triangleList.add(f1);
                BuilderFace f2 = new BuilderFace();
                f2.map = face.map;
                f2.material = face.material;
                f2.add(v1);
                f2.add(v3);
                f2.add(v4);
                triangleList.add(f2);
            } else {
                countNGons++;
            }
        }
        int texturedCount = 0;
        int normalCount = 0;
        for (BuilderFace face : triangleList) {
            if ((face.vertices.get(0).n != null)
                    && (face.vertices.get(1).n != null)
                    && (face.vertices.get(2).n != null)) {
                normalCount++;
            }
            if ((face.vertices.get(0).t != null)
                    && (face.vertices.get(1).t != null)
                    && (face.vertices.get(2).t != null)) {
                texturedCount++;
            }
        }

        return triangleList;
    }
    
    public static List<Face> builderToFace(List<BuilderFace> bfs){
        List<Face> faces = new ArrayList<>();
        for(BuilderFace bf : bfs){
            Face face = new Face();
            face.v1 = bf.vertices.get(0);
            face.v2 = bf.vertices.get(1);
            face.v3 = bf.vertices.get(2);
            faces.add(face);
        }
        return faces;
    }
    
    private static FaceVertex findAdjacentVertex(Face f, int vid, List<Face> faces){
        int i = 0;
        if(vid == 1){
            i = f.adj1;
        }else if(vid == 2){
            i = f.adj2;
        }else if(vid == 3){
            i = f.adj3;
        }
        
        if(i == -1){
            return f.v1;
        }
        
        Face other = faces.get(i);
        
        FaceVertex adj = new FaceVertex();
        if(vid == 1){
            int v1e = 0, v2e = 0;
            if(f.v1.equals(other.v1)){v1e = 1;
            }else if(f.v1.equals(other.v2)){v1e = 2;
            }else if(f.v1.equals(other.v3)){v1e = 3;
            }
            
            if(f.v2.equals(other.v1)){v2e = 1;
            }else if(f.v2.equals(other.v2)){v2e = 2;
            }else if(f.v2.equals(other.v3)){v2e = 3;
            }
            
            if(v1e == 1){
                if(v2e == 2){adj = other.v3;}
                if(v2e == 3){adj = other.v2;}
            }
            
            if(v1e == 2){
                if(v2e == 1){adj = other.v3;}
                if(v2e == 3){adj = other.v1;}
            }
            if(v1e == 3){
                if(v2e == 1){adj = other.v2;}
                if(v2e == 2){adj = other.v1;}
            }
            
        }else if(vid == 2){
            
            int v2e = 0, v3e = 0;
            if(f.v2.equals(other.v1)){v2e = 1;
            }else if(f.v2.equals(other.v2)){v2e = 2;
            }else if(f.v2.equals(other.v3)){v2e = 3;
            }
            
            if(f.v3.equals(other.v1)){v3e = 1;
            }else if(f.v3.equals(other.v2)){v3e = 2;
            }else if(f.v3.equals(other.v3)){v3e = 3;
            }
            
            if(v2e == 1){
                if(v3e == 2){adj = other.v3;}
                if(v3e == 3){adj = other.v2;}
            }
            
            if(v2e == 2){
                if(v3e == 1){adj = other.v3;}
                if(v3e == 3){adj = other.v1;}
            }
            if(v2e == 3){
                if(v3e == 1){adj = other.v2;}
                if(v3e == 2){adj = other.v1;}
            }
            
        }else if(vid == 3){
            
            int v3e = 0, v1e = 0;
            if(f.v3.equals(other.v1)){v3e = 1;
            }else if(f.v3.equals(other.v2)){v3e = 2;
            }else if(f.v3.equals(other.v3)){v3e = 3;
            }
            
            if(f.v1.equals(other.v1)){v1e = 1;
            }else if(f.v1.equals(other.v2)){v1e = 2;
            }else if(f.v1.equals(other.v3)){v1e = 3;
            }
            
            if(v3e == 1){
                if(v1e == 2){adj = other.v3;}
                if(v1e == 3){adj = other.v2;}
            }
            
            if(v3e == 2){
                if(v1e == 1){adj = other.v3;}
                if(v1e == 3){adj = other.v1;}
            }
            if(v3e == 3){
                if(v1e == 1){adj = other.v2;}
                if(v1e == 2){adj = other.v1;}
            }
            
        }
        if(adj.equals(new FaceVertex())){
            adj = f.v1;
        }
        return adj;
    }
}
