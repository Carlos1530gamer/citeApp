package com.example.citeapp;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class Message {
    //constantes
    public static final String AUTOR = "autor";
    public static final String MESSAGE = "message";
    public static final String DATE = "date";


    String autor;
    String message;
    Timestamp date;

    public Message(String autor, String message, Timestamp date){
        this.autor = autor;
        this.message = message;
        this.date = date;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> returnValue = new HashMap<>();

        returnValue.put(AUTOR,this.autor);
        returnValue.put(MESSAGE,this.message);
        returnValue.put(DATE,this.date);

        return returnValue;
    }

    public static Message fromMap(Map<String, Object> map){
        String autor = (String) map.get(AUTOR);
        String message = (String) map.get(MESSAGE);
        Timestamp date = (Timestamp) map.get(DATE);
        return new Message(autor,message,date);
    }
}
