package com.vrtkarim.music.service;

import com.vrtkarim.music.entities.Data;
import com.vrtkarim.music.exceptions.FileError;
import com.vrtkarim.music.exceptions.UploadFailed;
import com.vrtkarim.music.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class ServiceImplementation implements MusicService{
    final MusicRepository musicRepository;

    public ServiceImplementation(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public Data getData(String musicNamePath){

        try {
            return musicRepository.getMusicData(musicNamePath);
        }catch (Exception e){
            throw new FileError(e.getMessage()) ;
        }
    }

    @Override
    public byte[] getImage(String musicNamePath){
        try {
            return musicRepository.getArtWork(musicNamePath);
        }catch (Exception e){
            throw new FileError(e.getMessage()) ;
        }
    }

    @Override
    public void setData(Data data, String musicPathName){
        try {
            musicRepository.setData(

                    data, musicPathName
            );
        }catch (Exception e){
            throw new UploadFailed(e.getMessage());
        }
    }

    @Override
    public void setArtwork(byte[] artwork, String imageNamePath, String musicNamePath){
        try{

            musicRepository.setArtWork(artwork, musicNamePath, imageNamePath);
        }catch (Exception e){
            throw new UploadFailed(e.getMessage());
        }

    }



    @Override
    public byte[] getMusic(String musicNamePath) {
        try {
            return musicRepository.getMusic(musicNamePath);
        }catch (Exception e){
            throw new UploadFailed(e.getMessage());

        }
    }



    @Override
    public void setLyrics(String lyrics, String musicNamePath) {
        musicRepository.setLyrics(lyrics, musicNamePath);
    }

    @Override
    public String getLyrics(String musicNamePath) {
        return musicRepository.getLyrics(musicNamePath);
    }


}
