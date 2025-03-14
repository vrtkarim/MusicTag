package com.vrtkarim.music.repository;


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
import java.util.HashMap;
import java.util.Map;

@Repository
public class MusicRepository {
    public void tryToRead(File file){
        // Try to read the file as an audio file
        try {
            AudioFile audioFile = AudioFileIO.read(file);
        }catch (Exception e){
            throw new UploadFailed("Uploaded file is not a supported music file");
        }
    }
    public Map<String, String> getMusicData(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        Map<String, String> map = new HashMap<>();
        AudioFile audioFile = AudioFileIO.read(file);
        Tag tag = audioFile.getTag();
        map.put("Title: " ,tag.getFirst(FieldKey.TITLE));
        map.put("Artist: " , tag.getFirst(FieldKey.ARTIST));
        map.put("Album: " , tag.getFirst(FieldKey.ALBUM));
        map.put("Year: " , tag.getFirst(FieldKey.YEAR));
        map.put("Genre: " , tag.getFirst(FieldKey.GENRE));
        map.put("Track: " , tag.getFirst(FieldKey.TRACK));
        map.put("Comment: " , tag.getFirst(FieldKey.COMMENT));
        map.put("Composer: " , tag.getFirst(FieldKey.COMPOSER));
        return map;
    }
    public byte[] getArtWork(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        AudioFile audioFile = AudioFileIO.read(file);
        Tag tag = audioFile.getTag();
        Artwork artwork = tag.getFirstArtwork();

        if (artwork != null) {
            byte[] imageData = artwork.getBinaryData();
            return  imageData;
            // You can now process the BufferedImage as needed
        } else {
            System.out.println("No artwork found in the file.");
            return null;
        }

    }

    public void setData(File file, String title, String artist, String album, String year, String genre, String track, String comment, String composer) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, CannotWriteException {
        AudioFile audioFile = AudioFileIO.read(file);
        Tag tag = audioFile.getTag();
        tag.setField(FieldKey.TITLE, title);
        tag.setField(FieldKey.COMMENT, comment);
        tag.setField(FieldKey.COMPOSER, composer);
        tag.setField(FieldKey.ARTIST, artist);
        tag.setField(FieldKey.ALBUM, album);
        tag.setField(FieldKey.YEAR, year);
        tag.setField(FieldKey.GENRE, genre);
        audioFile.commit();
    }
    public void setArtWork(File image, File music) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        AudioFile audioFile = AudioFileIO.read(music);
        Tag tag = audioFile.getTag();

        // Creating artwork from file
        Artwork artwork = Artwork.createArtworkFromFile(image );

    }

}
