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
    Data getData();
    byte[] getImage();
    void setData(Data data);
    void setArtwork(byte[] artwork, String name);

    void setMusic (byte[] bytes, String name);
    byte[] getMusic();
    String getFileName();
    void setLyrics(String lyrics);
    String getLyrics();




}
