package com.example.citeapp;

import java.util.HashMap;
import java.util.Map;

public class Message {
    //constantes
    public static final String AUTOR = "autor";
    public static final String MESSAGE = "message";


    String autor;
    String message;

    public Message(String autor, String message){
        this.autor = autor;
        this.message = message;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> returnValue = new HashMap<>();

        returnValue.put(AUTOR,this.autor);
        returnValue.put(MESSAGE,this.message);

        return returnValue;
    }

    public static Message fromMap(Map<String, Object> map){
        String autor = (String) map.get("autor");
        String message = (String) map.get("message");
        return new Message(autor,message);
    }
}
