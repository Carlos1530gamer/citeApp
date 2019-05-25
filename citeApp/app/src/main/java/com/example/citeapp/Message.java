package com.example.citeapp;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

/**
 * Este es el modelo de datos de un mensaje
 * @author Carlos Daniel Hernandez Chauteco
 */
public class Message {
    //constantes para la base de datos
    public static final String AUTOR = "autor";
    public static final String MESSAGE = "message";
    public static final String DATE = "date";


    String autor;
    String message;
    Timestamp date;

    /**
     * @param autor Autor del mensaje
     * @param message Contenido del mensaje
     * @param date Fecha del mensaje
     */
    public Message(String autor, String message, Timestamp date){
        this.autor = autor;
        this.message = message;
        this.date = date;
    }

    /**
     * Este metodo convierte la instancia en un mapa este funciona por que de esta manera lo enviamos a la 
     * base de datos
     * @return El mapa del objeto
     */
    public Map<String,Object> toMap(){
        Map<String,Object> returnValue = new HashMap<>();

        returnValue.put(AUTOR,this.autor);
        returnValue.put(MESSAGE,this.message);
        returnValue.put(DATE,this.date);

        return returnValue;
    }

    /**
     * Convierte un Map en una instancia del objeto mensaje este por eso es estatico este metodo
     * al final esto sirve por que la base de datos regresas mapas
     * @param map el mapa que se convertira en una instancia del tipo Mensaje
     * @return La instacia del mensaje como objeto
     */
    public static Message fromMap(Map<String, Object> map){
        String autor = (String) map.get(AUTOR);
        String message = (String) map.get(MESSAGE);
        Timestamp date = (Timestamp) map.get(DATE);
        return new Message(autor,message,date);
    }
}
