package com.vrtkarim.music.service;

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
    public Map<String, String> getData(File file){

        try {
            return musicRepository.getMusicData(file);
        }catch (Exception e){
            throw new FileError(e.getMessage()) ;
        }
    }

    @Override
    public byte[] getImage(File file){
        try {
            return musicRepository.getArtWork(file);
        }catch (Exception e){
            throw new FileError(e.getMessage()) ;
        }
    }

    @Override
    public void setData(File file, Map<String, String> data){
        try {
            musicRepository.setData(
                    file,
                    data.get("title"),
                    data.get("artist"),
                    data.get("album"),
                    data.get("year"),
                    data.get("genre"),
                    data.get("track"),
                    data.get("comment"),
                    data.get("composer")
            );
        }catch (Exception e){
            throw new UploadFailed(e.getMessage());
        }
    }

    @Override
    public void setImage(File image, File music){
        try{
            musicRepository.setArtWork(image, music);
        }catch (Exception e){
            throw new UploadFailed(e.getMessage());
        }

    }

    @Override
    public void tryToRead(File file) {
        musicRepository.tryToRead(file);
    }



    @Override
    public Map<String, String> setNameExtension(String name){
        String extension = name.substring(name.lastIndexOf(".") + 1);
        return musicRepository.setNameExtension(name, extension);

    }

    @Override
    public Map<String, String> getNameExtension() {
        return musicRepository.getMap();
    }

    @Override
    public void setTempFile(byte[] data) {
        try {
            musicRepository.createTempFile(data);
        }catch (Exception e){
            throw new FileError(e.getMessage()) ;
        }
    }


}
