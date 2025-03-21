package com.vrtkarim.music.controller;

import com.vrtkarim.music.entities.Data;
import com.vrtkarim.music.exceptions.ChangesFailed;
import com.vrtkarim.music.exceptions.FileError;
import com.vrtkarim.music.exceptions.UploadFailed;
import com.vrtkarim.music.service.MusicService;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;



@RestController
@RequestMapping("/api/music")
public class MusicController  {
    final MusicService musicService;
    @Autowired

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }


    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file") MultipartFile file) {
        String name = file.getOriginalFilename();
        assert name != null;
        if (!(name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".flac") ||
                name.endsWith(".ogg") || name.endsWith(".aac") || name.endsWith(".m4a") ||
                name.endsWith(".wma") || name.endsWith(".aiff"))) {

            throw new UploadFailed("Invalid file format");
        }
        String fileUploadStatus;


        try {
            musicService.setMusic(file.getBytes(), file.getOriginalFilename());
            fileUploadStatus = "File Uploaded Successfully";

        }catch (Exception e) {
            throw new FileError(e.getMessage());
        }
        return fileUploadStatus;
    }
    @PostMapping("/setlyrics")
    public ResponseEntity<String> setLyrics(@RequestParam(value = "text") String text) {
        System.out.println(text);
        musicService.setLyrics(text);
        return new ResponseEntity<>("Lyrics set successfully", HttpStatus.OK);
    }
    @GetMapping("/getlyrics")
    public ResponseEntity<String> getLyrics() {
        String lyrics = musicService.getLyrics();
        if (lyrics.isEmpty()){
            return new ResponseEntity<>("No Lyrics found", HttpStatus.OK);
        }
        return new ResponseEntity<>(lyrics, HttpStatus.OK);
    }

    @GetMapping("/getdata")
    public ResponseEntity<Data>getData(){
        return new ResponseEntity<>(musicService.getData(), HttpStatus.FOUND);

    }
    @GetMapping("getartwork")
    public ResponseEntity<?> getartwork(){
        HttpHeaders headers = new HttpHeaders();
        String contentType = "image/png";
        String headerValue = "attachment; filename=\"" + "artwork.png" + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(musicService.getImage());
    }
    @PostMapping("/setdata")
    public ResponseEntity<String> setData(@RequestBody Data data) {
            musicService.setData(data);
            return new ResponseEntity<>("Changes saved successfully", HttpStatus.OK);

    }
    @PostMapping("/setartwork")
    public  ResponseEntity<?>  setArtWork(@RequestParam("file") MultipartFile file) {
        try{

            musicService.setArtwork(file.getBytes(), file.getOriginalFilename());

        }catch (Exception e){
            throw new ChangesFailed(e.getMessage());
        }

        return new ResponseEntity<>("Artwork added successfully", HttpStatus.OK);

    }
    @GetMapping("/downloadmusic")
    public ResponseEntity<?> downloadMusic() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String fileName = musicService.getFileName();
        String contentType = "music/music";
        String headerValue = "attachment; filename=\"" + "ModifiedMusic"+ fileName + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(musicService.getMusic());
    }






}
