package com.icecoldbier.utils;

public class Logger {
    private static final boolean DEBUG = true;
    private String name;

    private Logger(String name){
        this.name = name;
    }

    private Logger(){}

    public static Logger getLogger(String name){
        return new Logger(name);
    }

    public void print(String message){
        if(DEBUG){
            System.out.println(this.name + ": " + message);
        }
    }
}
