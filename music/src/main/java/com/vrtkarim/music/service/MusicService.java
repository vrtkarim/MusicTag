package com.vrtkarim.music.service;

import com.vrtkarim.music.entities.Data;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface MusicService {
    Data getData(String musicPathName);
    byte[] getImage(String musicPathName);
    void setData(Data data,String musicPathName);
    void setArtwork(byte[] artwork, String musicPathName, String imagePathName);

    byte[] getMusic(String musicPathName);

    void setLyrics(String lyrics, String musicPathName);
    String getLyrics(String musicPathName);




}
