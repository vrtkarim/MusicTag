package com.vrtkarim.music.controller;

import com.vrtkarim.music.exceptions.ChangesFailed;
import com.vrtkarim.music.exceptions.FileError;
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
import java.util.Date;
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
        String name = file.getOriginalFilename();
        assert name != null;
        if (!(name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".flac") ||
                name.endsWith(".ogg") || name.endsWith(".aac") || name.endsWith(".m4a") ||
                name.endsWith(".wma") || name.endsWith(".aiff"))) {

            throw new UploadFailed("Invalid file format");
        }
        String fileUploadStatus;
        Map<String, String> map = musicService.setNameExtension(file.getOriginalFilename());

        try {


            long millis = System.currentTimeMillis() % 1000;

            File temp =  File.createTempFile(Long.toString(millis), map.get("extension"));
            System.out.println(temp.getPath());
            FileOutputStream fout = new FileOutputStream(temp);
            fout.write(file.getBytes());
            fout.close();
            fileUploadStatus = "File Uploaded Successfully";

        }

        // Catch block to handle exceptions
        catch (Exception e) {
            throw new FileError(e.getMessage());
        }
        return fileUploadStatus;
    }
    @PostMapping("/getdata")
    public ResponseEntity<Map<String, String> >getData(){
        Map<String, String> map = musicService.getNameExtension();
        File file = new File("temp."+map.get("extension"));
        return new ResponseEntity<>(musicService.getData(file), HttpStatus.FOUND);

    }
    @GetMapping("getartwork")
    public ResponseEntity<?> getartwork(){
        HttpHeaders headers = new HttpHeaders();
        String contentType = "image/png";
        String headerValue = "attachment; filename=\"" + "artwork.png" + "\"";
        Map<String, String> map = musicService.getNameExtension();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(musicService.getImage(new File("temp."+map.get("extension"))));
    }
    @PostMapping("/setdata")
    public ResponseEntity<String> setData(@RequestBody Map<String, String> data) {
        Map<String, String> map = musicService.getNameExtension();
        System.out.println(map.get("extension"));
            musicService.setData(new File("temp."+map.get("extension")), data);
            return new ResponseEntity<>("Changes saved successfully", HttpStatus.OK);

    }
    @PostMapping("/setartwork")
    public  ResponseEntity<?>  setArtWork(@RequestParam("file") MultipartFile file) {
        Map<String, String> map = musicService.getNameExtension();
        File music = new File("temp."+map.get("extension"));
        File img = new File("image.png");
        try(FileOutputStream fs = new FileOutputStream(img)){
                fs.write(file.getBytes());
            musicService.setImage(music, img);
        }catch (Exception e){
            throw new ChangesFailed(e.getMessage());
        }

        return new ResponseEntity<>("Artwork added successfully", HttpStatus.OK);

    }
    @GetMapping("/downloadmusic")
    public ResponseEntity<?> downloadMusic() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map = musicService.getNameExtension();
        String contentType = "music/"+map.get("extension");
        String headerValue = "attachment; filename=\"" + "music."+map.get("extension") + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(Files.readAllBytes(Path.of("temp."+map.get("extension"))));
    }


}
