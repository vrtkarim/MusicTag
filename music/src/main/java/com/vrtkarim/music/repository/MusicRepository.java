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
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MusicRepository {
    public byte[] getMusic(String musicNamepath) throws IOException {
        return Files.readAllBytes(Paths.get(musicNamepath));
    }

    public Data getMusicData(String pathname) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        File music = new File(pathname);
        AudioFile audioFile = AudioFileIO.read(music);
        Tag tag = audioFile.getTag();

        Data data = new Data();
        return Data.builder().title(tag.getFirst(FieldKey.TITLE)).artist(tag.getFirst(FieldKey.ARTIST))
                .album(tag.getFirst(FieldKey.ALBUM)).year(tag.getFirst(FieldKey.YEAR)).genre(tag.getFirst(FieldKey.GENRE))
                .comment(tag.getFirst(FieldKey.COMMENT)).composer(tag.getFirst(FieldKey.COMPOSER)).build();
    }
    public byte[] getArtWork(String pathname) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        File music = new File(pathname);
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
    public void setLyrics(String lyrics,String pathname){
        File music = new File(pathname);
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
    public String getLyrics(String pathname){
        File music = new File(pathname);
        try {
            AudioFile audioFile = AudioFileIO.read(music);
            Tag tag = audioFile.getTag();
            return tag.getFirst(FieldKey.LYRICS);

        }catch (Exception e){
            throw new FileError(e.getMessage());
        }
    }

    public void setData(Data data, String pathname) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, CannotWriteException {
        File music = new File(pathname);
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
    public void setArtWork(byte[] imageBytes, String musicpathname, String imagePathName) {
        File music = new File(musicpathname);
        try(FileOutputStream os = new FileOutputStream(imagePathName)) {
            os.write(imageBytes);

            AudioFile audioFile = AudioFileIO.read(music);
            Tag tag = audioFile.getTag();
            tag.deleteArtworkField();
            Artwork artwork = Artwork.createArtworkFromFile(new File(imagePathName));
            tag.setField(artwork);
            audioFile.commit();
        }catch (Exception e){
            throw new FileError(e.getMessage());
        }

    }

}
