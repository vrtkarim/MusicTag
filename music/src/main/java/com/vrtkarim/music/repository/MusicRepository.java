package com.vrtkarim.music.repository;


import com.vrtkarim.music.entities.Data;
import com.vrtkarim.music.exceptions.FileError;
import com.vrtkarim.music.exceptions.UploadFailed;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MusicRepository {
    File music;
    File image;
    public String getFileName(){

        try {
            return music.getName();
        }catch (
                Exception e
        ){
            throw new FileError(e.getMessage());
        }
    }
    public byte[] getMusic() {

        try {
           return Files.readAllBytes(Path.of(music.getPath()));
        }catch (
                Exception e
        ){
            throw new FileError(e.getMessage());
        }
    }
    public void setMusic(byte[] bytes, String name) {
        long millis = System.currentTimeMillis() % 1000;
        try {
            music =  File.createTempFile(Long.toString(millis), name);
            FileOutputStream fout = new FileOutputStream(music);
            fout.write(bytes);
            fout.close();

        }catch (Exception e){
            throw new FileError(e.getMessage());
        }
    }
    public void setImage(byte[] bytes, String name) throws CannotReadException {
        if (music == null || image ==null) {
            throw  new CannotReadException("Music file not found");

        }
        try {
            image = File.createTempFile("image", name);
            FileOutputStream fout = new FileOutputStream(image);
            fout.write(bytes);
            fout.close();
        }catch (Exception e){
            throw new FileError(e.getMessage());
        }

    }



    public Data getMusicData() throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {

        if (music == null) {
            throw  new CannotReadException("Music file not found");

        }
        AudioFile audioFile = AudioFileIO.read(music);
        Tag tag = audioFile.getTag();

        Data data = new Data();
        return Data.builder().title(tag.getFirst(FieldKey.TITLE)).artist(tag.getFirst(FieldKey.ARTIST))
                .album(tag.getFirst(FieldKey.ALBUM)).year(tag.getFirst(FieldKey.YEAR)).genre(tag.getFirst(FieldKey.GENRE))
                .comment(tag.getFirst(FieldKey.COMMENT)).composer(tag.getFirst(FieldKey.COMPOSER)).build();
    }
    public byte[] getArtWork() throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        AudioFile audioFile = AudioFileIO.read(music);
        Tag tag = audioFile.getTag();
        Artwork artwork = tag.getFirstArtwork();


        if (artwork != null) {
            return artwork.getBinaryData();
        } else {
            System.out.println("No artwork found in the file.");
            return null;
        }

    }
    public void setLyrics(String lyrics){
        try {
            AudioFile audioFile = AudioFileIO.read(music);
            Tag tag = audioFile.getTag();
            tag.deleteField(FieldKey.LYRICS);
            tag.setField(FieldKey.LYRICS, lyrics);
            audioFile.setTag(tag);
            audioFile.commit();
        }catch (Exception e){
            throw new FileError(e.getMessage());
        }

    }
    public String getLyrics(){
        try {
            AudioFile audioFile = AudioFileIO.read(music);
            Tag tag = audioFile.getTag();
            return tag.getFirst(FieldKey.LYRICS);

        }catch (Exception e){
            throw new FileError(e.getMessage());
        }
    }

    public void setData(Data data) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, CannotWriteException {
        AudioFile audioFile = AudioFileIO.read(music);
        Tag tag = audioFile.getTag();
        tag.setField(FieldKey.TITLE, data.getTitle().isEmpty() ?tag.getFirst(FieldKey.TITLE):data.getTitle());
        tag.setField(FieldKey.COMMENT, data.getComment().isEmpty()?tag.getFirst(FieldKey.COMMENT):data.getComment());
        tag.setField(FieldKey.COMPOSER, data.getComposer().isEmpty()?tag.getFirst(FieldKey.COMPOSER):data.getComposer());
        tag.setField(FieldKey.ARTIST, data.getArtist().isEmpty()?tag.getFirst(FieldKey.ARTIST):data.getArtist());
        tag.setField(FieldKey.ALBUM, data.getAlbum().isEmpty()?tag.getFirst(FieldKey.ALBUM):data.getAlbum());
        tag.setField(FieldKey.YEAR, data.getYear().isEmpty()?tag.getFirst(FieldKey.YEAR):data.getYear());
        tag.setField(FieldKey.GENRE, data.getGenre().isEmpty()?tag.getFirst(FieldKey.GENRE):data.getGenre());
        audioFile.commit();
    }
    public void setArtWork() {
        try {
            AudioFile audioFile = AudioFileIO.read(music);
            Tag tag = audioFile.getTag();
            tag.deleteArtworkField();
            Artwork artwork = Artwork.createArtworkFromFile(image);
            tag.setField(artwork);
            audioFile.commit();
        }catch (Exception e){
            throw new FileError(e.getMessage());
        }

    }

}
