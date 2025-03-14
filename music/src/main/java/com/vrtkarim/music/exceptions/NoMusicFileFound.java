package com.vrtkarim.music.exceptions;

public class NoMusicFileFound extends RuntimeException{
    public NoMusicFileFound(String message){
        super(message);
    }
}
