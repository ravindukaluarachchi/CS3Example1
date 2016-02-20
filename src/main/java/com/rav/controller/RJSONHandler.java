/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rav.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

/**
 *
 * @author ravklk
 * @param <T>
 */
public class RJSONHandler<T> {
    
    List<T> list ;
    File    file;
    T t;
    Class<?> classType;
    
    public RJSONHandler(String className ,File file) throws ClassNotFoundException {
        list = new ArrayList<T>();
        classType = Class.forName(className);
        this.file = file;
    }
    
    public List<T> readList() throws IOException{
        ObjectMapper om = new ObjectMapper();
        TypeFactory typeFactory = om.getTypeFactory();
        JavaType type = typeFactory.constructCollectionType(List.class, classType);
        return om.readValue(file,type);
        
        
    }
    
    public void writeToList(T t) throws IOException{
        List<T> list = readList();
        list.add(t);
        ObjectMapper om = new ObjectMapper();
        om.writeValue(file, list);
    }
    
    public void writeToList(List<T> ts) throws IOException{
        List<T> list = readList();
        for (T t : ts) {
            list.add(t);
        }        
        ObjectMapper om = new ObjectMapper();
        om.writeValue(file, list);
    }
    
    
    
    
}
