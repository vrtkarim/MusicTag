package com.vrtkarim.music.service;

import com.vrtkarim.music.repository.MusicRepository;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ServiceImplementation implements MusicService{
    final MusicRepository musicRepository;

    public ServiceImplementation(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public Map<String, String> getData(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        return musicRepository.getMusicData(file);
    }

    @Override
    public byte[] getImage(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        return musicRepository.getArtWork(file);
    }
}
