package com.example.citeapp;


import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase es el modelo de datos de una persona
 * @author Carlos Daniel Hernandez Chauteco
 */
public class Person {
    //constantes
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String IMAGE_PATH = "imagePath";
    public static final String AGE = "age";


    String name;
    String email;
    String imageUrl;
    String age;

    //variblesUtiles en ciertos casos
    String chatId = null;
    String userId = null;

    /**
     * @param name nombre de la persona
     * @param email email de la persona
     * @param imageUrl url de la imagen del usuario
     * @param age edad de la persona
     */
    public Person(String name, String email, String imageUrl, String age){
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.age = age;
    }

    /**
     * Contructor que sirve para hacer una persona solo con el nombre
     * @param name Nombre de la persona
     */
    public Person(String name){
        this.name = name;
        this.email = "";
        this.imageUrl = "";
        this.age = "-1";
    }

    /**
     * Convierte el objeto en un Map para subirlo a la base de datos
     */
    public HashMap<String,Object> toHashMap(){
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put(NAME,name);
        returnValue.put(EMAIL,email);
        returnValue.put(IMAGE_PATH,imageUrl);
        returnValue.put(AGE,age);
        return returnValue;
    }

    /**
     * Convierte un map en un objeto del tipo persona funciona para la base de datos
     * @param hashMap El map que se convertira
     * @return Regresa la instancia de la persona
     */
    public static Person fromhMap(Map<String,Object> hashMap){
        String name = (String) hashMap.get(NAME);
        String email = (String) hashMap.get(EMAIL);
        String imagePath = (String) hashMap.get(IMAGE_PATH);
        String age = (String) hashMap.get(AGE);

        return new Person(name,email,imagePath,age);
    }
}
