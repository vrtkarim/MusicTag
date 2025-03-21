package com.vrtkarim.music.service;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface MusicService {
    Map<String, String> getData();
    byte[] getImage();
    void setData(Map<String, String> data);
    void setArtwork(byte[] artwork, String name);

    void setMusic (byte[] bytes, String name);
    byte[] getMusic();
    String getExtension();




}
