package com.vrtkarim.music.controller;

import com.vrtkarim.music.exceptions.ChangesFailed;
import com.vrtkarim.music.exceptions.UploadFailed;
import com.vrtkarim.music.service.MusicService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import org.jaudiotagger.tag.TagException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    final MusicService musicService;
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {

        String fileUploadStatus;
        File temp = new File("temp.mp3");

        // Try block to check exceptions
        try {
            FileOutputStream fout = new FileOutputStream(temp);
            fout.write(file.getBytes());
            fout.close();
            fileUploadStatus = "File Uploaded Successfully";
            try {

            }catch (Exception e) {
                throw new UploadFailed("file is not a valid audio file");
            }

        }

        // Catch block to handle exceptions
        catch (Exception e) {
            throw new UploadFailed(e.getMessage());
        }
        return fileUploadStatus;
    }
    @PostMapping("/getdata")
    public ResponseEntity<Map<String, String> >getData(){
        File file = new File("temp.mp3");
        return new ResponseEntity<>(musicService.getData(file), HttpStatus.FOUND);

    }
    @GetMapping("getartwork")
    public ResponseEntity<?> getartwork(){


        HttpHeaders headers = new HttpHeaders();

        // Setting up values for contentType and headerValue
        String contentType = "image/png";
        String headerValue = "attachment; filename=\"" + "artwork.png" + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(musicService.getImage(new File("temp.mp3")));
    }
    @PostMapping("/setdata")
    public ResponseEntity<String> setData(@RequestBody Map<String, String> data) {
//        {
//            "title": "Song Title",
//                "comment": "This is a great song",
//                "composer": "John Doe",
//                "artist": "The Band Name",
//                "album": "Amazing Album",
//                "year": "2025",
//                "genre": "Rock"
            musicService.setData(new File("temp.mp3"), data);
            return new ResponseEntity<>("Changes saved successfully", HttpStatus.OK);

    }
    @PostMapping("/setartwork")
    public  ResponseEntity<?>  setArtWork(@RequestParam("file") MultipartFile file) {
        File temp = new File("temp.mp3");
        File img = new File("image.png");
        try(FileOutputStream fs = new FileOutputStream(img)){
                fs.write(file.getBytes());
            musicService.setImage(temp, img);
        }catch (Exception e){
            throw new ChangesFailed(e.getMessage());
        }

        return new ResponseEntity<>("Artwork added successfully", HttpStatus.OK);

    }
    @GetMapping("/downloadmusic")
    public ResponseEntity<?> downloadMusic() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        // Setting up values for contentType and headerValue
        String contentType = "image/png";
        String headerValue = "attachment; filename=\"" + "music.mp3" + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(Files.readAllBytes(Path.of("temp.mp3")));
    }


}
