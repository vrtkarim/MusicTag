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


    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }


    @PostMapping("/upload/{musicNamePath}")
    public String upload(@RequestParam(value = "file") MultipartFile file, @RequestParam String musicNamePath){
        String name = file.getOriginalFilename();
        assert name != null;
        if (!(name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".flac") ||
                name.endsWith(".ogg") || name.endsWith(".aac") || name.endsWith(".m4a") ||
                name.endsWith(".wma") || name.endsWith(".aiff"))) {

            throw new UploadFailed("Invalid file format");
        }
        String fileUploadStatus;


        try(FileOutputStream fos = new FileOutputStream(musicNamePath)) {
            fos.write(file.getBytes());
            fileUploadStatus = "File Uploaded Successfully";

        }catch (Exception e) {
            throw new FileError(e.getMessage());
        }
        return fileUploadStatus;
    }
    @PostMapping("/setlyrics/{musicNamePath}")
    public ResponseEntity<String> setLyrics(@RequestParam(value = "text") String text, @RequestParam String musicNamePath) {
        System.out.println(text);
        musicService.setLyrics(text, musicNamePath);
        return new ResponseEntity<>("Lyrics set successfully", HttpStatus.OK);
    }
    @GetMapping("/getlyrics/{musicNamePath}")
    public ResponseEntity<String> getLyrics(@RequestParam  String musicNamePath) {
        String lyrics = musicService.getLyrics(musicNamePath);
        if (lyrics.isEmpty()){
            return new ResponseEntity<>("No Lyrics found", HttpStatus.OK);
        }
        return new ResponseEntity<>(lyrics, HttpStatus.OK);
    }

    @GetMapping("/getdata/{musicNamePath}")
    public ResponseEntity<Data>getData(@RequestParam  String musicNamePath){
        return new ResponseEntity<>(musicService.getData(musicNamePath), HttpStatus.FOUND);

    }
    @GetMapping("getartwork/{musicNamePath}")
    public ResponseEntity<?> getartwork(@RequestParam String musicNamePath){
        HttpHeaders headers = new HttpHeaders();
        String contentType = "image/png";
        String headerValue = "attachment; filename=\"" + "artwork.png" + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(musicService.getImage(musicNamePath));
    }
    @PostMapping("/setdata/{musicNamePath}")
    public ResponseEntity<String> setData(@RequestBody Data data, @RequestParam String musicNamePath) {
            musicService.setData(data, musicNamePath);
            return new ResponseEntity<>("Changes saved successfully", HttpStatus.OK);

    }
    @PostMapping("/setartwork/{musicNamePath}/{imageNamePath}")
    public  ResponseEntity<?>  setArtWork(@RequestParam("file") MultipartFile file, @RequestParam String musicNamePath, @RequestParam String imageNamePath) {
        try{

            musicService.setArtwork(file.getBytes(), musicNamePath, imageNamePath);

        }catch (Exception e){
            throw new ChangesFailed(e.getMessage());
        }

        return new ResponseEntity<>("Artwork added successfully", HttpStatus.OK);

    }
    @GetMapping("/downloadmusic//{musicNamePath}")
    public ResponseEntity<?> downloadMusic(@RequestParam String musicNamePath) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        String contentType = "music/music";
        String headerValue = "attachment; filename=\"" + "ModifiedMusic"+ musicNamePath + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(musicService.getMusic(musicNamePath));
    }






}
