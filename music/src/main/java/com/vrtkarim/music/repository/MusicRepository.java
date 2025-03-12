package com.vrtkarim.music.repository;


import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
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

        // Get the tag (metadata)
        Tag tag = audioFile.getTag();

        // Get the artwork
        Artwork artwork = tag.getFirstArtwork();

        if (artwork != null) {
            // Get the binary data of the image
            byte[] imageData = artwork.getBinaryData();
            return  imageData;
            // You can now process the BufferedImage as needed
        } else {
            System.out.println("No artwork found in the file.");
            return null;
        }

    }

    private static void saveImageToFile(byte[] imageData, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(imageData);
            System.out.println("Image saved to " + fileName);
        }
    }

    // Convert byte array to BufferedImage
    private static BufferedImage getImageFromBytes(byte[] imageData) throws IOException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageData)) {
            return ImageIO.read(bis);
        }
    }

}
