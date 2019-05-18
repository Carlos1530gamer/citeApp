package com.example.citeapp;


import java.util.HashMap;
import java.util.Map;

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

    //es util para cuando tengas el chat la sigiente variable
    String chatId = null;


    public Person(String name, String email, String imageUrl, String age){
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.age = age;
    }

    public Person(String name){
        this.name = name;
        this.email = "";
        this.imageUrl = "";
        this.age = "-1";
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put(NAME,name);
        returnValue.put(EMAIL,email);
        returnValue.put(IMAGE_PATH,imageUrl);
        returnValue.put(AGE,age);
        return returnValue;
    }

    public static Person fromhMap(Map<String,Object> hashMap){
        String name = (String) hashMap.get(NAME);
        String email = (String) hashMap.get(EMAIL);
        String imagePath = (String) hashMap.get(IMAGE_PATH);
        String age = (String) hashMap.get(AGE);

        return new Person(name,email,imagePath,age);
    }
}
