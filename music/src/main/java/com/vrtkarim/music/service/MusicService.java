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
    Map<String, String> getData(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException;
    byte[] getImage(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException;
    void setData(File file, Map<String, String> data) throws CannotWriteException, TagException, ReadOnlyFileException, IOException, CannotReadException, InvalidAudioFrameException;
    void setImage(File music, File image) throws CannotWriteException, TagException, ReadOnlyFileException, IOException, CannotReadException, InvalidAudioFrameException;

}
